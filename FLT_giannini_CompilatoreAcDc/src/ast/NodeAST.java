package ast;

import visitor.IVisitor;

/** 
 * Classe astratta che rappresenta un nodo dell'AST.
 * 
 * @author Luca Iacobucci, 20035727
 * @author Andrija Jovic, 20034244
 */
public abstract class NodeAST {

    /**
	 * Implementa il pattern visitor per il nodo.
	 * 
	 * @param visitor il visitor
	 */
    public abstract void accept(IVisitor visitor);
   // public abstract String calcCodice();
}
