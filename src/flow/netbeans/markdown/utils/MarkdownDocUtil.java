/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flow.netbeans.markdown.utils;

import flow.netbeans.markdown.highlighter.MarkdownTokenId;
import javax.swing.text.AbstractDocument;
import javax.swing.text.Document;
import org.netbeans.api.lexer.TokenHierarchy;
import org.netbeans.api.lexer.TokenSequence;

/**
 *
 * @author junichi11
 */
public class MarkdownDocUtil {

    private MarkdownDocUtil() {
    }

    public static TokenSequence<MarkdownTokenId> getTokenSequence(Document document) {
        TokenHierarchy<Document> tokenHierarchy = TokenHierarchy.get(document);
        AbstractDocument ad = (AbstractDocument) document;
        ad.readLock();
        TokenSequence<MarkdownTokenId> ts;
        try {
            ts = tokenHierarchy.tokenSequence(MarkdownTokenId.language());
        } finally {
            ad.readUnlock();
        }
        return ts;
    }

}
