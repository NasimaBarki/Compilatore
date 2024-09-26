package eccezioni;

/**
 * eccezione di registro
 * @author Nasima Barki
 *
 */
public class RegisterException extends Exception {

	/**
	 *   costruttore
	 * @param msg messaggio di errore
	 */
	public RegisterException(String msg) {
		super(msg);
	}

}
