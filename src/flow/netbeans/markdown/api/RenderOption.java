
package flow.netbeans.markdown.api;

/**
 *
 * @author Holger Stenger
 */
public enum RenderOption {
    /** Resolve relative links using source document location. */
    RESOLVE_LINK_URLS,
    /** Resolve relative image paths using source document location. */
    RESOLVE_IMAGE_URLS,
    /** Generate output which can be rendered by Swing. */
    SWING_COMPATIBLE,
    /** If the file is currently opened in the editor, use the editor content instead of the file content. */
    PREFER_EDITOR
}
