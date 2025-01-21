package test;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Test;

import ast.*;
import exception.*;
import parser.Parser;
import scanner.Scanner;

public class TestAST {
	
	String path = "src/test/data/testParser/";


	@Test
	public void testSoloDichPrint() throws SyntacticException, LexicalException, IOException {
		
		Scanner s = new Scanner(path + "testSoloDichPrint.txt");
		Parser p = new Parser(s);
		
		ArrayList<NodeDecSt> node = new ArrayList<>();
		NodeProgramm n = new NodeProgramm(node);
		n = p.parse();
		System.out.println(n.toString()); 
		
	}

	@Test
	public void testParserCorretto1() throws SyntacticException, LexicalException, IOException {
		
		Scanner s = new Scanner(path + "testParserCorretto1.txt");
		Parser p = new Parser(s);
		
		ArrayList<NodeDecSt> node = new ArrayList<>();
		NodeProgramm n = new NodeProgramm(node);
		n = p.parse();
		System.out.println(n.toString()); 
		
	}

}
