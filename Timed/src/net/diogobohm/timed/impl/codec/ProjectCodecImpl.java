/**
 * TODO: define a license.
 */
package net.diogobohm.timed.impl.codec;

import net.diogobohm.timed.api.codec.ProjectCodec;
import net.diogobohm.timed.api.db.domain.DBProject;
import net.diogobohm.timed.api.domain.Project;

/**
 *
 * @author diogo.bohm
 */
public class ProjectCodecImpl implements ProjectCodec {

    @Override
    public DBProject encode(Project project) {
        return new DBProject(project.getName());
    }

    @Override
    public Project decode(DBProject project) {
        return new Project(project.getName());
    }

}
