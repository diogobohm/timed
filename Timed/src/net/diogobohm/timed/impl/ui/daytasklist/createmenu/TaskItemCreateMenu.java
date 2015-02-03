/**
 * TODO: define a license.
 */
package net.diogobohm.timed.impl.ui.daytasklist.createmenu;

import java.awt.event.ActionListener;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

/**
 *
 * @author diogo.bohm
 */
public class TaskItemCreateMenu extends JPopupMenu {

    private final JMenuItem newTaskItem;

    public TaskItemCreateMenu(ActionListener newTaskAction) {
        newTaskItem = createNewTaskItem(newTaskAction);

        add(newTaskItem);
    }

    private JMenuItem createNewTaskItem(ActionListener newTaskAction) {
        JMenuItem item = new JMenuItem("New task");
        item.addActionListener(newTaskAction);

        return item;
    }
}
