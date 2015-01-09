/**
 * TODO: define a license.
 */
package net.diogobohm.timed.api.db.exception;

import org.tmatesoft.sqljet.core.SqlJetException;

/**
 *
 * @author diogo.bohm
 */
public class DatabaseAccessException extends Exception {

    public DatabaseAccessException(SqlJetException exception, String message) {
        super(message, exception);
    }
}
