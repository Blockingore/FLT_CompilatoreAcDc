package symbolTable;

import ast.*;

public interface IVisitor {
    public abstract void visit (NodeProgramm node);
    public abstract void visit (NodeId node);
    public abstract void visit(NodeDecl node);
    public abstract void visit(NodeExpr node);
    public abstract void visit(NodePrint node);

}
