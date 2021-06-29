/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jdkstack.jdkringbuffer.jmh.jctool;

import java.util.Queue;
import org.openjdk.jmh.annotations.AuxCounters;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Group;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

@State(Scope.Benchmark)
@Warmup(iterations = 5, time = 1)
@Measurement(iterations = 5, time = 1)
public class QueueThroughputBackoffNone {
  static final long DELAY_PRODUCER = Long.getLong("delay.p", 0L);
  static final long DELAY_CONSUMER = Long.getLong("delay.c", 0L);
  static final Object TEST_ELEMENT = 1;
  Integer element = 1;
  Integer escape;
  Queue<Integer> q;

  @Param(value = {"MpmcArrayQueue"})
  String qType;

  @Param(value = {"1024"})
  String qCapacity;

  @Setup()
  public void createQandPrimeCompilation() {
    final String qType = this.qType;

    q = QueueByTypeFactory.createQueue(qType, 128);
    // stretch the queue to the limit, working through resizing and full
    for (int i = 0; i < 128 + 100; i++) {
      q.offer(element);
    }
    for (int i = 0; i < 128 + 100; i++) {
      q.poll();
    }
    // make sure the important common case is exercised
    for (int i = 0; i < 20000; i++) {
      q.offer(element);
      q.poll();
    }
    final String qCapacity = this.qCapacity;

    this.q = QueueByTypeFactory.buildQ(qType, qCapacity);
  }

  @AuxCounters
  @State(Scope.Thread)
  public static class PollCounters {
    public long pollsFailed;
    public long pollsMade;
  }

  @AuxCounters
  @State(Scope.Thread)
  public static class OfferCounters {
    public long offersFailed;
    public long offersMade;
  }

  @Benchmark
  @Group("tpt")
  public void offer(OfferCounters counters) {
    if (!q.offer(element)) {
      counters.offersFailed++;
      backoff();
    } else {
      counters.offersMade++;
    }
    if (DELAY_PRODUCER != 0) {
      Blackhole.consumeCPU(DELAY_PRODUCER);
    }
  }

  protected void backoff() {
    //
  }

  @Benchmark
  @Group("tpt")
  public void poll(PollCounters counters) {
    Integer e = q.poll();
    if (e == null) {
      counters.pollsFailed++;
      backoff();
    } else if (e == TEST_ELEMENT) {
      counters.pollsMade++;
    } else {
      escape = e;
    }
    if (DELAY_CONSUMER != 0) {
      Blackhole.consumeCPU(DELAY_CONSUMER);
    }
  }

  public static void main(String... args) {
    Options opt =
        new OptionsBuilder()
            .include(QueueThroughputBackoffNone.class.getSimpleName())
            .threads(3)
            .forks(1)
            .build();
    try {
      new Runner(opt).run();
    } catch (RunnerException e) {
      // Conversion into unchecked exception is also allowed.
      throw new RuntimeException(e);
    }
  }
}
