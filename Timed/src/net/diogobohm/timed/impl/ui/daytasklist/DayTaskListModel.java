/**
 * TODO: define a license.
 */
package net.diogobohm.timed.impl.ui.daytasklist;

import com.google.common.base.Optional;
import java.util.Date;
import net.diogobohm.timed.api.ui.mvc.MVCModel;
import net.diogobohm.timed.api.ui.mvc.model.LabeledBeanHolder;
import net.diogobohm.timed.api.ui.mvc.model.TaskDateValueHolder;
import net.diogobohm.timed.api.ui.mvc.model.TypedValueModel;
import net.diogobohm.timed.api.domain.Task;
import net.diogobohm.timed.api.ui.mvc.model.NewTypedValueModel;

/**
 *
 * @author diogo.bohm
 */
public class DayTaskListModel implements MVCModel<Task> {

    private final TypedValueModel<Optional<Date>> startDateHolder;
    private final TypedValueModel<Optional<Date>> stopDateHolder;
    private final LabeledBeanHolder<Task> taskLabelHolder;
    private final NewTypedValueModel<String> taskElapsedTimeHolder;

    public DayTaskListModel() {
        startDateHolder = new TaskDateValueHolder();
        stopDateHolder = new TaskDateValueHolder();
        taskLabelHolder = new LabeledBeanHolder();
        taskElapsedTimeHolder = new NewTypedValueModel();
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
        getTaskElapsedTimeHolder().setTypedValue(Task.convertWorkedTimeToString(getTaskTime(task)));
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

    public NewTypedValueModel<String> getTaskElapsedTimeHolder() {
        return taskElapsedTimeHolder;
    }

    private long getTaskTime(Task task) {
        Date startDate = task.getStart();
        Date endDate = new Date();

        if (task.getFinish().isPresent()) {
            endDate = task.getFinish().get();
        }

        return endDate.getTime() - startDate.getTime();
    }
}
