/**
 * TODO: define a license.
 */
package net.diogobohm.timed.impl.ui.mainwindow;

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
import net.diogobohm.timed.api.ui.domain.TaskList;
import net.diogobohm.timed.api.ui.mvc.controller.DomainEditor;
import net.diogobohm.timed.impl.ui.overviewwindow.OverviewWindowController;
import net.diogobohm.timed.impl.ui.tasklist.TaskListController;
import org.apache.commons.lang3.time.FastDateFormat;

/**
 *
 * @author diogo.bohm
 */
public class MainWindowController extends MVCController<MainWindowModel, MainWindowView> implements DomainEditor<Task> {

    private static final FastDateFormat DAY_FORMATTER = FastDateFormat.getInstance("yyyy-MM-dd");

    private final TaskListController taskListController;

    private MainWindowModel model;
    private MainWindowView view;

    public MainWindowController() {
        taskListController = new TaskListController(this);
    }

    public void showView() {
        getView().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getView().setVisible(true);
    }

    public void refreshDashBoard() {
        Dashboard dashboard = fetchDashboard();

        getModel().setDomainBean(dashboard);
    }

    @Override
    public void updateDomain(Optional<Task> oldValue, Optional<Task> newValue) {
        refreshDashBoard();
    }

    @Override
    protected MainWindowModel getModel() {
        if (model == null) {
            model = new MainWindowModel(taskListController.getModel());
        }

        return model;
    }

    @Override
    protected MainWindowView getView() {
        if (view == null) {
            view = new MainWindowView(getModel(), taskListController.getView(), createNewTaskAction(),
                    createShowOverviewAction());
        }

        return view;
    }

    private TaskList fetchTodaysTasks() {
        Database db = DatabaseConnection.getConnection();
        DBPersistenceOrchestrator orchestrator = DBPersistenceOrchestrator.getInstance();
        String currentDay = DAY_FORMATTER.format(new Date());

        try {
            List<Task> tasks = orchestrator.loadTasks(db, currentDay + " 00:00", currentDay + " 23:59");

            return new TaskList(tasks);
        } catch (DatabaseAccessException exception) {
            exception.printStackTrace();
        }

        return new TaskList(Lists.<Task>newArrayList());
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
        TaskList todaysTasks = fetchTodaysTasks();
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

    private ActionListener createShowOverviewAction() {
        return new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                OverviewWindowController overview = new OverviewWindowController();

                overview.showDefaultOverview();
            }
        };
    }
}
