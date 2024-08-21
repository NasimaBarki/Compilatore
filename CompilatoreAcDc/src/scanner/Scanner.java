package scanner;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PushbackReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import eccezioni.LexicalException;
import token.*;

public class Scanner {
	final char EOF = (char) -1; 
	private int riga;
	private PushbackReader buffer;
	private String log;
	private Token nextTk = null;

	// skpChars: insieme caratteri di skip (include EOF) e inizializzazione
	List<Character> skpChars = new ArrayList<Character>();
	// letters: insieme lettere e inizializzazione
	List<Character> letters = new ArrayList<Character>();
	// digits: cifre e inizializzazione
	List<Integer> digits = new ArrayList<Integer>();

	// char_type_Map: mapping fra caratteri '+', '-', '*', '/', ';', '=', ';' e il
	// TokenType corrispondente
	Map<Character, TokenType> char_type_Map = new HashMap<>();

	// keyWordsMap: mapping fra le stringhe "print", "float", "int" e il
	// TokenType  corrispondente
	Map<String, TokenType> keyWordsMap = new HashMap<>();

	public Scanner(String fileName) throws FileNotFoundException {

		this.buffer = new PushbackReader(new FileReader(fileName));
		riga = 1;
		
		// inizializzare campi che non hanno inizializzazione
		// caratteri di skip
		skpChars.add(' ');
		skpChars.add('\n');
		skpChars.add('\t');
		skpChars.add('\r');
		skpChars.add(EOF);
		
		// 26 caratteri dell’alfabeto inglese minuscoli
		for(char letter = 'a'; letter <= 'z'; letter++) {
			letters.add(letter);
		}
		
		// cifre
		for(int digit = 0; digit <= 9; digit++) {
			digits.add(digit);
		}
		
		// mapping fra caratteri e TokenType ('+', '-', '*', '/', ';', '=')
		char_type_Map.put('+', TokenType.PLUS);
		char_type_Map.put('-', TokenType.MINUS);
		char_type_Map.put('*', TokenType.TIMES);
		char_type_Map.put('/', TokenType.DIVIDE);
		char_type_Map.put(';', TokenType.SEMI);
		char_type_Map.put('=', TokenType.OP_ASSIGN);
		
		// mapping fra stringhe e TokenType ("print", "float", "int")
		keyWordsMap.put("print", TokenType.PRINT);
		keyWordsMap.put("float", TokenType.TYFLOAT);
		keyWordsMap.put("int", TokenType.TYINT);
	}
	
	public Token peekToken() throws LexicalException, IOException {
		if (nextTk == null) {
			nextTk = nextToken();
		}
		
		return nextTk;
	}

	public Token nextToken() throws LexicalException, IOException  {

		while (nextTk == null) {
			// nextChar contiene il prossimo carattere dell'input (non consumato).
			char nextChar;
			
			try {
				nextChar = peekChar();
			} catch (IOException e) {
				throw new LexicalException(e);
			} //Catturate l'eccezione IOException e 
			       // ritornate una LexicalException che la contiene

			// Avanza nel buffer leggendo i carattere in skipChars
			if (skpChars.contains(nextChar)) {
				nextChar = readChar();
				
				// incrementando riga se leggi '\n'.
				if (nextChar == '\n') {
					riga++;
				}
			}
		
			// Se raggiungi la fine del file ritorna il Token EOF
			if (nextChar == EOF) {
				return new Token (TokenType.EOF, riga);
			}

			// Se nextChar e' in letters
			// return scanId()
			// che legge tutte le lettere minuscole e ritorna un Token ID o
			// il Token associato Parola Chiave (per generare i Token per le
			// parole chiave usate l'HaskMap di corrispondenza
			if(letters.contains(nextChar)) {
				return scanId();
			}

			// Se nextChar e' o in operators oppure 
			// ritorna il Token associato con l'operatore o il delimitatore
			// TODO come faccio a considerare i casi  += | -= | *= | /= ? Creo un metodo simile a scanId?
			if(char_type_Map.containsKey(nextChar)) {
				return scanOp();
			}

			// Se nextChar e' in numbers
			// return scanNumber()
			// che legge sia un intero che un float e ritorna il Token INUM o FNUM
			// i caratteri che leggete devono essere accumulati in una stringa
			// che verra' assegnata al campo valore del Token
			if (Character.isDigit(nextChar)) {
				return scanNumber();
			}
			// Altrimenti il carattere NON E' UN CARATTERE LEGALE sollevate una
			// eccezione lessicale dicendo la riga e il carattere che la hanno
			// provocata. 
			if (!skpChars.contains(nextChar) && !letters.contains(nextChar) && !char_type_Map.containsKey(nextChar) && !Character.isDigit(nextChar)) {
				throw new LexicalException("Il carattere '" + nextChar + "' a riga " + riga + " non è un carattere legale");
			}
		}
			// TODO va bene mettere true e non EOF? EOF viene già considerato all'interno del do while
			Token tmp = nextTk;
			nextTk = null;
			return tmp;
		
	}

