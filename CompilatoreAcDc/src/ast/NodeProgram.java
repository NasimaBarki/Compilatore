package ast;

import java.util.ArrayList;

import visitor.IVisitor;

/**
 *   nodo program dell'AST
 * @author Nasima Barki
 *
 */
public class NodeProgram extends NodeAST{
	private ArrayList<NodeDecSt> decSts;

	/**
	 *   costruttore
	 * @param decSts le dichiarazioni
	 */
	public NodeProgram(ArrayList<NodeDecSt> decSts) {
		super();
		this.decSts = decSts;
	}

	/**
	 *   ritorna le dichiarazioni
	 * @return le dichiarazioni
	 */
	public ArrayList<NodeDecSt> getDecSts() {
		return decSts;
	}

	/**
	 *   stampa le informazioni sul nodo
	 * @return le informazioni sul nodo
	 */
	@Override
	public String toString() {
		return "AST: " + this.decSts.toString();
	}
	
	@Override
	public void accept(IVisitor visitor) {
		visitor.visit(this);
	}
}
