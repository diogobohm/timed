/**
 * TODO: define a license.
 */
package net.diogobohm.timed.impl.ui.factory;

import com.google.common.collect.Lists;
import java.awt.Window;
import java.util.List;
import net.diogobohm.timed.api.domain.Task;
import net.diogobohm.timed.api.ui.mvc.controller.DomainEditor;
import net.diogobohm.timed.impl.ui.taskitem.TaskItemController;

/**
 *
 * @author diogo.bohm
 */
public class TaskItemControllerFactory {

    private final DomainEditor<Task> dashboardEditor;

    public TaskItemControllerFactory(DomainEditor<Task> dashboardEditor) {
        this.dashboardEditor = dashboardEditor;
    }

    public List<TaskItemController> createTaskItemControllers(List<Task> tasks, Window owner) {
        List<TaskItemController> controllerList = Lists.newArrayList();

        for (Task task : tasks) {
            TaskItemController controller = new TaskItemController(dashboardEditor, owner);
            controller.setTask(task);

            controllerList.add(controller);
        }

        return controllerList;
    }

}
