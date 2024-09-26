package ast;

import visitor.IVisitor;

/**
 *   nodo id dell'AST
 * @author Nasima Barki
 *
 */
public class NodeId extends NodeAST{
	private final String name;

	/**
	 *   costruttore
	 * @param name il nome
	 */
	public NodeId(String name) {
		super();
		this.name = name;
	}

	
	/**
	 *   ritorna il nome
	 * @return il nome
	 */
	public String getName() {
		return name;
	}

	/**
	 *   stampa le informazioni sul nodo
	 * @return le informazioni sul nodo
	 */
	@Override
	public String toString() {
		return this.name;
	}
	
	@Override
	public void accept(IVisitor visitor) {
		visitor.visit(this);
	}
}
