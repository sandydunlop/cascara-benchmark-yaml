package net.vansencool.benchmark.runner;

import net.vansencool.benchmark.data.YamlData;
import net.vansencool.lsyaml.LSYAML;
import net.vansencool.lsyaml.parser.ParseOptions;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.SafeConstructor;

import java.util.concurrent.TimeUnit;

/**
 * Hot benchmark measuring sustained throughput after full JIT warmup.
 * This represents the best case performance once the JVM has had time
 * to optimize all code paths thoroughly.
 */
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Thread)
@Warmup(iterations = 3, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 3, time = 2, timeUnit = TimeUnit.SECONDS)
@Fork(1)
@SuppressWarnings("unused")
public class HotBenchmark {

    private Yaml snakeYaml;

    @Setup(Level.Trial)
    public void setup() {
        LoaderOptions options = new LoaderOptions();
        options.setAllowDuplicateKeys(false);
        options.setProcessComments(true);
        options.setMaxAliasesForCollections(Integer.MAX_VALUE);
        snakeYaml = new Yaml(new SafeConstructor(options));
    }

    // LSYAML Strict

    @Benchmark
    public void lsyamlStrict_simple(Blackhole bh) {
        bh.consume(LSYAML.parseMap(YamlData.SIMPLE_YAML));
    }

    @Benchmark
    public void lsyamlStrict_medium(Blackhole bh) {
        bh.consume(LSYAML.parseMap(YamlData.MEDIUM_YAML));
    }

    @Benchmark
    public void lsyamlStrict_complex(Blackhole bh) {
        bh.consume(LSYAML.parseMap(YamlData.COMPLEX_YAML));
    }

    @Benchmark
    public void lsyamlStrict_insane(Blackhole bh) {
        bh.consume(LSYAML.parseMap(YamlData.INSANE_YAML));
    }

    // LSYAML Lenient

    @Benchmark
    public void lsyamlLenient_simple(Blackhole bh) {
        bh.consume(LSYAML.parse(YamlData.SIMPLE_YAML, ParseOptions.lenient()));
    }

    @Benchmark
    public void lsyamlLenient_medium(Blackhole bh) {
        bh.consume(LSYAML.parse(YamlData.MEDIUM_YAML, ParseOptions.lenient()));
    }

    @Benchmark
    public void lsyamlLenient_complex(Blackhole bh) {
        bh.consume(LSYAML.parse(YamlData.COMPLEX_YAML, ParseOptions.lenient()));
    }

    @Benchmark
    public void lsyamlLenient_insane(Blackhole bh) {
        bh.consume(LSYAML.parse(YamlData.INSANE_YAML, ParseOptions.lenient()));
    }

    // SnakeYAML

    @Benchmark
    public void snakeYaml_simple(Blackhole bh) {
        bh.consume(snakeYaml.load(YamlData.SIMPLE_YAML));
    }

    @Benchmark
    public void snakeYaml_medium(Blackhole bh) {
        bh.consume(snakeYaml.load(YamlData.MEDIUM_YAML));
    }

    @Benchmark
    public void snakeYaml_complex(Blackhole bh) {
        bh.consume(snakeYaml.load(YamlData.COMPLEX_YAML));
    }

    @Benchmark
    public void snakeYaml_insane(Blackhole bh) {
        bh.consume(snakeYaml.load(YamlData.INSANE_YAML));
    }
}
