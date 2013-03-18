package flow.netbeans.markdown.highlighter;

import org.netbeans.api.lexer.Token;
import org.netbeans.spi.lexer.Lexer;
import org.netbeans.spi.lexer.LexerInput;
import org.netbeans.spi.lexer.LexerRestartInfo;
import org.pegdown.PegDownProcessor;
import org.pegdown.ast.RootNode;

class MarkdownLexer implements Lexer<MarkdownTokenId> {

    static Lexer<MarkdownTokenId> create(LexerRestartInfo<MarkdownTokenId> info) {
        return new MarkdownLexer(info);
    }

    private LexerRestartInfo<MarkdownTokenId> info;
    private final LexerInput input;
    private MarkdownVisitor markdownVisitor = new MarkdownVisitor();
    private final PegDownProcessor markdownProcessor;

    MarkdownLexer(LexerRestartInfo<MarkdownTokenId> info) {
        this.info = info;
        this.input = info.input();
        markdownProcessor = new PegDownProcessor();
    }

    @Override
    public Token<MarkdownTokenId> nextToken() {
                int i;
        while (true) {
            i = input.read();
            while (i != '\n' && i != '\r' && i != LexerInput.EOF) {
                i = input.read();
            }
            if(i == LexerInput.EOF) {
                break;
            }
            int peek = input.read();
            if(peek != '\n' 
                    && peek != '\r' 
                    && peek != '>' 
                    && peek != '+' 
                    && peek != '*' 
                    && peek != '-'
                    && peek != '#' 
                    && peek != LexerInput.EOF) {
                input.backup(1);
                continue;
            } else {
                input.backup(1);
            }
            CharSequence data = input.readText();
            RootNode node = markdownProcessor.parser.parse(data.toString().toCharArray());
            node.accept(markdownVisitor);
            return info.tokenFactory().createToken(markdownVisitor.getCurrentToken());
        }
        return null;
    }
    @Override
    public Object state() {
        return null;
    }

    @Override
    public void release() {
    }
}
