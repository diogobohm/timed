/**
 * TODO: define a license.
 */
package net.diogobohm.timed.api.db.serializer;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.diogobohm.timed.api.codec.ActivityCodec;
import net.diogobohm.timed.api.codec.ProjectCodec;
import net.diogobohm.timed.api.codec.TagCodec;
import net.diogobohm.timed.api.codec.TaskCodec;
import net.diogobohm.timed.api.db.access.Database;
import net.diogobohm.timed.api.db.access.configuration.DBObjectConfiguration;
import net.diogobohm.timed.api.db.domain.DBActivity;
import net.diogobohm.timed.api.db.domain.DBProject;
import net.diogobohm.timed.api.db.domain.DBTag;
import net.diogobohm.timed.api.db.domain.DBTask;
import net.diogobohm.timed.api.db.domain.DBTaskTag;
import net.diogobohm.timed.api.db.exception.DatabaseAccessException;
import net.diogobohm.timed.api.domain.Activity;
import net.diogobohm.timed.api.domain.Project;
import net.diogobohm.timed.api.domain.Tag;
import net.diogobohm.timed.api.domain.Task;
import net.diogobohm.timed.impl.codec.ActivityCodecImpl;
import net.diogobohm.timed.impl.codec.ProjectCodecImpl;
import net.diogobohm.timed.impl.codec.TagCodecImpl;
import net.diogobohm.timed.impl.codec.TaskCodecImpl;

/**
 *
 * @author diogo.bohm
 */
public class DBPersistenceOrchestrator {

    private final ActivityCodec activityCodec;
    private final ProjectCodec projectCodec;
    private final TagCodec tagCodec;
    private final TaskCodec taskCodec;

    public DBPersistenceOrchestrator() {
        this.activityCodec = new ActivityCodecImpl();
        this.projectCodec = new ProjectCodecImpl();
        this.tagCodec = new TagCodecImpl();
        this.taskCodec = new TaskCodecImpl();
    }

    public List<Task> loadTasks(Database database) throws DatabaseAccessException {
        Map<Integer, Activity> activities = loadActivityIndex(database);
        Map<Integer, Project> projects = loadProjectIndex(database);
        Multimap<Integer, Tag> tagIndex = loadTagIndex(database);
        List<DBTask> dbTasks = database.loadObjects(DBObjectConfiguration.TASK);
        List<Task> tasks = Lists.newArrayList();

        for (DBTask task : dbTasks) {
            Activity activity = activities.get(task.getActivityId());
            Project project = projects.get(task.getProjectId());
            Set<Tag> tags = Sets.newHashSet(tagIndex.get(task.getId()));

            tasks.add(taskCodec.decode(task, activity, project, tags));
        }

        return tasks;
    }

    public Map<Integer, Activity> loadActivityIndex(Database database) throws DatabaseAccessException {
        Map<Integer, Activity> activities = Maps.newHashMap();
        List<DBActivity> dbActivities = database.loadObjects(DBObjectConfiguration.ACTIVITY);

        for (DBActivity activity : dbActivities) {
            activities.put(activity.getId(), activityCodec.decode(activity));
        }

        return activities;
    }

    public Map<Integer, Project> loadProjectIndex(Database database) throws DatabaseAccessException {
        Map<Integer, Project> projects = Maps.newHashMap();
        List<DBProject> dbProjects = database.loadObjects(DBObjectConfiguration.PROJECT);

        for (DBProject project : dbProjects) {
            projects.put(project.getId(), projectCodec.decode(project));
        }

        return projects;
    }

    public Multimap<Integer, Tag> loadTagIndex(Database database) throws DatabaseAccessException {
        Multimap<Integer, Tag> taskTags = HashMultimap.create();
        Map<Integer, Tag> tagIndex = Maps.newHashMap();
        List<DBTag> dbTags = database.loadObjects(DBObjectConfiguration.TAG);
        List<DBTaskTag> dbTaskTags = database.loadObjects(DBObjectConfiguration.TASK_TAG);

        for (DBTag tag : dbTags) {
            tagIndex.put(tag.getId(), tagCodec.decode(tag));
        }

        for (DBTaskTag taskTag : dbTaskTags) {
            tagIndex.put(taskTag.getTaskId(), tagIndex.get(taskTag.getTagId()));
        }

        return taskTags;
    }

    public Integer writeTask(Database database, Task task) throws DatabaseAccessException {
        Integer activityId = writeActivity(database, task.getActivity());
        Integer projectId = writeProject(database, task.getProject());
        Set<Integer> tagIds = writeTags(database, task.getTags());
        DBTask dbTask = taskCodec.encode(task, activityId, projectId);

        database.write(dbTask);

        writeTaskTags(database, dbTask.getId(), tagIds);

        return dbTask.getId();
    }

    public Integer writeActivity(Database database, Activity activity) throws DatabaseAccessException {
        DBActivity dbActivity = activityCodec.encode(activity);

        database.write(dbActivity);

        return dbActivity.getId();
    }

    public Integer writeProject(Database database, Project project) throws DatabaseAccessException {
        DBProject dbProject = projectCodec.encode(project);

        database.write(dbProject);

        return dbProject.getId();
    }

    public Integer writeTag(Database database, Tag tag) throws DatabaseAccessException {
        DBTag dbTag = tagCodec.encode(tag);

        database.write(dbTag);

        return dbTag.getId();
    }

    private Integer writeTaskTag(Database database, Integer taskId, Integer tagId) throws DatabaseAccessException {
        DBTaskTag dbTaskTag = new DBTaskTag(taskId, tagId);

        database.write(dbTaskTag);

        return dbTaskTag.getId();
    }

    private void writeTaskTags(Database database, Integer taskId, Set<Integer> tagIds) throws DatabaseAccessException {
        for (Integer tagId : tagIds) {
            writeTaskTag(database, taskId, tagId);
        }
    }

    private Set<Integer> writeTags(Database database, Set<Tag> tags) throws DatabaseAccessException {
        Set<Integer> tagSet = Sets.newHashSet();

        for (Tag tag : tags) {
            tagSet.add(writeTag(database, tag));
        }

        return tagSet;
    }
}