package flow.netbeans.markdown.highlighter;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Properties;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * The unit tests in this class verify that all token IDs are correctly
 * registered in the editor font and color settings.
 *
 * @author Holger Stenger
 */
@RunWith(Parameterized.class)
public class MarkdownTokenIdTest {

    private static final String FONT_AND_COLORS_PATH = "flow/netbeans/markdown/resources/FontAndColors.xml";

    private static final String BUNDLE_PATH = "flow/netbeans/markdown/resources/Bundle.properties";

    private static Document fontAndColorsDoc;
    private static Properties props;

    private final MarkdownTokenId tokenId;

    public MarkdownTokenIdTest(final MarkdownTokenId tokenId) {
        this.tokenId = tokenId;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> getParameters() {
        Collection<Object[]> parameters = new ArrayList<Object[]>();
        for (MarkdownTokenId tokenId : MarkdownTokenId.values()) {
            parameters.add(new Object[]{tokenId});
        }
        return parameters;
    }

    @BeforeClass
    public static void readFontAndColorsXML() throws SAXException, IOException, ParserConfigurationException {
        InputStream stream = MarkdownTokenIdTest.class.getClassLoader().getResourceAsStream(FONT_AND_COLORS_PATH);
        assertNotNull("input stream for FontAndColors.xml", stream);
        try {
            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            fontAndColorsDoc = docBuilder.parse(stream);
        } finally {
            try {
                stream.close();
            } catch (IOException ex) {
            }
        }
    }

    @BeforeClass
    public static void readBundleProperties() throws IOException {
        InputStream stream = MarkdownTokenIdTest.class.getClassLoader().getResourceAsStream(BUNDLE_PATH);
        assertNotNull("input stream for Bundle.properties", stream);
        try {
            props = new Properties();
            props.load(stream);
        } finally {
            try {
                stream.close();
            } catch (IOException ex) {
            }
        }
    }

    @Test
    public void testFontAndColorsContainsEntryForPrimaryCategory() throws XPathExpressionException {
        XPath xpath = XPathFactory.newInstance().newXPath();
        String expression = "/fontscolors/fontcolor[@name='" + tokenId.primaryCategory() + "']";
        NodeList nodes = (NodeList) xpath.evaluate(expression, fontAndColorsDoc.getDocumentElement(), XPathConstants.NODESET);
        assertEquals(tokenId.toString(), 1, nodes.getLength());
    }

    @Test
    public void testBundleDefinesDisplayNameForPrimaryCategory() {
        String displayName = props.getProperty(tokenId.primaryCategory());
        assertNotNull(tokenId.toString(), displayName);
    }
}
