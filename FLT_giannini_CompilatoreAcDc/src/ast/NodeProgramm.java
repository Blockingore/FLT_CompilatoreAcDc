package ast;

import java.util.ArrayList;

import visitor.IVisitor;

public class NodeProgramm extends NodeAST {
	
	private ArrayList<NodeDecSt> decSts;
	
	public NodeProgramm(ArrayList<NodeDecSt> decSts) {
		this.decSts = decSts;
	}

	public ArrayList<NodeDecSt> getDecSts() {
		return decSts;
	}
	
	@Override
	public String toString() {
		return "NodeProgramm -> [DecSts=" + decSts + "];\t";
	}

	@Override
	public void accept(IVisitor visitor) {
		visitor.visit(this);
	}

	@Override
	public String calcCodice() {
		return null;
	}
}
