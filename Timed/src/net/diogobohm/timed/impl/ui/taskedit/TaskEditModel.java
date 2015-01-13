/**
 * TODO: define a license.
 */
package net.diogobohm.timed.impl.ui.taskedit;

import com.google.common.base.Optional;
import com.google.common.collect.Sets;
import java.text.ParseException;
import java.util.Date;
import java.util.Set;
import net.diogobohm.timed.api.domain.Activity;
import net.diogobohm.timed.api.domain.Project;
import net.diogobohm.timed.api.domain.Tag;
import net.diogobohm.timed.api.domain.Task;
import net.diogobohm.timed.api.ui.mvc.model.LabeledBeanComboBoxModel;
import net.diogobohm.timed.api.ui.mvc.model.TypedValueHolder;
import net.diogobohm.timed.api.ui.mvc.model.TypedValueModel;
import org.apache.commons.lang3.time.FastDateFormat;

/**
 *
 * @author diogo.bohm
 */
public class TaskEditModel {

    private static final FastDateFormat DATETIME_FORMATTER = FastDateFormat.getInstance("yyyy-MM-dd HH:mm");

    private final LabeledBeanComboBoxModel<Activity> activityHolder;
    private final LabeledBeanComboBoxModel<Project> projectHolder;
    private final TypedValueModel<String> tagExpressionHolder;
    private final TypedValueModel<String> startDateTextHolder;
    private final TypedValueModel<String> startTimeTextHolder;
    private final TypedValueModel<String> stopTimeTextHolder;
    private final TypedValueModel<Boolean> taskInCourseHolder;
    private final TypedValueModel<String> descriptionHolder;

    public TaskEditModel() {
        activityHolder = new LabeledBeanComboBoxModel();
        projectHolder = new LabeledBeanComboBoxModel();
        tagExpressionHolder = new TypedValueHolder();
        startDateTextHolder = new TypedValueHolder();
        startTimeTextHolder = new TypedValueHolder();
        stopTimeTextHolder = new TypedValueHolder();
        taskInCourseHolder = new TypedValueHolder();
        descriptionHolder = new TypedValueHolder();
    }

    public Task getEditedTask() throws ParseException {
        Activity activityName = (Activity) getActivityHolder().getSelectedItem();
        Project projectName = (Project) getProjectHolder().getSelectedItem();
        String tagExpression = getTagExpressionHolder().getValue();
        String startDate = getStartDateTextHolder().getValue();
        String startTime = getStartTimeTextHolder().getValue();
        String stopTime = getStopTimeTextHolder().getValue();
        Boolean inCourse = getTaskInCourseHolder().getValue();

        Date startTask = DATETIME_FORMATTER.parse(startDate + " " + startTime);
        Optional<Date> stopTask = Optional.absent();
        Set<Tag> tagSet = parseTagsFromExpression(tagExpression);

        if (!inCourse) {
            stopTask = Optional.of(DATETIME_FORMATTER.parse(startDate + " " + stopTime));
        }

        return new Task(activityName, projectName, startTask, stopTask, stopTime, tagSet);
    }

    public void setDomainBean(Task task) throws ParseException {
        
    }

    protected LabeledBeanComboBoxModel<Activity> getActivityHolder() {
        return activityHolder;
    }

    protected LabeledBeanComboBoxModel<Project> getProjectHolder() {
        return projectHolder;
    }

    protected TypedValueModel<String> getTagExpressionHolder() {
        return tagExpressionHolder;
    }

    protected TypedValueModel<String> getStartDateTextHolder() {
        return startDateTextHolder;
    }

    protected TypedValueModel<String> getStartTimeTextHolder() {
        return startTimeTextHolder;
    }

    protected TypedValueModel<String> getStopTimeTextHolder() {
        return stopTimeTextHolder;
    }

    protected TypedValueModel<Boolean> getTaskInCourseHolder() {
        return taskInCourseHolder;
    }

    protected TypedValueModel getDescriptionHolder() {
        return descriptionHolder;
    }

    private Set<Tag> parseTagsFromExpression(String tagExpression) {
        String trimmedTags = tagExpression.replaceAll(" ", "").replaceAll(",", "").replaceAll(";", "");
        Set<Tag> tagSet = Sets.newHashSet();

        for (String tagName : trimmedTags.split("#")) {
            tagSet.add(new Tag(tagName));
        }

        return tagSet;
    }

}
