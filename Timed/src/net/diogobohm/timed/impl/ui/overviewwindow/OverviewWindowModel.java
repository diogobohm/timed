/**
 * TODO: define a license.
 */
package net.diogobohm.timed.impl.ui.overviewwindow;

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
import net.diogobohm.timed.api.ui.mvc.model.LabeledBeanHolder;
import net.diogobohm.timed.api.ui.mvc.model.NewTypedValueModel;
import net.diogobohm.timed.api.ui.mvc.model.TypedComboBoxModel;
import net.diogobohm.timed.api.ui.mvc.model.bean.LabeledBean;
import net.diogobohm.timed.impl.ui.daytasklist.DayTaskListController;
import net.diogobohm.timed.impl.ui.factory.DayTaskListControllerFactory;
import net.diogobohm.timed.impl.ui.factory.TaskItemControllerFactory;
import net.diogobohm.timed.impl.ui.tasklist.TaskListModel;

/**
 *
 * @author diogo.bohm
 */
public class OverviewWindowModel implements MVCModel<Overview> {

    private final NewTypedValueModel<String> startDateHolder;
    private final NewTypedValueModel<String> endDateHolder;
    private final NewTypedValueModel<List<DayTaskListController>> taskListsHolder;
    private final DayTaskListControllerFactory itemFactory;
    
    public OverviewWindowModel(DayTaskListControllerFactory itemFactory) {
        this.itemFactory = itemFactory;

        startDateHolder = new NewTypedValueModel();
        endDateHolder = new NewTypedValueModel();
        taskListsHolder = new NewTypedValueModel();
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
        Optional<Task> currentTask = domainBean.getCurrentTask();

        getCurrentTaskHolder().setTypedValue(currentTask);
        getTaskListModel().setDomainBean(domainBean.getTasks());

        getNewActivityComboHolder().resetElementList(buildLabels(domainBean.getActivities()));
        getNewProjectComboHolder().resetElementList(buildLabels(domainBean.getProjects()));

        getWorkedTimeHolder().setTypedValue(Task.convertWorkedTimeToString(domainBean.getWorkedTime()));

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

    @Override
    public void setDomainBean(Overview domainBean) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
