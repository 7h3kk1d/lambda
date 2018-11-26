package com.jnape.palatable.lambda.lens.functions;

import com.jnape.palatable.lambda.adt.Maybe;
import com.jnape.palatable.lambda.functions.Fn1;
import com.jnape.palatable.lambda.functor.builtin.Const;
import com.jnape.palatable.lambda.optics.Getter;

import static com.jnape.palatable.lambda.adt.Maybe.just;
import static com.jnape.palatable.lambda.functions.builtin.fn1.Constantly.constantly;

public final class Pre<A, S> implements Fn1<Getter<Const<Maybe<A>, ?>, Const<Maybe<A>, A>, Const<Maybe<A>, S>, S, A>,
        Getter<Const<Maybe<A>, ?>, Const<Maybe<A>, Maybe<A>>, Const<Maybe<A>, S>, S, Maybe<A>>> {

    private static final Pre<?, ?> INSTANCE = new Pre<>();

    private Pre() {
    }

    @Override
    public Getter<Const<Maybe<A>, ?>, Const<Maybe<A>, Maybe<A>>, Const<Maybe<A>, S>, S, Maybe<A>> apply(
            Getter<Const<Maybe<A>, ?>, Const<Maybe<A>, A>, Const<Maybe<A>, S>, S, A> getter) {
        return () -> (f, s) -> f.apply(getter.getter().apply(a -> new Const<>(just(a)), s).runConst()).fmap(constantly(s));
    }

    @SuppressWarnings("unchecked")
    public static <A, S> Pre<A, S> pre() {
        return (Pre<A, S>) INSTANCE;
    }

    public static <A, S> Getter<Const<Maybe<A>, ?>, Const<Maybe<A>, Maybe<A>>, Const<Maybe<A>, S>, S, Maybe<A>> pre(
            Getter<Const<Maybe<A>, ?>, Const<Maybe<A>, A>, Const<Maybe<A>, S>, S, A> getter) {
        return Pre.<A, S>pre().apply(getter);
    }
}
