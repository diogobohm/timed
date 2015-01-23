/**
 * TODO: define a license.
 */
package net.diogobohm.timed.impl.ui.tasklist;

import java.awt.Color;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import net.diogobohm.timed.api.ui.mvc.MVCView;
import net.diogobohm.timed.impl.ui.taskitem.TaskItemController;

/**
 *
 * @author diogo.bohm
 */
public class TaskListPanel extends JPanel implements MVCView {

    private final TaskListModel model;

    public TaskListPanel() {
        this(new TaskListModel(null));
    }

    public TaskListPanel(TaskListModel model) {
        this.model = model;
        
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setOpaque(true);
        setBackground(Color.white);
        updateTaskItems();

        this.model.getTaskItemListHolder().addValueChangeListener(createTaskListChangeListener());
    }

    private PropertyChangeListener createTaskListChangeListener() {
        return new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                updateTaskItems();
            }
        };

    }

    private void updateTaskItems() {
        removeAll();

        for (TaskItemController taskController : model.getTaskItemListHolder().getValue()) {
            add(taskController.getView());
        }

        repaint();
    }
}
