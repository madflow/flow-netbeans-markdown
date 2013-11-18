package flow.netbeans.markdown.highlighter;

import flow.netbeans.markdown.options.MarkdownGlobalOptions;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.netbeans.api.lexer.Token;
import org.netbeans.spi.lexer.Lexer;
import org.netbeans.spi.lexer.LexerInput;
import org.netbeans.spi.lexer.LexerRestartInfo;
import org.pegdown.ParsingTimeoutException;
import org.pegdown.PegDownProcessor;
import org.pegdown.ast.RootNode;


class MarkdownLexer implements Lexer<MarkdownTokenId> {
    private static final Logger LOG = Logger.getLogger(MarkdownLexer.class.getName());

    static Lexer<MarkdownTokenId> create(LexerRestartInfo<MarkdownTokenId> info) {
        return new MarkdownLexer(info);
    }
    
    private final LexerRestartInfo<MarkdownTokenId> info;
    boolean inited = false;
    private Iterator<MarkdownToken> tokenIterator = null;

    MarkdownLexer(LexerRestartInfo<MarkdownTokenId> info) {
        this.info = info;
    }

    @Override
    public Token<MarkdownTokenId> nextToken() {
        // Tokenize the input on the first nextToken() call
        if (!inited) {
            synchronized (this) {
                if (!inited) {
                    // TODO: Handle ParsingTimeoutException.
                    tokenizeInput();
                    inited = true;
                }
            }
        }

        final int readLength = info.input().readLength();

        // Nothing here
        if(readLength <= 0) {
            return null;
        }
        
        // Retrieve the next token from the iterator.
        if(tokenIterator.hasNext()) {
            MarkdownToken token = tokenIterator.next();
            return info.tokenFactory().createToken(token.getId(), token.getLength());
        }

        //  Legacy safety net. @todo remove this
        if(readLength > 0) {
            LOG.log(Level.WARNING, "Caught by legacy safety net");
            return info.tokenFactory().createToken(MarkdownTokenId.PLAIN, readLength);
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

        // Read the complete input and feed the PegDown Parser
        // Selected extensions over plain markdown are taken in account
        char[] markdownSource = readAll(info.input());

        int inputLength = markdownSource.length;

        List<MarkdownToken> tokens;
        try {
            RootNode rootNode = markdownProcessor.parseMarkdown(markdownSource);

            MarkdownTokenListBuilder builder = new MarkdownTokenListBuilder(inputLength);
            MarkdownLexerVisitor visitor = new MarkdownLexerVisitor(builder);
            rootNode.accept(visitor);
            tokens = builder.build();
        }
        catch (ParsingTimeoutException ex) {
            LOG.log(Level.WARNING, "Time out while parsing Markdown source, falling back to no highlighting");
            tokens = Collections.singletonList(new MarkdownToken(MarkdownTokenId.PLAIN, 0, inputLength));
        }

        tokenIterator = tokens.iterator();
    }
    
    private char[] readAll(LexerInput lexerInput) {
        StringBuilder sb = new StringBuilder();
        int i;
        while ((i = lexerInput.read()) != LexerInput.EOF) {
            sb.append((char) i);
        }
        char[] markdownSource = sb.toString().toCharArray();
        return markdownSource;
    }
    
    @Override
    public Object state() {
        return null;
    }

    @Override
    public void release() {
    }
}
