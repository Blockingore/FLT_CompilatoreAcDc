package test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import exception.LexicalException;
import exception.SyntacticException;
import parser.Parser;
import scanner.Scanner;


/**
 * Test per il parser
 * 
 * @author Luca Iacobucci, 20035727
 * @author Andrija Jovic, 20034244
 */
public class TestParser {

	String path = "src/test/data/testParser/";

	@Test
	void testParserCorretto1() throws SyntacticException, LexicalException, IOException {
		Scanner s = new Scanner(path + "testParserCorretto1.txt");
		
		Parser p = new Parser(s);
		
		assertDoesNotThrow( () -> p.parse());
		
	}
	
	@Test
	void testParserCorretto2() throws SyntacticException, LexicalException, IOException {
		Scanner s = new Scanner(path + "testParserCorretto2.txt");
		Parser p = new Parser(s);
		
		assertDoesNotThrow( () -> p.parse());
	}

	@Test
	void testParserEcc_0() throws SyntacticException, LexicalException, IOException {
		Scanner s = new Scanner(path + "testParserEcc_0.txt");
		Parser p = new Parser(s);
		
		SyntacticException e = assertThrows(  SyntacticException.class, () -> {
			p.parse();});  
			assertEquals(e.getMessage(), "Errore parser da ParseOp: previsto un Token tra: ASSIGN, OP_ASSIGN;\n Token trovato: SEMI, alla riga 1"); 
	}
	
	@Test
	void testParserEcc_1() throws SyntacticException, LexicalException, IOException {
		Scanner s = new Scanner(path + "testParserEcc_1.txt");
		Parser p = new Parser(s);
		
		SyntacticException e = assertThrows(  SyntacticException.class, () -> {
			p.parse();});  
		assertEquals(e.getMessage(), "Errore parser da ParseTr: previsto un Token tra: ID, FLOAT, INT;\n Token trovato: TIMES, alla riga 2"); 
	}
	
	@Test
	void testParserEcc_2() throws SyntacticException, LexicalException, IOException {
		Scanner s = new Scanner(path + "testParserEcc_2.txt");
		Parser p = new Parser(s);
		
		SyntacticException e = assertThrows(  SyntacticException.class, () -> {
			p.parse();});  
		assertEquals(e.getMessage(), "Errore parser da ParseDSs: previsto un Token tra: TYFLOAT, TYINT, ID, PRINT, EOF;\n Token trovato: INT, alla riga 3"); 
	}
	
	@Test
	void testParserEcc_3() throws SyntacticException, LexicalException, IOException {
		Scanner s = new Scanner(path + "testParserEcc_3.txt");
		Parser p = new Parser(s);
		
		SyntacticException e = assertThrows(  SyntacticException.class, () -> {
			p.parse();});  
		assertEquals(e.getMessage(), "Errore parser da ParseOp: previsto un Token tra: ASSIGN, OP_ASSIGN;\n Token trovato: PLUS, alla riga 2"); 
	}

	@Test
	void testParserEcc_4() throws SyntacticException, LexicalException, IOException {
		Scanner s = new Scanner(path + "testParserEcc_4.txt");
		Parser p = new Parser(s);
	
		SyntacticException e = assertThrows(  SyntacticException.class, () -> {
			p.parse();});  
		assertEquals(e.getMessage(), "Errore nel match: previsto un Token: ID, alla riga: 2. Invece trovato token: INT"); 
	}

	@Test
	void testParserEcc_5() throws SyntacticException, LexicalException, IOException {
		Scanner s = new Scanner(path + "testParserEcc_5.txt");
		Parser p = new Parser(s);
		
		SyntacticException e = assertThrows(  SyntacticException.class, () -> {
			p.parse();});  
		assertEquals(e.getMessage(), "Errore nel match: previsto un Token: ID, alla riga: 3. Invece trovato token: INT"); 
	}
	
	@Test
	void testParserEcc_6() throws SyntacticException, LexicalException, IOException {
		Scanner s = new Scanner(path + "testParserEcc_6.txt");
		Parser p = new Parser(s);
		
		SyntacticException e = assertThrows(  SyntacticException.class, () -> {
			p.parse();});  
		assertEquals(e.getMessage(), "Errore nel match: previsto un Token: ID, alla riga: 3. Invece trovato token: TYFLOAT"); 
	}
	
	@Test
	void testParserEcc_7() throws SyntacticException, LexicalException, IOException {
		Scanner s = new Scanner(path + "testParserEcc_7.txt");
		Parser p = new Parser(s);
		
		SyntacticException e = assertThrows(  SyntacticException.class, () -> {
			p.parse();});  
		assertEquals(e.getMessage(), "Errore nel match: previsto un Token: ID, alla riga: 2. Invece trovato token: ASSIGN"); 
	}
	
	@Test
	void testSoloDich() throws SyntacticException, LexicalException, IOException {
		Scanner s = new Scanner(path + "testSoloDich.txt");
		Parser p = new Parser(s);
	
		assertDoesNotThrow(() -> p.parse() );	  
	}
	
	@Test
	void testSoloDichPrint() throws SyntacticException, LexicalException, IOException {
		Scanner s = new Scanner(path + "testSoloDichPrint.txt");
		Parser p = new Parser(s);
		
		assertDoesNotThrow(() -> p.parse() );	  
	}

}
