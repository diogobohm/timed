/**
 * TODO: define a license.
 */
package net.diogobohm.timed.api.codec;

import java.util.Set;
import net.diogobohm.timed.api.db.domain.DBTask;
import net.diogobohm.timed.api.domain.Activity;
import net.diogobohm.timed.api.domain.Project;
import net.diogobohm.timed.api.domain.Tag;
import net.diogobohm.timed.api.domain.Task;

/**
 *
 * @author diogo.bohm
 */
public interface TaskCodec {

    DBTask encode(Task task, Integer activityId, Integer projectId);

    Task decode(DBTask task, Activity activity, Project project, Set<Tag> tags);
}
