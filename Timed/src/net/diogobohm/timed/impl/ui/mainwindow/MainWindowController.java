/**
 * TODO: define a license.
 */
package net.diogobohm.timed.impl.ui.mainwindow;

import com.google.common.collect.Lists;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import net.diogobohm.timed.api.db.access.Database;
import net.diogobohm.timed.api.db.access.DatabaseConnection;
import net.diogobohm.timed.api.db.exception.DatabaseAccessException;
import net.diogobohm.timed.api.db.serializer.DBPersistenceOrchestrator;
import net.diogobohm.timed.api.ui.mvc.MVCController;
import net.diogobohm.timed.api.ui.domain.Dashboard;
import net.diogobohm.timed.api.domain.Task;
import net.diogobohm.timed.api.ui.mvc.controller.DomainEditor;

/**
 *
 * @author diogo.bohm
 */
public class MainWindowController extends MVCController<MainWindowModel, MainWindow> {

    private MainWindowModel model;
    private MainWindow view;

    public void showView() {
        getView().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getView().setVisible(true);
    }

    public void addTodaysTasks() {
        Dashboard dashboard = new Dashboard();

        dashboard.getTasks().addAll(getTodaysTasks());

        getModel().setDomainBean(dashboard);
    }

    @Override
    protected MainWindowModel getModel() {
        if (model == null) {
            model = new MainWindowModel();
        }

        return model;
    }

    @Override
    protected MainWindow getView() {
        if (view == null) {
            view = new MainWindow(getModel());
        }

        return view;
    }

    private Collection<Task> getTodaysTasks() {
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
