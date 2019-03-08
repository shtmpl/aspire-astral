package aspire.astral;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class AnyUnitTest {

    @Test
    public void shouldRunWhenItShould() throws Exception {
        assertThat(42, is(Answers.answerTo("The Ultimate Question of Life, the Universe, and Everything")));
    }
}
