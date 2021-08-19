package org.jdkstack.jdkringbuffer.jmh.jctool;

import java.util.Queue;
import org.jdkstack.jdkringbuffer.jmh.all.StudyJuliRuntimeException;
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

/**
 * This is a class description.
 *
 * <p>Another description after blank line.
 *
 * @author admin
 */
@SuppressWarnings("all")
@State(Scope.Benchmark)
@Warmup(iterations = 5, time = 1)
@Measurement(iterations = 5, time = 1)
public class QueueThroughputBackoffNone {
  private static final long DELAY_PRODUCER = Long.getLong("delay.p", 0L);
  private static final long DELAY_CONSUMER = Long.getLong("delay.c", 0L);
  private static final Object TEST_ELEMENT = 1;
  private Queue<Integer> q;

  @Param(value = {"MpmcArrayQueue"})
  private String qType;

  @Param(value = {"1024"})
  private String qCapacity;

  @Setup()
  public void createQandPrimeCompilation() {
    this.q = QueueByTypeFactory.buildQ(qType, this.qCapacity);
  }

  @AuxCounters
  @State(Scope.Thread)
  public static class PollCounters {
    private long pollsFailed;
    private long pollsMade;
  }

  @AuxCounters
  @State(Scope.Thread)
  public static class OfferCounters {
    private long offersFailed;
    private long offersMade;
  }

  @Benchmark
  @Group("tpt")
  public void offer(OfferCounters counters) {
    if (!q.offer(1)) {
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
    }
    if (e == TEST_ELEMENT) {
      counters.pollsMade++;
    }
    if (DELAY_CONSUMER != 0) {
      Blackhole.consumeCPU(DELAY_CONSUMER);
    }
  }

  public static void main(String... args) {
    Options opt =
        new OptionsBuilder()
            .include(QueueThroughputBackoffNone.class.getSimpleName())
            .threads(1)
            .forks(1)
            .build();
    try {
      new Runner(opt).run();
    } catch (RunnerException e) {
      // Conversion into unchecked exception is also allowed.
      throw new StudyJuliRuntimeException(e);
    }
  }
}
