/**
 * TODO: define a license.
 */
package net.diogobohm.timed.impl.ui.taskitem;

import com.google.common.base.Optional;
import java.awt.event.ActionListener;
import net.diogobohm.timed.api.ui.mvc.MVCModel;
import net.diogobohm.timed.api.ui.mvc.model.LabeledBeanHolder;
import net.diogobohm.timed.api.ui.mvc.model.TagSetHolder;
import net.diogobohm.timed.api.ui.mvc.model.TaskDateValueHolder;
import net.diogobohm.timed.api.ui.mvc.model.TypedValueModel;
import net.diogobohm.timed.api.domain.Task;

/**
 *
 * @author diogo.bohm
 */
public class TaskItemModel implements MVCModel<Task> {

    private final TypedValueModel startDateHolder;
    private final TypedValueModel stopDateHolder;
    private final LabeledBeanHolder<Task> taskLabelHolder;
    private final TypedValueModel tagSetHolder;
    private final ActionListener editButtonAction;

    public TaskItemModel(ActionListener editButtonAction) {
        this.editButtonAction = editButtonAction;

        startDateHolder = new TaskDateValueHolder();
        stopDateHolder = new TaskDateValueHolder();
        taskLabelHolder = new LabeledBeanHolder();
        tagSetHolder = new TagSetHolder();
    }

    @Override
    public Task getDomainBean() {
        return getTaskLabelHolder().getValue();
    }

    @Override
    public void setDomainBean(Task task) {
        getStartDateHolder().setValue(Optional.of(task.getStart()));
        getStopDateHolder().setValue(task.getFinish());
        getTaskLabelHolder().setValue(task);
        getTagSetHolder().setValue(task.getTags());
    }

    public TypedValueModel getStartDateHolder() {
        return startDateHolder;
    }

    public TypedValueModel getStopDateHolder() {
        return stopDateHolder;
    }

    public LabeledBeanHolder<Task> getTaskLabelHolder() {
        return taskLabelHolder;
    }

    public TypedValueModel getTagSetHolder() {
        return tagSetHolder;
    }

    protected ActionListener getEditTaskAction() {
        return editButtonAction;
    }
}
