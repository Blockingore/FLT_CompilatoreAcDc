package ast;

import visitor.IVisitor;

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
	public String calcCodice() {
		return expr.calcCodice() + " s" + id.calcCodice() ;
	}

	@Override
	public void accept(IVisitor visitor) {
		visitor.visit(this);
	}

	
}
