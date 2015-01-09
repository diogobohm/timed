/**
 * TODO: define a license.
 */
package net.diogobohm.timed.api.codec;

import net.diogobohm.timed.api.db.domain.DBProject;
import net.diogobohm.timed.api.domain.Project;

/**
 *
 * @author diogo.bohm
 */
public interface ProjectCodec {

    DBProject encode(Project project);

    Project decode(DBProject project);
}
