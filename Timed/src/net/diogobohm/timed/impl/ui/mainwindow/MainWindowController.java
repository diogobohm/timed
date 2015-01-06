/**
 * TODO: define a license.
 */
package net.diogobohm.timed.impl.ui.mainwindow;

import com.google.common.base.Optional;
import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.Date;
import java.util.Set;
import javax.swing.JFrame;
import net.diogobohm.timed.api.domain.Activity;
import net.diogobohm.timed.api.ui.mvc.MVCController;
import net.diogobohm.timed.api.ui.domain.Dashboard;
import net.diogobohm.timed.api.domain.Project;
import net.diogobohm.timed.api.domain.Tag;
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
