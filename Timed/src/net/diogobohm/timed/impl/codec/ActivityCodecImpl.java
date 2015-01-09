/**
 * TODO: define a license.
 */
package net.diogobohm.timed.impl.codec;

import net.diogobohm.timed.api.codec.ActivityCodec;
import net.diogobohm.timed.api.db.domain.DBActivity;
import net.diogobohm.timed.api.domain.Activity;

/**
 *
 * @author diogo.bohm
 */
public class ActivityCodecImpl implements ActivityCodec {

    @Override
    public DBActivity encode(Activity activity) {
        return new DBActivity(activity.getName());
    }

    @Override
    public Activity decode(DBActivity activity) {
        return new Activity(activity.getName());
    }

}
