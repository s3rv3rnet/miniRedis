package com.miniredis.command;

import com.miniredis.store.Store;

public final class CommandExecutor {

    /**
     * this class is final because we don't want anyone to extend this class.
     * basically, there are only two choices - design classes either to be inherited
     * or forbit it. there is no middle ground. If you want someone to inherit your class
     * you need to clearly document how and what features should be extended. otherwise
     * its better to make it final.
     */

    private final Store store;

    public CommandExecutor(Store store) {
        this.store = store;
    }

    public Result execute(Command command) {
        return switch (command) {
            case Command.Set(var key, var value) -> {
                store.set(key, value);
                yield new Result.Ok();
            }
            case Command.Get(var key) -> {
                yield store.get(key).<Result>map(Result.Bulk::new).orElse(new Result.Nil());
            }
            case Command.Del(var keys) -> {
                yield new Result.IntReply(keys.stream().filter(store::delete).count());
            }
            case Command.Exists(var keys) -> new Result.IntReply(keys.stream().filter(store::exists).count());
            case Command.Quit() -> {
                yield new Result.Quit();
            }
        };
    }
}
