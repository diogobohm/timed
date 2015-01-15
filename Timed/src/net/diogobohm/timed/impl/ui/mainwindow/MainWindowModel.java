/**
 * TODO: define a license.
 */
package net.diogobohm.timed.impl.ui.mainwindow;

import com.google.common.base.Optional;
import com.google.common.collect.Sets;
import java.util.ArrayList;
import java.util.List;
import net.diogobohm.timed.api.ui.mvc.MVCModel;
import net.diogobohm.timed.api.ui.mvc.model.DashboardTaskValueHolder;
import net.diogobohm.timed.api.ui.mvc.model.TagSetHolder;
import net.diogobohm.timed.api.ui.mvc.model.TypedValueHolder;
import net.diogobohm.timed.api.ui.domain.Dashboard;
import net.diogobohm.timed.api.domain.Tag;
import net.diogobohm.timed.api.domain.Task;
import net.diogobohm.timed.impl.ui.tasklist.TaskListModel;

/**
 *
 * @author diogo.bohm
 */
public class MainWindowModel implements MVCModel<Dashboard> {

    private final TaskListModel taskListModel;
    private final TagSetHolder currentTaskTagsHolder;
    private final DashboardTaskValueHolder currentTaskHolder;

    public MainWindowModel(TaskListModel taskListModel) {
        this.taskListModel = taskListModel;

        currentTaskTagsHolder = new TagSetHolder();
        currentTaskHolder = new DashboardTaskValueHolder();
    }

    public DashboardTaskValueHolder getCurrentTaskHolder() {
        return currentTaskHolder;
    }

    public TagSetHolder getCurrentTaskTagsHolder() {
        return currentTaskTagsHolder;
    }

    public TaskListModel getTaskListModel() {
        return taskListModel;
    }

    @Override
    public Dashboard getDomainBean() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setDomainBean(Dashboard domainBean) {
        Optional<Task> currentTask = domainBean.getCurrentTask();

        getCurrentTaskHolder().setValue(currentTask);
        getTaskListModel().setDomainBean(domainBean.getTasks());

        if (currentTask.isPresent()) {
            getCurrentTaskTagsHolder().setValue(domainBean.getCurrentTask().get().getTags());
        } else {
            getCurrentTaskTagsHolder().setValue(Sets.<Tag>newHashSet());
        }
    }

}
