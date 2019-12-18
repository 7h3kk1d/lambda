package testsupport.matchers;

import com.jnape.palatable.lambda.functor.builtin.Identity;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class IdentityMatcher<A> extends TypeSafeMatcher<Identity<A>> {
    private final Matcher<A> matcher;

    private IdentityMatcher(Matcher<A> matcher) {
        this.matcher = matcher;
    }

    @Override
    protected boolean matchesSafely(Identity<A> item) {
        return matcher.matches(item.runIdentity());
    }

    @Override
    protected void describeMismatchSafely(Identity<A> item, Description mismatchDescription) {
        mismatchDescription.appendValue("was Identity of ");
        mismatchDescription.appendValue(item.runIdentity());
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("Identity of ");
        matcher.describeTo(description);
    }

    public static <A> IdentityMatcher<A> isIdentityThat(Matcher<A> matcher) {
        return new IdentityMatcher<A>(matcher);
    }
}
