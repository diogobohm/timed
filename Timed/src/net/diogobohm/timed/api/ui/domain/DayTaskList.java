/**
 * TODO: define a license.
 */
package net.diogobohm.timed.api.ui.domain;

import java.util.Date;

/**
 *
 * @author diogo.bohm
 */
public class DayTaskList {

    private final Date date;
    private final TaskList tasks;

    public DayTaskList(Date date, TaskList tasks) {
        this.date = date;
        this.tasks = tasks;
    }

    public Date getDate() {
        return date;
    }

    public TaskList getTaskList() {
        return tasks;
    }

}
