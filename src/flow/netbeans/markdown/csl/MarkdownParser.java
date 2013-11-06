
package flow.netbeans.markdown.csl;

import flow.netbeans.markdown.options.MarkdownGlobalOptions;
import javax.swing.event.ChangeListener;
import org.netbeans.modules.parsing.api.Snapshot;
import org.netbeans.modules.parsing.api.Task;
import org.netbeans.modules.parsing.spi.ParseException;
import org.netbeans.modules.parsing.spi.Parser;
import org.netbeans.modules.parsing.spi.SourceModificationEvent;
import org.pegdown.PegDownProcessor;
import org.pegdown.ast.RootNode;

/**
 *
 * @author Holger
 */
public class MarkdownParser extends Parser {
    private Snapshot snapshot;
    private RootNode rootNode;

    public MarkdownParser() {
    }

    @Override
    public void parse(Snapshot snapshot, Task task, SourceModificationEvent event) throws ParseException {
        this.snapshot = snapshot;

        CharSequence text = snapshot.getText();

        MarkdownGlobalOptions markdownOptions = MarkdownGlobalOptions.getInstance();
        PegDownProcessor markdownProcessor = new PegDownProcessor(markdownOptions.getExtensionsValue());

        rootNode = markdownProcessor.parser.parse(text.toString().toCharArray());
    }

    @Override
    public Result getResult(Task task) throws ParseException {
        return new MarkdownParserResult(snapshot, rootNode);
    }

    @Override
    public void addChangeListener(ChangeListener changeListener) {
    }

    @Override
    public void removeChangeListener(ChangeListener changeListener) {
    }
}
