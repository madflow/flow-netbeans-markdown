
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

    public MarkdownParserResult(Snapshot snapshot, RootNode rootNode) {
        super(snapshot);
        valid = true;
        this.rootNode = rootNode;
    }

    public RootNode getRootNode() throws ParseException {
        if (!valid) {
            throw new ParseException();
        }
        return rootNode;
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
