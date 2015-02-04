/**
 * TODO: define a license.
 */
package net.diogobohm.timed.impl.ui.tasklist;

import com.google.common.collect.Lists;
import java.util.List;
import net.diogobohm.timed.api.domain.Task;
import net.diogobohm.timed.api.ui.domain.TaskList;
import net.diogobohm.timed.api.ui.mvc.MVCModel;
import net.diogobohm.timed.api.ui.mvc.model.TypedValueHolder;
import net.diogobohm.timed.api.ui.mvc.model.TypedValueModel;
import net.diogobohm.timed.impl.ui.factory.TaskItemControllerFactory;
import net.diogobohm.timed.impl.ui.taskitem.TaskItemController;

/**
 *
 * @author diogo.bohm
 */
public class TaskListModel implements MVCModel<TaskList> {

    private final TaskItemControllerFactory itemFactory;
    private final TypedValueModel<List<TaskItemController>> taskItemListHolder;

    public TaskListModel(TaskItemControllerFactory itemFactory) {
        this.itemFactory = itemFactory;
        taskItemListHolder = new TypedValueHolder();
    }

    protected TypedValueModel<List<TaskItemController>> getTaskItemListHolder() {
        return taskItemListHolder;
    }

    @Override
    public TaskList getDomainBean() {
        List<Task> tasks = extractTasks(getTaskItemListHolder().getValue());

        return new TaskList(tasks);
    }

    @Override
    public void setDomainBean(TaskList domainBean) {
        List<Task> tasks = domainBean.getTasks();
        List<TaskItemController> controllers = itemFactory.createTaskItemControllers(tasks);
        
        getTaskItemListHolder().setValue(controllers);
    }

    private List<Task> extractTasks(List<TaskItemController> controllers) {
        List<Task> taskList = Lists.newArrayList();

        for (TaskItemController controller : controllers) {
            taskList.add(controller.getTask());
        }

        return taskList;
    }
}
