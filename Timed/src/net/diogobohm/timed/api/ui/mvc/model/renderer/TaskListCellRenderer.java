/**
 * TODO: define a license.
 */
package net.diogobohm.timed.api.ui.mvc.model.renderer;

import java.awt.Component;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import net.diogobohm.timed.impl.ui.taskitem.TaskItemController;

/**
 *
 * @author diogo.bohm
 */
public class TaskListCellRenderer implements ListCellRenderer<TaskItemController> {

    @Override
    public Component getListCellRendererComponent(JList<? extends TaskItemController> list, TaskItemController value,
            int index, boolean isSelected, boolean cellHasFocus) {
        return value.getView();
    }

}
