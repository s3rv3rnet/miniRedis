package com.miniredis.command;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CommandParserTest {

    @Test
    void parsesSet() {
        assertEquals(new Command.Set("k", "v"), CommandParser.parse("set k v"));
    }

    @Test
    void parsesGet() {
        assertEquals(new Command.Get("k"), CommandParser.parse("get k"));
    }

    @Test
    void parsesExists() {
        assertEquals(new Command.Exists(List.of("a", "b", "c")), CommandParser.parse("EXISTS a b c"));
    }

    @Test
    void setTooManyArgsThrows() {
        assertThrows(CommandException.class, () -> CommandParser.parse("SET a b c"));
    }

    @Test
    void setTooFewArgsThrows() {
        assertThrows(CommandException.class, () -> CommandParser.parse("SET a"));
    }

    @Test
    void delNoKeysThrows() {
        assertThrows(CommandException.class, () -> CommandParser.parse("DEL"));
    }

    // --- unknown command ---
    @Test
    void unknownCommandThrows() {
        assertThrows(CommandException.class, () -> CommandParser.parse("FOO a"));
    }
}

