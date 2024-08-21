package test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import eccezioni.LexicalException;
import scanner.Scanner;
import token.Token;
import token.TokenType;

class TestScanner {
	Scanner scannerTestEOF;
	Scanner scannerTestId;
	Scanner scannerTestIdKeyWords;
	Scanner scannerTestKeywords;
	Scanner scannerTestOperators;
	Scanner scannerTestINT;
	Scanner scannerTestFLOAT;
	Scanner scannerTestErroriNumbers;
	Scanner scannerTestGenerale;
	Scanner scannerPeekToken;
	
	@BeforeEach
	public void setup() {
		// TODO erroriID non è un file txt
		// TODO è giusto indicare il percorso in questo modo?
		try {
			String percorso = "." + File.separator + "src" + File.separator + "test" + File.separator + "data" + File.separator + "testScanner"  + File.separator;
			
			scannerTestEOF = new Scanner(percorso  + "testEOF.txt");
			scannerTestId = new Scanner(percorso  + "testId.txt");
			scannerTestIdKeyWords = new Scanner(percorso  + "testIdKeyWords.txt");
			scannerTestKeywords = new Scanner(percorso  + "testKeywords.txt");
			scannerTestOperators = new Scanner(percorso  + "testOperators.txt");
			scannerTestINT = new Scanner(percorso  + "testINT.txt");
			scannerTestFLOAT = new Scanner(percorso  + "testFLOAT.txt");
			scannerTestErroriNumbers = new Scanner(percorso  + "erroriNumbers.txt");
			scannerTestGenerale = new Scanner(percorso  + "testGenerale.txt");
			scannerPeekToken = new Scanner(percorso  + "testGenerale.txt");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	void testEOF() throws LexicalException, IOException {
		String nextToken = scannerTestEOF.nextToken().toString();
		assertEquals("EOF,r:4", nextToken);
	}

	@Test
	void testId() throws LexicalException, IOException {
		String nextToken = scannerTestId.nextToken().toString();
		assertEquals("ID,r:1,jskjdsfhkjdshkf", nextToken);
		
		nextToken = scannerTestId.nextToken().toString();
		assertEquals("ID,r:2,printl", nextToken);
		
		nextToken = scannerTestId.nextToken().toString();
		assertEquals("ID,r:4,ffloat", nextToken);
		
		nextToken = scannerTestId.nextToken().toString();
		assertEquals("ID,r:6,hhhjj", nextToken);
		
		// TODO essendo solo un test degli id, devo controllare l'EOF?
		nextToken = scannerTestId.nextToken().toString();
		assertEquals("EOF,r:7", nextToken);
	}
	
	@Test
	void testIdKeyWords() throws LexicalException, IOException {
		String nextToken = scannerTestIdKeyWords.nextToken().toString();
		assertEquals("TYINT,r:1", nextToken);
		
		nextToken = scannerTestIdKeyWords.nextToken().toString();
		assertEquals("ID,r:1,inta", nextToken);
		
		nextToken = scannerTestIdKeyWords.nextToken().toString();
		assertEquals("TYFLOAT,r:2", nextToken);
		
		nextToken = scannerTestIdKeyWords.nextToken().toString();
		assertEquals("PRINT,r:3", nextToken);
		
		nextToken = scannerTestIdKeyWords.nextToken().toString();
		assertEquals("ID,r:4,nome", nextToken);
		
		nextToken = scannerTestIdKeyWords.nextToken().toString();
		assertEquals("ID,r:5,intnome", nextToken);
		
		nextToken = scannerTestIdKeyWords.nextToken().toString();
		assertEquals("TYINT,r:6", nextToken);
		
		nextToken = scannerTestIdKeyWords.nextToken().toString();
		assertEquals("ID,r:6,nome", nextToken);
	}
	
	@Test
	void testKeywords() throws LexicalException, IOException {
		String nextToken = scannerTestKeywords.nextToken().toString();
		assertEquals("PRINT,r:2", nextToken);
		
		nextToken = scannerTestKeywords.nextToken().toString();
		assertEquals("TYFLOAT,r:2", nextToken);
		
		nextToken = scannerTestKeywords.nextToken().toString();
		assertEquals("TYINT,r:5", nextToken);
	}
	
	@Test
	void testOperators() throws LexicalException, IOException {
		String nextToken = scannerTestOperators.nextToken().toString();
		assertEquals("PLUS,r:1", nextToken);
		
		nextToken = scannerTestOperators.nextToken().toString();
		assertEquals("OP_ASSIGN,r:1,/=", nextToken);
		
		nextToken = scannerTestOperators.nextToken().toString();
		assertEquals("MINUS,r:2", nextToken);
		
		nextToken = scannerTestOperators.nextToken().toString();
		assertEquals("TIMES,r:2", nextToken);
		
		nextToken = scannerTestOperators.nextToken().toString();
		assertEquals("DIVIDE,r:3", nextToken);
		
		nextToken = scannerTestOperators.nextToken().toString();
		assertEquals("OP_ASSIGN,r:5,+=", nextToken);
		
		nextToken = scannerTestOperators.nextToken().toString();
		assertEquals("OP_ASSIGN,r:6,=", nextToken);
		
		nextToken = scannerTestOperators.nextToken().toString();
		assertEquals("OP_ASSIGN,r:6,-=", nextToken);
		
		nextToken = scannerTestOperators.nextToken().toString();
		assertEquals("MINUS,r:8", nextToken);
		
		nextToken = scannerTestOperators.nextToken().toString();
		assertEquals("OP_ASSIGN,r:8,=", nextToken);
		
		nextToken = scannerTestOperators.nextToken().toString();
		assertEquals("OP_ASSIGN,r:8,*=", nextToken);
		
		nextToken = scannerTestOperators.nextToken().toString();
		assertEquals("SEMI,r:10", nextToken);
	}
	
	@Test
	void testINT() throws LexicalException, IOException {
		String nextToken = scannerTestINT.nextToken().toString();
		assertEquals("INT,r:2,698", nextToken);
		
		nextToken = scannerTestINT.nextToken().toString();
		assertEquals("INT,r:4,560099", nextToken);
		
		nextToken = scannerTestINT.nextToken().toString();
		assertEquals("INT,r:5,1234", nextToken);
	}
	
	@Test
	void testFLOAT() throws LexicalException, IOException {
		String nextToken = scannerTestFLOAT.nextToken().toString();
		assertEquals("FLOAT,r:1,098.8095", nextToken);
		
		nextToken = scannerTestFLOAT.nextToken().toString();
		assertEquals("FLOAT,r:2,0.", nextToken);
		
		nextToken = scannerTestFLOAT.nextToken().toString();
		assertEquals("FLOAT,r:3,98.", nextToken);
		
		nextToken = scannerTestFLOAT.nextToken().toString();
		assertEquals("FLOAT,r:5,89.99999", nextToken);
	}
	
	@Test
	void testErroriNumbers() throws LexicalException, IOException {
		LexicalException exc = Assertions.assertThrows(LexicalException.class, () -> {
			scannerTestErroriNumbers.nextToken().toString();
		});
		assertEquals("Il numero '00' a riga 1 non è valido.", exc.getMessage());
		
		exc = Assertions.assertThrows(LexicalException.class, () -> {
			scannerTestErroriNumbers.nextToken().toString();
		});
		assertEquals("Il numero '123a' a riga 2 non può contenere lettere.", exc.getMessage());
		
		exc = Assertions.assertThrows(LexicalException.class, () -> {
			System.out.println(scannerTestErroriNumbers.nextToken().toString());
		});
		assertEquals("Il numero '12.a' a riga 3 non può contenere lettere.", exc.getMessage());
		
		// TODO devo andare più nello specifico e indicare che non possono esserci più di 5 cifre dopo la virgola?*
		exc = Assertions.assertThrows(LexicalException.class, () -> {
			System.out.println(scannerTestErroriNumbers.nextToken().toString());
		});
		assertEquals("Il numero '123.121212' a riga 4 non è valido.", exc.getMessage());
	}
	
	@Test
	void testGenerale() throws LexicalException, IOException {
		// riga 1
		String nextToken = scannerTestGenerale.nextToken().toString();
		assertEquals("TYINT,r:1", nextToken);
		
		nextToken = scannerTestGenerale.nextToken().toString();
		assertEquals("ID,r:1,temp", nextToken);
		
		nextToken = scannerTestGenerale.nextToken().toString();
		assertEquals("SEMI,r:1", nextToken);
		
		// riga 2
		nextToken = scannerTestGenerale.nextToken().toString();
		assertEquals("ID,r:2,temp", nextToken);
		
		nextToken = scannerTestGenerale.nextToken().toString();
		assertEquals("OP_ASSIGN,r:2,+=", nextToken);
		
		nextToken = scannerTestGenerale.nextToken().toString();
		assertEquals("FLOAT,r:2,5.", nextToken);
		
		nextToken = scannerTestGenerale.nextToken().toString();
		assertEquals("SEMI,r:2", nextToken);
		
		// riga 4
		nextToken = scannerTestGenerale.nextToken().toString();
		assertEquals("TYFLOAT,r:4", nextToken);
		
		nextToken = scannerTestGenerale.nextToken().toString();
		assertEquals("ID,r:4,b", nextToken);
		
		nextToken = scannerTestGenerale.nextToken().toString();
		assertEquals("SEMI,r:4", nextToken);
		
		// riga 5
		nextToken = scannerTestGenerale.nextToken().toString();
		assertEquals("ID,r:5,b", nextToken);
		
		nextToken = scannerTestGenerale.nextToken().toString();
		assertEquals("OP_ASSIGN,r:5,=", nextToken);
		
		nextToken = scannerTestGenerale.nextToken().toString();
		assertEquals("ID,r:5,temp", nextToken);
		
		nextToken = scannerTestGenerale.nextToken().toString();
		assertEquals("PLUS,r:5", nextToken);
		
		nextToken = scannerTestGenerale.nextToken().toString();
		assertEquals("FLOAT,r:5,3.2", nextToken);
		
		nextToken = scannerTestGenerale.nextToken().toString();
		assertEquals("SEMI,r:5", nextToken);
		
		// riga 6
		nextToken = scannerTestGenerale.nextToken().toString();
		assertEquals("PRINT,r:6", nextToken);
		
		nextToken = scannerTestGenerale.nextToken().toString();
		assertEquals("ID,r:6,b", nextToken);
		
		nextToken = scannerTestGenerale.nextToken().toString();
		assertEquals("SEMI,r:6", nextToken);
		
		// riga 7
		nextToken = scannerTestGenerale.nextToken().toString();
		assertEquals("EOF,r:7", nextToken);
	}
	
	@Test
	void peekToken () throws LexicalException, IOException {
		assertEquals(scannerPeekToken.peekToken().getTipo(), TokenType.TYINT );
		assertEquals(scannerPeekToken.nextToken().getTipo(), TokenType.TYINT );
		assertEquals(scannerPeekToken.peekToken().getTipo(), TokenType.ID );
		assertEquals(scannerPeekToken.peekToken().getTipo(), TokenType.ID );
		Token t = scannerPeekToken.nextToken();
		assertEquals(t.getTipo(), TokenType.ID);
		assertEquals(t.getRiga(), 1);
		assertEquals(t.getVal(), "temp");
		
		// TODO provare con altri tipi di token
	}
}
