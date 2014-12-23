/**
 * TODO: define a license.
 */
package net.diogobohm.timed.impl.ui.taskitem;

import net.diogobohm.timed.api.ui.mvc.MVCController;
import net.diogobohm.timed.api.domain.Task;

/**
 *
 * @author diogo.bohm
 */
public class TaskItemController extends MVCController<TaskItemModel, TaskItemPanel> {

    private TaskItemModel model;
    private TaskItemPanel view;

    public void setTask(Task task) {
        getModel().setDomainBean(task);
    }

    @Override
    protected TaskItemModel getModel() {
        if (model == null) {
            model = new TaskItemModel();
        }

        return model;
    }

    @Override
    public TaskItemPanel getView() {
        if (view == null) {
            view = new TaskItemPanel(getModel());
        }

        return view;
    }
}
