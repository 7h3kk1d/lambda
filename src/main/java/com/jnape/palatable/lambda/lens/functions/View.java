package com.jnape.palatable.lambda.lens.functions;

import com.jnape.palatable.lambda.functions.Fn1;
import com.jnape.palatable.lambda.functions.Fn2;
import com.jnape.palatable.lambda.functor.builtin.Const;
import com.jnape.palatable.lambda.optics.Getting;

/**
 * Given some optic that can be used for {@link Getting} and a "larger" value <code>S</code>, retrieve a "smaller" value
 * <code>R</code> by lifting the optic into {@link Const}.
 * <p>
 * More idiomatically, this function can be used to treat a lens as a "getter" of <code>A</code>s from <code>S</code>s.
 *
 * @param <R> the type of the smaller retrieving value
 * @param <S> the type of the larger value
 * @param <A> the type of the smaller retrieving value
 * @see Set
 * @see Over
 */
public final class View<R, S, A> implements Fn2<Getting<R, S, A>, S, R> {

    private static final View INSTANCE = new View();

    private View() {
    }

    @Override
    public R apply(Getting<R, S, A> getting, S s) {
        return getting.getting().apply(Const::new, s);
    }

    @SuppressWarnings("unchecked")
    public static <R, S, A> View<R, S, A> view() {
        return INSTANCE;
    }

    public static <R, S, A> Fn1<S, R> view(Getting<R, S, A> getting) {
        return View.<R, S, A>view().apply(getting);
    }

    public static <R, S, A> R view(Getting<R, S, A> getting, S s) {
        return view(getting).apply(s);
    }
}
