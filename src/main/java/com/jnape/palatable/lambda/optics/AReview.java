package com.jnape.palatable.lambda.optics;

import com.jnape.palatable.lambda.functions.Fn1;
import com.jnape.palatable.lambda.functor.builtin.Identity;
import com.jnape.palatable.lambda.functor.builtin.Tagged;

public interface AReview<T, B> {

    Fn1<Tagged<B, Identity<B>>, Tagged<T, Identity<T>>> aReview();
}