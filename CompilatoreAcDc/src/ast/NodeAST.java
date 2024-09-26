package ast;

import visitor.IVisitor;

/**
 *   nodo dell'AST
 * @author Nasima Barki
 *
 */
public abstract class NodeAST {
	public abstract void accept(IVisitor visitor);
}
