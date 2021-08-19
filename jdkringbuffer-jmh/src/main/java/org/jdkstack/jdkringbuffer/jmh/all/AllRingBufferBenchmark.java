package org.jdkstack.jdkringbuffer.jmh.all;

import com.conversantmedia.util.concurrent.DisruptorBlockingQueue;
import com.conversantmedia.util.concurrent.MPMCBlockingQueue;
import com.conversantmedia.util.concurrent.SpinPolicy;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import org.jdkstack.jdkringbuffer.core.mpmc.version1.MpmcBlockingQueueV1;
import org.jdkstack.jdkringbuffer.core.mpmc.version2.MpmcBlockingQueueV2;
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
public class AllRingBufferBenchmark {
  private final BlockingQueue<String> queue1 = new MpmcBlockingQueueV1<>();
  private final BlockingQueue<String> queue2 = new ArrayBlockingQueue<>(1024);
  private final BlockingQueue<String> queue3 =
      new DisruptorBlockingQueue<>(1024, SpinPolicy.SPINNING);
  private final BlockingQueue<String> queue4 = new MpmcBlockingQueueV2<>(1024);
  private final BlockingQueue<String> queue5 = new MPMCBlockingQueue<>(1024, SpinPolicy.SPINNING);

  /**
   * This is a method description.
   *
   * <p>Another description after blank line.
   *
   * @author admin
   * @param args args.
   */
  public static void main(final String... args) {
    Options opt =
        new OptionsBuilder()
            .include(AllRingBufferBenchmark.class.getSimpleName())
            .threads(10)
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
  public void setup() {
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
  public void throughputSimple1() {
    try {
      queue1.put("123");
    } catch (InterruptedException ignored) {
      Thread.currentThread().interrupt();
    }
    try {
      String info = queue1.take();
      if (info != null) {
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
  public void throughputSimple2() {
    try {
      queue2.put("123");
    } catch (InterruptedException ignored) {
      Thread.currentThread().interrupt();
    }
    try {
      String info = queue2.take();
      if (info != null) {
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
  public void throughputSimple3() {
    try {
      queue3.put("123");
    } catch (InterruptedException ignored) {
      Thread.currentThread().interrupt();
    }
    try {
      String info = queue3.take();
      if (info != null) {
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
  public void throughputSimple4() {
    try {
      queue4.put("123");
    } catch (InterruptedException ignored) {
      Thread.currentThread().interrupt();
    }
    try {
      String info = queue4.take();
      if (info != null) {
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
      String info = queue5.take();
      if (info != null) {
        //
      }
    } catch (InterruptedException ignored) {
      Thread.currentThread().interrupt();
    }
  }
}
