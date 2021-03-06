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
import net.diogobohm.timed.api.ui.domain.validation.ValidationException;
import net.diogobohm.timed.api.ui.domain.validation.Validator;
import net.diogobohm.timed.api.ui.mvc.model.LabeledBeanComboBoxModel;
import net.diogobohm.timed.api.ui.mvc.model.NewTypedValueModel;
import net.diogobohm.timed.api.ui.mvc.model.TypedComboBoxModel;
import net.diogobohm.timed.api.ui.mvc.model.bean.LabeledBean;
import org.apache.commons.lang3.time.FastDateFormat;

/**
 *
 * @author diogo.bohm
 */
public class TaskEditModel implements Validator {

    private static final FastDateFormat DATETIME_FORMATTER = FastDateFormat.getInstance("yyyy-MM-dd HH:mm");
    private static final FastDateFormat DATE_FORMATTER = FastDateFormat.getInstance("yyyy-MM-dd");
    private static final FastDateFormat TIME_FORMATTER = FastDateFormat.getInstance("HH:mm");

    private final NewTypedValueModel<Optional<Task>> originalTaskHolder;
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

    public Optional<Task> getOriginalTask() {
        return getOriginalTaskHolder().getValue();
    }

    public void setDomainBean(List<Activity> activities, List<Project> projects, Task task) throws ParseException {
        getActivityHolder().resetElementList(buildLabels(activities));
        getProjectHolder().resetElementList(buildLabels(projects));

        setTaskData(task);
    }

    public void setDomainBean(List<Activity> activities, List<Project> projects, Date initialDate) throws ParseException {
        getActivityHolder().resetElementList(buildLabels(activities));
        getProjectHolder().resetElementList(buildLabels(projects));

        setDefaultData(initialDate);
    }

    @Override
    public void validate() throws ValidationException {
        String activityName = (String) getActivityHolder().getSelectedItem();
        String projectName = (String) getProjectHolder().getSelectedItem();
        String startDate = getStartDateTextHolder().getValue();
        String startTime = getStartTimeTextHolder().getValue();
        String stopTime = getStopTimeTextHolder().getValue();
        Boolean inCourse = getTaskInCourseHolder().getValue();

        if (activityName.isEmpty()) {
            throw new ValidationException("An activity must be specified!");
        }

        if (projectName.isEmpty()) {
            throw new ValidationException("A project must be specified!");
        }

        if (startDate.isEmpty()) {
            throw new ValidationException("A date must be specified!");
        } else {
            try {
                DATE_FORMATTER.parse(startDate);
            } catch (ParseException exception) {
                throw new ValidationException("A valid date must be provided!");
            }
        }

        if (startTime.isEmpty()) {
            throw new ValidationException("The start time must be specified!");
        } else {
            try {
                TIME_FORMATTER.parse(startTime);
            } catch (ParseException exception) {
                throw new ValidationException("A valid start time must be provided!");
            }
        }

        if (!inCourse) {
            if (stopTime.isEmpty()) {
                throw new ValidationException("The end time must be specified when not in progress!");
            } else {
                try {
                    TIME_FORMATTER.parse(stopTime);
                } catch (ParseException exception) {
                    throw new ValidationException("A valid end time must be provided!");
                }
            }
        }
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

    private void setTaskData(Task task) {
        getOriginalTaskHolder().setTypedValue(Optional.of(task));
        getActivityHolder().setSelectedItem(task.getActivity().getLabel());

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

    private void setDefaultData(Date initialDate) {
        String defaultValue = "";

        getOriginalTaskHolder().setTypedValue(Optional.<Task>absent());
        getActivityHolder().setSelectedItem(null);
        getProjectHolder().setSelectedItem(null);

        getTagExpressionHolder().setTypedValue(defaultValue);
        getStartDateTextHolder().setTypedValue(extractDateFromDate(initialDate));
        getStartTimeTextHolder().setTypedValue(extractTimeFromDate(initialDate));

        getTaskInCourseHolder().setTypedValue(Boolean.FALSE);
        getStopTimeTextHolder().setTypedValue(extractTimeFromDate(initialDate));

        getDescriptionHolder().setTypedValue(defaultValue);
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

    private NewTypedValueModel<Optional<Task>> getOriginalTaskHolder() {
        return originalTaskHolder;
    }
}
