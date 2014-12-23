/**
 * TODO: define a license.
 */
package net.diogobohm.timed.api.ui.mvc.model;

import com.google.common.collect.Lists;
import java.util.List;
import javax.swing.AbstractListModel;
import net.diogobohm.timed.api.domain.Task;
import net.diogobohm.timed.impl.ui.taskitem.TaskItemController;

/**
 *
 * @author diogo.bohm
 */
public class TaskListModel extends AbstractListModel<TaskItemController> {

    private final List<TaskItemController> controllers;

    public TaskListModel(TypedValueModel<List<Task>> model) {
        controllers = buildTaskItemControllers(model);
    }

    @Override
    public int getSize() {
        return controllers.size();
    }

    @Override
    public TaskItemController getElementAt(int index) {
        return controllers.get(index);
    }

    private List<TaskItemController> buildTaskItemControllers(TypedValueModel<List<Task>> model) {
        List<TaskItemController> controllerList = Lists.newArrayList();

        for (Task task : model.getValue()) {
            TaskItemController controller = new TaskItemController();
            controller.setTask(task);

            controllerList.add(controller);
        }

        return controllerList;
    }


}
