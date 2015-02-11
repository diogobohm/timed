/**
 * TODO: define a license.
 */
package net.diogobohm.timed.impl.ui.tasklist;

import java.awt.Window;
import net.diogobohm.timed.api.domain.Task;
import net.diogobohm.timed.api.ui.domain.TaskList;
import net.diogobohm.timed.api.ui.mvc.MVCController;
import net.diogobohm.timed.api.ui.mvc.controller.DomainEditor;
import net.diogobohm.timed.impl.ui.factory.TaskItemControllerFactory;

/**
 *
 * @author diogo.bohm
 */
public class TaskListController extends MVCController<TaskListModel, TaskListPanel> {

    private final TaskItemControllerFactory itemFactory;

    private TaskListModel model;
    private TaskListPanel view;
    private Window owner;

    public TaskListController(DomainEditor<Task> dashboardEditor) {
        itemFactory = new TaskItemControllerFactory(dashboardEditor);
    }

    public void setOwner(Window owner) {
        getModel().setOwner(owner);
    }

    public void setTasks(TaskList taskList) {
        getModel().setDomainBean(taskList);
    }

    @Override
    public TaskListModel getModel() {
        if (model == null) {
            model = new TaskListModel(itemFactory);
        }

        return model;
    }

    @Override
    public TaskListPanel getView() {
        if (view == null) {
            view = new TaskListPanel(getModel());
        }

        return view;
    }

}
