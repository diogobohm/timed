/**
 * TODO: define a license.
 */
package net.diogobohm.timed.impl.ui.tasklist;

import java.util.List;
import net.diogobohm.timed.api.domain.Task;
import net.diogobohm.timed.api.ui.domain.Dashboard;
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

    public TaskListController(DomainEditor<Task> dashboardEditor) {
        itemFactory = new TaskItemControllerFactory(dashboardEditor);
    }

    public void setTasks(List<Task> tasks) {
        getModel().setDomainBean(tasks);
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
