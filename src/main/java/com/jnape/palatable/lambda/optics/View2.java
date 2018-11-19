package com.jnape.palatable.lambda.optics;

import com.jnape.palatable.lambda.functions.Fn1;
import com.jnape.palatable.lambda.functions.Fn2;
import com.jnape.palatable.lambda.functor.builtin.Const;

public final class View2<R, S, A> implements Fn2<Getting<R, S, A>, S, R> {

    private static final View2 INSTANCE = new View2();

    private View2() {
    }

    @Override
    public R apply(Getting<R, S, A> getting, S s) {
        return getting.getting().apply(Const::new, s);
    }

    @SuppressWarnings("unchecked")
    public static <R, S, A> View2<R, S, A> view() {
        return INSTANCE;
    }

    public static <R, S, A> Fn1<S, R> view(Getting<R, S, A> getting) {
        return View2.<R, S, A>view().apply(getting);
    }

    public static <R, S, A> R view(Getting<R, S, A> getting, S s) {
        return view(getting).apply(s);
    }
}
