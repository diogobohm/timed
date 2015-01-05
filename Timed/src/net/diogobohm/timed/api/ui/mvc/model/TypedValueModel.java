/**
 * TODO: define a license.
 */
package net.diogobohm.timed.api.ui.mvc.model;

import com.jgoodies.binding.value.ValueModel;
import java.beans.PropertyChangeListener;

/**
 *
 * @author diogo.bohm
 */
public interface TypedValueModel<T> {

    String PROPERTY_VALUE = "value";

    T getValue();

    void setValue(T newValue);

    void addValueChangeListener(PropertyChangeListener listener);

    void removeValueChangeListener(PropertyChangeListener listener);

    ValueModel getRenderer();
}
