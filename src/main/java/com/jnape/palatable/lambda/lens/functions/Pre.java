package com.jnape.palatable.lambda.lens.functions;

import com.jnape.palatable.lambda.adt.Maybe;
import com.jnape.palatable.lambda.functions.Fn1;
import com.jnape.palatable.lambda.functions.Fn2;
import com.jnape.palatable.lambda.functor.Contravariant;
import com.jnape.palatable.lambda.functor.Functor;
import com.jnape.palatable.lambda.functor.builtin.Const;
import com.jnape.palatable.lambda.optics.Getter;
import com.jnape.palatable.lambda.optics.Getting;

import static com.jnape.palatable.lambda.adt.Maybe.just;
import static com.jnape.palatable.lambda.functions.builtin.fn1.Constantly.constantly;

public final class Pre<A, S> implements Fn1<Getting<Maybe<A>, S, A>, Getter<S, Maybe<A>>> {

    private static final Pre<?, ?> INSTANCE = new Pre<>();

    private Pre() {
    }

    @Override
    public Getter<S, Maybe<A>> apply(Getting<Maybe<A>, S, A> getting) {
        return new Getter<S, Maybe<A>>() {
            @Override
            @SuppressWarnings("unchecked")
            public <F extends Functor & Contravariant, FA extends Functor<Maybe<A>, F> & Contravariant<Maybe<A>, F>, FS extends Functor<S, F> & Contravariant<S, F>> Fn2<Fn1<Maybe<A>, FA>, S, FS> getter() {
                return (f, s) -> (FS) f.apply(getting.getting().apply(a -> new Const<>(just(a)), s).runConst()).fmap(constantly(s));
            }
        };
    }

    @SuppressWarnings("unchecked")
    public static <A, S> Pre<A, S> pre() {
        return (Pre<A, S>) INSTANCE;
    }

    public static <A, S> Getter<S, Maybe<A>> pre(Getting<Maybe<A>, S, A> getting) {
        return Pre.<A, S>pre().apply(getting);
    }
}
