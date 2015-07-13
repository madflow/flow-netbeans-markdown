package flow.netbeans.markdown.preview.ext;

import flow.netbeans.markdown.preview.AbstractHtmlView;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.embed.swing.JFXPanel;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebEvent;
import javafx.scene.web.WebView;
import javafx.util.Callback;
import javax.swing.JComponent;

public class FXHtmlView extends AbstractHtmlView {

    private static final int PANEL_WIDTH_INT = 675;

    private static final int PANEL_HEIGHT_INT = 400;

    private final JFXPanel fxPanel;

    private WebView webView;

    private String currentContent = "";

    public FXHtmlView() {
        fxPanel = new JFXPanel();
        Platform.setImplicitExit(false);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                createScene();
            }
        });
    }

    @Override
    public JComponent getComponent() {
        return fxPanel;
    }

    @Override
    public void setContent(final String content) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                currentContent = content;

                final Number x = (Number) webView.getEngine().executeScript("document.body.scrollLeft");
                final Number y = (Number) webView.getEngine().executeScript("document.body.scrollTop");

                webView.getEngine().loadContent(currentContent);

                final Worker<Void> loadWorker = webView.getEngine().getLoadWorker();
                loadWorker.stateProperty().addListener(new ChangeListener<Worker.State>() {
                    @Override
                    public void changed(ObservableValue<? extends Worker.State> observable,
                            Worker.State oldValue, Worker.State newValue) {
                        switch (newValue) {
                            case SUCCEEDED:
                                loadWorker.stateProperty().removeListener(this);
                                String script = String.format("window.scrollTo(%d,%d);", x, y);
                                webView.getEngine().executeScript(script);
                                break;
                            case FAILED:
                            case CANCELLED:
                                loadWorker.stateProperty().removeListener(this);
                                break;
                        }
                    }
                });
            }
        });
    }

    @Override
    public boolean isHtmlFullySupported() {
        return true;
    }

    private void createScene() {
        Double widthDouble = new Integer(PANEL_WIDTH_INT).doubleValue();
        Double heightDouble = new Integer(PANEL_HEIGHT_INT).doubleValue();

        webView = new WebView();
        webView.setMinSize(widthDouble, heightDouble);
        webView.setPrefSize(widthDouble, heightDouble);
        webView.setContextMenuEnabled(false);
        webView.getEngine().setConfirmHandler(new Callback<String, Boolean>() {
            @Override
            public Boolean call(String p) {
                return false;
            }
        });
        webView.getEngine().locationProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String oldValue, String newValue) {
                if (!"".equals(newValue)) {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            webView.getEngine().loadContent(currentContent);
                        }
                    });
                    showURLExternal(newValue);
                }
            }
        });
        webView.getEngine().setOnStatusChanged(new EventHandler<WebEvent<String>>() {
            @Override
            public void handle(WebEvent<String> e) {
                setStatusMessage(e.getData());
            }
        });

        BorderPane pane = new BorderPane();
        pane.setCenter(webView);

        Scene scene = new Scene(pane);
        fxPanel.setScene(scene);
    }
}
