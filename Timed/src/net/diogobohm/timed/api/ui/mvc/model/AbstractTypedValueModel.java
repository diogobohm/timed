/**
 * TODO: define a license.
 */
package net.diogobohm.timed.api.ui.mvc.model;

import com.jgoodies.binding.beans.Model;
import com.jgoodies.binding.value.ValueHolder;
import com.jgoodies.binding.value.ValueModel;
import java.beans.PropertyChangeListener;

/**
 *
 * @author diogo.bohm
 */
public abstract class AbstractTypedValueModel<T> extends Model implements TypedValueModel<T> {

    private ValueHolder renderedView;
    private T value;

    public AbstractTypedValueModel() {
    }

    public AbstractTypedValueModel(T initialValue) {
        value = initialValue;
    }

    @Override
    public T getValue() {
        return value;
    }

    @Override
    public void setValue(T newValue) {
        Object oldValue = getValue();
        if (oldValue == newValue) {
            return;
        }

        value = newValue;
        render();
        fireValueChange((T) oldValue, newValue);
    }

    @Override
    public final void addValueChangeListener(PropertyChangeListener l) {
        addPropertyChangeListener(PROPERTY_VALUE, l);
    }

    @Override
    public final void removeValueChangeListener(PropertyChangeListener l) {
        removePropertyChangeListener(PROPERTY_VALUE, l);
    }

    public final void fireValueChange(T oldValue, T newValue) {
        firePropertyChange(PROPERTY_VALUE, oldValue, newValue);
    }

    public final void fireValueChange(T oldValue, T newValue, boolean checkIdentity) {
        firePropertyChange(PROPERTY_VALUE, oldValue, newValue, checkIdentity);
    }

    @Override
    public ValueModel getRenderer() {
        if (renderedView == null) {
            renderedView = new ValueHolder();
        }

        return renderedView;
    }

    abstract String buildRenderedView();

    private void render() {
        getRenderer().setValue(buildRenderedView());
    }
}
