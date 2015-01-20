/**
 * TODO: define a license.
 */
package net.diogobohm.timed.api.ui.domain;

import java.util.Date;
import java.util.List;
import net.diogobohm.timed.api.domain.Task;

/**
 *
 * @author diogo.bohm
 */
public class Overview {

    private final List<DayTaskList> workedDays;

    public Overview(List<DayTaskList> workedDays) {
        this.workedDays = workedDays;
    }

    public List<DayTaskList> getWorkedDays() {
        return workedDays;
    }

}
