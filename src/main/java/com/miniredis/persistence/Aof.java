package com.miniredis.persistence;

import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.CREATE;

public class Aof implements Closeable {

    /**
     * A file handle is an OS resource that must be released. Implementing Closeable lets callers
     * use try-with-resources, which auto-closes even on exception. Standard idiom for anything
     * holding a file/socket.
     */

    private final BufferedWriter writer;

    public Aof(Path path) throws IOException {
        this.writer = Files.newBufferedWriter(path, CREATE, APPEND);
        // create flag creates if not present, and append flag appends to the file when writing.
    }

    public void append(String line) throws IOException {
        writer.write(line);
        writer.newLine();
        writer.flush();
    }

    @Override
    public void close() throws IOException {
        writer.close();
    }
}
