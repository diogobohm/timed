/**
 * TODO: define a license.
 */
package net.diogobohm.timed.api.ui.domain.builder;

import com.google.common.collect.Lists;
import java.util.Date;
import java.util.List;
import java.util.Map;
import net.diogobohm.timed.api.domain.Task;
import net.diogobohm.timed.api.ui.domain.DayTaskList;
import net.diogobohm.timed.api.ui.domain.Overview;

/**
 *
 * @author diogo.bohm
 */
public class OverviewBuilder {

    public Overview build(Date startDate, Date endDate, List<Task> tasks) {
        List<DayTaskList> dayTasks = Lists.newArrayList();
        Map<Date, List<Task>> dayTaskMap = createDayTaskMap(tasks);

        for (long start = startDate.getTime(); start < endDate.getTime(); start += 1000 * 60 * 60 * 24) {

        }

        return new Overview(dayTasks);
    }

    private Map<Date, List<Task>> createDayTaskMap(List<Task> tasks) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
