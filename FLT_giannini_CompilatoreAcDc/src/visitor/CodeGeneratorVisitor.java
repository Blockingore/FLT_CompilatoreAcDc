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

    @Override
    public void visit(NodeBinOp node) {
        /* 
        node.getLeft().accept(this);
        String left = codiceDc;
        node.getRight().accept(this);
        String right = codiceDc;
        */
        
        node.accept(this);
        codiceDc = node.calcCodice();

        /* 
        if(node.getOp() == LangOper.PLUS){
            codiceDc = left + " " + right + " +";
        }else if(node.getOp() == LangOper.MINUS){
            codiceDc = node.getLeft().calcCodice() + " " + node.getRight().calcCodice() + " -";
        }else if(node.getOp() == LangOper.TIMES){
            codiceDc = node.getLeft().calcCodice() + " " + node.getRight().calcCodice() + " *";
        }else if(node.getOp() == LangOper.DIV){
            codiceDc = node.getLeft().calcCodice() + " " + node.getRight().calcCodice() + " /";
        }else if(node.getOp() == LangOper.PLUS){
            codiceDc = node.getLeft().calcCodice() + " " + node.getRight().calcCodice() + " +";
        }
        */
    }

    @Override
    public void visit(NodeCost node) {
        node.accept(this);
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
        
            if(log != null){
        
                n.accept(this);
                codiceGenerato += codiceDc + " "; 
                codiceDc = "";
        
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

        if(node.getInit() != null){
            /*
            node.getInit().accept(this);
            String init = codiceDc;
            
            node.getId().accept(this);
            String id = codiceDc;
            */
            node.accept(this);
            codiceDc = node.calcCodice();

            //resetto la precisione se modificata in precedenza
            if(codiceDc.contains("5 k"))
                codiceDc = codiceDc.concat("0 k");
        }
    }

    @Override
    public void visit(NodePrint node) {
        node.accept(this);
        codiceDc = node.calcCodice();
    }

    @Override
    public void visit(NodeAssign node) {
/* 
        node.getExpr().accept(this);
		String exprCodice = node.getExpr().calcCodice();

		node.getId().accept(this);
		String idCodice = node.getId().calcCodice(); */
        node.accept(this);
		codiceDc = node.calcCodice();
		/* resetta la precisione se è stata modificata in precedenza */
		if (codiceDc.contains("5 k"))
			codiceDc = codiceDc.concat(" 0 k");
    }

}
