/**
 * TODO: define a license.
 */
package net.diogobohm.timed.impl.ui.daytasklist;

import com.google.common.base.Optional;
import com.google.common.collect.Sets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.Set;
import net.diogobohm.timed.api.db.access.Database;
import net.diogobohm.timed.api.db.access.DatabaseConnection;
import net.diogobohm.timed.api.db.exception.DatabaseAccessException;
import net.diogobohm.timed.api.db.serializer.DBPersistenceOrchestrator;
import net.diogobohm.timed.api.domain.Activity;
import net.diogobohm.timed.api.domain.Project;
import net.diogobohm.timed.api.domain.Tag;
import net.diogobohm.timed.api.domain.Task;
import net.diogobohm.timed.api.ui.mvc.MVCController;
import net.diogobohm.timed.api.ui.domain.DayTaskList;
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
        taskListController.setTasks(dayTaskList.getTasks());
        getModel().setDomainBean(dayTaskList);
    }

    @Override
    public void updateDomain(Task oldValue, Task newValue) {
        Database db = DatabaseConnection.getConnection();
        DBPersistenceOrchestrator orchestrator = DBPersistenceOrchestrator.getInstance();

        try {
            orchestrator.writeSingleTask(db, newValue);
        } catch (DatabaseAccessException exception) {
            exception.printStackTrace();
        }

        // Make it refresh the context.
    }

    private ActionListener createNewTaskAction() {
        final DomainEditor<Task> caller = this;

        return new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Date date = model.getDay();
                Set<Tag> tags = Sets.newHashSet();
                Task task = new Task(new Activity(""), new Project(""), date, Optional.of(date), "", tags);

                TaskEditController editor = new TaskEditController(caller);
                editor.editTask(task);
            }
        };
    }
}
