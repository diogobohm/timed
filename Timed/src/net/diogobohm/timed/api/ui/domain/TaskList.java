/**
 * TODO: define a license.
 */
package net.diogobohm.timed.api.ui.domain;

import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import net.diogobohm.timed.api.domain.Task;
import net.diogobohm.timed.api.ui.domain.comparator.TaskComparator;

/**
 *
 * @author diogo.bohm
 */
public class TaskList {

    private final LinkedList<Task> taskList;

    public TaskList(List<Task> taskList) {
        this.taskList = Lists.newLinkedList(taskList);
        sortTasks();
    }

    public List<Task> getTasks() {
        return Lists.newArrayList(taskList);
    }

    public void removeTask(Task task) {
        taskList.remove(task);
    }

    public void addTask(Task task) {
        taskList.add(task);
        sortTasks();
    }

    public long getWorkedTime() {
        long time = 0;
        Date currentTime = new Date();

        for (Task task : taskList) {
            Date startTime = task.getStart();
            Date endTime = currentTime;
            if (task.getFinish().isPresent()) {
                endTime = task.getFinish().get();
            }

            time += endTime.getTime() - startTime.getTime();
        }

        return time;
    }

    public Optional<Task> getLastOpenTask() {
        Task lastTask = taskList.getLast();

        if (lastTask.getFinish().isPresent()) {
            return Optional.absent();
        }

        return Optional.of(lastTask);
    }

    public Optional<Task> getCurrentTask() {
        if (taskList.isEmpty()) {
            return Optional.absent();
        }

        return getLastOpenTask();
    }

    private void sortTasks() {
        Collections.sort(taskList, new TaskComparator());
    }
}
