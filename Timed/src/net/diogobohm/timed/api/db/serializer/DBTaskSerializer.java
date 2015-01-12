/**
 * TODO: define a license.
 */
package net.diogobohm.timed.api.db.serializer;

import com.google.common.collect.Maps;
import java.util.Map;
import net.diogobohm.timed.api.db.domain.DBTask;
import net.diogobohm.timed.api.db.domain.DBTaskTag;

/**
 *
 * @author diogo.bohm
 */
public class DBTaskSerializer implements DBSerializer<DBTask> {

    @Override
    public DBTask deserialize(Object[] data) {
        Integer id = ((Long) data[0]).intValue();
        Integer activityId = ((Long) data[1]).intValue();
        Integer projectId = ((Long) data[2]).intValue();
        String startDateTime = (String) data[3];
        String finishDateTime = (String) data[4];
        String description = (String) data[5];

        DBTask object = new DBTask(activityId, projectId, startDateTime, finishDateTime, description);
        object.setId(id);

        return object;
    }

    @Override
    public Map<String, Object> serialize(DBTask object) {
        Map<String, Object> valueMap = Maps.newHashMap();

        valueMap.put("activity_id", object.getActivityId());
        valueMap.put("project_id", object.getProjectId());
        valueMap.put("start_time", object.getStartDateTime());
        valueMap.put("end_time", object.getFinishDateTime());
        valueMap.put("description", object.getDescription());

        return valueMap;
    }

}
