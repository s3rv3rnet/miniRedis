package com.miniredis.command;

import com.miniredis.store.Store;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CommandExecutorTest {

    private CommandExecutor executor;
    private Store store;

    @BeforeEach
    void setUp() {
        store = new Store();
        executor = new CommandExecutor(store);
    }

    @Test
    void setReturnsOk() {
        assertEquals(new Result.Ok(), executor.execute(new Command.Set("k", "v")));
        assertEquals("v", store.get("k").orElseThrow());   // and it actually stored
    }

    @Test
    void getHitReturnsBulk() {
        store.set("k", "v");
        assertEquals(new Result.Bulk("v"), executor.execute(new Command.Get("k")));
    }

    @Test
    void getMissReturnsNil() {
        assertEquals(new Result.Nil(), executor.execute(new Command.Get("absent")));
    }

    @Test
    void delCountsOnlyPresentKeys() {
        store.set("a", "1");
        store.set("c", "3");
        var result = executor.execute(new Command.Del(List.of("a", "b", "c")));
        assertEquals(new Result.IntReply(2), result);      // a and c deleted, b absent
    }

    @Test
    void existsCountsDuplicates() {
        store.set("foo", "1");
        assertEquals(new Result.IntReply(2), executor.execute(new Command.Exists(List.of("foo", "foo"))));
    }
}
