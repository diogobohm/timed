/**
 * TODO: define a license.
 */
package net.diogobohm.timed.impl.ui.mainwindow;

import com.google.common.collect.Lists;
import java.util.List;
import javax.swing.JFrame;
import net.diogobohm.timed.api.db.access.Database;
import net.diogobohm.timed.api.db.access.DatabaseConnection;
import net.diogobohm.timed.api.db.exception.DatabaseAccessException;
import net.diogobohm.timed.api.db.serializer.DBPersistenceOrchestrator;
import net.diogobohm.timed.api.ui.mvc.MVCController;
import net.diogobohm.timed.api.ui.domain.Dashboard;
import net.diogobohm.timed.api.domain.Task;
import net.diogobohm.timed.api.ui.mvc.controller.DomainEditor;
import net.diogobohm.timed.impl.ui.tasklist.TaskListController;

/**
 *
 * @author diogo.bohm
 */
public class MainWindowController extends MVCController<MainWindowModel, MainWindowView> implements DomainEditor<Task> {

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
        List<Task> todaysTasks = fetchTodaysTasks();

        getModel().setDomainBean(new Dashboard(todaysTasks));
    }

    @Override
    public void updateDomain(Task oldValue, Task newValue) {
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
            view = new MainWindowView(getModel(), taskListController.getView());
        }

        return view;
    }

    private List<Task> fetchTodaysTasks() {
        Database db = DatabaseConnection.getConnection();
        DBPersistenceOrchestrator orchestrator = DBPersistenceOrchestrator.getInstance();

        try {
            return orchestrator.loadTasks(db, "2011-10-01 00:00", "2011-10-29 23:59");
        } catch (DatabaseAccessException exception) {
            exception.printStackTrace();
        }

        return Lists.newArrayList();
    }
}
