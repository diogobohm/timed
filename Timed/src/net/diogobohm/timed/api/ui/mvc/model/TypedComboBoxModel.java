/**
 * TODO: define a license.
 */
package net.diogobohm.timed.api.ui.mvc.model;

import java.util.Collection;
import net.diogobohm.timed.api.ui.mvc.model.*;
import java.util.List;
import javax.swing.DefaultComboBoxModel;

/**
 *
 * @author diogo.bohm
 */
public class TypedComboBoxModel<T> extends DefaultComboBoxModel<T> {

    public void addAll(List<T> elements) {
        for (T element : elements) {
            this.addElement(element);
        }
    }

    public void resetElementList(List<T> elements) {
        removeAllElements();
        addAll(elements);
    }
}
