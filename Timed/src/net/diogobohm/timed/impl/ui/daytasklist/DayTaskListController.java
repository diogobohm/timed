/**
 * TODO: define a license.
 */
package net.diogobohm.timed.impl.ui.daytasklist;

import com.google.common.base.Optional;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.List;
import net.diogobohm.timed.api.domain.Task;
import net.diogobohm.timed.api.ui.mvc.MVCController;
import net.diogobohm.timed.api.ui.domain.DayTaskList;
import net.diogobohm.timed.api.ui.domain.TaskList;
import net.diogobohm.timed.api.ui.mvc.controller.DomainEditor;
import net.diogobohm.timed.impl.ui.taskedit.TaskEditController;
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
            view = new DayTaskListView(getModel(), taskListController.getView(), createNewTaskAction());
        }

        return view;
    }

    public void setDomain(DayTaskList dayTaskList) {
        taskListController.setTasks(dayTaskList.getTaskList());
        getModel().setDomainBean(dayTaskList);
    }

    @Override
    public void updateDomain(Optional<Task> oldValue, Optional<Task> newValue) {
        DayTaskList currentDay = getModel().getDomainBean();
        TaskList taskList = currentDay.getTaskList();

        if (oldValue.isPresent()) {
            taskList.removeTask(oldValue.get());
        }

        if (newValue.isPresent()) {
            taskList.addTask(newValue.get());
        }

        setDomain(currentDay);
    }

    private ActionListener createNewTaskAction() {
        final DomainEditor<Task> caller = this;

        return new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Date date = model.getDay();
                
                TaskEditController editor = new TaskEditController(caller);
                editor.newTask(date);
            }
        };
    }
}
