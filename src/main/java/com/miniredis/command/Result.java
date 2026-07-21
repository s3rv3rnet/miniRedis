package com.miniredis.command;

public sealed interface Result permits Result.Ok, Result.Bulk, Result.Nil, Result.IntReply, Result.Error, Result.Quit {

    record Ok() implements Result {}
    record Bulk(String value) implements Result {}
    record Nil() implements Result {}
    record IntReply(long value) implements Result {}
    record Error(String message) implements Result {}
    record Quit() implements Result {}

    default String toReplyString() {
        return switch(this) {
            case Ok ok -> "OK";                           // type pattern, is this is instance of OK, then bind that to the variable ok
            case Bulk(var value) ->  "\"" + value + "\"";          // record deconstruction pattern, if this is Bulk, then pull its fields as well and bind them.
            case Nil nil -> "(nil)";
            case IntReply(var n) -> "(integer) " + n;
            case Error(var msg) -> "(error) ERR " + msg;
            case Quit quit -> "";
        };
    }
}