	// private Token scanNumber()
	private Token scanNumber() throws LexicalException, IOException {
		char nextChar;
		String value = "";
		boolean containsLetter = false;
		
		do {
			try {
				nextChar = peekChar();
				
				if (!Character.isDigit(nextChar)) {
					if (letters.contains(nextChar)) {
						containsLetter = true;
					} else if (nextChar != '.') {
						break;
					}
				}
			} catch (IOException e) {
				throw new LexicalException(e);
			}
			
			nextChar = readChar();
			value += nextChar;
		} while(true);
		
		if(containsLetter) {
			throw new LexicalException("Il numero '" + value + "' a riga " + riga + " non può contenere lettere.");
		}
		
		// se non è un intero
		if (!value.contains(".")) {
			// se equivale a 0 oppure è un numero a più cifre che non inizia con 0
			if (value.equals("0") || (value.length() == 1 && !value.equals("0")) || (value.length() > 1 && value.charAt(0) != '0')) {
				return new Token (TokenType.INT, riga, value);
			}
		} else if (value.contains(".")){
			String[] floatNumber = value.split("\\.");
			if (floatNumber.length == 2 && floatNumber[1].length() >= 1 && floatNumber[1].length() <= 5) {
				return new Token (TokenType.FLOAT, riga, value);
			}
			else if (value.charAt(0) != '.' && floatNumber.length == 1) {
				return new Token (TokenType.FLOAT, riga, value);
			}
		}
		
		throw new LexicalException("Il numero '" + value + "' a riga " + riga + " non è valido.");
	}

	private Token scanOp() throws LexicalException, IOException {
		char nextChar;
		String value = "";
		
		do {
			try {
				nextChar = peekChar();
				
				if (!char_type_Map.containsKey(nextChar)) {
					break;
				}
			} catch (IOException e) {
				throw new LexicalException(e);
			}
			
			nextChar = readChar();
			value += nextChar;
			
		} while (true);
		
		if (value.length() == 1) {
			if (value.equals("=")) {
				return new Token(char_type_Map.get(value.charAt(0)), riga, value);
			} else
				return new Token(char_type_Map.get(value.charAt(0)), riga);
		}
		else if (value.equals("+=")  || value.equals("-=") || value.equals("*=") || value.equals("/=")) {
			return new Token(TokenType.OP_ASSIGN, riga, value);
		} else
			throw new LexicalException("L'operatore '" + value + "' a riga " + riga + " non è ammissibile.");
	}

	// private Token scanId()
	private Token scanId() throws LexicalException, IOException {
		char nextChar;
		String value = "";
		
		do {
			try {
				nextChar = peekChar();
				
				// per non scorrere nel buffer in caso non sia una lettera
				if(!letters.contains(nextChar)) {
					// TODO posso controllare se c'è un numero in questo modo o devo scorrere digits?
					if(Character.isDigit(nextChar)) {
						throw new LexicalException("L'identificatore '" + value + "+ a riga " + riga + " non può contenere numeri.");
					}
					break;
				}
			} catch (IOException e) {
				throw new LexicalException(e);
			}
			
			nextChar = readChar();
			value += nextChar;
			
		} while (true);
		
		if (keyWordsMap.containsKey(value)) {
			return new Token(keyWordsMap.get(value), riga);
		} else
			return new Token(TokenType.ID, riga, value);
	}

	private char readChar() throws IOException {
		return ((char) this.buffer.read());
	}

	private char peekChar() throws IOException {
		char c = (char) buffer.read();
		buffer.unread(c);
		return c;
	}
}
