package com.jnape.palatable.lambda.optics;

import com.jnape.palatable.lambda.adt.Maybe;
import com.jnape.palatable.lambda.adt.choice.Choice2;
import com.jnape.palatable.lambda.adt.coproduct.CoProduct2;
import com.jnape.palatable.lambda.functions.Fn1;
import com.jnape.palatable.lambda.functions.Fn2;
import com.jnape.palatable.lambda.functor.Applicative;
import com.jnape.palatable.lambda.functor.Costrong;
import com.jnape.palatable.lambda.functor.builtin.Const;
import com.jnape.palatable.lambda.functor.builtin.Identity;
import com.jnape.palatable.lambda.functor.builtin.Tagged;

import java.util.function.Function;

import static com.jnape.palatable.lambda.adt.Maybe.nothing;
import static com.jnape.palatable.lambda.functions.builtin.fn1.Constantly.constantly;

public interface Prism<S, T, A, B> extends AReview<T, B> {

    <P extends Costrong, F extends Applicative, FB extends Applicative<B, F>, FT extends Applicative<T, F>,
            PAFB extends Costrong<A, FB, P>,
            PSFT extends Costrong<S, FT, P>> PSFT apply(PAFB pafb, Function<? super T, ? extends FT> pure);

    @Override
    default Fn1<Tagged<B, Identity<B>>, Tagged<T, Identity<T>>> aReview() {
        return taggedB -> new Tagged<>(this.<Tagged, Identity, Identity<B>, Identity<T>, Tagged<A, Identity<B>>, Tagged<S, Identity<T>>>apply(new Tagged<>(taggedB.unTagged()), Identity::new).unTagged());
    }

    static <S, T, A, B> Prism<S, T, A, B> prism(Function<? super B, ? extends T> bt,
                                                Function<? super S, ? extends CoProduct2<T, A, ?>> sta) {
        return new Prism<S, T, A, B>() {
            @Override
            @SuppressWarnings("unchecked")
            public <P extends Costrong, F extends Applicative, FB extends Applicative<B, F>, FT extends Applicative<T, F>, PAFB extends Costrong<A, FB, P>, PSFT extends Costrong<S, FT, P>> PSFT apply(
                    PAFB pafb, Function<? super T, ? extends FT> pure) {
                return (PSFT) pafb.<T>costrengthen().<S, FT>diMap(s -> sta.apply(s).match(Choice2::a, Choice2::b),
                                                                  tOrFb -> tOrFb.match(pure, f -> (FT) f.fmap(bt)));
            }
        };
    }

    static <S, A> SimplePrism<S, A> simplePrism(Function<? super S, ? extends Maybe<A>> sa,
                                                Function<? super A, ? extends S> as) {
        return Prism.<S, S, A, A>prism(as, s -> sa.apply(s).<Choice2<S, A>>fmap(Choice2::b).orElse(Choice2.a(s)))::apply;
    }

    interface SimplePrism<S, A> extends Prism<S, S, A, A>, Getting<Maybe<A>, S, A> {
        @Override
        default Fn2<Fn1<A, Const<Maybe<A>, A>>, S, Const<Maybe<A>, S>> getting() {
            return (f, s) -> this.<Fn1, Const<Maybe<A>, ?>, Const<Maybe<A>, A>, Const<Maybe<A>, S>, Fn1<A, Const<Maybe<A>, A>>, Fn1<S, Const<Maybe<A>, S>>>apply(f, constantly(new Const<>(nothing()))).apply(s);
        }
    }
}
