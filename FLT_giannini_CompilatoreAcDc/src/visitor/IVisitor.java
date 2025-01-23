package visitor;

import ast.*;

/**
 * Interfaccia per il pattern visitor.
 * 
 * @author Luca Iacobucci, 20035727
 * @author Andrija Jovic, 20034244
 */
public interface IVisitor {
    
    /**
	 * Visita il nodo NodeBinOp.
	 * 
	 * @param node il NodeBinOp da visitare
	 */
    public abstract void visit(NodeBinOp node);
    
    /**
	 * Visita il nodo NodeConst.
	 * 
	 * @param node il NodeConst da visitare
	 */
    public abstract void visit(NodeCost node);

    /**
	 * Visita il nodo NodeDeref.
	 * 
	 * @param node il NodeDeref da visitare
	 */
    public abstract void visit(NodeDeref node);

    /**
	 * Visita il nodo NodeProgramm.
	 * 
	 * @param node il NodeProgramm da visitare
	 */   
    public abstract void visit(NodeProgramm node);

    /**
	 * Visita il nodo NodeId.
	 * 
	 * @param node il NodeId da visitare
	 */
    public abstract void visit(NodeId node);

    /**
	 * Visita il nodo NodeDecl.
	 * 
	 * @param node il NodeDecl da visitare
	 */
    public abstract void visit(NodeDecl node);

    /**
	 * Visita il nodo NodePrint.
	 * 
	 * @param node il NodePrint da visitare
	 */
    public abstract void visit(NodePrint node);

    /**
	 * Visita il nodo NodeAssign.
	 *
	 * @param node il NodeAssign da visitare
	 */
    public abstract void visit(NodeAssign node);

}