
package flow.netbeans.markdown.csl;

import java.util.Collections;
import java.util.Set;
import org.netbeans.modules.csl.api.ElementHandle;
import org.netbeans.modules.csl.api.ElementKind;
import org.netbeans.modules.csl.api.Modifier;
import org.netbeans.modules.csl.api.OffsetRange;
import org.netbeans.modules.csl.spi.ParserResult;
import org.openide.filesystems.FileObject;

/**
 *
 * @author Holger
 */
public class MarkdownTOCEntryHandle implements ElementHandle {
    private final FileObject file;
    private final String name;
    private final int startIndex;
    private final int endIndex;

    public MarkdownTOCEntryHandle(FileObject file, String name, int startIndex, int endIndex) {
        this.file = file;
        this.name = name;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
    }

    @Override
    public FileObject getFileObject() {
        return file;
    }

    @Override
    public String getMimeType() {
        return null;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getIn() {
        return null;
    }

    @Override
    public ElementKind getKind() {
        return ElementKind.TAG;
    }

    @Override
    public Set<Modifier> getModifiers() {
        return Collections.emptySet();
    }

    @Override
    public boolean signatureEquals(ElementHandle eh) {
        if (eh instanceof MarkdownTOCEntryHandle) {
            return name.equals(((MarkdownTOCEntryHandle) eh).name);
        }
        return false;
    }

    @Override
    public OffsetRange getOffsetRange(ParserResult pr) {
        return new OffsetRange(startIndex, endIndex);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + (this.file != null ? this.file.hashCode() : 0);
        hash = 53 * hash + (this.name != null ? this.name.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final MarkdownTOCEntryHandle other = (MarkdownTOCEntryHandle) obj;
        if (this.file != other.file && (this.file == null || !this.file.equals(other.file))) {
            return false;
        }
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
            return false;
        }
        return true;
    }
}
