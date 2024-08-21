package test;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import token.Token;
import token.TokenType;

class TestToken {
	
	@Test
	void testCostruttoreSenzaVal() {
		Token token = new Token(TokenType.PRINT, 1);
		
		assertEquals(TokenType.PRINT, token.getTipo());
		assertEquals(1, token.getRiga());
	}
	
	@Test
	void testCostruttoreConVal() {
		Token token = new Token(TokenType.ID, 1, "tempa");
				
		assertEquals(TokenType.ID, token.getTipo());
		assertEquals(1, token.getRiga());
		assertEquals("tempa", token.getVal());
	}
	
	@Test
	void testToString() {
		Token tokenVal = new Token(TokenType.ID, 1, "tempa");
		Token tokenNoVal = new Token(TokenType.PRINT, 1);
		
		assertEquals("ID,r:1,tempa", tokenVal.toString());
		assertEquals("PRINT,r:1", tokenNoVal.toString());
	}
}
