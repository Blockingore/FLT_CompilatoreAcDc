package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.FileNotFoundException;
import parser.*;
import scanner.Scanner;
import visitor.TypeCheckinVisitor;
import ast.*;
import exception.SyntacticException;
import org.junit.Test;

/**
 * Test per il TypeChecking
 * 
 * @author Luca Iacobucci, 20035727
 * @author Andrija Jovic, 20034244
 */
public class TestTypeChecking {
	
    String path = "src/test/data/testTypeChecking/";

    @Test
	public void test1_DicRipetute() throws FileNotFoundException, SyntacticException {
		NodeProgramm nP = new Parser(new Scanner(path + "1_dicRipetute.txt")).parse();
		var tcVisit = new TypeCheckinVisitor();
		nP.accept(tcVisit);
		
		assertEquals(tcVisit.getLog(), "Errore: Variabile 'a' già dichiarata; ");
	}

    @Test
	public void test2_idNonDec() throws FileNotFoundException, SyntacticException {
		NodeProgramm nP = new Parser(new Scanner(path + "2_idNonDec.txt")).parse();
		var tcVisit = new TypeCheckinVisitor();
		nP.accept(tcVisit);
		
		assertEquals(tcVisit.getLog(), "Errore: Impossibile stampare 'b': variabile non dichiarata; ");
	}

    @Test
	public void test3_idNonDec() throws FileNotFoundException, SyntacticException {
		NodeProgramm nP = new Parser(new Scanner(path + "3_idNonDec.txt")).parse();
		var tcVisit = new TypeCheckinVisitor();
		nP.accept(tcVisit);
		
		assertEquals(tcVisit.getLog(), "Errore: Variabile 'c' non dichiarata; Errore da rightTD");
	}

    @Test
	public void test4_tipoNonCompatibile() throws FileNotFoundException, SyntacticException {
		NodeProgramm nP = new Parser(new Scanner(path + "4_tipoNonCompatibile.txt")).parse();
		var tcVisit = new TypeCheckinVisitor();
		nP.accept(tcVisit);
		
		assertEquals(tcVisit.getLog(), "Errore: Impossibile assegnare un valore di tipo FLOAT a una variabile di tipo INT ");
	}

    @Test
	public void test5_corretto() throws FileNotFoundException, SyntacticException {
		NodeProgramm nP = new Parser(new Scanner(path + "5_corretto.txt")).parse();
		var tcVisit = new TypeCheckinVisitor();
		nP.accept(tcVisit);
		
		assertEquals(tcVisit.getLog(), "");
	}

    @Test
	public void test6_corretto() throws FileNotFoundException, SyntacticException {
		NodeProgramm nP = new Parser(new Scanner(path + "6_corretto.txt")).parse();
		var tcVisit = new TypeCheckinVisitor();
		nP.accept(tcVisit);
		
		assertEquals(tcVisit.getLog(), "");
	}

    @Test
	public void test7_corretto() throws FileNotFoundException, SyntacticException {
		NodeProgramm nP = new Parser(new Scanner(path + "7_corretto.txt")).parse();
		var tcVisit = new TypeCheckinVisitor();
		nP.accept(tcVisit);
		
		assertEquals(tcVisit.getLog(), "");
	}

}
