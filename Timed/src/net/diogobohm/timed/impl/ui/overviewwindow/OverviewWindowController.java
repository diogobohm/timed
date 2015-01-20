/**
 * TODO: define a license.
 */
package net.diogobohm.timed.impl.ui.overviewwindow;

import net.diogobohm.timed.impl.ui.mainwindow.*;
import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.swing.JFrame;
import net.diogobohm.timed.api.db.access.Database;
import net.diogobohm.timed.api.db.access.DatabaseConnection;
import net.diogobohm.timed.api.db.exception.DatabaseAccessException;
import net.diogobohm.timed.api.db.serializer.DBPersistenceOrchestrator;
import net.diogobohm.timed.api.domain.Activity;
import net.diogobohm.timed.api.domain.Project;
import net.diogobohm.timed.api.ui.mvc.MVCController;
import net.diogobohm.timed.api.ui.domain.Dashboard;
import net.diogobohm.timed.api.domain.Task;
import net.diogobohm.timed.api.ui.domain.Overview;
import net.diogobohm.timed.api.ui.mvc.controller.DomainEditor;
import net.diogobohm.timed.impl.ui.tasklist.TaskListController;
import org.apache.commons.lang3.time.FastDateFormat;

/**
 *
 * @author diogo.bohm
 */
public class OverviewWindowController extends MVCController<OverviewWindowModel, OverviewWindowView> implements DomainEditor<Overview> {

    private static final FastDateFormat DAY_FORMATTER = FastDateFormat.getInstance("yyyy-MM-dd");

    private OverviewWindowModel model;
    private OverviewWindowView view;

    public OverviewWindowController(OverviewBuilder overviewBuilder) {
    }

    public void showView() {
        getView().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getView().setVisible(true);
    }

    @Override
    protected OverviewWindowModel getModel() {
        if (model == null) {
            model = new OverviewWindowModel();
        }

        return model;
    }

    @Override
    protected OverviewWindowView getView() {
        if (view == null) {
            view = new OverviewWindowView(getModel());
        }

        return view;
    }

    private Overview fetchDefaultOverview() {
        Database db = DatabaseConnection.getConnection();
        DBPersistenceOrchestrator orchestrator = DBPersistenceOrchestrator.getInstance();
        String lastSunday = DAY_FORMATTER.format(getLastSunday());
        String currentDay = DAY_FORMATTER.format(new Date());
        List<Task> tasks = Lists.newArrayList();

        try {
            tasks.addAll(orchestrator.loadTasks(db, lastSunday + " 00:00", currentDay + " 23:59"));
        } catch (DatabaseAccessException exception) {
            exception.printStackTrace();
        }

        return overviewBuilder.buildOverview(tasks);
    }

    private Collection<Activity> fetchActivities() {
        Database db = DatabaseConnection.getConnection();
        DBPersistenceOrchestrator orchestrator = DBPersistenceOrchestrator.getInstance();

        try {
            return orchestrator.loadActivityIndex(db).values();
        } catch (DatabaseAccessException exception) {
            exception.printStackTrace();
        }

        return Lists.newArrayList();
    }

    private Collection<Project> fetchProjects() {
        Database db = DatabaseConnection.getConnection();
        DBPersistenceOrchestrator orchestrator = DBPersistenceOrchestrator.getInstance();

        try {
            return orchestrator.loadProjectIndex(db).values();
        } catch (DatabaseAccessException exception) {
            exception.printStackTrace();
        }

        return Lists.newArrayList();
    }

    private Dashboard fetchDashboard() {
        List<Task> todaysTasks = fetchTodaysTasks();
        Collection<Activity> activities = fetchActivities();
        Collection<Project> projects = fetchProjects();

        return new Dashboard(todaysTasks, activities, projects);
    }

    private void writeNewTask(Task newTask) {
        Database db = DatabaseConnection.getConnection();
        DBPersistenceOrchestrator orchestrator = DBPersistenceOrchestrator.getInstance();
        Collection<Task> tasksToUpdate = Lists.newArrayList(newTask);
        Optional<Task> currentTask = model.getCurrentTask();

        if (currentTask.isPresent()) {
            Task currentFinishedTask = finishCurrentTask(currentTask.get(), newTask);

            tasksToUpdate.add(currentFinishedTask);
        }

        try {
            orchestrator.writeTasks(db, tasksToUpdate);
        } catch (DatabaseAccessException exception) {
            exception.printStackTrace();
        }
    }

    private ActionListener createNewTaskAction() {
        return new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Task newTask = getModel().getNewTask();

                writeNewTask(newTask);
                refreshDashBoard();
            }
        };
    }

    private Task finishCurrentTask(Task currentOpenTask, Task newTask) {
        Date endDate = new Date(newTask.getStart().getTime() - 60000);

        return new Task(currentOpenTask.getActivity(), currentOpenTask.getProject(), currentOpenTask.getStart(),
                Optional.of(endDate), currentOpenTask.getDescription(), currentOpenTask.getTags());
    }

    @Override
    public void updateDomain(Overview oldValue, Overview newValue) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private Object getLastSunday() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
