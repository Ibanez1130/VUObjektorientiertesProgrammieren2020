package cashregister;

public class NotRegisteredException extends Exception {
	private static final long serialVersionUID = 8L;

	public NotRegisteredException (String message) {
		super(message);
	}
}
