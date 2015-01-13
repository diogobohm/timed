/**
 * TODO: define a license.
 */
package net.diogobohm.timed.impl.ui.taskitem;

import com.google.common.collect.Lists;
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

    private TaskItemModel model;
    private TaskItemPanel view;

    public void setTask(Task task) {
        getModel().setDomainBean(task);
    }

    @Override
    protected TaskItemModel getModel() {
        if (model == null) {
            model = new TaskItemModel(createEditButtonAction());
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

    @Override
    public void updateDomain(Task oldValue, Task newValue) {
        Database db = DatabaseConnection.getConnection();
        DBPersistenceOrchestrator orchestrator = DBPersistenceOrchestrator.getInstance();

        try {
            //db.remove(oldValue);
            orchestrator.writeTasks(db, Lists.newArrayList(newValue));
        } catch (DatabaseAccessException exception) {
            exception.printStackTrace();
        }

        setTask(newValue);
    }

    private ActionListener createEditButtonAction() {
        final DomainEditor<Task> caller = this;

        return new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                TaskEditController editor = new TaskEditController(caller);
                editor.editTask(model.getDomainBean());
            }
        };
    }
}
