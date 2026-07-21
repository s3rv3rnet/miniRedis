package com.miniredis.command;

import java.util.List;

public sealed interface Command permits Command.Set, Command.Get, Command.Del, Command.Exists, Command.Quit {

    record Set(String key, String value) implements Command {}

    record Get(String key) implements Command {}

    record Del(List<String> keys)  implements Command {

        public Del {
            keys = List.copyOf(keys);      // making a copy so that the list is really immutable.
            // if this is not present, caller can add items to the list with the keys reference.
        }
    }

    record Exists(List<String> keys)  implements Command {
        public Exists {
            keys = List.copyOf(keys);
        }
    }

    record Quit() implements Command {}
}
