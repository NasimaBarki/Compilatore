package eccezioni;

import java.io.IOException;

public class LexicalException extends Exception {
	
	// TODO guardare il file "eccezioni nel compilatore"
	// Costruttori
	public LexicalException(IOException e) {
		super(e);
	}

	public LexicalException(String msg) {
		super(msg);
	}

}
