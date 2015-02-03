/**
 * TODO: define a license.
 */
package net.diogobohm.timed.impl.ui.taskedit;

import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Set;
import net.diogobohm.timed.api.domain.Activity;
import net.diogobohm.timed.api.domain.Project;
import net.diogobohm.timed.api.domain.Tag;
import net.diogobohm.timed.api.domain.Task;
import net.diogobohm.timed.api.ui.mvc.model.LabeledBeanComboBoxModel;
import net.diogobohm.timed.api.ui.mvc.model.NewTypedValueModel;
import net.diogobohm.timed.api.ui.mvc.model.TypedComboBoxModel;
import net.diogobohm.timed.api.ui.mvc.model.bean.LabeledBean;
import org.apache.commons.lang3.time.FastDateFormat;

/**
 *
 * @author diogo.bohm
 */
public class TaskEditModel {

    private static final FastDateFormat DATETIME_FORMATTER = FastDateFormat.getInstance("yyyy-MM-dd HH:mm");

    private final NewTypedValueModel<Task> originalTaskHolder;
    private final TypedComboBoxModel<String> activityHolder;
    private final TypedComboBoxModel<String> projectHolder;
    private final NewTypedValueModel<String> tagExpressionHolder;
    private final NewTypedValueModel<String> startDateTextHolder;
    private final NewTypedValueModel<String> startTimeTextHolder;
    private final NewTypedValueModel<String> stopTimeTextHolder;
    private final NewTypedValueModel<Boolean> taskInCourseHolder;
    private final NewTypedValueModel<String> descriptionHolder;

    public TaskEditModel() {
        originalTaskHolder = new NewTypedValueModel();
        activityHolder = new LabeledBeanComboBoxModel();
        projectHolder = new LabeledBeanComboBoxModel();
        tagExpressionHolder = new NewTypedValueModel();
        startDateTextHolder = new NewTypedValueModel();
        startTimeTextHolder = new NewTypedValueModel();
        stopTimeTextHolder = new NewTypedValueModel();
        taskInCourseHolder = new NewTypedValueModel();
        descriptionHolder = new NewTypedValueModel();
    }

    public Task getEditedTask() throws ParseException {
        String activityName = (String) getActivityHolder().getSelectedItem();
        String projectName = (String) getProjectHolder().getSelectedItem();
        String tagExpression = getTagExpressionHolder().getValue();
        String startDate = getStartDateTextHolder().getValue();
        String startTime = getStartTimeTextHolder().getValue();
        String stopTime = getStopTimeTextHolder().getValue();
        Boolean inCourse = getTaskInCourseHolder().getValue();
        String description = getDescriptionHolder().getValue();

        Date startTask = DATETIME_FORMATTER.parse(startDate + " " + startTime);
        Optional<Date> stopTask = Optional.absent();
        Set<Tag> tagSet = Tag.parseTagsFromString(tagExpression);

        if (!inCourse) {
            stopTask = Optional.of(DATETIME_FORMATTER.parse(startDate + " " + stopTime));
        }

        return new Task(new Activity(activityName), new Project(projectName), startTask, stopTask, description, tagSet);
    }

    public Task getOriginalTask() {
        return getOriginalTaskHolder().getValue();
    }

    public void setDomainBean(List<Activity> activities, List<Project> projects, Task task) throws ParseException {
        getOriginalTaskHolder().setTypedValue(task);

        getActivityHolder().resetElementList(buildLabels(activities));
        getActivityHolder().setSelectedItem(task.getActivity().getLabel());

        getProjectHolder().resetElementList(buildLabels(projects));
        getProjectHolder().setSelectedItem(task.getProject().getLabel());

        getTagExpressionHolder().setTypedValue(Tag.buildTagString(task.getTags()));
        getStartDateTextHolder().setTypedValue(extractDateFromDate(task.getStart()));
        getStartTimeTextHolder().setTypedValue(extractTimeFromDate(task.getStart()));

        if (task.getFinish().isPresent()) {
            getStopTimeTextHolder().setTypedValue(extractTimeFromDate(task.getFinish().get()));
            getTaskInCourseHolder().setTypedValue(Boolean.FALSE);
        } else {
            getTaskInCourseHolder().setTypedValue(Boolean.TRUE);
        }

        getDescriptionHolder().setTypedValue(task.getDescription());
    }

    protected TypedComboBoxModel<String> getActivityHolder() {
        return activityHolder;
    }

    protected TypedComboBoxModel<String> getProjectHolder() {
        return projectHolder;
    }

    protected NewTypedValueModel<String> getTagExpressionHolder() {
        return tagExpressionHolder;
    }

    protected NewTypedValueModel<String> getStartDateTextHolder() {
        return startDateTextHolder;
    }

    protected NewTypedValueModel<String> getStartTimeTextHolder() {
        return startTimeTextHolder;
    }

    protected NewTypedValueModel<String> getStopTimeTextHolder() {
        return stopTimeTextHolder;
    }

    protected NewTypedValueModel<Boolean> getTaskInCourseHolder() {
        return taskInCourseHolder;
    }

    protected NewTypedValueModel<String> getDescriptionHolder() {
        return descriptionHolder;
    }

    private String extractDateFromDate(Date date) {
        String dateTime = DATETIME_FORMATTER.format(date);
        String[] dateChunks = dateTime.split(" ");

        return dateChunks[0];
    }

    private String extractTimeFromDate(Date date) {
        String dateTime = DATETIME_FORMATTER.format(date);
        String[] dateChunks = dateTime.split(" ");

        return dateChunks[1];
    }

    private <T extends LabeledBean> List<String> buildLabels(List<T> beans) {
        List<String> labels = Lists.newArrayList();

        for (LabeledBean bean : beans) {
            labels.add(bean.getLabel());
        }

        return labels;
    }

    private NewTypedValueModel<Task> getOriginalTaskHolder() {
        return originalTaskHolder;
    }

}
