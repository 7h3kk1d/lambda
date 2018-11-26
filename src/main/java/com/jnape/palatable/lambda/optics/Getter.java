package com.jnape.palatable.lambda.optics;

import com.jnape.palatable.lambda.functions.Fn1;
import com.jnape.palatable.lambda.functions.Fn2;
import com.jnape.palatable.lambda.functor.Contravariant;
import com.jnape.palatable.lambda.functor.Functor;
import com.jnape.palatable.lambda.functor.builtin.Const;

public interface Getter<S, A> extends Getting<A, S, A> {

    <F extends Functor & Contravariant,
            FA extends Functor<A, F> & Contravariant<A, F>,
            FS extends Functor<S, F> & Contravariant<S, F>> Fn2<Fn1<A, FA>, S, FS> getter();

    @Override
    default Fn2<Fn1<A, Const<A, A>>, S, Const<A, S>> getting() {
        return this.getter();
    }
}
