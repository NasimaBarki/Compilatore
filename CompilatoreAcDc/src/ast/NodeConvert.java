package ast;

import visitor.IVisitor;

/**
 *   nodo di conversione dell'AST
 * @author Nasima Barki
 *
 */
public class NodeConvert extends NodeExpr{
	private final NodeExpr exp;
	
	/**
	 *   costruttore
	 * @param exp l'espressione da convertire
	 */
	public NodeConvert(NodeExpr exp) {
		super();
		this.exp = exp;
	}

	/**
	 *   ritorna l'espressione da convertire
	 * @return l'espressione da convertire
	 */
	public NodeExpr getExp() {
		return exp;
	}

	/**
	 *   stampa le informazioni sul nodo
	 * @return le informazioni sul nodo
	 */
	@Override
	public String toString() {
		return "<CONVERT: " + getExp() + ">";
	}

	@Override
	public void accept(IVisitor visitor) {
		visitor.visit(this);
	}
}
