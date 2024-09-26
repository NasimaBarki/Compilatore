package ast;

import visitor.IVisitor;

/**
 *   nodeAssign dell'AST
 * @author Nasima Barki
 *
 */
public class NodeAssign extends NodeStm{
	private final NodeId id;
	private final NodeExpr expr;
	
	/**
	 *   costruttore
	 * @param id nodo id
	 * @param expr nodo expr
	 */
	public NodeAssign(NodeId id, NodeExpr expr) {
		super();
		this.id = id;
		this.expr = expr;
	}

	/**
	 *   ottieni l'id
	 * @return id
	 */
	public NodeId getId() {
		return id;
	}

	/**
	 *   ottieni l'espressione
	 * @return espressione
	 */
	public NodeExpr getExpr() {
		return expr;
	}

	/**
	 * stampa le informazioni del nodo
	 */
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "<ASSIGN: " + getId().toString() + "," + getExpr().toString() + ">";
	}
	
	@Override
	public void accept(IVisitor visitor) {
		visitor.visit(this);
	}

}
