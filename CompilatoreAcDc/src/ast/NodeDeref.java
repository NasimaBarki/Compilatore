package ast;

import visitor.IVisitor;

/**
 *   nodo deref dell'AST
 * @author Nasima Barki
 *
 */
public class NodeDeref extends NodeExpr{
	private final NodeId id;

	/**
	 * costruttore
	 * @param id il nodo id
	 */
	public NodeDeref(NodeId id) {
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
		// TODO Auto-generated method stub
		return this.id.toString();
	}
	
	@Override
	public void accept(IVisitor visitor) {
		visitor.visit(this);
	}
}
