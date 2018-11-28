package com.jnape.palatable.lambda.functions.builtin.fn1;

import com.jnape.palatable.lambda.functions.Fn1;
import com.jnape.palatable.lambda.functions.Fn2;
import com.jnape.palatable.lambda.functor.Contravariant;
import com.jnape.palatable.lambda.functor.Functor;
import com.jnape.palatable.lambda.functor.builtin.Identity;
import com.jnape.palatable.lambda.functor.builtin.Tagged;
import com.jnape.palatable.lambda.optics.AReview;
import com.jnape.palatable.lambda.optics.Getter;

import static com.jnape.palatable.lambda.functions.builtin.fn1.Constantly.constantly;

public final class Re<B, T> implements Fn1<AReview<T, B>, Getter<B, T>> {

    private static final Re<?, ?> INSTANCE = new Re<>();

    private Re() {
    }

    @Override
    public Getter<B, T> apply(AReview<T, B> aReview) {
        return new Getter<B, T>() {
            @Override
            @SuppressWarnings("unchecked")
            public <F extends Functor & Contravariant, FT extends Functor<T, F> & Contravariant<T, F>, FB extends Functor<B, F> & Contravariant<B, F>> Fn2<Fn1<T, FT>, B, FB> getter() {
                return (f, b) -> (FB) f.apply(aReview.aReview().apply(new Tagged<>(new Identity<>(b))).unTagged().runIdentity()).fmap(constantly(b));
            }
        };
    }

    @SuppressWarnings("unchecked")
    public static <B, T> Re<B, T> re() {
        return (Re<B, T>) INSTANCE;
    }

    public static <B, T> Getter<B, T> re(AReview<T, B> aReview) {
        return Re.<B, T>re().apply(aReview);
    }
}
