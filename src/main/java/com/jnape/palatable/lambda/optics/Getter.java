package com.jnape.palatable.lambda.optics;

import com.jnape.palatable.lambda.functions.Fn1;
import com.jnape.palatable.lambda.functions.Fn2;
import com.jnape.palatable.lambda.functor.Contravariant;
import com.jnape.palatable.lambda.functor.Functor;


//todo: idea, existentially quantify method, then offer a "fix" to get back the fixed fn
public interface Getter<F extends Functor & Contravariant,
        FA extends Functor<A, F> & Contravariant<A, F>,
        FS extends Functor<S, F> & Contravariant<S, F>, S, A> {

    Fn2<Fn1<A, FA>, S, FS> getter();
}
