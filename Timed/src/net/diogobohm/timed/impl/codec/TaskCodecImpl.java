/**
 * TODO: define a license.
 */
package net.diogobohm.timed.impl.codec;

import com.google.common.base.Optional;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;
import net.diogobohm.timed.api.codec.ActivityCodec;
import net.diogobohm.timed.api.codec.ProjectCodec;
import net.diogobohm.timed.api.codec.TaskCodec;
import net.diogobohm.timed.api.db.domain.DBActivity;
import net.diogobohm.timed.api.db.domain.DBProject;
import net.diogobohm.timed.api.db.domain.DBTask;
import net.diogobohm.timed.api.domain.Activity;
import net.diogobohm.timed.api.domain.Project;
import net.diogobohm.timed.api.domain.Tag;
import net.diogobohm.timed.api.domain.Task;

/**
 *
 * @author diogo.bohm
 */
public class TaskCodecImpl implements TaskCodec {

    private static final SimpleDateFormat DATETIME_FORMATTER = new SimpleDateFormat("EEE MMM dd hh:mm:ss z YYYY");

    @Override
    public DBTask encode(Task task, Integer activityId, Integer projectId) {
        String start = task.getStart().toString();
        String end = null;

        if (task.getFinish().isPresent()) {
            end = task.getFinish().get().toString();
        }

        return new DBTask(start, end, activityId, projectId, task.getDescription());
    }

    @Override
    public Task decode(DBTask task, Activity activity, Project project, Set<Tag> tags) {
        Date start = null;
        Optional<Date> end = Optional.absent();

        try {
            start = DATETIME_FORMATTER.parse(task.getStartDateTime());
            if (task.getFinishDateTime() != null) {
                end = Optional.of(DATETIME_FORMATTER.parse(task.getFinishDateTime()));
            }
        } catch (ParseException ex) {
            ex.printStackTrace();
        }

        return new Task(start, end, activity, tags, project, task.getDescription());
    }

}
