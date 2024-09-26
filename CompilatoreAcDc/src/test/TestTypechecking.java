package test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import ast.NodeProgram;
import ast.TipoTD;
import ast.TypeDescriptor;
import eccezioni.LexicalException;
import eccezioni.SyntacticException;
import parser.Parser;
import scanner.Scanner;
import visitor.TypeCheckingVisitor;

class TestTypechecking {
	String percorso = "." + File.separator + "src" + File.separator + "test" + File.separator + "data" + File.separator + "testTypechecking"  + File.separator;
	
	@Test
	void testDicRipetute() throws SyntacticException, LexicalException, IOException {
		Scanner scanner = new Scanner (percorso + "1_dicRipetute.txt");
		Parser parser = new Parser(scanner);
		
		NodeProgram node = parser.parse();
		TypeCheckingVisitor typeCheckingVisitor = new TypeCheckingVisitor();
		node.accept(typeCheckingVisitor);
		
		assertEquals("La variabile 'a' e' gia' stata dichiarata.\n", typeCheckingVisitor.getResType().getMsg());
	}
	
	@Test
	void idNonDec() throws SyntacticException, LexicalException, IOException {
		Scanner scanner = new Scanner (percorso + "2_idNonDec.txt");
		Parser parser = new Parser(scanner);
		
		NodeProgram node = parser.parse();
		TypeCheckingVisitor typeCheckingVisitor = new TypeCheckingVisitor();
		node.accept(typeCheckingVisitor);
		
		assertEquals("La variabile 'b' non e' ancora stata dichiarata.\n", typeCheckingVisitor.getResType().getMsg());
	}
	
	@Test
	void testIdNonDec() throws SyntacticException, LexicalException, IOException {
		Scanner scanner = new Scanner (percorso + "3_idNonDec.txt");
		Parser parser = new Parser(scanner);
		
		NodeProgram node = parser.parse();
		TypeCheckingVisitor typeCheckingVisitor = new TypeCheckingVisitor();
		node.accept(typeCheckingVisitor);
		
		assertEquals("La variabile 'c' non e' ancora stata dichiarata.\n", typeCheckingVisitor.getResType().getMsg());
	}
	
	@Test
	void testTipoNonCompatibile() throws SyntacticException, LexicalException, IOException {
		Scanner scanner = new Scanner (percorso + "4_TipoNonCompatibile.txt");
		Parser parser = new Parser(scanner);
		
		NodeProgram node = parser.parse();
		TypeCheckingVisitor typeCheckingVisitor = new TypeCheckingVisitor();
		node.accept(typeCheckingVisitor);
		
		assertEquals("Alla variabile intera 'b' non e' possibile assegnare un valore di tipo float.\n", typeCheckingVisitor.getResType().getMsg());
	}
	
	@Test
	void testCorretto5() throws SyntacticException, LexicalException, IOException {
		Scanner scanner = new Scanner (percorso + "5_Corretto.txt");
		Parser parser = new Parser(scanner);
		
		NodeProgram node = parser.parse();
		TypeCheckingVisitor typeCheckingVisitor = new TypeCheckingVisitor();
		node.accept(typeCheckingVisitor);
		
		assertEquals(null, typeCheckingVisitor.getResType().getMsg());
	}
	
	@Test
	void testCorretto6() throws SyntacticException, LexicalException, IOException {
		Scanner scanner = new Scanner (percorso + "6_Corretto.txt");
		Parser parser = new Parser(scanner);
		
		NodeProgram node = parser.parse();
		TypeCheckingVisitor typeCheckingVisitor = new TypeCheckingVisitor();
		node.accept(typeCheckingVisitor);
		
		assertEquals(null, typeCheckingVisitor.getResType().getMsg());
	}
	
	@Test
	void testCorretto7() throws SyntacticException, LexicalException, IOException {
		Scanner scanner = new Scanner (percorso + "7_Corretto.txt");
		Parser parser = new Parser(scanner);
		
		NodeProgram node = parser.parse();
		TypeCheckingVisitor typeCheckingVisitor = new TypeCheckingVisitor();
		node.accept(typeCheckingVisitor);
		
		assertEquals(null, typeCheckingVisitor.getResType().getMsg());
	}

}
