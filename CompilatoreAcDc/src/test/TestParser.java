package test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileNotFoundException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import eccezioni.SyntacticException;
import parser.Parser;
import scanner.Scanner;

class TestParser {
	String percorso = "." + File.separator + "src" + File.separator + "test" + File.separator + "data" + File.separator + "testParser"  + File.separator;
	
	@Test
	void testParserCorretto1() throws FileNotFoundException {
		Scanner scanner = new Scanner (percorso + "testParserCorretto1.txt");
		Parser parser = new Parser(scanner);

		assertDoesNotThrow(() -> {
			parser.parse();
		});
	}
	
	@Test
	void testParserCorretto2() throws FileNotFoundException {
		Scanner scanner = new Scanner (percorso + "testParserCorretto2.txt");
		Parser parser = new Parser(scanner);

		assertDoesNotThrow(() -> {
			parser.parse();
		});
	}
	
	@Test
	void testParserEcc0() throws FileNotFoundException {
		Scanner scanner = new Scanner (percorso + "testParserEcc_0.txt");
		Parser parser = new Parser(scanner);

		SyntacticException exc = Assertions.assertThrows(SyntacticException.class, () -> {
			parser.parse();
		});
		assertEquals("Previsto token 'OP_ASSIGN' alla riga 1.", exc.getMessage());
	}

	@Test
	void testParserEcc1() throws FileNotFoundException {
		Scanner scanner = new Scanner (percorso + "testParserEcc_1.txt");
		Parser parser = new Parser(scanner);

		SyntacticException exc = Assertions.assertThrows(SyntacticException.class, () -> {
			parser.parse();
		});
		assertEquals("Errore a riga 2: dovrebbe esserci un token del tipo ID, FLOAT o INT.", exc.getMessage());
	}
	
	@Test
	void testParserEcc2() throws FileNotFoundException {
		Scanner scanner = new Scanner (percorso + "testParserEcc_2.txt");
		Parser parser = new Parser(scanner);

		SyntacticException exc = Assertions.assertThrows(SyntacticException.class, () -> {
			parser.parse();
		});
		assertEquals("Errore a riga 3: dovrebbe esserci un token del tipo TYFLOAT, TYINT, ID, PRINT o EOF.", exc.getMessage());
	}
	
	@Test
	void testParserEcc3() throws FileNotFoundException {
		Scanner scanner = new Scanner (percorso + "testParserEcc_3.txt");
		Parser parser = new Parser(scanner);

		SyntacticException exc = Assertions.assertThrows(SyntacticException.class, () -> {
			parser.parse();
		});
		assertEquals("Previsto token 'OP_ASSIGN' alla riga 2.", exc.getMessage());
	}
	
	@Test
	void testParserEcc4() throws FileNotFoundException {
		Scanner scanner = new Scanner (percorso + "testParserEcc_4.txt");
		Parser parser = new Parser(scanner);

		SyntacticException exc = Assertions.assertThrows(SyntacticException.class, () -> {
			parser.parse();
		});
		assertEquals("Previsto token 'ID' alla riga 2.", exc.getMessage());
	}
	
	@Test
	void testParserEcc5() throws FileNotFoundException {
		Scanner scanner = new Scanner (percorso + "testParserEcc_5.txt");
		Parser parser = new Parser(scanner);

		SyntacticException exc = Assertions.assertThrows(SyntacticException.class, () -> {
			parser.parse();
		});
		assertEquals("Previsto token 'ID' alla riga 3.", exc.getMessage());
	}
	
	@Test
	void testParserEcc6() throws FileNotFoundException {
		Scanner scanner = new Scanner (percorso + "testParserEcc_6.txt");
		Parser parser = new Parser(scanner);

		SyntacticException exc = Assertions.assertThrows(SyntacticException.class, () -> {
			parser.parse();
		});
		assertEquals("Previsto token 'ID' alla riga 4.", exc.getMessage());
	}
	
	@Test
	void testParserEcc7() throws FileNotFoundException {
		Scanner scanner = new Scanner (percorso + "testParserEcc_7.txt");
		Parser parser = new Parser(scanner);

		SyntacticException exc = Assertions.assertThrows(SyntacticException.class, () -> {
			parser.parse();
		});
		assertEquals("Previsto token 'ID' alla riga 2.", exc.getMessage());
	}
	
	@Test
	void testSoloDichPrint1() throws FileNotFoundException {
		Scanner scanner = new Scanner (percorso + "testSoloDichPrint1.txt");
		Parser parser = new Parser(scanner);

		assertDoesNotThrow(() -> {
			parser.parse();
		});
	}
}
