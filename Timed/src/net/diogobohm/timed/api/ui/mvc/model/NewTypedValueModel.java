/**
 * TODO: define a license.
 */
package net.diogobohm.timed.api.ui.mvc.model;

import com.jgoodies.binding.value.AbstractValueModel;

/**
 *
 * @author diogo.bohm
 */
public class NewTypedValueModel<T> extends AbstractValueModel {

    private T value;

    @Override
    public T getValue() {
        return value;
    }

    public void setTypedValue(T newValue) {
        value = newValue;
    }
    
    @Override
    @Deprecated
    public void setValue(Object newValue) {
        setTypedValue((T) newValue);
    }

}
