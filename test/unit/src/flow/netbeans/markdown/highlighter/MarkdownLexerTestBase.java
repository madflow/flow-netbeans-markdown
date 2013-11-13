package flow.netbeans.markdown.highlighter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;
import org.netbeans.api.lexer.Language;
import org.netbeans.api.lexer.TokenHierarchy;
import org.netbeans.api.lexer.TokenId;
import org.netbeans.api.lexer.TokenSequence;
import org.openide.util.Utilities;

public class MarkdownLexerTestBase {
    
    protected String getTestResult(String filename) throws Exception {
        String content = getFileContent(getTestFile(filename));
        Language<MarkdownTokenId> language = MarkdownTokenId.language();
        TokenHierarchy<?> hierarchy = TokenHierarchy.create(content, language);
        return createResult(hierarchy.tokenSequence(language));
    }

    private String createResult(TokenSequence<?> ts) throws Exception {
        StringBuilder result = new StringBuilder();
        while (ts.moveNext()) {
            TokenId tokenId = ts.token().id();
            CharSequence text = ts.token().text();
            result.append("token #");
            result.append(ts.index());
            result.append(" ");
            result.append(tokenId.name());
            String token = replaceLinesAndTabs(text.toString());
            if (!token.isEmpty()) {
                result.append(" ");
                result.append("[");
                result.append(token);
                result.append("]");
            }
            result.append("\n");
        }
        return result.toString();
    }

    protected File getTestDataDir() {
        URL codebase = getClass().getProtectionDomain().getCodeSource().getLocation();
        if (!codebase.getProtocol().equals("file")) {
            throw new Error("Cannot find data directory from " + codebase);
        }
        File dataDir;
        try {
            dataDir = new File(Utilities.toFile(codebase.toURI()).getParentFile(), "data");
        } catch (URISyntaxException x) {
            throw new Error(x);
        }
        return dataDir;
    }

    protected File getGoldenFilesDir() {
        return new File(getTestDataDir(), "goldenfiles");
    }

    protected File getTestFilesDir() {
        return new File(getTestDataDir(), "testfiles");
    }
    
    protected File getGoldenFile(String name) {
        return new File(getGoldenFilesDir(), name);
    }
    
    protected String getGoldenFileContent(String name) throws IOException {
        return getFileContent(getGoldenFile(name));
    }
    
    protected String getTestFileContent(String name) throws IOException {
        return getFileContent(getTestFile(name));
    }
    
    protected File getTestFile(String name) {
        return new File(getTestFilesDir(), name);
    }

    /**
     * Get content of the file.
     */
    public static String getFileContent(File file) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
        try {
            String line = br.readLine();
            while (line != null) {
                sb.append(line);
                sb.append("\n");
                line = br.readLine();
            }
        } finally {
            br.close();
        }
        return sb.toString();
    }

    /**
     * Escape "\n", "\r" and "\t".
     */
    public static String replaceLinesAndTabs(String input) {
        String escapedString = input;
        escapedString = escapedString.replaceAll("\n", "\\\\n"); //NOI18N
        escapedString = escapedString.replaceAll("\r", "\\\\r"); //NOI18N
        escapedString = escapedString.replaceAll("\t", "\\\\t"); //NOI18N
        return escapedString;
    }
}
