package com.jnape.palatable.lambda.lens.functions;

import com.jnape.palatable.lambda.functions.Fn1;
import com.jnape.palatable.lambda.functions.Fn2;
import com.jnape.palatable.lambda.optics.AReview;

import static com.jnape.palatable.lambda.lens.functions.Re.re;
import static com.jnape.palatable.lambda.lens.functions.View.view;

public final class Review<B, T> implements Fn2<AReview<T, B>, B, T> {

    private static final Review<?, ?> INSTANCE = new Review<>();

    private Review() {
    }

    @Override
    public T apply(AReview<T, B> aReview, B b) {
        return view(re(aReview), b);
    }

    @SuppressWarnings("unchecked")
    public static <T, B> Review<T, B> review() {
        return (Review<T, B>) INSTANCE;
    }

    public static <T, B> Fn1<B, T> review(AReview<T, B> aReview) {
        return Review.<B, T>review().apply(aReview);
    }

    public static <T, B> T review(AReview<T, B> aReview, B b) {
        return review(aReview).apply(b);
    }
}
