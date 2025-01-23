package ast;

import visitor.IVisitor;

/** 
 * Implementa il nodo NodePrint dell'AST.
 * 
 * @author Luca Iacobucci, 20035727
 * @author Andrija Jovic, 20034244
 */
public class NodePrint extends NodeStm {
	private NodeId id;
	
	/**
	 * Costruttore per NodePrint.
	 * 
	 * @param id NodeId assegnato al nodo
	 */
	public NodePrint(NodeId id) {
		this.id = id;
	}

	/**
	 * Restituisce il NodeId assegnato a un NodePrint.
	 * 
	 * @return NodeId assegnato al nodo
	 */
	public NodeId getId() {
		return id;
	}

	@Override
	public String toString() {
		return "NodePrint -> [id=" + id.toString() + "];\t";
	}
/* 
	@Override
	public String calcCodice() {
		return "l" +id.calcCodice() + " p P";
	}
*/
	@Override
	public void accept(IVisitor visitor) {
		visitor.visit(this);
	}

}
