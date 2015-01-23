/**
 * TODO: define a license.
 */
package net.diogobohm.timed.api.ui.domain.builder;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import net.diogobohm.timed.api.domain.Task;
import net.diogobohm.timed.api.ui.domain.DayTaskList;
import net.diogobohm.timed.api.ui.domain.Overview;
import org.apache.commons.lang3.time.DateUtils;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;

/**
 *
 * @author diogo.bohm
 */
public class OverviewBuilder {

    public Overview build(Date startDate, Date endDate, List<Task> tasks) {
        List<DayTaskList> dayTasks = Lists.newArrayList();
        Multimap<LocalDate, Task> dayTaskMap = createDayTaskMap(tasks);
        LocalDate startDay = LocalDate.fromDateFields(startDate);
        LocalDate endDay = LocalDate.fromDateFields(endDate);

        for (LocalDate curDay = startDay; !curDay.isAfter(endDay); curDay = curDay.plusDays(1)) {
            List<Task> curDayTasks = Lists.newArrayList();

            if (dayTaskMap.containsKey(curDay)) {
                curDayTasks.addAll(dayTaskMap.get(curDay));
            }

            dayTasks.add(new DayTaskList(curDay.toDate(), curDayTasks));
        }

        return new Overview(dayTasks);
    }

    private Multimap<LocalDate, Task> createDayTaskMap(List<Task> tasks) {
        Multimap<LocalDate, Task> dayTaskIndex = HashMultimap.create();

        for (Task task : tasks) {
            LocalDate taskStart = LocalDate.fromDateFields(task.getStart());

            dayTaskIndex.put(taskStart, task);
        }

        return dayTaskIndex;
    }
}
