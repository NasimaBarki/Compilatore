package ast;

import visitor.IVisitor;

/**
 *  nodo decl dell'AST
 * @author Nasima Barki
 *
 */
public class NodeDecl extends NodeDecSt{
	private final NodeId id;
	private final LangType type;
	private final NodeExpr init;
	
	/**
	 * costruttore
	 * @param id nodo id
	 * @param type tipo
	 * @param init inizializzazione
	 */
	public NodeDecl(NodeId id, LangType type, NodeExpr init) {
		super();
		this.id = id;
		this.type = type;
		this.init = init;
	}

	/**
	 * ottieni l'id
	 * @return id
	 */
	public NodeId getId() {
		return id;
	}

	/**
	 *  ottieni il tipo
	 * @return tipo
	 */
	public LangType getType() {
		return type;
	}

	/**
	 * ottieni l'inizializzazione
	 * @return inizializzazione
	 */
	public NodeExpr getInit() {
		return init;
	}

	/**
	 *   stampa le informazioni sul nodo
	 * @return le informazioni sul nodo
	 */
	@Override
	public String toString() {
		String returnString =  "<DECL: " + this.id.toString() + "," + this.type.toString();
		if(this.init != null) {
			returnString += "," + this.init.toString();
		}
		returnString += ">";
		return returnString;
	}
	
	@Override
	public void accept(IVisitor visitor) {
		visitor.visit(this);
	}
}
