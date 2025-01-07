package test;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Test;

import ast.*;
import parser.Parser;
import parser.SyntacticException;
import scanner.LexicalException;
import scanner.Scanner;

public class testAST {
	
	private String path = "C:\\Users\\Luca\\Downloads\\FLT_giannini_CompilatoreAcDc-20241210T082414Z-001\\FLT_giannini_CompilatoreAcDc\\src\\test\\data\\testParser\\";
	
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
