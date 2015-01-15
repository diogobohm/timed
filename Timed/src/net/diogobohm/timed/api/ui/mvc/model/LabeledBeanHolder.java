/**
 * TODO: define a license.
 */
package net.diogobohm.timed.api.ui.mvc.model;

import com.jgoodies.binding.value.ValueModel;
import net.diogobohm.timed.api.ui.mvc.model.bean.LabeledBean;

/**
 *
 * @author diogo.bohm
 */
public class LabeledBeanHolder<T extends LabeledBean> extends NewTypedValueModel<T> {

    private NewTypedValueModel<String> renderer;

    @Override
    public void setTypedValue(T newValue) {
        super.setTypedValue(newValue);
        refreshRenderer();
    }

    public ValueModel getRenderer() {
        if (renderer == null) {
            renderer = new NewTypedValueModel();
        }

        return renderer;
    }

    private void refreshRenderer() {
        getRenderer().setValue(getValue().getLabel());
    }

}
