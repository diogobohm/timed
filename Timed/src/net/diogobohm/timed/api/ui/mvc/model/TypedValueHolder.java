/**
 * TODO: define a license.
 */
package net.diogobohm.timed.api.ui.mvc.model;

/**
 *
 * @author diogo.bohm
 */
public class TypedValueHolder<T> extends AbstractTypedValueModel<T> {

    @Override
    public String buildRenderedView() {
        return getValue().toString();
    }

}
