package com.jnape.palatable.lambda.lens.functions;

import com.jnape.palatable.lambda.functions.Fn1;
import com.jnape.palatable.lambda.functions.Fn2;
import com.jnape.palatable.lambda.functor.builtin.Const;
import com.jnape.palatable.lambda.optics.Getter;
import com.jnape.palatable.lambda.optics.Getting;

/**
 * Given some optic that can be used for {@link Getting} and a "larger" value <code>S</code>, retrieve a "smaller" value
 * <code>A</code> by lifting the optic into {@link Const}.
 * <p>
 * More idiomatically, this function can be used to treat a lens as a "getter" of <code>A</code>s from <code>S</code>s.
 *
 * @param <S> the type of the larger value
 * @param <A> the type of the smaller retrieving value
 * @see Set
 * @see Over
 */
public final class View<S, A> implements Fn2<Getter<Const<A, ?>, Const<A, A>, Const<A, S>, S, A>, S, A> {

    private static final View INSTANCE = new View();

    private View() {
    }

    @Override
    public A apply(Getter<Const<A, ?>, Const<A, A>, Const<A, S>, S, A> getter, S s) {
        return getter.getter().apply(Const::new, s).runConst();
    }

    @SuppressWarnings("unchecked")
    public static <S, A> View<S, A> view() {
        return INSTANCE;
    }

    public static <S, A> Fn1<S, A> view(Getter<Const<A, ?>, Const<A, A>, Const<A, S>, S, A> getter) {
        return View.<S, A>view().apply(getter);
    }

    public static <S, A> A view(Getter<Const<A, ?>, Const<A, A>, Const<A, S>, S, A> getter, S s) {
        return view(getter).apply(s);
    }
}
