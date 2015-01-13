/**
 * TODO: define a license.
 */
package net.diogobohm.timed.impl.ui.mainwindow;

import java.util.Collection;
import javax.swing.JFrame;
import net.diogobohm.timed.api.ui.mvc.MVCController;
import net.diogobohm.timed.api.ui.domain.Dashboard;
import net.diogobohm.timed.api.domain.Task;

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

    public void addTasks(Collection<Task> tasks) {
        Dashboard dashboard = new Dashboard();
        dashboard.getTasks().addAll(tasks);

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

}
