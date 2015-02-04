/**
 * TODO: define a license.
 */
package net.diogobohm.timed.impl.ui.mainwindow;

import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;
import net.diogobohm.timed.api.domain.Activity;
import net.diogobohm.timed.api.domain.Project;
import net.diogobohm.timed.api.ui.mvc.MVCModel;
import net.diogobohm.timed.api.ui.mvc.model.TagSetHolder;
import net.diogobohm.timed.api.ui.domain.Dashboard;
import net.diogobohm.timed.api.domain.Tag;
import net.diogobohm.timed.api.domain.Task;
import net.diogobohm.timed.api.ui.domain.TaskList;
import net.diogobohm.timed.api.ui.mvc.model.LabeledBeanHolder;
import net.diogobohm.timed.api.ui.mvc.model.NewTypedValueModel;
import net.diogobohm.timed.api.ui.mvc.model.TypedComboBoxModel;
import net.diogobohm.timed.api.ui.mvc.model.bean.LabeledBean;
import net.diogobohm.timed.impl.ui.tasklist.TaskListModel;

/**
 *
 * @author diogo.bohm
 */
public class MainWindowModel implements MVCModel<Dashboard> {

    private static final Activity NO_ACTIVITY = new Activity("Not currently working.");
    private static final Project NO_PROJECT = new Project("");

    private final TypedComboBoxModel<String> newActivityComboHolder;
    private final TypedComboBoxModel<String> newProjectComboHolder;
    private final NewTypedValueModel<String> newTaskTagsHolder;
    private final LabeledBeanHolder<Activity> currentActivityHolder;
    private final LabeledBeanHolder<Project> currentProjectHolder;
    private final TagSetHolder currentTaskTagsHolder;
    private final TaskListModel taskListModel;
    private final NewTypedValueModel<String> workedTimeHolder;
    private final NewTypedValueModel<Optional<Task>> currentTaskHolder;
    
    public MainWindowModel(TaskListModel taskListModel) {
        this.taskListModel = taskListModel;

        newActivityComboHolder = new TypedComboBoxModel();
        newProjectComboHolder = new TypedComboBoxModel();
        newTaskTagsHolder = new NewTypedValueModel();
        currentActivityHolder = new LabeledBeanHolder();
        currentProjectHolder = new LabeledBeanHolder();
        currentTaskTagsHolder = new TagSetHolder();

        workedTimeHolder = new NewTypedValueModel();
        currentTaskHolder = new NewTypedValueModel();
    }

    public TagSetHolder getCurrentTaskTagsHolder() {
        return currentTaskTagsHolder;
    }

    public TaskListModel getTaskListModel() {
        return taskListModel;
    }

    public LabeledBeanHolder<Activity> getCurrentActivityHolder() {
        return currentActivityHolder;
    }

    public LabeledBeanHolder<Project> getCurrentProjectHolder() {
        return currentProjectHolder;
    }

    public NewTypedValueModel<Optional<Task>> getCurrentTaskHolder() {
        return currentTaskHolder;
    }

    public NewTypedValueModel<String> getWorkedTimeHolder() {
        return workedTimeHolder;
    }

    @Override
    public Dashboard getDomainBean() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Task getNewTask() {
        Activity activity = new Activity((String) getNewActivityComboHolder().getSelectedItem());
        Project project = new Project((String) getNewProjectComboHolder().getSelectedItem());
        Set<Tag> newTags = Tag.parseTagsFromString(getNewTaskTagsHolder().getString());
        Date start = new Date();
        Optional<Date> end = Optional.absent();
        String description = "";

        return new Task(activity, project, start, end, description, newTags);
    }

    public Optional<Task> getCurrentTask() {
        return getCurrentTaskHolder().getValue();
    }

    @Override
    public void setDomainBean(Dashboard domainBean) {
        TaskList taskList = domainBean.getTaskList();

        Optional<Task> currentTask = taskList.getCurrentTask();

        getCurrentTaskHolder().setTypedValue(currentTask);
        getTaskListModel().setDomainBean(taskList);

        getNewActivityComboHolder().resetElementList(buildLabels(domainBean.getActivities()));
        getNewProjectComboHolder().resetElementList(buildLabels(domainBean.getProjects()));

        getWorkedTimeHolder().setTypedValue(Task.convertWorkedTimeToString(taskList.getWorkedTime()));

        if (currentTask.isPresent()) {
            Task task = currentTask.get();

            getCurrentActivityHolder().setTypedValue(task.getActivity());
            getCurrentProjectHolder().setTypedValue(task.getProject());
            getCurrentTaskTagsHolder().setValue(task.getTags());
        } else {
            getCurrentActivityHolder().setTypedValue(NO_ACTIVITY);
            getCurrentProjectHolder().setTypedValue(NO_PROJECT);
            getCurrentTaskTagsHolder().setValue(Sets.<Tag>newHashSet());
        }
    }

    public TypedComboBoxModel<String> getNewActivityComboHolder() {
        return newActivityComboHolder;
    }

    public TypedComboBoxModel<String> getNewProjectComboHolder() {
        return newProjectComboHolder;
    }

    public NewTypedValueModel<String> getNewTaskTagsHolder() {
        return newTaskTagsHolder;
    }

    private <T extends LabeledBean> List<String> buildLabels(Collection<T> beans) {
        List<String> labels = Lists.newArrayList();

        for (LabeledBean bean : beans) {
            labels.add(bean.getLabel());
        }

        return labels;
    }

}
