package com.jnape.palatable.lambda.optics;

import com.jnape.palatable.lambda.functor.builtin.Const;

public interface Getting<R, S, A> extends Getter<Const<R, ?>, Const<R, A>, Const<R, S>, S, A> {
}
