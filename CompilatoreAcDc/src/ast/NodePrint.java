package ast;

public class NodePrint extends NodeStm{
	private NodeId id;
	
	
	public NodePrint(NodeId id) {
		super();
		this.id = id;
	}

	public NodeId getId() {
		return id;
	}

	@Override
	public String toString() {
		return id.toString();
	}
	
}
