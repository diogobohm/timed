/**
 * TODO: define a license.
 */
package net.diogobohm.timed.impl.ui.mainwindow;

import com.google.common.base.Optional;
import com.google.common.collect.Sets;
import java.util.Date;
import java.util.Set;
import javax.swing.JFrame;
import net.diogobohm.timed.api.domain.Activity;
import net.diogobohm.timed.api.ui.mvc.MVCController;
import net.diogobohm.timed.api.domain.Dashboard;
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
        addTasks();

        getView().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getView().setVisible(true);
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

    private void addTasks() {
        Tag codingTag = new Tag("Coding");
        Tag testTag = new Tag("Test");
        Project project1 = new Project("Project1");
        Project project2 = new Project("Project2");
        Activity activity1 = new Activity("TA1234 - Doitall");
        Activity activity2 = new Activity("TA1235 - DoitallOnceAgain");

        Task task1 = new Task(new Date(), Optional.of(new Date()), activity1,
                Sets.newHashSet(codingTag, testTag), project1, "");

        Task task2 = new Task(new Date(), Optional.<Date>absent(), activity2,
                Sets.newHashSet(codingTag), project2, "");

        Dashboard dashboard = new Dashboard();
        dashboard.getTasks().add(task1);
        dashboard.getTasks().add(task2);

        getModel().setDomainBean(dashboard);
    }

}
