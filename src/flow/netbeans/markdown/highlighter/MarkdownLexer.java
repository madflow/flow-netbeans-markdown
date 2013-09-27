package flow.netbeans.markdown.highlighter;

import flow.netbeans.markdown.options.MarkdownGlobalOptions;
import java.util.Iterator;
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
    
    private MarkdownVisitor markdownVisitor = new MarkdownVisitor();
    private LexerRestartInfo<MarkdownTokenId> info;
    private final LexerInput input;
    boolean inited = false;
    private Iterator<MarkdownToken> tokenIterator = null;
    private MarkdownTokenMap tokenMap;
    RootNode rootNode;

    MarkdownLexer(LexerRestartInfo<MarkdownTokenId> info) {
        this.info = info;
        this.input = info.input();
    }

    @Override
    public Token<MarkdownTokenId> nextToken() {
        
        // Tokenize the input on the first nextToken() call
        if (!inited) {
            synchronized (this) {
                if (!inited) {
                    inited = true;
                    tokenizeInput();
                }
            }
        }

        // Nothing here
        if(input.readLength() <= 0) {
            return null;
        }
        
        // There is something - but not tokenozed
        if(rootNode.getChildren().isEmpty()) {
            return info.tokenFactory().createToken(MarkdownTokenId.PLAIN);
        }
        
        // Iterate over the Token Map and spit out netbeans tokens
        while(tokenIterator.hasNext()) {
            MarkdownToken token = tokenIterator.next();
            return info.tokenFactory().createToken(token.getId(), token.getLength());
        }

        //  Legacy safety net. @todo remove this
        if(input.readLength() > 0) {
            return info.tokenFactory().createToken(MarkdownTokenId.PLAIN, input.readLength());
        }
        
        return null;
    }
    
    /**
     * Tokenizes the input
     */
    private void tokenizeInput()
    {
        MarkdownGlobalOptions markdownOptions = MarkdownGlobalOptions.getInstance();
        PegDownProcessor markdownProcessor = new PegDownProcessor(markdownOptions.getExtensionsValue());
        LexerInput lexerInput = info.input();
        StringBuilder data = new StringBuilder();
        
        // Read the complete input and feed the Pedgown Parser 
        // Selected extensions over plain markdown are taken in account
        int i;
        while ((i = lexerInput.read()) != LexerInput.EOF) {
            data.append((char) i);
        }
        int inputLength = data.length();
        rootNode = markdownProcessor.parser.parse(data.toString().toCharArray());
        rootNode.accept(markdownVisitor);
        MarkdownTokenMap tempMap = markdownVisitor.getTokenMap();
        
        // The MarkdownVisitor does leave "gaps" in the tokenized input - so we fill theses 
        // gaps with MarkdownTokenId.PLAIN tokens
        tokenMap = new MarkdownTokenMap();
        int j = 0;
        while (j <= inputLength) {
            if(tempMap.containsKey(j)) {
                MarkdownToken currentToken = (MarkdownToken) tempMap.get(j);
                tokenMap.put(j, currentToken);
                j += currentToken.getLength();
                continue;
            } else {
               tokenMap.put(j, new MarkdownToken(MarkdownTokenId.PLAIN, 1));
            }
            j += 1;
        }
        
        // Shorthand iterator
        tokenIterator = tokenMap.values().iterator();
    }
    
    
    @Override
    public Object state() {
        return null;
    }

    @Override
    public void release() {
    }
}