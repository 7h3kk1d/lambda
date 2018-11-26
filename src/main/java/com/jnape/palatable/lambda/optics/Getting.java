package com.jnape.palatable.lambda.optics;

import com.jnape.palatable.lambda.functions.Fn1;
import com.jnape.palatable.lambda.functions.Fn2;
import com.jnape.palatable.lambda.functor.builtin.Const;

public interface Getting<R, S, A> {

    Fn2<Fn1<A, Const<R, A>>, S, Const<R, S>> getting();
}
