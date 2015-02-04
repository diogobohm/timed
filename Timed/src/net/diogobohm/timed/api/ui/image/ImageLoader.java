/**
 * TODO: define a license.
 */
package net.diogobohm.timed.api.ui.image;

import com.google.common.collect.Maps;
import java.util.Map;
import javax.swing.ImageIcon;

/**
 *
 * @author diogo.bohm
 */
public class ImageLoader {

    private static ImageLoader INSTANCE;

    private Map<ImageResource, ImageIcon> imageMap;

    private ImageLoader() {
        imageMap = Maps.newHashMap();
    }

    public static ImageLoader getInstance() {
        if (INSTANCE == null) {
            synchronized (ImageLoader.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ImageLoader();
                }
            }
        }

        return INSTANCE;
    }

    public ImageIcon getIcon(ImageResource resource) {
        if (!imageMap.containsKey(resource)) {
            synchronized (ImageLoader.class) {
                if (!imageMap.containsKey(resource)) {
                    imageMap.put(resource, loadImage(resource));
                }
            }
        }

        return imageMap.get(resource);
    }

    private ImageIcon loadImage(ImageResource image) {
        return new ImageIcon(ImageResource.class.getResource(image.getFilename()));
    }
}
