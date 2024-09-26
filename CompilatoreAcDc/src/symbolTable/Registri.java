package symbolTable;

import java.util.ArrayList;

import eccezioni.RegisterException;

/**
 *   registri
 * @author Nasima Barki
 *
 */
public class Registri {
	private static ArrayList<Character> characters;
	
	/**
	 * inizializzazione dei registri
	 */
	public static void init() {
		characters = new ArrayList<Character>();
		for (char character = 'a'; character <= 'z'; character++) {
			characters.add(character);
		}
	}
	
	/**
	 *   ritorna un nuovo registro
	 * @return un nuovo registro
	 * @throws RegisterException se non ci sono registri
	 */
	public static Character newRegister() throws RegisterException {
		if(!characters.isEmpty()) {
			return characters.remove(0);
		} else {
			throw new RegisterException("Non e' possibile creare un nuovo registro, e' stato raggiunto il limite massimo.");
		}
	}
}
