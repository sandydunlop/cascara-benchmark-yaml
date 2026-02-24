package net.vansencool.benchmark.data;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * Shared YAML test data used across all benchmark classes.
 * Provides four test documents at increasing complexity levels:
 * simple (50 B), medium (647 B), complex (218 KB), and insane (1.4 MB).
 * Simple and medium are AI Generated.
 * Complex and insane are generated via a specialized tool containing anchors, lists, flows, and complex keys.
 */
public final class YamlData {

    private YamlData() {
    }

    /**
     * Loads a resource file from the classpath.
     */
    private static String loadResource(String resourceName) {
        try (var inputStream = YamlData.class.getClassLoader().getResourceAsStream(resourceName)) {
            if (inputStream == null) {
                throw new IllegalArgumentException("Resource not found: " + resourceName);
            }
            return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load resource: " + resourceName, e);
        }
    }

    public static final String SIMPLE_YAML = loadResource("simple.yaml");

    public static final String MEDIUM_YAML = loadResource("medium.yaml");

    public static final String COMPLEX_YAML = loadResource("complex.yaml");

    public static final String INSANE_YAML = loadResource("insane.yaml");
}
