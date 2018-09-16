package com.jnape.palatable.lambda.functions;

import com.jnape.palatable.traitor.annotations.TestTraits;
import com.jnape.palatable.traitor.runners.Traits;
import org.junit.Test;
import org.junit.runner.RunWith;
import testsupport.EqualityAwareFn1;
import testsupport.traits.ApplicativeLaws;
import testsupport.traits.FunctorLaws;
import testsupport.traits.MonadLaws;

import java.util.function.Function;

import static com.jnape.palatable.lambda.adt.Maybe.just;
import static com.jnape.palatable.lambda.functions.builtin.fn2.ReduceLeft.reduceLeft;
import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

@RunWith(Traits.class)
public class Fn1Test {

    @TestTraits({FunctorLaws.class, ApplicativeLaws.class, MonadLaws.class})
    public Fn1<String, Integer> testSubject() {
        return new EqualityAwareFn1<>("1", Integer::parseInt);
    }

    @Test
    public void fn1() {
        Function<String, Integer> parseInt = Integer::parseInt;
        assertEquals((Integer) 1, Fn1.fn1(parseInt).apply("1"));
    }

    @Test
    public void thunk() {
        Fn1<Integer, String> toString = Object::toString;
        assertEquals("1", toString.thunk(1).apply());
    }

    @Test
    public void widen() {
        Fn1<Integer, Integer> addOne = x -> x + 1;
        assertEquals(just(4), reduceLeft(addOne.widen().toBiFunction(), asList(1, 2, 3)));
    }
}
