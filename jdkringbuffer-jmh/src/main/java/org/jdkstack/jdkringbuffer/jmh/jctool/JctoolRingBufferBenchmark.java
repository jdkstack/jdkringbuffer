package org.jdkstack.jdkringbuffer.jmh.jctool;

import java.util.concurrent.TimeUnit;
import org.jctools.queues.MessagePassingQueue;
import org.jctools.queues.MpmcArrayQueue;
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
 * <p>java -jar target/benchmarks.jar ".*JctoolRingBufferBenchmark.*" -f 1 -i 10 -wi 20 -bm sample
 * -tu ns .
 *
 * @author admin
 */
@State(Scope.Benchmark)
@Warmup(iterations = 5, time = 1)
@Measurement(iterations = 5, time = 1)
public class JctoolRingBufferBenchmark {
  private final MessagePassingQueue<String> queue = new MpmcArrayQueue<>(1024);

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
            .include(JctoolRingBufferBenchmark.class.getSimpleName())
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
  public void setUp() {
    //
  }

  @TearDown(Level.Trial)
  public void down() {
    //
  }

  @Benchmark
  @BenchmarkMode(Mode.Throughput)
  @OutputTimeUnit(TimeUnit.SECONDS)
  public void throughputSimple() {
    queue.offer("123");
    String info = queue.poll();
    if (info != null) {
      //
    }
  }
}
