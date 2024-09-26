package ast;

import visitor.IVisitor;

/**
 *   nodo operazione binaria dell'AST
 * @author Nasima Barki
 *
 */
public class NodeBinOp extends NodeExpr{
	private final LangOper op;
	private NodeExpr left;
	private NodeExpr right;

	/**
	 *   costruttore
	 * @param op operazione
	 * @param left primo operando
	 * @param right secondo operando
	 */
	public NodeBinOp(LangOper op, NodeExpr left, NodeExpr right) {
		super();
		this.op = op;
		this.left = left;
		this.right = right;
	}

	/**
	 *   ritorna l'operazione
	 * @return l'operazione
	 */
	public LangOper getOp() {
		return op;
	}

	/**
	 *   ritorna il primo operando
	 * @return il primo operando
	 */
	public NodeExpr getLeft() {
		return left;
	}

	/**
	 *   ritorna il secondo operando
	 * @return il secondo operando
	 */
	public NodeExpr getRight() {
		return right;
	}
	
	/**
	 *   setta il primo operando
	 * @param left il primo operando
	 */
	public void setLeft(NodeExpr left) {
		this.left = left;
	}

	/**
	 *   setta il secondo operando
	 * @param right il secondo operando
	 */
	public void setRight(NodeExpr right) {
		this.right = right;
	}

	/**
	 *   stampa le informazioni sul nodo
	 * @return le informazioni sul nodo
	 */
	@Override
	public String toString() {
		return "<BINOP: " + this.left.toString() + "," + this.op.toString() + "," + this.right.toString() + ">";
	}

	@Override
	public void accept(IVisitor visitor) {
		visitor.visit(this);
	}

}
