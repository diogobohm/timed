/**
 * TODO: define a license.
 */
package net.diogobohm.timed.api.ui.mvc.model;

import com.google.common.base.Optional;
import net.diogobohm.timed.api.domain.Task;

/**
 *
 * @author diogo.bohm
 */
public class DashboardTaskValueHolder extends AbstractTypedValueModel<Optional<Task>> {

    @Override
    String buildRenderedView() {
        Optional<Task> value = getValue();

        if (value.isPresent()) {
            return value.get().getLabel();
        }

        return "No work in progress.";
    }

}
