package ast;

public class NodeDecl extends NodeDecSt{
	private NodeId id;
	private LangType type;
	private NodeExpr init;
	
	public NodeDecl(NodeId id, LangType type, NodeExpr init) {
		super();
		this.id = id;
		this.type = type;
		this.init = init;
	}

	
	public NodeId getId() {
		return id;
	}


	public LangType getType() {
		return type;
	}


	public NodeExpr getInit() {
		return init;
	}


	@Override
	public String toString() {
		return id.toString() + type.toString() + init.toString();
	}
	
}
