package ast;

import visitor.IVisitor;

public class NodeBinOp extends NodeExpr {
	
	private LangOper op;
	private NodeExpr left;
	private NodeExpr right;
	
	public NodeBinOp(LangOper op, NodeExpr left, NodeExpr right) {
		this.op = op;
		this.left = left;
		this.right = right;
	}

	public LangOper getOp() {
		return op;
	}

	public NodeExpr getLeft() {
		return left;
	}

	public NodeExpr getRight() {
		return right;
	}

	@Override
	public String toString() {
		return "NodeBinOp -> [op=" + op + ", left=" + left + ", right=" + right + "];\t";
	}


	//Questo metodo è implementato in tutti i nodi concreti e ha lo stesso comportamento del visitor del nodo corrispondente.
	@Override
	public String calcCodice() {

			String leftCd = left.calcCodice();//codice della espressione sinistra
			String rightCd = right.calcCodice();//codice della espressione destra

			

			return leftCd + " " + rightCd + " " + this.getOp() ;//codice dell’espressione binaria
	}

	@Override
	public void accept(IVisitor visitor) {
		visitor.visit(this);
	}

}

