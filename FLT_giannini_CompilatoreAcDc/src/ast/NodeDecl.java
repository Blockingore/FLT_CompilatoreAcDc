package ast;

import visitor.IVisitor;

/** 
 * Implementa il nodo NodeDecl dell'AST.
 * 
 * @author Luca Iacobucci, 20035727
 * @author Andrija Jovic, 20034244
 */
public class NodeDecl extends NodeDecSt {

	private NodeId id;
	private LangType type;
	private NodeExpr init;
	
	/**
	 * Costruttore per NodeDecl.
	 * 
	 * @param id   il nome dell'id
	 * @param type il tipo dell'id
	 * @param init inizializzazione dell'id
	 */
	public NodeDecl(NodeId id, LangType type, NodeExpr init) 
	{
		this.id = id;
		this.type = type;
		this.init = init;
	}

	/**
	 * Restituisce l'id assegnato a un NodeDecl.
	 * 
	 * @return NodeId assegnato al nodo
	 */
	public NodeId getId() {
		return id;
	}

	/**
	 * Restituisce il tipo assegnato a un NodeDecl.
	 * 
	 * @return il tipo assegnato al nodo
	 */
	public LangType getType() {
		return type;
	}

	/**
	 * Restituisce l'espressione assegnata a un NodeDecl.
	 * 
	 * @return NodeExpr assegnato al nodo
	 */
	public NodeExpr getInit() {
		return init;
	}

	@Override
	public String toString() {
		return "\tNodeDecl -> [id=" + id.toString() + ", type=" + type + ", init=" + init + "];\t";
	}

	/* 
	@Override
	public String calcCodice() {
		if(init == null)
		return "";
		else
		return init.calcCodice() + " s" + id.calcCodice();
	}
	*/

	@Override
	public void accept(IVisitor visitor) {
		visitor.visit(this);
	}
}
