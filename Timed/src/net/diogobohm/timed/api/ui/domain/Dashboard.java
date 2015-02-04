/**
 * TODO: define a license.
 */
package net.diogobohm.timed.api.ui.domain;

import java.util.Collection;
import net.diogobohm.timed.api.domain.Activity;
import net.diogobohm.timed.api.domain.Project;

/**
 *
 * @author diogo.bohm
 */
public class Dashboard {

    private final TaskList taskList;
    private final Collection<Activity> activities;
    private final Collection<Project> projects;

    public Dashboard(TaskList tasks, Collection<Activity> activities, Collection<Project> projects) {
        this.taskList = tasks;
        this.activities = activities;
        this.projects = projects;
    }

    public TaskList getTaskList() {
        return taskList;
    }

    public Collection<Activity> getActivities() {
        return activities;
    }

    public Collection<Project> getProjects() {
        return projects;
    }

}
