package com.miniredis.command;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CommandTest {

    @Test
    void delKeysAreImmutable() {
        List<String> src = new ArrayList<>(List.of("a", "b"));
        Command.Del del = new Command.Del(src);
        src.add("c");
        assertEquals(List.of("a", "b"), del.keys());
        assertThrows(UnsupportedOperationException.class, () -> del.keys().add("c"));
    }
}
