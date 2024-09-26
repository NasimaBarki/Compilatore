package eccezioni;

import java.io.IOException;

/**
 *   eccezione di lessico
 * @author Nasima Barki
 *
 */
public class LexicalException extends Exception {
	
	// TODO guardare il file "eccezioni nel compilatore"
	// Costruttori
	
	/**
	 *   costruttore
	 * @param e eccezione di I/O
	 */
	public LexicalException(IOException e) {
		super(e);
	}

	/**
	 *   costruttore
	 * @param msg messaggio di errore
	 */
	public LexicalException(String msg) {
		super(msg);
	}

}
