package com.miniredis.command;

import java.util.Arrays;
import java.util.List;

public final class CommandParser {

    private CommandParser() {}

    public static Command parse(String line) {
        String[] parts = line.trim().split("\\s+");
        String name = parts[0].toUpperCase();

        return switch(name) {
            case "SET" -> {
                if(parts.length != 3) {
                    throw wrongArgs("set");
                }
                yield new Command.Set(parts[1], parts[2]);
            }
            case "GET"  -> {
                if(parts.length != 2) { throw wrongArgs("get"); }
                yield new Command.Get(parts[1]);
            }
            case "DEL" -> {
                if(parts.length < 2) throw wrongArgs("del");
                yield new Command.Del(keysFrom(parts));
            }
            case "EXISTS" -> {
                if(parts.length < 2) throw wrongArgs("exists");
                yield new Command.Exists(keysFrom(parts));
            }
            case "QUIT" -> {
                if(parts.length != 1) throw wrongArgs("quit");
                yield new Command.Quit();
            }
            default -> throw new CommandException("unknown command '" + parts[0] + "'");
        };
    }

    private static CommandException wrongArgs(String cmd) {
        return new CommandException("wrong number of arguments for '" + cmd + "' command");
    }

    private static List<String> keysFrom(String[] parts) {
        return List.of(Arrays.copyOfRange(parts, 1, parts.length));
    }
}
