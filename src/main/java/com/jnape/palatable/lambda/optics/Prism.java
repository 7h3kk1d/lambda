package com.jnape.palatable.lambda.optics;

import com.jnape.palatable.lambda.adt.Maybe;
import com.jnape.palatable.lambda.adt.choice.Choice2;
import com.jnape.palatable.lambda.functions.Fn1;
import com.jnape.palatable.lambda.functions.Fn2;
import com.jnape.palatable.lambda.functor.Applicative;
import com.jnape.palatable.lambda.functor.Costrong;
import com.jnape.palatable.lambda.functor.builtin.Const;

import java.util.Collections;
import java.util.Map;
import java.util.function.Function;

import static com.jnape.palatable.lambda.adt.Maybe.just;
import static com.jnape.palatable.lambda.adt.Maybe.maybe;
import static com.jnape.palatable.lambda.adt.Maybe.nothing;
import static com.jnape.palatable.lambda.functions.builtin.fn1.Constantly.constantly;
import static com.jnape.palatable.lambda.optics.View2.view;

public interface Prism<S, T, A, B> extends Getting<Maybe<A>, S, A> {

    <P extends Costrong, F extends Applicative, FB extends Applicative<B, F>, FT extends Applicative<T, F>,
            PAFB extends Costrong<A, FB, P>,
            PSFT extends Costrong<S, FT, P>> PSFT apply(PAFB pafb, Function<? super T, ? extends FT> pure);

    @Override
    default Fn2<Function<? super Maybe<A>, ? extends Const<Maybe<A>, A>>, S, Maybe<A>> getting() {
        return (arb, s) -> this.<Fn1, Const<Maybe<A>, ?>, Const<Maybe<A>, B>, Const<Maybe<A>, T>, Fn1<A, Const<Maybe<A>, B>>, Fn1<S, Const<Maybe<A>, T>>>apply(a -> new Const<>(just(a)), constantly(new Const<>(nothing()))).apply(s).runConst();
    }

    static <S, T, A, B> Prism<S, T, A, B> prism(Function<? super B, ? extends T> bt,
                                                Function<? super S, ? extends Choice2<T, A>> sta) {
        return new Prism<S, T, A, B>() {
            @Override
            @SuppressWarnings("unchecked")
            public <P extends Costrong, F extends Applicative, FB extends Applicative<B, F>, FT extends Applicative<T, F>, PAFB extends Costrong<A, FB, P>, PSFT extends Costrong<S, FT, P>> PSFT apply(
                    PAFB pafb, Function<? super T, ? extends FT> pure) {
                return (PSFT) pafb.<T>costrengthen().<S, FT>diMap(sta, tOrFb -> tOrFb.match(pure, f -> (FT) f.fmap(bt)));
            }
        };
    }

    static <S, A> Prism<S, S, A, A> simplePrism(Function<? super S, ? extends Maybe<A>> sa,
                                                Function<? super A, ? extends S> as) {
        return prism(as, s -> sa.apply(s).<Choice2<S, A>>fmap(Choice2::b).orElse(Choice2.a(s)));
    }

    public static void main(String[] args) {
        Function<? super Integer, ? extends Map<String, Integer>> as = v -> Collections.singletonMap("foo", v);
        Prism<Map<String, Integer>, Map<String, Integer>, Integer, Integer> atFoo = simplePrism(m -> maybe(m.get("foo")),
                                                                                                as);

        System.out.println(view(atFoo, Collections.singletonMap("foo",2)));


    }
}
