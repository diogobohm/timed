/**
 * TODO: define a license.
 */
package net.diogobohm.timed.api.db.serializer;

import net.diogobohm.timed.api.db.domain.DBTask;

/**
 *
 * @author diogo.bohm
 */
public class DBTaskSerializer implements DBSerializer<DBTask> {

    @Override
    public DBTask deserialize(Object[] data) {
        Integer id = ((Long) data[0]).intValue();
        String startDateTime = (String) data[1];
        String finishDateTime = (String) data[2];

        //FIXME: null activity não vale
        Object dbActivity = data[3];
        Integer activityId = 1;

        if (dbActivity != null) {
            activityId = ((Long) dbActivity).intValue();
        }
        
        //FIXME: null project não vale
        Object dbProjectId = data[4];
        Integer projectId = 1;

        if (dbProjectId != null) {
            projectId = ((Long) dbProjectId).intValue();
        }

        String description = (String) data[5];

        DBTask object = new DBTask(startDateTime, finishDateTime, activityId, projectId, description);
        object.setId(id);

        return object;
    }

    @Override
    public Object[] serialize(DBTask object) {
        return new Object[]{object.getStartDateTime(), object.getFinishDateTime(), object.getActivityId(),
            object.getProjectId(), object.getDescription()};
    }

}
