package visitor;

import ast.*;

/**
 *   interfaccia visitor
 * @author Nasima Barki
 *
 */
public interface IVisitor {
	public abstract void visit(NodeProgram node);
	public abstract void visit(NodeId node);
	public abstract void visit(NodeDecl node);
	public abstract void visit(NodeBinOp node);
	public abstract void visit(NodePrint node);
	public abstract void visit(NodeAssign node);
	public abstract void visit(NodeConvert node);
	public abstract void visit(NodeCost node);
	public abstract void visit(NodeDeref node);
}
