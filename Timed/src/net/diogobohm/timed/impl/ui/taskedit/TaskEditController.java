/**
 * TODO: define a license.
 */
package net.diogobohm.timed.impl.ui.taskedit;

import com.google.common.collect.Lists;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.List;
import java.util.Map;
import net.diogobohm.timed.api.db.access.Database;
import net.diogobohm.timed.api.db.access.DatabaseConnection;
import net.diogobohm.timed.api.db.exception.DatabaseAccessException;
import net.diogobohm.timed.api.db.serializer.DBPersistenceOrchestrator;
import net.diogobohm.timed.api.domain.Activity;
import net.diogobohm.timed.api.domain.Project;
import net.diogobohm.timed.api.domain.Task;
import net.diogobohm.timed.api.ui.mvc.MVCController;
import net.diogobohm.timed.api.ui.mvc.controller.DomainEditor;

/**
 *
 * @author diogo.bohm
 */
public class TaskEditController extends MVCController<TaskEditModel, TaskEditView> {

    private final DomainEditor<Task> caller;

    private TaskEditModel model;
    private TaskEditView view;

    public TaskEditController(DomainEditor<Task> caller) {
        this.caller = caller;
    }

    public void editTask(Task task) {
        try {
            getModel().setDomainBean(fetchActivities(), fetchProjects(), task);
            getView().setVisible(true);
        } catch (ParseException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    protected TaskEditModel getModel() {
        if (model == null) {
            model = new TaskEditModel();
        }

        return model;
    }

    @Override
    public TaskEditView getView() {
        if (view == null) {
            view = new TaskEditView(getModel(), createSaveButtonAction());
        }

        return view;
    }

    protected ActionListener createSaveButtonAction() {
        return new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Task originalTask = model.getOriginalTask();
                    Task editedTask = model.getEditedTask();

                    caller.updateDomain(originalTask, editedTask);
                } catch (ParseException ex) {
                    ex.printStackTrace();
                }
                view.dispose();
            }

        };
    }

    private List<Activity> fetchActivities() {
        Database db = DatabaseConnection.getConnection();
        DBPersistenceOrchestrator orchestrator = DBPersistenceOrchestrator.getInstance();

        try {
            Map<Integer, Activity> map = orchestrator.loadActivityIndex(db);

            return Lists.newArrayList(map.values());
        } catch (DatabaseAccessException exception) {
            exception.printStackTrace();
        }

        return Lists.newArrayList();
    }

    private List<Project> fetchProjects() {
        Database db = DatabaseConnection.getConnection();
        DBPersistenceOrchestrator orchestrator = DBPersistenceOrchestrator.getInstance();

        try {
            Map<Integer, Project> map = orchestrator.loadProjectIndex(db);

            return Lists.newArrayList(map.values());
        } catch (DatabaseAccessException exception) {
            exception.printStackTrace();
        }

        return Lists.newArrayList();
    }
}
