/**
 * TODO: define a license.
 */
package net.diogobohm.timed.impl.ui.daytasklist;

import net.diogobohm.timed.api.domain.Task;
import net.diogobohm.timed.api.ui.mvc.MVCController;
import net.diogobohm.timed.api.ui.domain.DayTaskList;
import net.diogobohm.timed.api.ui.domain.Overview;
import net.diogobohm.timed.api.ui.mvc.controller.DomainEditor;
import net.diogobohm.timed.impl.ui.tasklist.TaskListController;

/**
 *
 * @author diogo.bohm
 */
public class DayTaskListController extends MVCController<DayTaskListModel, DayTaskListView> implements DomainEditor<Task> {

    private final TaskListController taskListController;

    private DayTaskListModel model;
    private DayTaskListView view;

    public DayTaskListController() {
        taskListController = new TaskListController(this);
    }

    @Override
    protected DayTaskListModel getModel() {
        if (model == null) {
            model = new DayTaskListModel();
        }

        return model;
    }

    @Override
    public DayTaskListView getView() {
        if (view == null) {
            view = new DayTaskListView(getModel(), taskListController.getView());
        }

        return view;
    }

    public void setDomain(DayTaskList dayTaskList) {
        taskListController.setTasks(dayTaskList.getTasks());
        getModel().setDomainBean(dayTaskList);
    }

    @Override
    public void updateDomain(Task oldValue, Task newValue) {

    }

}
