package flow.netbeans.markdown.highlighter;

import org.netbeans.api.lexer.Token;
import org.netbeans.spi.lexer.Lexer;
import org.netbeans.spi.lexer.LexerInput;
import org.netbeans.spi.lexer.LexerRestartInfo;

class MarkdownLexer implements Lexer<MarkdownTokenId> {

    static Lexer<MarkdownTokenId> create(LexerRestartInfo<MarkdownTokenId> info) {
        return new MarkdownLexer(info);
    }

    private LexerRestartInfo<MarkdownTokenId> info;
    private final LexerInput input;
    private static final int EOF = LexerInput.EOF;

    MarkdownLexer(LexerRestartInfo<MarkdownTokenId> info) {
        this.info = info;
        this.input = info.input();
    }

    @Override
    public Token<MarkdownTokenId> nextToken() {

        int i = input.read();

        switch (i) {
            case EOF:
                return null;
            case '#':
                if (read("", input)) {
                    return info.tokenFactory().createToken(MarkdownTokenId.HEADER);
                }
                return info.tokenFactory().createToken(MarkdownTokenId.PLAIN);
            case '=':
                if (read("==", input)) {
                    return info.tokenFactory().createToken(MarkdownTokenId.HEADER);
                }
                return info.tokenFactory().createToken(MarkdownTokenId.PLAIN);
            case '-':
                if (read("--", input)) {
                    return info.tokenFactory().createToken(MarkdownTokenId.HEADER);
                }
                return info.tokenFactory().createToken(MarkdownTokenId.PLAIN);
            case '>':
                if (read(" ", input)) {
                    return info.tokenFactory().createToken(MarkdownTokenId.BLOCKQUOTE);
                }
                return info.tokenFactory().createToken(MarkdownTokenId.PLAIN);
            case '+':
                if (read(" ", input)) {
                    return info.tokenFactory().createToken(MarkdownTokenId.LIST);
                }
                return info.tokenFactory().createToken(MarkdownTokenId.PLAIN);
            case ' ':
                if (read("+", input) || read("-", input) ) {
                    return info.tokenFactory().createToken(MarkdownTokenId.LIST);
                }
                return info.tokenFactory().createToken(MarkdownTokenId.PLAIN);
            default:
                read("", input);
                return info.tokenFactory().createToken(MarkdownTokenId.PLAIN);
        }
    }

    private static boolean read(String text, LexerInput input) {

        boolean result = true;

        for (int i = 0; i < text.length(); i++) {
            int c;
            if (text.charAt(i) != (c = input.read())) {
                result = false;
                if (c == '\n' || c == '\r') {
                    input.backup(1);
                }
                break;
            }
        }

        int i = input.read();

        while (i != '\n'
                && i != '\r'
                && i != LexerInput.EOF) {
            i = input.read();
        }
        while (i != LexerInput.EOF
                && (i == '\n'
                || i == '\r')) {
            i = input.read();
        }
        if (i != LexerInput.EOF) {
            input.backup(1);
        }
        return result;
    }

    @Override
    public Object state() {
        return null;
    }

    @Override
    public void release() {
    }

    private void parseInput(){

        LexerInput input = info.input();
        // Read the whole data into a string (parboiled works of a string, not a stream)
        StringBuffer data = new StringBuffer();
        int i = 0;
        while ((i = input.read()) != LexerInput.EOF)
        {
                data.append((char) i);
        }



    }

}
