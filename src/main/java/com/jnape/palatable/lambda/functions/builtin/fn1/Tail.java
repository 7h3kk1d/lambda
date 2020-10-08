package com.jnape.palatable.lambda.functions.builtin.fn1;

import com.jnape.palatable.lambda.functions.Fn1;

import static com.jnape.palatable.lambda.functions.builtin.fn2.Drop.drop;

/**
 * Returns the tail of an {@link Iterable}; the is, an {@link Iterable} of all the elements except for the
 * head element. If the input {@link Iterable} is empty, the result is also an empty {@link Iterable};
 *
 * @param <A> the Iterable element type
 */
public final class Tail<A> implements Fn1<Iterable<A>, Iterable<A>> {

    private static final Tail<?> INSTANCE = new Tail<>();

    private Tail() {
    }

    @Override
    public Iterable<A> checkedApply(Iterable<A> as) {
        return drop(1, as);
    }

    @SuppressWarnings("unchecked")
    public static <A> Tail<A> tail() {
        return (Tail<A>) INSTANCE;
    }

    public static <A> Iterable<A> tail(Iterable<A> as) {
        return Tail.<A>tail().apply(as);
    }
}
