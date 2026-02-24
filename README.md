# LSYAML JMH Benchmark

Proper JMH benchmarks comparing **LSYAML** against **SnakeYAML** across different YAML complexities and JVM conditions.

## What this measures

LSYAML is a lightning fast, format preserving YAML parser for Java. This project puts that claim to the test using
[JMH](https://github.com/openjdk/jmh), the standard Java microbenchmarking harness, so the numbers are reliable and
reproducible.

Four YAML documents are tested at increasing complexity:

| Document | Description                                    | Size      |
|----------|------------------------------------------------|-----------|
| Simple   | Flat key value pairs                           | 50 B      |
| Medium   | Nested maps, lists, multiple sections          | 647 B     |
| Complex  | Deep nesting, lists of maps, multiple types    | 218 KB    |
| Insane   | Extremely large real-world stress test         | 1.4 MB    |

Each document is parsed by three configurations:
- **LSYAML (strict)** via `LSYAML.parseMap()` for complete YAML 1.2 validation
- **LSYAML (lenient)** via `LSYAML.parse()` with `ParseOptions.lenient()` for flexible parsing
- **SnakeYAML** via `Yaml.load()` with `SafeConstructor` for baseline comparison

## Benchmark modes

### Hot Benchmark
Measures sustained **throughput** (operations per millisecond) after the JVM has fully warmed up. This is the steady
state performance you would see in a long running application where the JIT compiler has had plenty of time to optimize
everything.

- 3 warmup iterations (1 second each)
- 3 measurement iterations (2 seconds each)
- 1 forked JVM process

### Cold Benchmark
Measures **single shot time** (microseconds per call) with zero warmup. This tells you how fast parsing is on the very
first call, before the JIT has kicked in. Useful for CLI tools, short lived scripts, or startup sensitive applications.

- No warmup
- 50 measurement iterations with batch size 1
- 5 forked JVM processes

## Running

Run all benchmarks:

```bash
java -jar target/benchmarks.jar
```

Run only the hot (throughput) benchmarks:

```bash
java -jar target/benchmarks.jar HotBenchmark
```

Run only the cold (single shot) benchmarks:

```bash
java -jar target/benchmarks.jar ColdBenchmark
```

## Test Data

The benchmark uses four YAML test files loaded from resource files:

- **Simple** and **Medium** are AI Generated test documents for baseline performance measurement
- **Complex** and **Insane** are generated via a specialized tool that includes anchors, lists, flow collections, and complex keys to stress test advanced YAML features