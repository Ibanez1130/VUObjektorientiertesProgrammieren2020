/**
 * @name Konstantin Kobel
 * @martrikelnummer 01525841
 * @date 30.05.2020
 */
package cashregister;

public class NotRegisteredException extends Exception {
	private static final long serialVersionUID = 8L;

	public NotRegisteredException (String message) {
		super(message);
	}
}
