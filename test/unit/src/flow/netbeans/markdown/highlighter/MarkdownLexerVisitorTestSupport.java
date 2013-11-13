
package flow.netbeans.markdown.highlighter;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import static flow.netbeans.markdown.matchers.Matchers.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import org.pegdown.PegDownProcessor;
import org.pegdown.ast.RootNode;

/**
 *
 * @author Holger
 */
public class MarkdownLexerVisitorTestSupport {
    public static final String TEST_RESOURCES = "/flow/netbeans/markdown/testresources/";

    public MarkdownLexerVisitorTestSupport() {
    }

    public void runTestWithExtensions(String baseName, int extensions) throws IOException {
        String source = getTestResourceAsString(baseName + ".md");
        PegDownProcessor processor = new PegDownProcessor(extensions);
        RootNode rootNode = processor.parseMarkdown(source.toCharArray());

        assertThat("rootNode", rootNode, notNullValue());

        final int totalLength = source.length();
        MarkdownTokenListBuilder builder = new MarkdownTokenListBuilder(totalLength);
        MarkdownLexerVisitor visitor = new MarkdownLexerVisitor(builder);
        rootNode.accept(visitor);
        List<MarkdownToken> tokens = builder.build();
        
        assertThat(tokens, everyItem(nonZeroLength()));
        assertThat(totalLengthOf(tokens), equalTo(totalLength));
    }

    public String getTestResourceAsString(String name) throws IOException {
        final String fullName = TEST_RESOURCES + name;
        InputStream stream = getClass().getResourceAsStream(fullName);
        if (stream == null) {
            throw new FileNotFoundException("Test resource not found: " + fullName);
        }
        try {
            InputStreamReader reader = new InputStreamReader(stream, "UTF-8");
            StringBuilder sb = new StringBuilder();
            try {
                char[] buffer = new char[4096];
                boolean done = false;
                while (!done) {
                    int count = reader.read(buffer);
                    if (count < 0) {
                        done = true;
                    } else {
                        sb.append(buffer, 0, count);
                    }
                }
                return buffer.toString();
            }
            finally {
                try {
                    reader.close();
                }
                catch (IOException ex) {
                    // Ignore.
                }
            }
        }
        finally {
            try {
                stream.close();
            } catch (IOException ex) {
                // Ignore.
            }
        }
    }
}
