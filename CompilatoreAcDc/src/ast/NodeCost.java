package ast;

import visitor.IVisitor;

/**
 *   nodo cost dell'AST
 * @author Nasima Barki
 *
 */
public class NodeCost extends NodeExpr{
	private final String value;
	private final LangType type;
	
	/**
	 *   costruttore
	 * @param value il valore
	 * @param type il tipo
	 */
	public NodeCost(String value, LangType type) {
		super();
		this.value = value;
		this.type = type;
	}

	/**
	 *   ritorna il valore
	 * @return il valore
	 */
	public String getValue() {
		return value;
	}

	/**
	 *   ritorna il tipo
	 * @return il tipo
	 */
	public LangType getType() {
		return type;
	}

	/**
	 *   stampa le informazioni sul nodo
	 * @return le informazioni sul nodo
	 */
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "<" + this.value.toString() + "," + this.type.toString() + ">";
	}
	
	@Override
	public void accept(IVisitor visitor) {
		visitor.visit(this);
	}

}
