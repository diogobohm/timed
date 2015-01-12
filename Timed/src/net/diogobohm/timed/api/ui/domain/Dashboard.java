/**
 * TODO: define a license.
 */
package net.diogobohm.timed.api.ui.domain;

import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import java.util.List;
import net.diogobohm.timed.api.domain.Task;

/**
 *
 * @author diogo.bohm
 */
public class Dashboard {

    private final List<Task> tasks;

    public Dashboard() {
        tasks = Lists.newArrayList();
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public Optional<Task> getCurrentTask() {
        if (tasks.isEmpty()) {
            return Optional.absent();
        }

        return Optional.of(tasks.get(tasks.size() - 1));
    }

}