/**
 * TODO: define a license.
 */
package net.diogobohm.timed.impl.ui.taskitem;

import com.google.common.base.Optional;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import net.diogobohm.timed.api.db.access.Database;
import net.diogobohm.timed.api.db.access.DatabaseConnection;
import net.diogobohm.timed.api.db.exception.DatabaseAccessException;
import net.diogobohm.timed.api.db.serializer.DBPersistenceOrchestrator;
import net.diogobohm.timed.api.ui.mvc.MVCController;
import net.diogobohm.timed.api.domain.Task;
import net.diogobohm.timed.api.ui.mvc.controller.DomainEditor;
import net.diogobohm.timed.impl.ui.taskedit.TaskEditController;

/**
 *
 * @author diogo.bohm
 */
public class TaskItemController extends MVCController<TaskItemModel, TaskItemPanel> implements DomainEditor<Task> {

    private final DomainEditor<Task> dashboardEditor;
    private final Window owner;

    private TaskItemModel model;
    private TaskItemPanel view;

    public TaskItemController(DomainEditor<Task> dashboardEditor, Window owner) {
        this.dashboardEditor = dashboardEditor;
        this.owner = owner;
    }

    public void setTask(Task task) {
        getModel().setDomainBean(task);
    }

    public Task getTask() {
        return getModel().getDomainBean();
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
            view = new TaskItemPanel(getModel(), createEditTaskAction(), createDeleteTaskAction());
        }

        return view;
    }

    @Override
    public void updateDomain(Optional<Task> oldValue, Optional<Task> newValue) {
        dashboardEditor.updateDomain(oldValue, newValue);

        if (newValue.isPresent()) {
            setTask(newValue.get());
        }
    }

    private void deleteDomain(Task taskToDelete) {
        Database db = DatabaseConnection.getConnection();
        DBPersistenceOrchestrator orchestrator = DBPersistenceOrchestrator.getInstance();

        try {
            orchestrator.removeSingleTask(db, taskToDelete);
            dashboardEditor.updateDomain(Optional.of(taskToDelete), Optional.<Task>absent());
        } catch (DatabaseAccessException exception) {
            exception.printStackTrace();
        }
    }

    private ActionListener createEditTaskAction() {
        final DomainEditor<Task> caller = this;

        return new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                TaskEditController editor = new TaskEditController(caller, owner);
                editor.editTask(getModel().getDomainBean());
            }
        };
    }

    private ActionListener createDeleteTaskAction() {
        return new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Task task = model.getDomainBean();

                deleteDomain(task);
            }
        };
    }

}
