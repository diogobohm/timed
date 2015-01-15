/**
 * TODO: define a license.
 */
package net.diogobohm.timed.impl.ui.taskitem;

import com.google.common.base.Optional;
import java.util.Date;
import net.diogobohm.timed.api.ui.mvc.MVCModel;
import net.diogobohm.timed.api.ui.mvc.model.LabeledBeanHolder;
import net.diogobohm.timed.api.ui.mvc.model.TaskDateValueHolder;
import net.diogobohm.timed.api.ui.mvc.model.TypedValueModel;
import net.diogobohm.timed.api.domain.Task;

/**
 *
 * @author diogo.bohm
 */
public class TaskItemModel implements MVCModel<Task> {

    private final TypedValueModel<Optional<Date>> startDateHolder;
    private final TypedValueModel<Optional<Date>> stopDateHolder;
    private final LabeledBeanHolder<Task> taskLabelHolder;

    public TaskItemModel() {
        startDateHolder = new TaskDateValueHolder();
        stopDateHolder = new TaskDateValueHolder();
        taskLabelHolder = new LabeledBeanHolder();
    }

    @Override
    public Task getDomainBean() {
        return getTaskLabelHolder().getValue();
    }

    @Override
    public void setDomainBean(Task task) {
        getStartDateHolder().setValue(Optional.of(task.getStart()));
        getStopDateHolder().setValue(task.getFinish());
        getTaskLabelHolder().setTypedValue(task);
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
}
