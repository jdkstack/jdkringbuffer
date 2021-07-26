package org.jdkstack.jdkringbuffer.jmh.lmax;

import com.lmax.disruptor.BusySpinWaitStrategy;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.lmax.disruptor.util.DaemonThreadFactory;
import org.jdkstack.jdkringbuffer.jmh.all.StudyJuliRuntimeException;
import org.jdkstack.jdkringbuffer.jmh.lmax.util.SimpleEvent;
import org.jdkstack.jdkringbuffer.jmh.lmax.util.SimpleEventHandler;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.TearDown;
import org.openjdk.jmh.annotations.Threads;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

@State(Scope.Benchmark)
@Warmup(iterations = 5, time = 1)
@Measurement(iterations = 5, time = 1)
public class LmaxRingBufferMpscBenchmark {
  private RingBuffer<SimpleEvent> ringBuffer;
  private Disruptor<SimpleEvent> disruptor;
  private static final int BIG_BUFFER = 1 << 22;

  @Setup
  public void setup(final Blackhole bh) {
    disruptor =
        new Disruptor<>(
            SimpleEvent::new,
            BIG_BUFFER,
            DaemonThreadFactory.INSTANCE,
            ProducerType.MULTI,
            new BusySpinWaitStrategy());

    disruptor.handleEventsWith(new SimpleEventHandler(bh));

    ringBuffer = disruptor.start();
  }

  @Benchmark
  @Threads(10)
  public void producing() {
    long sequence = ringBuffer.next();
    SimpleEvent simpleEvent = ringBuffer.get(sequence);
    simpleEvent.setValue(0);
    ringBuffer.publish(sequence);
  }

  @TearDown
  public void tearDown() {
    disruptor.shutdown();
  }

  public static void main(final String... args) throws RunnerException {
    Options opt =
        new OptionsBuilder()
            .include(LmaxRingBufferMpscBenchmark.class.getSimpleName())
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
