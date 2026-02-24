package net.vansencool.benchmark;

import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

/**
 * Main entry point for running the LSYAML vs SnakeYAML benchmarks.
 * <p>
 * Usage:
 * <ul>
 *   <li>{@code java -jar benchmarks.jar} to run all benchmarks</li>
 *   <li>{@code java -jar benchmarks.jar HotBenchmark} to run only the hot (throughput) benchmarks</li>
 *   <li>{@code java -jar benchmarks.jar ColdBenchmark} to run only the cold (single shot) benchmarks</li>
 * </ul>
 */
public class BenchmarkMain {

    public static void main(String[] args) throws RunnerException {
        String filter = "net.vansencool.benchmark.*";
        if (args.length > 0) {
            filter = "net.vansencool.benchmark." + args[0];
        }

        Options options = new OptionsBuilder()
                .include(filter)
                .shouldDoGC(true)
                .build();

        new Runner(options).run();
    }
}
