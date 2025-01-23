package ast;

import visitor.IVisitor;

/** 
 * Implementa il nodo NodeCost dell'AST.
 * 
 * @author Luca Iacobucci, 20035727
 * @author Andrija Jovic, 20034244
 */
public class NodeCost extends NodeExpr {
	private String value;
	private LangType type;
	
	/**
	 * Costruttore per NodeCost
	 * 
	 * @param value il valore della costante
	 * @param type  il tipo della costante
	 */
	public NodeCost(String value, LangType type) {
		this.value = value;
		this.type = type;
	}

	/**
	 * Ritorna il valore assegnato a un NodeCost.
	 * 
	 * @return il valore della costante rappresentata da un NodeCost
	 */
	public String getValue() {
		return value;
	}

	/**
	 * Ritorna il tipo del valore assegnato a un NodeCost.
	 * 
	 * @return il tipo del valore della costante rappresentata da un NodeCost
	 */
	public LangType getType() {
		return type;
	}

	@Override
	public String toString() {
		return "NodeCost -> [value=" + value + ", type=" + type + "];\t";
	}

/*	
	@Override
	public String calcCodice() {
		return value;
	}
*/
	@Override
	public void accept(IVisitor visitor) {
		visitor.visit(this);
	}
	
}
