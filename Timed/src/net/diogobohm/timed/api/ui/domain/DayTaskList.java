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
public class DayTaskList {

    private final Date date;
    private final List<Task> tasks;

    public DayTaskList(Date date, List<Task> tasks) {
        this.date = date;
        this.tasks = tasks;
    }

    public Date getDate() {
        return date;
    }

    public List<Task> getTasks() {
        return tasks;
    }

}
