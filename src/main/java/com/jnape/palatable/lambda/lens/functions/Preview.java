package com.jnape.palatable.lambda.lens.functions;

import com.jnape.palatable.lambda.adt.Maybe;
import com.jnape.palatable.lambda.functions.Fn1;
import com.jnape.palatable.lambda.functions.Fn2;
import com.jnape.palatable.lambda.functor.builtin.Const;
import com.jnape.palatable.lambda.optics.Getter;

import static com.jnape.palatable.lambda.lens.functions.Pre.pre;
import static com.jnape.palatable.lambda.lens.functions.View.view;

public final class Preview<A, S> implements Fn2<Getter<Const<Maybe<A>, ?>, Const<Maybe<A>, A>, Const<Maybe<A>, S>, S, A>, S, Maybe<A>> {

    public static final Preview<?, ?> INSTANCE = new Preview<>();

    private Preview() {
    }

    @Override
    public Maybe<A> apply(Getter<Const<Maybe<A>, ?>, Const<Maybe<A>, A>, Const<Maybe<A>, S>, S, A> getter, S s) {
        return view(pre(getter), s);
    }

    @SuppressWarnings("unchecked")
    public static <A, S> Preview<A, S> preview() {
        return (Preview<A, S>) INSTANCE;
    }

    public static <A, S> Fn1<S, Maybe<A>> preview(
            Getter<Const<Maybe<A>, ?>, Const<Maybe<A>, A>, Const<Maybe<A>, S>, S, A> getter) {
        return Preview.<A, S>preview().apply(getter);
    }

    public static <A, S> Maybe<A> preview(
            Getter<Const<Maybe<A>, ?>, Const<Maybe<A>, A>, Const<Maybe<A>, S>, S, A> getter, S s) {
        return preview(getter).apply(s);
    }
}
