package com.jnape.palatable.lambda.lens.functions;

import com.jnape.palatable.lambda.functions.Fn1;
import com.jnape.palatable.lambda.functor.builtin.Const;
import com.jnape.palatable.lambda.functor.builtin.Identity;
import com.jnape.palatable.lambda.functor.builtin.Tagged;
import com.jnape.palatable.lambda.optics.AReview;
import com.jnape.palatable.lambda.optics.Getter;

import static com.jnape.palatable.lambda.functions.builtin.fn1.Constantly.constantly;

public final class Re<B, T> implements Fn1<AReview<T, B>, Getter<Const<T, ?>, Const<T, T>, Const<T, B>, B, T>> {

    private static final Re<?, ?> INSTANCE = new Re<>();

    private Re() {
    }

    @Override
    public Getter<Const<T, ?>, Const<T, T>, Const<T, B>, B, T> apply(AReview<T, B> aReview) {
        return () -> (f, b) -> f.apply(aReview.aReview().apply(new Tagged<>(new Identity<>(b))).unTagged().runIdentity()).fmap(constantly(b));
    }

    @SuppressWarnings("unchecked")
    public static <B, T> Re<B, T> re() {
        return (Re<B, T>) INSTANCE;
    }

    public static <B, T> Getter<Const<T, ?>, Const<T, T>, Const<T, B>, B, T> re(AReview<T, B> aReview) {
        return Re.<B, T>re().apply(aReview);
    }
}
