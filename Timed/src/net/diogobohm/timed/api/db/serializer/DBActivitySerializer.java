/**
 * TODO: define a license.
 */
package net.diogobohm.timed.api.db.serializer;

import com.google.common.collect.Maps;
import java.util.HashMap;
import java.util.Map;
import net.diogobohm.timed.api.db.domain.DBActivity;

/**
 *
 * @author diogo.bohm
 */
public class DBActivitySerializer implements DBSerializer<DBActivity> {

    @Override
    public DBActivity deserialize(Object[] data) {
        Integer id = ((Long) data[0]).intValue();
        String name = (String) data[1];

        DBActivity object = new DBActivity(name);
        object.setId(id);

        return object;
    }

    @Override
    public Map<String, Object> serialize(DBActivity object) {
        Map<String, Object> valueMap = Maps.newHashMap();

        valueMap.put("name", object.getName());

        return valueMap;
    }

}
