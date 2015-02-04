/**
 * TODO: define a license.
 */
package net.diogobohm.timed.impl.ui.taskedit;

import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;
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

    public void newTask(Date initialDate) {
        try {
            getModel().setDomainBean(fetchActivities(), fetchProjects(), initialDate);
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
                    Optional<Task> originalTask = model.getOriginalTask();
                    Task editedTask = model.getEditedTask();

                    updateTask(originalTask, editedTask);
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

    private void updateTask(Optional<Task> originalTask, Task newTask) {
        Database db = DatabaseConnection.getConnection();
        DBPersistenceOrchestrator orchestrator = DBPersistenceOrchestrator.getInstance();

        try {
            orchestrator.writeTask(db, originalTask, newTask);
            caller.updateDomain(originalTask, Optional.of(newTask));
        } catch (DatabaseAccessException exception) {
            exception.printStackTrace();
        }
    }
}
