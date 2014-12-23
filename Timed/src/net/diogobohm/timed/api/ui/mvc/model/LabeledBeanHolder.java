/**
 * TODO: define a license.
 */
package net.diogobohm.timed.api.ui.mvc.model;

import net.diogobohm.timed.api.ui.mvc.model.bean.LabeledBean;

/**
 *
 * @author diogo.bohm
 */
public class LabeledBeanHolder extends AbstractTypedValueModel<LabeledBean> {

    @Override
    public String buildRenderedView() {
        return getValue().getLabel();
    }

}
