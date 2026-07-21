package com.miniredis.command;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ResultTest {

    @Test
    void okFormatsAsOK() {
        assertEquals("OK", new Result.Ok().toReplyString());
    }

    @Test
    void bulkIsQuoted() {
        assertEquals("\"bar\"", new Result.Bulk("bar").toReplyString());
    }

    @Test
    void nilFormats() {
        assertEquals("(nil)", new Result.Nil().toReplyString());
    }

    @Test
    void intReplyFormats() {
        assertEquals("(integer) 3", new Result.IntReply(3).toReplyString());
    }

    @Test
    void errorFormats() {
        assertEquals("(error) ERR bad", new Result.Error("bad").toReplyString());
    }
}
