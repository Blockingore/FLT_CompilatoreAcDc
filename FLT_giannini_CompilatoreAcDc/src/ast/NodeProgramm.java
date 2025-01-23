package ast;

import java.util.ArrayList;

import visitor.IVisitor;

/** 
 * Implementa il nodo NodeProgramm dell'AST.
 * 
 * @author Luca Iacobucci, 20035727
 * @author Andrija Jovic, 20034244
 */
public class NodeProgramm extends NodeAST {
	
	private ArrayList<NodeDecSt> decSts;
	
	/**
	 * Costruttore per NodeProgramm.
	 * 
	 * @param decSts la lista di dichiarazioni
	 */
	public NodeProgramm(ArrayList<NodeDecSt> decSts) {
		this.decSts = decSts;
	}

	/**
	 * Restituisce la lista di dichiarazioni
	 * 
	 * @return la lista di dichiarazioni
	 */
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
/*
	@Override
	public String calcCodice() {
		return "";
	}
*/
}

