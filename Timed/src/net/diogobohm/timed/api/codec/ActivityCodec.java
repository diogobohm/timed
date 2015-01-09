/**
 * TODO: define a license.
 */
package net.diogobohm.timed.api.codec;

import net.diogobohm.timed.api.db.domain.DBActivity;
import net.diogobohm.timed.api.domain.Activity;

/**
 *
 * @author diogo.bohm
 */
public interface ActivityCodec {

    DBActivity encode(Activity activity);

    Activity decode(DBActivity activity);
}
