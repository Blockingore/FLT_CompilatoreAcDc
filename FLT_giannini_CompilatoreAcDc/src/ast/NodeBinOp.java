package ast;

import visitor.IVisitor;

/** 
 * Implementa il nodo NodeBinOp dell'AST.
 * 
 * @author Luca Iacobucci, 20035727
 * @author Andrija Jovic, 20034244
 */
public class NodeBinOp extends NodeExpr {
	
	private LangOper op;
	private NodeExpr left;
	private NodeExpr right;
	
	/**
	 * Costruttore per NodeBinOp.
	 * 
	 * @param left  l'espressione a sinistra
	 * @param op    l'operatore
	 * @param right l'espressione a destra
	 */
	public NodeBinOp(LangOper op, NodeExpr left, NodeExpr right) {
		this.op = op;
		this.left = left;
		this.right = right;
	}

	/**
	 * Restituisce l'operatore assegnato a un NodeBinOp.
	 * 
	 * @return il tipo di operatore
	 */
	public LangOper getOp() {
		return op;
	}

	/**
	 * Imposta l'operatore del NodeBinOp.
	 * 
	 * @param op l'operatore da impostare al NodeBinOp
	 */
	public void setOp(LangOper op) {
		this.op = op;
	}

	/**
	 * Restituisce l'espressione a sinistra assegnata a un NodeBinOp.
	 * 
	 * @return l'espressione a sinistra dell'operatore
	 */
	public NodeExpr getLeft() {
		return left;
	}

	/**
	 * Restituisce l'espressione a destra assegnata a un NodeBinOp.
	 * 
	 * @return l'espressione a destra dell'operatore
	 */
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

