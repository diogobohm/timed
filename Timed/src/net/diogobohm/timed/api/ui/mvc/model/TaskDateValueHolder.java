/**
 * TODO: define a license.
 */
package net.diogobohm.timed.api.ui.mvc.model;

import com.google.common.base.Optional;
import com.jgoodies.binding.value.AbstractValueModel;
import java.util.Date;
import net.diogobohm.timed.api.ui.mvc.model.formatter.TaskTimeFormatter;

/**
 *
 * @author diogo.bohm
 */
public class TaskDateValueHolder extends AbstractTypedValueModel<Optional<Date>> {

    private static final TaskTimeFormatter dateFormatter = new TaskTimeFormatter();

    @Override
    String buildRenderedView() {
        return dateFormatter.format(getValue());
    }

}
