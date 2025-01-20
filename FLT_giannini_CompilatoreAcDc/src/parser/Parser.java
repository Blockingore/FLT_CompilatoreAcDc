package parser;

import java.util.ArrayList;

import ast.*;
import scanner.LexicalException;
import scanner.Scanner;
import token.*;


public class Parser {
	
	private Scanner s;
	
	public Parser(Scanner scan) {
		this.s = scan;
	}
	
	public NodeProgramm parse () throws SyntacticException{
		return this.parsePrg();
	}
	
	private NodeProgramm parsePrg() throws SyntacticException{
		
		 Token tk;

		 try {
			tk = s.peekToken();
		} catch (LexicalException e){
			throw new SyntacticException(e.getMessage());
		} 
		
		switch (tk.getTipo()) {
		
			case TYFLOAT:
			case TYINT:
			case ID:
			case PRINT:
			case EOF:
				ArrayList<NodeDecSt> dec = parseDSs();
				NodeProgramm node = new NodeProgramm(dec);
				match(TokenType.EOF);
				return node;
				
		default:
			throw new SyntacticException("Errore parser da ParsePrg: previsto un Token tra: TYFLOAT, TYINT, ID, PRINT, EOF;\n Token trovato: " + tk.getTipo() + ", alla riga " + tk.getRiga());
		}
	}
	
	private ArrayList<NodeDecSt> parseDSs() throws SyntacticException {
		
		Token tk;

		try {
			tk = s.peekToken();
		} catch (LexicalException e){
			throw new SyntacticException(e.getMessage());
		}

		ArrayList<NodeDecSt> dec = new ArrayList<NodeDecSt>(); 
		
		switch (tk.getTipo()) {
		 
			// produzione -> Dcl DSs
			case TYFLOAT:
			case TYINT:
				NodeDecl node = parseDcl();
				dec = parseDSs();
				dec.add(0, node);
				return dec;

			// produzione -> Stm DSs  
			case ID:
			case PRINT:
				NodeStm nodePrint = parseStm();
				dec = parseDSs();
				dec.add(0, nodePrint);
				return dec;
	
			// produzione -> Stringa vuota
			case EOF:
				return dec;
			
			default:
				throw new SyntacticException("Errore parser da ParseDSs: previsto un Token tra: TYFLOAT, TYINT, ID, PRINT, EOF;\n Token trovato: " + tk.getTipo() + ", alla riga " + tk.getRiga());
		}
	}
	
	private NodeDecl parseDcl() throws SyntacticException {

		Token tk;

		try {
			tk = s.peekToken();
		} catch (LexicalException e){
			throw new SyntacticException(e.getMessage());
		}			

		switch (tk.getTipo()) {
			
		//produzione -> Ty id DclP
		case TYFLOAT:
		case TYINT:
			LangType ty = parseTy();
			NodeId nId = new NodeId(match(TokenType.ID).getVal());

			NodeExpr nExpr = parseDclP();

			return new NodeDecl( nId ,ty, nExpr);

		default:
			throw new SyntacticException("Errore parser da ParseDcl: previsto un Token tra: TYFLOAT, TYINT;\n Token trovato: " + tk.getTipo() + ", alla riga " + tk.getRiga());
		}
	}
	
	private NodeExpr parseDclP() throws SyntacticException {
		
		Token tk;

		try {
			tk = s.peekToken();
		} catch (LexicalException e){
			throw new SyntacticException(e.getMessage());
		} 

		NodeExpr e;
		
		switch (tk.getTipo()) {
		 
		//produzione -> ;
		case SEMI:
			match(TokenType.SEMI);
			return null;
			
			//produzione -> = Exp ;
		case ASSIGN:
			match(TokenType.ASSIGN);
			e = parseExp();
			match(TokenType.SEMI);
			return e;
		
		default:
			throw new SyntacticException("Errore parser da ParseDclP: previsto un Token tra: SEMI, ASSIGN;\n Token trovato: " + tk.getTipo() + ", alla riga " + tk.getRiga());
		}
	}
	
