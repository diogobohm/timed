/**
 * TODO: define a license.
 */
package net.diogobohm.timed.api.db.serializer;

import com.google.common.collect.Maps;
import java.util.Map;
import net.diogobohm.timed.api.db.domain.DBTaskTag;

/**
 *
 * @author diogo.bohm
 */
public class DBTaskTagSerializer implements DBSerializer<DBTaskTag> {

    @Override
    public DBTaskTag deserialize(Object[] data) {
        Integer id = ((Long) data[0]).intValue();
        Integer taskId = ((Long) data[1]).intValue();
        // FIXME: está criando registro null.
        Object dbTag = data[2];
        Integer tagId = null;

        if (dbTag != null) {
            tagId = ((Long) data[2]).intValue();
        }

        DBTaskTag object = new DBTaskTag(taskId, tagId);
        object.setId(id);

        return object;
    }

    @Override
    public Map<String, Object> serialize(DBTaskTag object) {
        Map<String, Object> valueMap = Maps.newHashMap();

        valueMap.put("task_id", object.getTaskId());
        valueMap.put("tag_id", object.getTagId());

        return valueMap;
    }

}
