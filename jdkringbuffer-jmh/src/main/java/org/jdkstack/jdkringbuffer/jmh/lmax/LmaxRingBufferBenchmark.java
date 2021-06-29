package org.jdkstack.jdkringbuffer.jmh.lmax;

import com.lmax.disruptor.BusySpinWaitStrategy;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.lmax.disruptor.util.DaemonThreadFactory;
import org.jdkstack.jdkringbuffer.jmh.all.StudyJuliRuntimeException;
import org.jdkstack.jdkringbuffer.jmh.lmax.util.SimpleEventHandler;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Level;
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

/**
 * How run this?
 *
 * <p>java -jar target/benchmarks.jar ".*JdkLogBenchmark.*" -f 1 -i 10 -wi 20 -bm sample -tu ns .
 *
 * @author admin
 */
@SuppressWarnings("all")
@State(Scope.Benchmark)
@Warmup(iterations = 5, time = 1)
@Measurement(iterations = 5, time = 1)
public class LmaxRingBufferBenchmark {
  private static final int BIG_BUFFER = 1 << 22;
  private RingBuffer<String> ringBuffer;
  private Disruptor<String> disruptor;

  @Setup
  public void setup(final Blackhole bh) {
    disruptor =
        new Disruptor<>(
            String::new,
            BIG_BUFFER,
            DaemonThreadFactory.INSTANCE,
            ProducerType.MULTI,
            new BusySpinWaitStrategy());

    disruptor.handleEventsWith(new SimpleEventHandler(bh));

    ringBuffer = disruptor.start();
  }

  @Benchmark
  @Threads(4)
  public void producing() {
    long sequence = ringBuffer.next();
    ringBuffer.get(sequence);
    ringBuffer.publish(sequence);
  }

  @TearDown
  public void tearDown() {
    disruptor.shutdown();
  }

  /**
   * .
   *
   * @author admin
   * @param args args.
   */
  public static void main(String... args) {
    Options opt =
        new OptionsBuilder()
            .include(LmaxRingBufferBenchmark.class.getSimpleName())
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

  @Setup(Level.Trial)
  public void up() {
    //
  }

  @TearDown(Level.Trial)
  public void down() {
    //
  }
}
