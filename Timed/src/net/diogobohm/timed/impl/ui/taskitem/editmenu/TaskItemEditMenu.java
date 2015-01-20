/**
 * TODO: define a license.
 */
package net.diogobohm.timed.impl.ui.taskitem.editmenu;

import com.jgoodies.common.swing.MnemonicUtils;
import java.awt.event.ActionListener;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

/**
 *
 * @author diogo.bohm
 */
public class TaskItemEditMenu extends JPopupMenu {

    private final JMenuItem editTaskItem;
    private final JMenuItem deleteTaskItem;

    public TaskItemEditMenu(ActionListener editTaskAction, ActionListener deleteTaskAction) {
        editTaskItem = createEditTaskItem(editTaskAction);
        deleteTaskItem = createDeleteTaskItem(deleteTaskAction);

        add(editTaskItem);
        add(deleteTaskItem);
    }

    private JMenuItem createEditTaskItem(ActionListener editTaskAction) {
        JMenuItem item = new JMenuItem("Edit task");
        item.addActionListener(editTaskAction);

        return item;
    }

    private JMenuItem createDeleteTaskItem(ActionListener deleteTaskAction) {
        JMenuItem item = new JMenuItem("Delete task");
        item.addActionListener(deleteTaskAction);

        return item;
    }
}