	private LangType parseTy() throws SyntacticException {
		
		Token tk;

		try {
			tk = s.peekToken();
		} catch (LexicalException e){
			throw new SyntacticException(e.getMessage());
		} 

		switch (tk.getTipo()) {
		 
		//produzione float
		case TYFLOAT:
			match(TokenType.TYFLOAT);
			return LangType.FLOAT;
		//produzione int
		case TYINT:
			match(TokenType.TYINT);
			return LangType.INT;
		default:
			throw new SyntacticException("Errore parser da ParseTy: previsto un Token tra: TYFLOAT, TYINT;\n Token trovato: " + tk.getTipo() + ", alla riga " + tk.getRiga());
		}
		
		
	}

	private NodeStm parseStm() throws SyntacticException {
		
		Token tk;

		try {
			tk = s.peekToken();
		} catch (LexicalException e){
			throw new SyntacticException(e.getMessage());
		} 

			switch (tk.getTipo()) {
			 
			//solo produzione 7 : Stm -> id Op Exp ;
			case ID:
				NodeId nodeId = new NodeId(match(TokenType.ID).getVal());
				Token op = parseOp();
				NodeExpr expr = parseExp();

				switch (op.getVal()) {
					case "+=":
						expr = new NodeBinOp(LangOper.PLUS, new NodeDeref(nodeId), expr);
						break;
					case "-=":
						expr = new NodeBinOp(LangOper.MINUS, new NodeDeref(nodeId), expr);
						break;
					case "*=":
						expr = new NodeBinOp(LangOper.TIMES, new NodeDeref(nodeId), expr);
						break;
					case "/=":
						expr = new NodeBinOp(LangOper.DIV, new NodeDeref(nodeId), expr);
						break;
					default:
						break;
				}

				match(TokenType.SEMI);
				
				return new NodeAssign(nodeId, expr);
				
			//solo produzione 8 : Stm -> print id ;
			case PRINT:
				match(TokenType.PRINT);
				NodeId nId = new NodeId(match(TokenType.ID).getVal());
				
				match(TokenType.SEMI);

				return new NodePrint(nId);

			default:
				throw new SyntacticException("Errore parser da ParseStm: previsto un Token tra: ID, PRINT;\n Token trovato: " + tk.getTipo() + ", alla riga " + tk.getRiga());
			}
		
	}
	
	private NodeExpr parseExp() throws SyntacticException {
		
		Token tk;

		try {
			tk = s.peekToken();
		} catch (LexicalException e){
			throw new SyntacticException(e.getMessage());
		} 

		NodeExpr tr;
		NodeExpr expP; 
		
			switch(tk.getTipo()) {
				//produzione -> Tr ExpP
				case ID:
				case FLOAT:
				case INT:
					tr = parseTr();
					expP = parseExpP(tr);
					return expP;
				
				default:
					throw new SyntacticException("Errore parser da ParseExp: previsto un Token tra: ID, FLOAT, INT;\n Token trovato: " + tk.getTipo() + ", alla riga " + tk.getRiga());
			}
	}
	
	private NodeExpr parseExpP(NodeExpr left) throws SyntacticException {
		
		Token tk ;

		try {
			tk = s.peekToken();
		} catch (LexicalException e){
			throw new SyntacticException(e.getMessage());
		}


		NodeExpr tr, expP;
		
		switch(tk.getTipo()) {
			//produzione -> + Tr ExpP
			case PLUS:
				match(TokenType.PLUS);
				tr = parseTr();
				expP = parseExpP(tr);
				return new NodeBinOp(LangOper.PLUS, left, expP);
				
				//produzione -> - Tr ExpP
				case MINUS:
				match(TokenType.MINUS);
				tr = parseTr();
				expP = parseExpP(tr);
				return new NodeBinOp(LangOper.MINUS, left, expP);

			//produzione -> ϵ 
			case SEMI:
				return left;
				
			default:
				throw new SyntacticException("Errore parser da ParseExpP: previsto un Token tra: PLUS, MINUS, SEMI;\n Token trovato: " + tk.getTipo() + ", alla riga " + tk.getRiga());
		}
	}
	
