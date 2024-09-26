package token;

/**
 *   Classe per la gestione dei token
 * @author Nasima Barki
 *
 */
public class Token {

	private int riga;
	private TokenType tipo;
	private String val;
	
	/**
	 *   costruttore di default con valore assegnato
	 * @param tipo il tipo del token
	 * @param riga la riga del token
	 * @param val il valore del token
	 */
	// costruttori
	public Token(TokenType tipo, int riga, String val) {
		super();
		this.tipo = tipo;
		this.riga = riga;
		this.val = val;
	}
	
	/**
	 *   costruttore di default senza valore
	 * @param tipo il tipo del token
	 * @param riga la riga del token
	 */
	public Token(TokenType tipo, int riga) {
		super();
		this.tipo = tipo;
		this.riga = riga;
	}

    // Getters per i campi
	
	/**
	 *   ritorna la riga del token
	 * @return la riga del token
	 */
	public int getRiga() {
		return riga;
	}

	/**
	 *   ritorna il tipo del token
	 * @return il tipo del token
	 */
	public TokenType getTipo() {
		return tipo;
	}

	/**
	 *   ritorna il valore del token
	 * @return il valore del token
	 */
	public String getVal() {
		return val;
	}

	/**
	 *   stampa le informazioni sul token
	 * @return le informazioni sul token
	 */
	public String toString() {
		String stringa = String.valueOf(getTipo()) + ",r:" + String.valueOf(getRiga());
		
		//Se il token ha un valore, esso viene stampato
		//TODO controllo il tokentype o semplicemente se val è nullo?
		//TODO devo stampare i valori tra <> o no?
		if(getVal() != null) {
			stringa += "," + String.valueOf(getVal());
		}
		
		return stringa;
	}
	
	
}
