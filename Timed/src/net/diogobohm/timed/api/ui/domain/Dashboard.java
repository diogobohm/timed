/**
 * TODO: define a license.
 */
package net.diogobohm.timed.api.ui.domain;

import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import net.diogobohm.timed.api.domain.Activity;
import net.diogobohm.timed.api.domain.Project;
import net.diogobohm.timed.api.domain.Task;

/**
 *
 * @author diogo.bohm
 */
public class Dashboard {

    private final LinkedList<Task> tasks;
    private final Collection<Activity> activities;
    private final Collection<Project> projects;

    public Dashboard(Collection<Task> tasks, Collection<Activity> activities, Collection<Project> projects) {
        this.tasks = Lists.newLinkedList(tasks);
        this.activities = activities;
        this.projects = projects;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public Optional<Task> getCurrentTask() {
        if (tasks.isEmpty()) {
            return Optional.absent();
        }

        return getLastOpenTask();
    }

    public Collection<Activity> getActivities() {
        return activities;
    }

    public Collection<Project> getProjects() {
        return projects;
    }

    public long getWorkedTime() {
        long time = 0;
        Date currentTime = new Date();

        for (Task task : tasks) {
            Date startTime = task.getStart();
            Date endTime = currentTime;
            if (task.getFinish().isPresent()) {
                endTime = task.getFinish().get();
            }

            time += endTime.getTime() - startTime.getTime();
        }

        return time;
    }

    private Optional<Task> getLastOpenTask() {
        Task lastTask = tasks.getLast();

        if (lastTask.getFinish().isPresent()) {
            return Optional.absent();
        }

        return Optional.of(lastTask);
    }

}
