/**
 * TODO: define a license.
 */
package net.diogobohm.timed.api.db.access.configuration;

/**
 *
 * @author diogo.bohm
 */
public interface DBTableConfiguration {

    String getTableName();

    String getIndexName();

    String getCreateTableQuery();

    String getCreateIndexQuery();

}
