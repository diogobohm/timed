/**
 * TODO: define a license.
 */
package net.diogobohm.timed.impl.codec;

import com.google.common.base.Optional;
import java.text.ParseException;
import java.util.Date;
import java.util.Set;
import net.diogobohm.timed.api.codec.TaskCodec;
import net.diogobohm.timed.api.db.domain.DBTask;
import net.diogobohm.timed.api.domain.Activity;
import net.diogobohm.timed.api.domain.Project;
import net.diogobohm.timed.api.domain.Tag;
import net.diogobohm.timed.api.domain.Task;
import org.apache.commons.lang3.time.FastDateFormat;

/**
 *
 * @author diogo.bohm
 */
public class TaskCodecImpl implements TaskCodec {

    private static final FastDateFormat DATETIME_FORMATTER = FastDateFormat.getInstance("yyyy-MM-dd HH:mm");
    
    @Override
    public DBTask encode(Task task, Integer activityId, Integer projectId) {
        String start = DATETIME_FORMATTER.format(task.getStart());
        String end = null;
        String description = task.getDescription();

        if (task.getFinish().isPresent()) {
            end = DATETIME_FORMATTER.format(task.getFinish().get());
        }

        return new DBTask(activityId, projectId, start, end, description);
    }

    @Override
    public Task decode(DBTask task, Activity activity, Project project, Set<Tag> tags) {
        Date start = null;
        Optional<Date> end = Optional.absent();
        String description = task.getDescription();

        try {
            start = DATETIME_FORMATTER.parse(task.getStartDateTime());
            if (task.getFinishDateTime() != null) {
                end = Optional.of(DATETIME_FORMATTER.parse(task.getFinishDateTime()));
            }
        } catch (ParseException ex) {
            ex.printStackTrace();
        }

        return new Task(activity, project, start, end, description, tags);
    }

    @Override
    public boolean indexChanged(Task value1, Task value2) {
        DBTask task1 = encode(value1, 0, 0);
        DBTask task2 = encode(value2, 0, 0);

        return !task1.getIndexValue().equals(task2.getIndexValue());
    }

}
