package com.jnape.palatable.lambda.io;

import com.jnape.palatable.lambda.adt.Maybe;
import com.jnape.palatable.lambda.functions.Fn1;

import java.util.Collections;
import java.util.concurrent.ArrayBlockingQueue;

import static com.jnape.palatable.lambda.adt.Maybe.maybe;
import static com.jnape.palatable.lambda.functions.Fn0.fn0;
import static com.jnape.palatable.lambda.functions.builtin.fn1.Constantly.constantly;
import static com.jnape.palatable.lambda.io.IO.io;

public final class MVar<A> {

    private final ArrayBlockingQueue<A> queue;

    private MVar() {
        queue = new ArrayBlockingQueue<>(1, true);
    }

    private MVar(A a) {
        queue = new ArrayBlockingQueue<>(1, true, Collections.singletonList(a));
    }

    public IO<A> take() {
        return io(queue::take);
    }

    public IO<MVar<A>> put(A a) {
        return io(() -> {
            queue.put(a);
            return this;
        });
    }

    public IO<A> read() {
        return take()
                .flatMap(a -> put(a)
                        .fmap(constantly(a)));
    }

    public IO<A> swap(A a) {
        return take()
                .flatMap(a1 -> put(a)
                        .fmap(constantly(a1)));
    }

    // Like tryTakeMVar
    public IO<Maybe<A>> poll(A a) {
        return io(() -> maybe(queue.poll()));
    }

    // Like tryPutMVar
    public IO<Boolean> add(A a) {
        return io(() -> queue.add(a));
    }

    public IO<MVar<A>> modify(Fn1<A, IO<A>> fn) {
        return take()
                .flatMap(a -> fn.apply(a)
                                .flatMap(this::put)
                                .catchError(t -> put(a)));
    }

    public static <A> IO<MVar<A>> newMVar() {
        return io(fn0(MVar::new));
    }

    public static <A> IO<MVar<A>> newMVar(A a) {
        return io(() -> new MVar<>(a));
    }
}
