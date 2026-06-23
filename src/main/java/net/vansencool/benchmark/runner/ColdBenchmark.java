package net.vansencool.benchmark.runner;

import net.vansencool.benchmark.data.YamlData;
import net.vansencool.lsyaml.LSYAML;
import net.vansencool.lsyaml.parser.ParseOptions;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.SafeConstructor;

import io.github.qishr.cascara.lang.yaml.processor.YamlParser;

import java.util.concurrent.TimeUnit;

/**
 * Cold benchmark measuring single shot execution time without warmup.
 * This simulates the very first parse calls, before the JIT compiler
 * has had a chance to optimize anything. Useful for understanding
 * startup and short lived application performance.
 */
@BenchmarkMode(Mode.SingleShotTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@State(Scope.Thread)
@Warmup(iterations = 0)
@Measurement(iterations = 50, batchSize = 1)
@Fork(5)
@SuppressWarnings("unused")
public class ColdBenchmark {

    private Yaml snakeYaml;
    private YamlParser cascaraYaml;

    @Setup(Level.Trial)
    public void setup() {
        LoaderOptions options = new LoaderOptions();
        options.setAllowDuplicateKeys(false);
        options.setProcessComments(true);
        options.setMaxAliasesForCollections(Integer.MAX_VALUE);
        snakeYaml = new Yaml(new SafeConstructor(options));
        cascaraYaml = new YamlParser();
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

    // Cascara YAML

    @Benchmark
    public void cascaraYaml_simple(Blackhole bh) {
        bh.consume(cascaraYaml.parse(YamlData.SIMPLE_YAML));
    }

    @Benchmark
    public void cascaraYaml_medium(Blackhole bh) {
        bh.consume(cascaraYaml.parse(YamlData.MEDIUM_YAML));
    }

    @Benchmark
    public void cascaraYaml_complex(Blackhole bh) {
        bh.consume(cascaraYaml.parse(YamlData.COMPLEX_YAML));
    }

    @Benchmark
    public void cascaraYaml_insane(Blackhole bh) {
        bh.consume(cascaraYaml.parse(YamlData.INSANE_YAML));
    }
}
