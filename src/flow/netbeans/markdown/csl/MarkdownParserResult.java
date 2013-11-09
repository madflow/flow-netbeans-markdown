
package flow.netbeans.markdown.csl;

import java.util.Collections;
import java.util.List;
import org.netbeans.modules.csl.api.Error;
import org.netbeans.modules.csl.spi.ParserResult;
import org.netbeans.modules.parsing.api.Snapshot;
import org.netbeans.modules.parsing.spi.ParseException;
import org.pegdown.ast.RootNode;

/**
 *
 * @author Holger
 */
public class MarkdownParserResult extends ParserResult {
    private final RootNode rootNode;

    private boolean valid;
    private final int extensions;

    public MarkdownParserResult(Snapshot snapshot, RootNode rootNode, int extensions) {
        super(snapshot);
        valid = true;
        this.rootNode = rootNode;
        this.extensions = extensions;
    }

    public RootNode getRootNode() throws ParseException {
        if (!valid) {
            throw new ParseException();
        }
        return rootNode;
    }

    public int getExtensions() throws ParseException {
        if (!valid) {
            throw new ParseException();
        }
        return extensions;
    }

    @Override
    protected void invalidate() {
        valid = false;
    }

    @Override
    public List<? extends Error> getDiagnostics() {
        return Collections.emptyList();
    }
}
