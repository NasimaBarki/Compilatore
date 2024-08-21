package token;

public class Token {

	private int riga;
	private TokenType tipo;
	private String val;
	
	// costruttori
	public Token(TokenType tipo, int riga, String val) {
		super();
		this.tipo = tipo;
		this.riga = riga;
		this.val = val;
	}
	
	public Token(TokenType tipo, int riga) {
		super();
		this.tipo = tipo;
		this.riga = riga;
	}

    // Getters per i campi
    
	public int getRiga() {
		return riga;
	}

	public TokenType getTipo() {
		return tipo;
	}

	public String getVal() {
		return val;
	}

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