	private NodeExpr parseTr() throws SyntacticException {

		Token tk;

		try {
			tk = s.peekToken();
		} catch (LexicalException e){
			throw new SyntacticException(e.getMessage());
		}

		NodeExpr val;
		
		switch(tk.getTipo()) {
			//produzione -> Val Trp
			case ID:
			case FLOAT:
			case INT:
				val = parseVal();
				return parseTrP(val);
			
			default:
				throw new SyntacticException("Errore parser da ParseTr: previsto un Token tra: ID, FLOAT, INT;\n Token trovato: " + tk.getTipo() + ", alla riga " + tk.getRiga());
		}
	}
	
	private NodeExpr parseTrP(NodeExpr left) throws SyntacticException {
		
		Token tk;
	
		try {
			tk = s.peekToken();
		} catch (LexicalException e){
			throw new SyntacticException(e.getMessage());
		} 

		NodeExpr val, trp;
		
		switch(tk.getTipo()) {
			//produzione -> * Val TrP
			case TIMES:
				match(TokenType.TIMES);
				val = parseVal();
				trp = parseTrP(val);
				return new NodeBinOp(LangOper.TIMES, left, trp);
				
				
			//produzione -> / Val TrP
			case DIV:
				match(TokenType.DIV);
				val = parseVal();
				trp = parseTrP(val);
				return new NodeBinOp(LangOper.DIV, left, trp);
				
			//produzione -> ϵ 
			case MINUS:
			case PLUS:
			case SEMI:
				return left;
				
			default:
				throw new SyntacticException("Errore parser da ParseTrP: previsto un Token tra: TIMES, DIV, MINUS, PLUS, SEMI;\n Token trovato: " + tk.getTipo() + ", alla riga " + tk.getRiga());
		}
	}
	
	private NodeExpr parseVal() throws SyntacticException {
		
		Token tk;

		try {
			tk = s.peekToken();
		} catch (LexicalException e){
			throw new SyntacticException(e.getMessage());
		}
		
		switch(tk.getTipo()) {
			//produzione -> intVal
			case INT:
				return new NodeCost( match(TokenType.INT).getVal() , LangType.INT );
				
			//produzione -> float Val
			case FLOAT:
				return new NodeCost(match(TokenType.FLOAT).getVal(), LangType.FLOAT);
				
			//produzione -> id
			case ID:
				NodeId nId = new NodeId(match(TokenType.ID).getVal());
				return new NodeDeref(nId);
	
			default:
				throw new SyntacticException("Errore parser da ParseVal: previsto un Token tra: INT, FLOAT, ID;\n Token trovato: " + tk.getTipo() + ", alla riga " + tk.getRiga());
		}
	}
	
	private Token parseOp() throws SyntacticException {
		
		Token tk;

		try {
			tk = s.peekToken();
		} catch (LexicalException e){
			throw new SyntacticException(e.getMessage());
		}
		
		switch(tk.getTipo()) {
			//produzione -> =
			case ASSIGN:
				Token tkn = match(TokenType.ASSIGN);
				return new Token(tkn.getTipo(), tkn.getRiga(), tkn.getVal());
				
				
			//produzione -> OpAss
			case OP_ASSIGN:
				Token tkn1 = match(TokenType.OP_ASSIGN);
				return new Token(tkn1.getTipo(), tkn1.getRiga(), tkn1.getVal());
					
			default:
				throw new SyntacticException("Errore parser da ParseOp: previsto un Token tra: ASSIGN, OP_ASSIGN;\n Token trovato: " + tk.getTipo() + ", alla riga " + tk.getRiga());
		}
	}
	
	private Token match(TokenType type) throws SyntacticException{
		
		Token tk;

		try {
			tk = s.peekToken();
		} catch (LexicalException e){
			throw new SyntacticException(e.getMessage());
		}
		
		if(type.equals(tk.getTipo())){
			try {
				return s.nextToken();
			} catch (LexicalException e){
				throw new SyntacticException(e.getMessage());
			}	
		}else
			throw new SyntacticException("Errore nel match: previsto un Token: " + type + ", alla riga: " + tk.getRiga() + ". Invece trovato token: " + tk.getTipo());
	}
		
}




