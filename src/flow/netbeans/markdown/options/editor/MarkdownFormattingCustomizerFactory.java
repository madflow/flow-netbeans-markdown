/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flow.netbeans.markdown.options.editor;

import java.util.prefs.Preferences;
import org.netbeans.modules.options.editor.spi.PreferencesCustomizer;

/**
 *
 * @author Holger Stenger
 */
public class MarkdownFormattingCustomizerFactory implements PreferencesCustomizer.Factory {

    @Override
    public PreferencesCustomizer create(Preferences p) {
        return new MarkdownFormattingCustomizer("text/x-markdown", p);
    }

}
