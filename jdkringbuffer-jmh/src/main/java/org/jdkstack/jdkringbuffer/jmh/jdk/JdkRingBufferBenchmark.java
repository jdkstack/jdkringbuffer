package org.jdkstack.jdkringbuffer.jmh.jdk;

import java.util.concurrent.TimeUnit;
import org.jdkstack.jdkringbuffer.core.JdkRingBufferBlockingQueue;
import org.jdkstack.jdkringbuffer.core.JdkRingBufferBlockingQueueV2;
import org.jdkstack.jdkringbuffer.jmh.all.StudyJuliRuntimeException;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.TearDown;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

/**
 * How run this?
 *
 * <p>java -jar target/benchmarks.jar ".*JdkRingBufferBenchmark.*" -f 1 -i 10 -wi 20 -bm sample -tu
 * ns .
 *
 * @author admin
 */
@State(Scope.Benchmark)
@Warmup(iterations = 5, time = 1)
@Measurement(iterations = 5, time = 1)
public class JdkRingBufferBenchmark {
  private final JdkRingBufferBlockingQueue<String> queue = new JdkRingBufferBlockingQueue<>();
  private final JdkRingBufferBlockingQueueV2<String> queue5 =
      new JdkRingBufferBlockingQueueV2<>(1024);

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @author admin
   * @param args args.
   */
  public static void main(String... args) {
    Options opt =
        new OptionsBuilder()
            .include(JdkRingBufferBenchmark.class.getSimpleName())
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

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @author admin
   */
  @Benchmark
  @BenchmarkMode(Mode.Throughput)
  @OutputTimeUnit(TimeUnit.SECONDS)
  public void throughputSimple() {
    try {
      queue.put("123");
    } catch (InterruptedException ignored) {
      Thread.currentThread().interrupt();
    }
    try {
      String kafkaInfoEvent = queue.take();
      if (kafkaInfoEvent != null) {
        //
      }
    } catch (InterruptedException ignored) {
      Thread.currentThread().interrupt();
    }
  }

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @author admin
   */
  @Benchmark
  @BenchmarkMode(Mode.Throughput)
  @OutputTimeUnit(TimeUnit.SECONDS)
  public void throughputSimple5() {
    try {
      queue5.put("123");
    } catch (InterruptedException ignored) {
      Thread.currentThread().interrupt();
    }
    try {
      String kafkaInfoEvent = queue5.take();
      if (kafkaInfoEvent != null) {
        //
      }
    } catch (InterruptedException ignored) {
      Thread.currentThread().interrupt();
    }
  }
}
