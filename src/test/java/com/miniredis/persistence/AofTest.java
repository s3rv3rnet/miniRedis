package com.miniredis.persistence;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AofTest {

    @Test
    void appendsLinesToFile(@TempDir Path tempDir) throws IOException {
        Path log = tempDir.resolve("test.aof");
        try (Aof aof = new Aof(log)) {
            aof.append("SET foo bar");
            aof.append("DEL foo");
        }

        assertEquals(List.of("SET foo bar", "DEL foo"), Files.readAllLines(log));
    }

    @Test
    void reopeningAppendsInsteadOfOverwriting(@TempDir Path tempDir) throws IOException {
        Path log = tempDir.resolve("test.aof");
        try (Aof aof = new Aof(log)) {
            aof.append("SET a 1");
        } // first session
        try (Aof aof = new Aof(log)) {
            aof.append("SET a 2");
        } // second session after restart

        assertEquals(List.of("SET a 1", "SET a 2"), Files.readAllLines(log));
    }
}
