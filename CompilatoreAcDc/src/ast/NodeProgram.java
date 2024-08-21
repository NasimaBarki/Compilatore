package ast;

import java.util.ArrayList;

public class NodeProgram extends NodeAST{
	private ArrayList<NodeDecSt> decSts;

	public NodeProgram(ArrayList<NodeDecSt> decSts) {
		super();
		this.decSts = decSts;
	}

	public ArrayList<NodeDecSt> getDecSts() {
		return decSts;
	}

	@Override
	public String toString() {
		String decStsString = "";

		for (NodeDecSt s : decSts)
		{
			decStsString += s.toString() + "\t";
		}

		return decStsString;
	}
}
