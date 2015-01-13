/**
 * TODO: define a license.
 */
package net.diogobohm.timed.api.ui.mvc.model;

import net.diogobohm.timed.api.ui.mvc.model.bean.LabeledBean;

/**
 *
 * @author diogo.bohm
 */
public class LabeledBeanHolder<T extends LabeledBean> extends AbstractTypedValueModel<T> {

    @Override
    public String buildRenderedView() {
        return getValue().getLabel();
    }

}
