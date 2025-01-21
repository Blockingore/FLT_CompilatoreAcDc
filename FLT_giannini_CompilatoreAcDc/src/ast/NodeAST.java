package ast;

import visitor.IVisitor;

public abstract class NodeAST {

    private String log = ""; ; // per l’eventuale errore nella generazione del codice
    public abstract void accept(IVisitor visitor);
   // public abstract String calcCodice();
}
