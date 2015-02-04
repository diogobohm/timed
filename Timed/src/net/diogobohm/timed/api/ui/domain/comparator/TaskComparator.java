/**
 * TODO: define a license.
 */
package net.diogobohm.timed.api.ui.domain.comparator;

import java.util.Comparator;
import net.diogobohm.timed.api.domain.Task;

/**
 *
 * @author diogo.bohm
 */
public class TaskComparator implements Comparator<Task> {

    @Override
    public int compare(Task o1, Task o2) {
        if (o1 == null || o2 == null) {
            return 0;
        }

        return o1.getStart().compareTo(o2.getStart());
    }

}
