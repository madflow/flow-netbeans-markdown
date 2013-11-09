package flow.netbeans.markdown.csl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.netbeans.modules.csl.api.OffsetRange;
import org.netbeans.modules.csl.api.StructureItem;
import org.netbeans.modules.csl.api.StructureScanner;
import org.netbeans.modules.csl.spi.ParserResult;
import org.netbeans.modules.parsing.spi.ParseException;
import org.openide.filesystems.FileObject;
import org.openide.util.Exceptions;
import org.pegdown.Extensions;
import org.pegdown.ast.Node;
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
                int extensions = result.getExtensions();
                if (rootNode != null) {
                    FileObject file = pr.getSnapshot().getSource().getFileObject();
                    MarkdownTOCVisitor visitor = new MarkdownTOCVisitor(file);
                    rootNode.accept(visitor);
                    MarkdownTOCRootItem tocRootItem
                            = new MarkdownTOCRootItem(file, rootNode, visitor.getTOCEntryItems());
                    MarkdownReferencesRootItem refsRootItem
                            = new MarkdownReferencesRootItem(file, rootNode);

                    if ((extensions & Extensions.ABBREVIATIONS) != 0) {
                        MarkdownAbbreviationsRootItem abbrRootItem
                                = new MarkdownAbbreviationsRootItem(file, rootNode);
                        items = Arrays.asList(tocRootItem, refsRootItem, abbrRootItem);
                    } else {
                        items = Arrays.asList(tocRootItem, refsRootItem);
                    }
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
        Map<String, List<OffsetRange>> foldsByType = null;
        if (pr instanceof MarkdownParserResult) {
            MarkdownParserResult result = (MarkdownParserResult) pr;
            try {
                RootNode rootNode = result.getRootNode();
                if (rootNode != null) {
                    List<OffsetRange> sectionFolds = new ArrayList<OffsetRange>();
                    for (Node node : rootNode.getChildren()) {
                        MarkdownTOCVisitor visitor = new MarkdownTOCVisitor(pr.getSnapshot().getSource().getFileObject());
                        rootNode.accept(visitor);
                        sectionFolds = visitor.getOffsetRanges();
                    }
                    foldsByType = Collections.singletonMap("comments", sectionFolds);
                }
            }
            catch (ParseException ex) {
                Exceptions.printStackTrace(ex);
            }
        }
        if (foldsByType == null) {
            foldsByType = Collections.emptyMap();
        }
        return foldsByType;
    }

    @Override
    public Configuration getConfiguration() {
        return new Configuration(false, false);
    }
}
