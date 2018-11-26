package com.jnape.palatable.lambda.lens.functions;

import com.jnape.palatable.lambda.adt.Maybe;
import com.jnape.palatable.lambda.functions.Fn1;
import com.jnape.palatable.lambda.functions.Fn2;
import com.jnape.palatable.lambda.optics.Getting;

import static com.jnape.palatable.lambda.lens.functions.Pre.pre;
import static com.jnape.palatable.lambda.lens.functions.View.view;

public final class Preview<A, S> implements Fn2<Getting<Maybe<A>, S, A>, S, Maybe<A>> {

    public static final Preview<?, ?> INSTANCE = new Preview<>();

    private Preview() {
    }

    @Override
    public Maybe<A> apply(Getting<Maybe<A>, S, A> getting, S s) {
        return view(pre(getting), s);
    }

    @SuppressWarnings("unchecked")
    public static <A, S> Preview<A, S> preview() {
        return (Preview<A, S>) INSTANCE;
    }

    public static <A, S> Fn1<S, Maybe<A>> preview(Getting<Maybe<A>, S, A> getting) {
        return Preview.<A, S>preview().apply(getting);
    }

    public static <A, S> Maybe<A> preview(Getting<Maybe<A>, S, A> getting, S s) {
        return preview(getting).apply(s);
    }
}
