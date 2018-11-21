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

import java.util.Collections;
import java.util.Map;
import java.util.function.Function;

import static com.jnape.palatable.lambda.adt.Maybe.maybe;
import static com.jnape.palatable.lambda.adt.choice.Choice2.b;
import static com.jnape.palatable.lambda.functions.Fn1.fn1;
import static com.jnape.palatable.lambda.functions.builtin.fn1.Constantly.constantly;
import static com.jnape.palatable.lambda.lens.functions.View.view;

public interface Prism<S, T, A, B> extends Getting<T, B, T> {

    <P extends Costrong, F extends Applicative, FB extends Applicative<B, F>, FT extends Applicative<T, F>,
            PAFB extends Costrong<A, FB, P>,
            PSFT extends Costrong<S, FT, P>> PSFT apply(PAFB pafb, Function<? super T, ? extends FT> pure);

    @Override
    default Fn2<Function<? super T, ? extends Const<T, T>>, B, Const<T, B>> getting() {
        AReview<T, B> p = bIdentityTagged -> new Tagged<>(this.<Tagged, Identity, Identity<B>, Identity<T>, Tagged<A, Identity<B>>, Tagged<S, Identity<T>>>apply(new Tagged<>(bIdentityTagged.unTagged()), Identity::new).unTagged());
        return (f, b) -> re(p).apply(fn1(f)).apply(b);
    }

    static <B, T> Fn1<B, T> hash(Fn1<Tagged<B, Identity<B>>, Tagged<T, Identity<T>>> f) {
        return b -> f.apply(new Tagged<>(new Identity<>(b))).unTagged().runIdentity();
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

    static <S, A> Prism<S, S, A, A> simplePrism(Function<? super S, ? extends Maybe<A>> sa,
                                                Function<? super A, ? extends S> as) {
        return prism(as, s -> sa.apply(s).<Choice2<S, A>>fmap(Choice2::b).orElse(Choice2.a(s)));
    }

    public static <T, B> Fn1<Fn1<T, Const<T, T>>, Fn1<B, Const<T, B>>> re(AReview<T, B> p) {
        Fn1<B, T> bt = b -> p.apply(new Tagged<>(new Identity<>(b))).unTagged().runIdentity();
        return tIdentityFn1 -> b -> tIdentityFn1.contraMap(bt).apply(b).fmap(constantly(b));
    }


    public static <S, T, A, B> void playtime(Prism<S, T, A, B> prism) {
        AReview<T, B> p = bIdentityTagged -> new Tagged<>(prism.<Tagged, Identity, Identity<B>, Identity<T>, Tagged<A, Identity<B>>, Tagged<S, Identity<T>>>apply(new Tagged<>(bIdentityTagged.unTagged()), Identity::new).unTagged());
        Fn1<Fn1<T, Const<T, T>>, Fn1<B, Const<T, B>>> re = re(p);
        view((Getting<T, B, T>) () -> (function, b) -> re.apply(fn1(function)).apply(b));
    }

    public static void main(String[] args) {
        Function<? super Map<String, Integer>, ? extends Maybe<Integer>> sa = m -> maybe(m.get("foo"));
        Prism<Map<String, Integer>, Map<String, Integer>, Integer, Integer> prism = simplePrism(sa, i -> Collections.singletonMap("foo", i));




    }

    public static interface AReview<T, B> extends Fn1<Tagged<B, Identity<B>>, Tagged<T, Identity<T>>> {
    }

        /*

    re :: AReview t b -> Getter b t


    type Getter s a = forall f. (Contravariant f, Functor f) => (a -> f a) -> s -> f s

    type AReview t b = Optic' Tagged Identity t b
    type Optic' p f s a = Optic p f s s a a
    type Optic p f s t a b = p a (f b) -> p s (f t)

    so...

    type AReview t b = Tagged b (Identity b) -> Tagged t (Identity t)
    type Getter b t = (t -> f t) -> b -> f b

     */

    public static interface Getter<B, T> extends Fn1<Fn1<T, Identity<T>>, Fn1<B, Identity<B>>> {
    }

    public static final class Tagged<S, B> implements Costrong<S, B, Tagged> {
        private final B b;

        public Tagged(B b) {
            this.b = b;
        }

        public B unTagged() {
            return b;
        }

        @Override
        public <C> Tagged<Choice2<C, S>, Choice2<C, B>> costrengthen() {
            return new Tagged<>(b(b));
        }

        @Override
        public <Z, C> Tagged<Z, C> diMap(Function<? super Z, ? extends S> lFn,
                                         Function<? super B, ? extends C> rFn) {
            return new Tagged<>(rFn.apply(b));
        }
    }
}
