
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

    private MarkdownTokenListBuilder sut;

    @Before
    public void createBuilder() {
        sut = new MarkdownTokenListBuilder(DEFAULT_INPUT_LENGTH);
    }

    @Test
    public void testNoToken() {
        final List<MarkdownToken> tokens = sut.build();
        
        assertThat(tokens, everyItem(nonZeroLength()));
        assertThat(totalLengthOf(tokens), equalTo(DEFAULT_INPUT_LENGTH));

        assertThat(tokens.size(), equalTo(1));
    }

    @Test
    public void testSingleTokenWithFullCoverage() {
        sut.addLeafTreeToken(CODE, 0, DEFAULT_INPUT_LENGTH);

        final List<MarkdownToken> tokens = sut.build();

        assertThat(tokens, everyItem(nonZeroLength()));
        assertThat(totalLengthOf(tokens), equalTo(DEFAULT_INPUT_LENGTH));

        assertThat(tokens.size(), equalTo(1));
        assertThat(tokens, containsTokensWithId(CODE));
    }

    @Test
    public void testSingleTokenWithoutFullCoverage() {
        sut.addLeafTreeToken(CODE, 1, DEFAULT_INPUT_LENGTH - 1);

        final List<MarkdownToken> tokens = sut.build();

        assertThat(tokens, everyItem(nonZeroLength()));
        assertThat(totalLengthOf(tokens), equalTo(DEFAULT_INPUT_LENGTH));

        assertThat(tokens.size(), equalTo(3));
        assertThat(tokens, containsTokensWithId(WHITESPACE, CODE, WHITESPACE));
        assertThat(tokens, containsTokensWithLength(1, DEFAULT_INPUT_LENGTH - 2, 1));
    }

    @Test
    public void testNestedTokenWithLargerRangeThanOuterToken() {
        sut.beginTreeToken(CODE, 1, DEFAULT_INPUT_LENGTH - 1);
        sut.addLeafTreeToken(EMPH, 0, DEFAULT_INPUT_LENGTH);
        sut.endTreeToken();

        final List<MarkdownToken> tokens = sut.build();

        assertThat(tokens, everyItem(nonZeroLength()));
        assertThat(totalLengthOf(tokens), equalTo(DEFAULT_INPUT_LENGTH));

        assertThat(tokens.size(), equalTo(3));
        assertThat(tokens, containsTokensWithId(WHITESPACE, EMPH, WHITESPACE));
        assertThat(tokens, containsTokensWithLength(1, DEFAULT_INPUT_LENGTH - 2, 1));
    }

    @Test
    public void testEmptyInput() {
        sut = new MarkdownTokenListBuilder(0);

        List<MarkdownToken> tokens = sut.build();

        assertThat(tokens.size(), equalTo(0));
    }

    @Test(expected = IllegalStateException.class)
    public void testUnmatchedEndTokenCall() {
        sut.endTreeToken();
    }

    @Test(expected = IllegalStateException.class)
    public void testUnmatchedBeginTokenCall() {
        sut.beginTreeToken(CODE, 0, DEFAULT_INPUT_LENGTH);

        sut.build();
    }

    @Test(expected = IllegalStateException.class)
    public void testBeginTokenCallAfterBuild() {
        sut.build();

        sut.beginTreeToken(CODE, 0, DEFAULT_INPUT_LENGTH);
    }
}
