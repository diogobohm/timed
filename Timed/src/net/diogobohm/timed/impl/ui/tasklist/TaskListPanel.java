/**
 * TODO: define a license.
 */
package net.diogobohm.timed.impl.ui.tasklist;

import com.jgoodies.binding.adapter.Bindings;
import java.awt.Color;
import java.awt.GridLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.JPanel;
import net.diogobohm.timed.api.ui.mvc.MVCView;
import net.diogobohm.timed.impl.ui.taskitem.TaskItemController;

/**
 *
 * @author diogo.bohm
 */
public class TaskListPanel extends JPanel implements MVCView {

    private final TaskListModel model;

    public TaskListPanel(TaskListModel model) {
        this.model = model;
        
        setLayout(new GridLayout(0, 1));
        setOpaque(true);
        setBackground(Color.white);

        this.model.getTaskItemListHolder().addValueChangeListener(createTaskListChangeListener());
    }

    private PropertyChangeListener createTaskListChangeListener() {
        return new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                removeAll();

                for (TaskItemController taskController : model.getTaskItemListHolder().getValue()) {
                    add(taskController.getView());
                }

                repaint();
            }
        };

    }
}
