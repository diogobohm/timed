/**
 * TODO: define a license.
 */
package net.diogobohm.timed.impl.hamster.migration;

import com.google.common.base.Optional;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import net.diogobohm.timed.api.domain.Activity;
import net.diogobohm.timed.api.domain.Project;
import net.diogobohm.timed.api.domain.Tag;
import net.diogobohm.timed.api.domain.Task;
import org.apache.commons.lang3.time.FastDateFormat;
import org.tmatesoft.sqljet.core.SqlJetException;
import org.tmatesoft.sqljet.core.SqlJetTransactionMode;
import org.tmatesoft.sqljet.core.table.ISqlJetCursor;
import org.tmatesoft.sqljet.core.table.ISqlJetTable;
import org.tmatesoft.sqljet.core.table.SqlJetDb;

/**
 *
 * @author diogo.bohm
 */
public class HamsterMigration {
    
    private static final FastDateFormat DATETIME_FORMATTER = FastDateFormat.getInstance("yyyy-MM-dd HH:mm");
    private static final Project UNDEFINED_PROJECT = new Project("Undefined");
    
    public Collection<Task> migrateHamsterDatabase(File hamsterDbFile) throws SqlJetException {
        SqlJetDb hamsterDb = SqlJetDb.open(hamsterDbFile, false);
        
        hamsterDb.beginTransaction(SqlJetTransactionMode.READ_ONLY);
        
        Map<Integer, Tag> tags = loadTags(hamsterDb);
        Multimap<Integer, Tag> taskTags = loadTaskTags(hamsterDb, tags);
        Map<Integer, Project> projects = loadProjects(hamsterDb);
        Map<Activity, Project> activityProjects = Maps.newHashMap();
        Map<Integer, Activity> activities = loadActivities(hamsterDb, projects, activityProjects);
        Map<Integer, Task> tasks = loadTasks(hamsterDb, activities, activityProjects, taskTags);
        
        hamsterDb.close();
        
        return tasks.values();
    }
    
    private Map<Integer, Tag> loadTags(SqlJetDb hamsterDb) throws SqlJetException {
        Map<Integer, Tag> tags = Maps.newHashMap();
        ISqlJetTable tagsTable = hamsterDb.getTable("tags");
        ISqlJetCursor tagsCursor = tagsTable.order(tagsTable.getPrimaryKeyIndexName());
        
        if (!tagsCursor.eof()) {
            do {
                Integer id = fromLong(tagsCursor.getInteger("id"));
                String name = tagsCursor.getString("name");
                
                tags.put(id, new Tag(name));
            } while (tagsCursor.next());
        }
        
        return tags;
    }
    
    private Multimap<Integer, Tag> loadTaskTags(SqlJetDb hamsterDb, Map<Integer, Tag> tags) throws SqlJetException {
        Multimap<Integer, Tag> taskTagIndex = HashMultimap.create();
        ISqlJetTable taskTagsTable = hamsterDb.getTable("fact_tags");
        ISqlJetCursor taskTagsCursor = taskTagsTable.open();
        
        if (!taskTagsCursor.eof()) {
            do {
                Integer taskId = fromLong(taskTagsCursor.getInteger("fact_id"));
                Integer tagId = fromLong(taskTagsCursor.getInteger("tag_id"));
                
                taskTagIndex.put(taskId, tags.get(tagId));
            } while (taskTagsCursor.next());
        }
        
        return taskTagIndex;
    }
    
    private Map<Integer, Project> loadProjects(SqlJetDb hamsterDb) throws SqlJetException {
        Map<Integer, Project> projects = Maps.newHashMap();
        ISqlJetTable projectsTable = hamsterDb.getTable("categories");
        ISqlJetCursor projectsCursor = projectsTable.order(projectsTable.getPrimaryKeyIndexName());
        
        if (!projectsCursor.eof()) {
            do {
                Integer id = fromLong(projectsCursor.getInteger("id"));
                String name = projectsCursor.getString("name");
                
                projects.put(id, new Project(name));
            } while (projectsCursor.next());
        }
        
        return projects;
    }
    
    private Map<Integer, Activity> loadActivities(SqlJetDb hamsterDb, Map<Integer, Project> projects,
            Map<Activity, Project> activityProjects) throws SqlJetException {
        Map<Integer, Activity> activities = Maps.newHashMap();
        ISqlJetTable activitiesTable = hamsterDb.getTable("activities");
        ISqlJetCursor activitiesCursor = activitiesTable.order(activitiesTable.getPrimaryKeyIndexName());
        
        if (!activitiesCursor.eof()) {
            do {
                Integer id = fromLong(activitiesCursor.getInteger("id"));
                String name = activitiesCursor.getString("name");
                Integer projectId = fromLong(activitiesCursor.getInteger("category_id"));
                Activity activity = new Activity(name);
                
                activities.put(id, activity);
                
                if (projectId > 0) {
                    activityProjects.put(activity, projects.get(projectId));
                } else {
                    activityProjects.put(activity, UNDEFINED_PROJECT);
                }
                
            } while (activitiesCursor.next());
        }
        
        return activities;
    }
    
    private Map<Integer, Task> loadTasks(SqlJetDb hamsterDb, Map<Integer, Activity> activityMap,
            Map<Activity, Project> activityProjects, Multimap<Integer, Tag> taskTags) throws SqlJetException {
        Map<Integer, Task> tasks = Maps.newHashMap();
        ISqlJetTable tasksTable = hamsterDb.getTable("facts");
        ISqlJetCursor tasksCursor = tasksTable.order(tasksTable.getPrimaryKeyIndexName());
        
        if (!tasksCursor.eof()) {
            do {
                Integer activityId = fromLong(tasksCursor.getInteger("activity_id"));
                Activity activity = activityMap.get(activityId);
                Project project = activityProjects.get(activity);
                
                Integer id = fromLong(tasksCursor.getInteger("id"));
                String description = tasksCursor.getString("description");
                String start = tasksCursor.getString("start_time");
                String end = tasksCursor.getString("end_time");
                
                Date startTime = parseTime(start);
                Optional<Date> endTime = Optional.absent();
                if (!tasksCursor.isNull("end_time")) {
                    if (start.equals(end)) {
                        System.out.println("BAAAAD");
                    }
                    endTime = Optional.of(parseTime(end));
                }
                
                Set<Tag> tags = Sets.newHashSet();
                if (taskTags.containsKey(id)) {
                    tags.addAll(taskTags.get(id));
                }
                
                tasks.put(id, new Task(activity, project, startTime, endTime, description, tags));
            } while (tasksCursor.next());
        }
        
        return tasks;
    }
    
    private Integer fromLong(long number) {
        return Long.valueOf(number).intValue();
    }
    
    private Date parseTime(String date) {
        try {
            return DATETIME_FORMATTER.parse(date.substring(0, 16));
        } catch (ParseException ex) {
            System.err.println("Error parsing date '" + date + "'.");
        }
        
        return null;
    }
}
