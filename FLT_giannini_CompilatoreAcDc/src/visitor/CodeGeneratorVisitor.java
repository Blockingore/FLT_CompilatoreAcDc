package visitor;
    
import ast.*;
import symbolTable.SymbolTable;
import symbolTable.SymbolTable.Attributes;
import symbolTable.CodeGeneretorException;
import symbolTable.Registri;

public class CodeGeneratorVisitor implements IVisitor{

    String codiceDc;    // mantiene il codice della visita
    String codiceGenerato; // mantiene il codice generato
    String log; // mantiene il log degli errori

    public CodeGeneratorVisitor(){
        Registri.init();
        codiceGenerato = "";
        log = "";
    }

    public String getCodiceGenerato() {
        return codiceGenerato.trim();
    }

    public String getLog() {
        return log;
    }

    @Override
    public void visit(NodeBinOp node) {
        node.accept(this);
        codiceDc = node.calcCodice();
    }

    @Override
    public void visit(NodeCost node) {
        codiceDc = node.calcCodice();
    }

    @Override
    public void visit(NodeDeref node) {
        node.accept(this);
        codiceDc = node.calcCodice();
    }

    @Override
    public void visit(NodeProgramm node) {

        for(NodeDecSt n : node.getDecSts()){
        
            if(log == ""){
                n.accept(this);
                if(codiceDc == ""){
                    codiceGenerato += "";
                    codiceDc = "";
                }else{
                    codiceGenerato += codiceDc + " "; 
                    codiceDc = "";
                }
            }else{
                codiceGenerato = "";
            }
        }
    }

    @Override
    public void visit(NodeId node) {
        node.accept(this);
        codiceDc = String.valueOf(SymbolTable.lookup(node.calcCodice()).getRegistro());              
    }
    
    @Override
    public void visit(NodeDecl node) {

        Attributes attr = SymbolTable.lookup(node.getId().getName());
        char registro;

        try {
            registro = Registri.newRegister();
        } catch (CodeGeneretorException e) {
            log = e.getMessage();
            return;
        }
    
        attr.setRegistro(registro);
            codiceDc = node.calcCodice();        
    }

    @Override
    public void visit(NodePrint node) {
        codiceDc = node.calcCodice();
    }

    @Override
    public void visit(NodeAssign node) {
		codiceDc = node.calcCodice();
    }

}
