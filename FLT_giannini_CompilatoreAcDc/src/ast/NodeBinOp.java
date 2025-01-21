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

	public void setOp(LangOper op) {
		this.op = op;
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

/*
	//Questo metodo è implementato in tutti i nodi concreti e ha lo stesso comportamento del visitor del nodo corrispondente.
	@Override
	public String calcCodice() {

			if(this.getOp() == LangOper.PLUS){
				return this.getLeft().calcCodice() + " " + this.getRight().calcCodice() + " +";
			}else if(this.getOp() == LangOper.MINUS){
				return this.getLeft().calcCodice() + " " + this.getRight().calcCodice() + " -";
			}else if(this.getOp() == LangOper.TIMES){
				return this.getLeft().calcCodice() + " " + this.getRight().calcCodice() + " *";
			}else if(this.getOp() == LangOper.DIV){
				return this.getLeft().calcCodice() + " " + this.getRight().calcCodice() + " /";
			}else if(this.getOp() == LangOper.PLUS){
				return this.getLeft().calcCodice() + " " + this.getRight().calcCodice() + " +";
			}else if(this.getOp() == LangOper.DIV_FLOAT){
				return this.getLeft().calcCodice() + " " + this.getRight().calcCodice() + " 5 k / 0 k";
			}else
				return "";
	}
 */
	@Override
	public void accept(IVisitor visitor) {
		visitor.visit(this);
	}

}

