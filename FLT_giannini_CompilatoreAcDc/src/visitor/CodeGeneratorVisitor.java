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
        codiceDc = "";
    }

    public String getCodiceGenerato() {
        return codiceGenerato.trim();
    }

    public String getLog() {
        return log;
    }

    @Override
    public void visit(NodeBinOp node) {
       // node.accept(this);
       // codiceDc = node.calcCodice();

        node.getLeft().accept(this);
        String leftCodice = codiceDc;
        node.getRight().accept(this);
        String rightCodice = codiceDc;

        if(node.getOp() == LangOper.PLUS){
            codiceDc = leftCodice + " " + rightCodice + " +";
        }else if(node.getOp() == LangOper.MINUS){
            codiceDc = leftCodice + " " + rightCodice + " -";
        }else if(node.getOp() == LangOper.TIMES){
            codiceDc = leftCodice + " " + rightCodice + " *";
        }else if(node.getOp() == LangOper.DIV){
            codiceDc = leftCodice + " " + rightCodice + " /";
        }else if(node.getOp() == LangOper.DIV_FLOAT){
            codiceDc = leftCodice + " " + rightCodice + " 5 k / 0 k";
        }
    }
    
    @Override
    public void visit(NodeCost node) {
       // codiceDc = node.calcCodice();
       codiceDc = node.getValue();
    }

    @Override
    public void visit(NodeDeref node) {
        node.getId().accept(this);
        //codiceDc = node.calcCodice();  
        String codiceId = codiceDc;  
        codiceDc = "l" + codiceId;

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
        //node.accept(this);
        //codiceDc = String.valueOf(SymbolTable.lookup(node.calcCodice()).getRegistro());              
        codiceDc = String.valueOf(SymbolTable.lookup(node.getName()).getRegistro());              
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

        if(node.getInit() == null){
            
            codiceDc = "";
        
        }else{

            node.getInit().accept(this);
            String codiceInit = codiceDc;

            node.getId().accept(this);
            String codiceId = codiceDc;

            codiceDc = codiceInit + " s" + codiceId;

            /*
                if (codiceDc.contains("5 k"))
				    codiceDc = codiceDc.concat(" 0 k");
            */
        }       

    }

    @Override
    public void visit(NodePrint node) {
       // codiceDc = node.calcCodice();

       node.getId().accept(this);
       String codiceId = codiceDc;
       
       codiceDc = "l" + codiceId + " p P";
    }

    @Override
    public void visit(NodeAssign node) {
		//codiceDc = node.calcCodice();

        node.getId().accept(this);
        String codiceId = codiceDc;

        node.getExpr().accept(this);
        String codiceExpr = codiceDc;


        codiceDc = codiceExpr + " s" + codiceId;

    }

}
