/**
 * TODO: define a license.
 */
package net.diogobohm.timed.impl.ui.taskedit;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Action;
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
            getModel().setDomainBean(task);
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
            view = new TaskEditView(getModel());
        }

        return view;
    }

    protected ActionListener createSaveButtonAction() {
        return new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Task editedTask = model.getEditedTask();

                    caller.updateDomain(editedTask, editedTask);
                } catch (ParseException ex) {
                    ex.printStackTrace();
                }
            }

        };
    }
}
