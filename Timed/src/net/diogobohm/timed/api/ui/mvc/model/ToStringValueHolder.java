/**
 * TODO: define a license.
 */
package net.diogobohm.timed.api.ui.mvc.model;

/**
 *
 * @author diogo.bohm
 */
public class ToStringValueHolder extends AbstractTypedValueModel<Object> {

    @Override
    public String buildRenderedView() {
        return getValue().toString();
    }

}
