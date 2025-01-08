package ast;

import symbolTable.IVisitor;

public class NodeAssign extends NodeStm {
	private NodeId id;
	private NodeExpr expr;
	
	public NodeAssign(NodeId id, NodeExpr expr) {
		this.id = id;
		this.expr = expr;
	}

	public NodeId getId() {
		return id;
	}

	public NodeExpr getExpr() {
		return expr;
	}

	@Override
	public String toString() {
		return "NodeAssign -> [id=" + id + ", expr=" + expr + "];\t";
	}

	@Override
	public TypeDescriptor calcResType() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'calcResType'");
	}

	@Override
	public String calcCodice() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'calcCodice'");
	}

	@Override
	public void accept(IVisitor visitor) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'accept'");
	}
	
}
