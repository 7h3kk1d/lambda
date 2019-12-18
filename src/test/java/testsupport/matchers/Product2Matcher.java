package testsupport.matchers;

import com.jnape.palatable.lambda.adt.product.Product2;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class Product2Matcher<_1, _2> extends TypeSafeMatcher<Product2<_1, _2>> {
    private final Matcher<_1> matcher1;
    private final Matcher<_2> matcher2;

    private Product2Matcher(Matcher<_1> matcher1, Matcher<_2> matcher2) {
        this.matcher1 = matcher1;
        this.matcher2 = matcher2;
    }

    @Override
    protected void describeMismatchSafely(Product2<_1, _2> item, Description mismatchDescription) {
        super.describeMismatchSafely(item, mismatchDescription);
    }

    @Override
    protected boolean matchesSafely(Product2<_1, _2> item) {
        return matcher1.matches(item._1()) && matcher2.matches(item._2());
    }

    @Override
    public void describeTo(Description description) {

    }

    public static <_1, _2> Product2Matcher<_1, _2> isProductThat(Matcher<_1> matcher1, Matcher<_2> matcher2) {
        return new Product2Matcher<_1, _2>(matcher1, matcher2);
    }
}
