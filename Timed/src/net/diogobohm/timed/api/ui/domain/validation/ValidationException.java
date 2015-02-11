/**
 * TODO: define a license.
 */
package net.diogobohm.timed.api.ui.domain.validation;

/**
 *
 * @author diogo.bohm
 */
public class ValidationException extends Exception {

    public ValidationException(String errorMessage) {
        super(errorMessage);
    }
}
