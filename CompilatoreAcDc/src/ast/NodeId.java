package ast;

public class NodeId extends NodeAST{
	private String name;

	public NodeId(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return name;
	}
}
