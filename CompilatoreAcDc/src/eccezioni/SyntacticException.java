package eccezioni;

/**
 *   eccezione di sintassi
 * @author Nasima Barki
 *
 */
public class SyntacticException extends Exception {

	/**
	 *   costruttore
	 * @param msg messaggio di errore
	 */
	public SyntacticException(String msg) {
		super(msg);
	}

	/**
	 *   costruttore
	 * @param string messaggio di errore
	 * @param e eccezione
	 */
	public SyntacticException(String string, Exception e) {
		// TODO Auto-generated constructor stub
		super(string, e);
	}
	
}
