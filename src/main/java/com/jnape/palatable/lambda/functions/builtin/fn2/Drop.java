package com.jnape.palatable.lambda.functions.builtin.fn2;

import com.jnape.palatable.lambda.functions.Fn1;
import com.jnape.palatable.lambda.functions.Fn2;
import com.jnape.palatable.lambda.internal.iteration.DroppingIterable;

/**
 * Lazily skip the first <code>n</code> elements from an {@link Iterable} by returning an {@link Iterable}
 * that begins iteration after the <code>nth</code> element. If <code>n</code> is greater than or equal to the length of
 * the {@link Iterable}, an empty {@link Iterable} is returned.
 *
 * @param <A> The Iterable element type
 * @see DropWhile
 * @see Take
 */
public final class Drop<A> implements Fn2<Integer, Iterable<A>, Iterable<A>> {

    private static final Drop<?> INSTANCE = new Drop<>();

    private Drop() {
    }

    @Override
    public Iterable<A> checkedApply(Integer n, Iterable<A> as) {
        return new DroppingIterable<>(n, as);
    }

    @SuppressWarnings("unchecked")
    public static <A> Drop<A> drop() {
        return (Drop<A>) INSTANCE;
    }

    public static <A> Fn1<Iterable<A>, Iterable<A>> drop(int n) {
        return Drop.<A>drop().apply(n);
    }

    public static <A> Iterable<A> drop(int n, Iterable<A> as) {
        return Drop.<A>drop(n).apply(as);
    }
}
