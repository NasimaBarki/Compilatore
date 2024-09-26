package test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.Test;

import ast.NodeProgram;
import eccezioni.LexicalException;
import eccezioni.SyntacticException;
import parser.Parser;
import scanner.Scanner;
import visitor.CodeGeneratorVisitor;
import visitor.TypeCheckingVisitor;

class TestCodeGenerator {
	String percorso = "." + File.separator + "src" + File.separator + "test" + File.separator + "data" + File.separator + "testCodeGenerator"  + File.separator;
	
	@Test
	void test() throws SyntacticException, LexicalException, IOException {
		Scanner scanner = new Scanner (percorso + "test1.txt");
		Parser parser = new Parser(scanner);
		
		NodeProgram node = parser.parse();
		TypeCheckingVisitor typeCheckingVisitor = new TypeCheckingVisitor();
		CodeGeneratorVisitor codeGeneratorVisitor = new CodeGeneratorVisitor();
		node.accept(typeCheckingVisitor);
		node.accept(codeGeneratorVisitor);

		assertEquals("1.0 6 5 k / sa la p P", codeGeneratorVisitor.getCodice());
	}

}
