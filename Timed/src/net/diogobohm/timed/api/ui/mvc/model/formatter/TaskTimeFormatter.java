/**
 * TODO: define a license.
 */
package net.diogobohm.timed.api.ui.mvc.model.formatter;

import com.google.common.base.Optional;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.lang3.time.FastDateFormat;

/**
 *
 * @author diogo.bohm
 */
public class TaskTimeFormatter {

    private static final FastDateFormat TIME_FORMATTER = FastDateFormat.getInstance("HH:mm");
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
