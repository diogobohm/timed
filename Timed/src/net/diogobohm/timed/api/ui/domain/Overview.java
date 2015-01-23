/**
 * TODO: define a license.
 */
package net.diogobohm.timed.api.ui.domain;

import java.util.List;

/**
 *
 * @author diogo.bohm
 */
public class Overview {

    private final List<DayTaskList> dayTaskLists;

    public Overview(List<DayTaskList> dayTaskLists) {
        this.dayTaskLists = dayTaskLists;
    }

    public List<DayTaskList> getDayTaskLists() {
        return dayTaskLists;
    }

}
