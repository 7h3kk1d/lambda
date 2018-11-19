package com.jnape.palatable.lambda.optics;

import com.jnape.palatable.lambda.functions.Fn2;
import com.jnape.palatable.lambda.functor.builtin.Const;

import java.util.function.Function;

public interface Getting<R, S, A> {

    Fn2<Function<? super R, ? extends Const<R, A>>, S, R> getting();
}
