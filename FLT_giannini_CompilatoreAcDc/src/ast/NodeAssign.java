package ast;

import visitor.IVisitor;

/** 
 * Implementa il nodo NodeAssign dell'AST.
 * 
 * @author Luca Iacobucci, 20035727
 * @author Andrija Jovic, 20034244
 */
public class NodeAssign extends NodeStm {

	private NodeId id;
	private NodeExpr expr;
	
	/**
	 * Costruttore per NodeAssign
	 * 
	 * @param id   il nome dell'id
	 * @param expr l'espressione assegnata all'id
	 */
	public NodeAssign(NodeId id, NodeExpr expr) {
		this.id = id;
		this.expr = expr;
	}

	/**
	 * Restituisce l'id assegnato a un NodeId.
	 * 
	 * @return id assegnato al nodo
	 */
	public NodeId getId() {
		return id;
	}

	/**
	 * Restituisce l'espressione assegnata a un NodeId.
	 * 
	 * @return espressione assegnata al nodo
	 */
	public NodeExpr getExpr() {
		return expr;
	}

	@Override
	public String toString() {
		return "NodeAssign -> [id=" + id + ", expr=" + expr + "];\t";
	}
/*
	@Override
	public String calcCodice() {
		return expr.calcCodice() + " s" + id.calcCodice() ;
	}
*/
	@Override
	public void accept(IVisitor visitor) {
		visitor.visit(this);
	}
}
