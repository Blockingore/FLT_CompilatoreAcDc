package visitor;



import ast.*;
import symbolTable.SymbolTable;


public class TypeCheckinVisitor implements IVisitor{

    private TypeDescriptor resType; // mantiene il risultato della visita

    public TypeCheckinVisitor(){
        SymbolTable.init();
    }

    @Override
    public void visit(NodeBinOp node) {

       node.getLeft().accept(this);
       
       TypeDescriptor leftTD = resType;

       node.getRight().accept(this);
       TypeDescriptor rightTD = resType;

        // se i due descrittori sono di tipo int allora il risultato è int
        if(leftTD.getTipo() == TipoTD.INT && rightTD.getTipo() == TipoTD.INT){
           resType = new TypeDescriptor(TipoTD.INT);
           node.setOp(LangOper.DIV);
        }

        // se uno dei due descrittori è di tipo float allora il risultato è float
        else if((leftTD.getTipo() == TipoTD.INT && rightTD.getTipo() == TipoTD.FLOAT) || (leftTD.getTipo() == TipoTD.FLOAT && rightTD.getTipo() == TipoTD.FLOAT) || (leftTD.getTipo() == TipoTD.FLOAT && rightTD.getTipo() == TipoTD.INT)){
            resType = new TypeDescriptor(TipoTD.FLOAT);
            node.setOp(LangOper.DIV_FLOAT);
        }

        // se uno dei due descrittori è di tipo errore allora il risultato è errore
        else if(leftTD.getTipo() == TipoTD.ERROR || rightTD.getTipo() == TipoTD.ERROR){
            
            StringBuilder errorMessage = new StringBuilder();

            if(leftTD.getTipo() == TipoTD.ERROR){
                errorMessage.append("Errore da leftTD");
                if(rightTD.getTipo() == TipoTD.ERROR)
                    errorMessage.append(" - Errore da rightTD");
            }
            else if(rightTD.getTipo() == TipoTD.ERROR){
                errorMessage.append("Errore da rightTD");
            }
            resType = new TypeDescriptor(TipoTD.ERROR, errorMessage.toString());
        }
    }

    @Override
    public void visit(NodeCost node) {
        if(node.getType() == LangType.FLOAT){
            resType = new TypeDescriptor(TipoTD.FLOAT);
        }
        else if(node.getType() == LangType.INT){
            resType = new TypeDescriptor(TipoTD.INT);
        }
        else{
            resType = new TypeDescriptor(TipoTD.ERROR, "Tipo non valido");
        }
    }

    @Override
    public void visit(NodeDeref node) {
        node.getId().accept(this);
    }

    @Override
    public void visit(NodeProgramm node) {
        for(NodeDecSt dec : node.getDecSts()){
            dec.accept(this);
        }
    }

    @Override
    public void visit(NodeId node) {
        
        if(SymbolTable.lookup(node.getName()) == null){
        
            resType = new TypeDescriptor(TipoTD.ERROR, "Variabile non dichiarata");
        
        }else{
        
            if(SymbolTable.lookup(node.getName()).getTipo() == LangType.FLOAT){
                resType = new TypeDescriptor(TipoTD.FLOAT);
            }
        
            else if(SymbolTable.lookup(node.getName()).getTipo() == LangType.INT){
                resType = new TypeDescriptor(TipoTD.INT);
            }

            else {
                resType = new TypeDescriptor(TipoTD.ERROR, "Tipo non valido");
            }
        }
    }

    @Override
    public void visit(NodeDecl node) {

        TypeDescriptor idTD;

    //prima controllo il tipo della variabile

        //faccio una enter della mia dichiarazione nella symbol table e controllo se è già presente una variabile con lo stesso nome
        if(SymbolTable.enter(node.getId().getName(), new SymbolTable.Attributes(node.getType()) )){
            if(node.getType() == LangType.FLOAT){
                idTD = new TypeDescriptor(TipoTD.FLOAT);
            }
            else if(node.getType() == LangType.INT){
                idTD = new TypeDescriptor(TipoTD.INT);
            }else{
                idTD = new TypeDescriptor(TipoTD.ERROR, "Tipo non valido");
            }
        }else{
            idTD = new TypeDescriptor(TipoTD.ERROR, "Variabile già dichiarata");
            return;
        }

    //controllo se è presente una inizializzazione
        if(node.getInit() == null){
            resType = new TypeDescriptor(TipoTD.OK);
            return;
        }

        node.getInit().accept(this);
        TypeDescriptor initTD = resType;
        
        //controllo se l'inizializzazione è compatibile con il tipo della variabile
        if(!initTD.compatibile(idTD)){
            resType = new TypeDescriptor(TipoTD.ERROR, "Impossibile assegnare un valore di tipo " + initTD + " a una variabile di tipo " + idTD);
        }
        else{
            resType = new TypeDescriptor(TipoTD.OK);
        }
    }

    @Override
    public void visit(NodePrint node) {
        
        node.getId().accept(this);

        if(resType.getTipo() ==TipoTD.ERROR){
            resType = new TypeDescriptor(TipoTD.ERROR, "Impossibile stampare una variabile non dichiarata");
        }
    }

    @Override
    public void visit(NodeAssign node) {

        node.getId().accept(this);
        TypeDescriptor idTD = resType;
        node.getExpr().accept(this);
        TypeDescriptor exprTD = resType;

        //controllo se l'espressione è compatibile con il tipo della variabile
        if(!exprTD.compatibile(idTD)){
            resType = new TypeDescriptor(TipoTD.ERROR, "Impossibile assegnare un valore di tipo " + exprTD + " a una variabile di tipo " + idTD);
        }
        else{
            resType = new TypeDescriptor(TipoTD.OK);
        }

    }
    
}
