package ast;

import visitor.IVisitor;

/**
 *   nodo print dell'AST
 * @author Nasima Barki
 *
 */
public class NodePrint extends NodeStm{
	private final NodeId id;
	
	/**
	 *   costruttore
	 * @param id il nodo id
	 */
	public NodePrint(NodeId id) {
		super();
		this.id = id;
	}

	/**
	 *   ritorna il nodo id
	 * @return il nodo id
	 */
	public NodeId getId() {
		return id;
	}

	/**
	 *   stampa le informazioni sul nodo
	 * @return le informazioni sul nodo
	 */
	@Override
	public String toString() {
		return "<PRINT: " + this.id.toString() + ">";
	}
	
	@Override
	public void accept(IVisitor visitor) {
		visitor.visit(this);
	}
}
