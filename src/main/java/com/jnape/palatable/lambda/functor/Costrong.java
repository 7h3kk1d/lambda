package com.jnape.palatable.lambda.functor;

import com.jnape.palatable.lambda.adt.choice.Choice2;

import java.util.function.Function;

/**
 * Costrength
 * @param <A>
 * @param <B>
 * @param <P>
 */
public interface Costrong<A, B, P extends Costrong> extends Profunctor<A, B, P> {

    <C> Costrong<Choice2<C, A>, Choice2<C, B>, P> costrengthen();

    @Override
    <Z, C> Costrong<Z, C, P> diMap(Function<? super Z, ? extends A> lFn, Function<? super B, ? extends C> rFn);

    @Override
    default <Z> Costrong<Z, B, P> diMapL(Function<? super Z, ? extends A> fn) {
        return (Costrong<Z, B, P>) Profunctor.super.<Z>diMapL(fn);
    }

    @Override
    default <C> Costrong<A, C, P> diMapR(Function<? super B, ? extends C> fn) {
        return (Costrong<A, C, P>) Profunctor.super.<C>diMapR(fn);
    }

    @Override
    default <Z> Costrong<Z, B, P> contraMap(Function<? super Z, ? extends A> fn) {
        return (Costrong<Z, B, P>) Profunctor.super.<Z>contraMap(fn);
    }
}