/**
 * TODO: define a license.
 */
package net.diogobohm.timed.api.ui.mvc.model.formatter;

import com.google.common.base.Optional;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author diogo.bohm
 */
public class TaskTimeFormatter {

    private static final SimpleDateFormat TIME_FORMATTER = new SimpleDateFormat("hh:mm");
    private static final String ABSENT_TIME = "--:--";

    public String format(Optional<Date> date) {
        if (date.isPresent()) {
            return format(date.get());
        }

        return ABSENT_TIME;
    }

    private String format(Date date) {
        return TIME_FORMATTER.format(date);
    }
}
