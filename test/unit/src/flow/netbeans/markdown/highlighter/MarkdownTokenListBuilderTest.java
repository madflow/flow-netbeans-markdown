
package flow.netbeans.markdown.highlighter;

import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import static flow.netbeans.markdown.matchers.Matchers.*;
import static flow.netbeans.markdown.highlighter.MarkdownTokenId.*;

import org.junit.Before;

/**
 * Unit tests for {@link MarkdownTokenListBuilder}.
 */
public class MarkdownTokenListBuilderTest {
    private static final int DEFAULT_INPUT_LENGTH = 10;

    private MarkdownTokenListBuilder builder;

    @Before
    public void createBuilder() {
        builder = new MarkdownTokenListBuilder(DEFAULT_INPUT_LENGTH);
    }

    @Test
    public void testNoToken() {
        final List<MarkdownToken> tokens = builder.build();
        
        assertThat(tokens, everyItem(nonZeroLength()));
        assertThat(totalLengthOf(tokens), equalTo(DEFAULT_INPUT_LENGTH));

        assertThat(tokens.size(), equalTo(1));
    }

    @Test
    public void testSingleTokenWithFullCoverage() {
        builder.addLeafTreeToken(CODE, 0, DEFAULT_INPUT_LENGTH);

        final List<MarkdownToken> tokens = builder.build();

        assertThat(tokens, everyItem(nonZeroLength()));
        assertThat(totalLengthOf(tokens), equalTo(DEFAULT_INPUT_LENGTH));

        assertThat(tokens.size(), equalTo(1));
        assertThat(tokens, containsTokensWithId(CODE));
    }

    @Test
    public void testSingleTokenWithoutFullCoverage() {
        builder.addLeafTreeToken(CODE, 1, DEFAULT_INPUT_LENGTH - 1);

        final List<MarkdownToken> tokens = builder.build();

        assertThat(tokens, everyItem(nonZeroLength()));
        assertThat(totalLengthOf(tokens), equalTo(DEFAULT_INPUT_LENGTH));

        assertThat(tokens.size(), equalTo(3));
        assertThat(tokens, containsTokensWithId(WHITESPACE, CODE, WHITESPACE));
        assertThat(tokens, containsTokensWithLength(1, DEFAULT_INPUT_LENGTH - 2, 1));
    }

    @Test
    public void testNestedTokenWithLargerRangeThanOuterToken() {
        builder.beginTreeToken(CODE, 1, DEFAULT_INPUT_LENGTH - 1);
        builder.addLeafTreeToken(EMPH, 0, DEFAULT_INPUT_LENGTH);
        builder.endTreeToken();

        final List<MarkdownToken> tokens = builder.build();

        assertThat(tokens, everyItem(nonZeroLength()));
        assertThat(totalLengthOf(tokens), equalTo(DEFAULT_INPUT_LENGTH));

        assertThat(tokens.size(), equalTo(3));
        assertThat(tokens, containsTokensWithId(WHITESPACE, EMPH, WHITESPACE));
        assertThat(tokens, containsTokensWithLength(1, DEFAULT_INPUT_LENGTH - 2, 1));
    }

    @Test
    public void testEmptyInput() {
        builder = new MarkdownTokenListBuilder(0);

        List<MarkdownToken> tokens = builder.build();

        assertThat(tokens.size(), equalTo(0));
    }

    @Test(expected = IllegalStateException.class)
    public void testUnmatchedEndTokenCall() {
        builder.endTreeToken();
    }

    @Test(expected = IllegalStateException.class)
    public void testUnmatchedBeginTokenCall() {
        builder.beginTreeToken(CODE, 0, DEFAULT_INPUT_LENGTH);

        builder.build();
    }

    @Test(expected = IllegalStateException.class)
    public void testBeginTokenCallAfterBuild() {
        builder.build();

        builder.beginTreeToken(CODE, 0, DEFAULT_INPUT_LENGTH);
    }

    @Test
    public void testBuildTwice() {
        builder.build();
        try {
            builder.build();
        } catch (IllegalStateException ex) {
            fail("Failed to build twice");
        }
    }
}
