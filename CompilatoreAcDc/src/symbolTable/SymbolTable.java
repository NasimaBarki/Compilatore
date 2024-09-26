package symbolTable;

import java.util.HashMap;
import java.util.Map.Entry;

import ast.LangType;

/**
 *   struttura dati dizionario
 * @author Nasima Barki
 *
 */
public class SymbolTable {
	public static HashMap<String, Attributes> symbolTable;
	
	/**
	 *  Attributi della symbol table
	 * @author nu
	 *
	 */
	public static class Attributes {
		private LangType tipo;
		private char registro;

		/**
		 * costruttore
		 * @param tipo 
		 */
		public Attributes(LangType tipo) {
			super();
			this.tipo = tipo;
		}
		
		/**
		 * costruttore
		 * @param tipo
		 * @param registro
		 */
		public Attributes(LangType tipo, char registro) {
			super();
			this.tipo = tipo;
			this.registro = registro;
		}

		/**
		 * ottieni tipo
		 * @return tipo
		 */
		public LangType getTipo() {
			return tipo;
		}

		/**
		 * ottieni registro
		 * @return registro
		 */
		public char getRegistro() {
			return registro;
		}

		/**
		 * setta registro
		 * @param registro
		 */
		public void setRegistro(char registro) {
			this.registro = registro;
		}
	}
	
	/**
	 * inizializzazione
	 */
	public static void init() {
		symbolTable = new HashMap<String, Attributes>();
	}
	
	/**
	 * inserimento
	 * @param id l'id
	 * @param entry attributo
	 * @return false se esiste già un valore con lo stesso id, false altrimenti
	 */
	public static boolean enter(String id, Attributes entry) {
		if(symbolTable.containsKey(id)) {
			return false;
		}
		else {
			symbolTable.put(id, entry);
			return true;
		}
	}
	
	/**
	 * ricerca
	 * @param id id
	 * @return entry con lo stesso id
	 */
	public static Attributes lookup(String id) {
		return symbolTable.get(id);
	}
	
	/**
	 * stampa i valori della symbol table
	 * @return stringa
	 */
	public static String toStr() {
		String stringa = "";
		for(Entry<String, Attributes> symbol : symbolTable.entrySet()) {
			String key = symbol.getKey();
			Attributes value = symbol.getValue();
		    stringa += key + " : " + value.toString() + "\n";
		}
		return stringa;
	}
	
	/**
	 * ritorna la dimensione della symbol table
	 * @return dimensione
	 */
	public static int size() {
		return symbolTable.size();
	}
}
