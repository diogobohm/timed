/**
 * TODO: define a license.
 */
package net.diogobohm.timed.api.ui.image;

/**
 *
 * @author diogo.bohm
 */
public enum ImageResource {

    ICON_EXPAND("icon_expand.png"),
    ICON_COLLAPSE("icon_collapse.png");

    private final String filename;

    private ImageResource(String filename) {
        this.filename = filename;
    }

    public String getFilename() {
        return filename;
    }
}
