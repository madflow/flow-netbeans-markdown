package flow.netbeans.markdown.csl;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.netbeans.modules.csl.api.OffsetRange;
import org.netbeans.modules.csl.api.StructureItem;
import org.netbeans.modules.csl.api.StructureScanner;
import org.netbeans.modules.csl.spi.ParserResult;
import org.netbeans.modules.parsing.spi.ParseException;
import org.pegdown.ast.RootNode;

/**
 *
 * @author Holger
 */
public class MarkdownStructureScanner implements StructureScanner {

    public MarkdownStructureScanner() {
    }

    @Override
    public List<? extends StructureItem> scan(ParserResult pr) {
        List<? extends StructureItem> items = null;
        if (pr instanceof MarkdownParserResult) {
            MarkdownParserResult result = (MarkdownParserResult) pr;
            try {
                RootNode rootNode = result.getRootNode();
                if (rootNode != null) {
                    MarkdownTOCVisitor visitor = new MarkdownTOCVisitor();
                    rootNode.accept(visitor);
                    items = visitor.getHeaderItems();
                }
            }
            catch (ParseException ex) {
                //Exceptions.printStackTrace(ex);
            }
        }
        if (items == null) {
            items = Collections.emptyList();
        }
        return items;
    }

    @Override
    public Map<String, List<OffsetRange>> folds(ParserResult pr) {
        return Collections.emptyMap();
    }

    @Override
    public Configuration getConfiguration() {
        return new Configuration(false, false);
    }
}
