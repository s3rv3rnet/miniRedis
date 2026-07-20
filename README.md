
# Mini Redis

## End Goal

A working in-memory key-value store (a mini-Redis) that speaks over the network, survives restarts, handles thousands of concurrent clients, and evicts data intelligently. Built in pure Java, no frameworks. By the end it's a real piece of systems software, not a toy.


## Five Phases

### Phase 1

Core Store (basics). A single-threaded, in-memory `Store` wrapping a map, driven a keyboard REPL. Commands: SET, GET, DEL, EXISTS. You practice classes, collections, exceptions, and the `equals`/`hashCode` reality of map keys.


### Phase 2

Command Modeling (OOP + Modern Java). Replace the string-`switch` dispatch with a proper `sealed interface Command` hierarchy and pattern-matching dispatch - the sealed + switch idiom. We will introduce JUnit here, because eyeballing the REPL stops scaling. You practice sealed types, records, pattern matching, and testing.

### Phase 3

Persistence (I/O). Make data survives restarts. First an append-only command log (replay it on startup to rebuild state), then periodic snapshots so replay doesn't grow unbounded. You practise file I/O, buffered streams, serialization, and the log-then-snapshot pattern real databases use.

### Phase 4

Network Server (Concurrency). Turn it into a TCP Server with a simple text protocol, so real clients (even telnet/redis-cli-style) can connect. Then walk the concurrency ladder deliberately: one-thread-per-connection -> bounded thread pool -> virtual-thread-per-connection, measuring the difference. This is where `hashmap` -> `concurrenthashmap`, the mutation/visibility issues, the interrupt-handling, and the virtual-threads material from our recent discussions all become concrete instead of theoretical.

### Phase 5

Advanced features. TTL/key expiry with a background sweeper thread, LRU eviction (ties back to `LinkedHashMap` and key identity), pub/sub (observer pattern + `BlockingQueue`), and optionally async command handling with `CompletableFuture`. You practice background threads, eviction policies, and event-driven patterns - then benchmark platform vs. virtual threads under 10k clients to feel the unmound-on-block behavior. 