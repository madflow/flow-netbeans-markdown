
package flow.netbeans.markdown.preview;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

/**
 *
 * @author Holger Stenger
 */
public class SwingHtmlView extends AbstractHtmlView {
    private final JTextPane textPane;

    private final JScrollPane scrollPane;

    public SwingHtmlView() {
        textPane = new JTextPane();
        textPane.setContentType("text/html");
        textPane.setEditable(false);
        textPane.addHyperlinkListener(new HyperlinkListener() {
            @Override
            public void hyperlinkUpdate(HyperlinkEvent e) {
                if (HyperlinkEvent.EventType.ENTERED.equals(e.getEventType())) {
                    final String statusMessage = e.getDescription();
                    setStatusMessage(statusMessage);
                } else if (HyperlinkEvent.EventType.EXITED.equals(e.getEventType())) {
                    setStatusMessage(null);
                } else if (HyperlinkEvent.EventType.ACTIVATED.equals(e.getEventType())) {
                    if (e.getURL() != null) {
                        showURLExternal(e.getURL());
                    }
                }
            }
        });

        scrollPane = new JScrollPane(textPane);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
    }

    @Override
    public JComponent getComponent() {
        return scrollPane;
    }

    @Override
    public void setContent(String content) {
        textPane.setText(content);
    }

    @Override
    public boolean isHtmlFullySupported() {
        return false;
    }
}
