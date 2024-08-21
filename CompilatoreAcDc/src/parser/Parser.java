package parser;

import java.io.IOException;

import eccezioni.LexicalException;
import eccezioni.SyntacticException;
import scanner.Scanner;
import token.Token;
import token.TokenType;

public class Parser {
	private Scanner scanner;

	public Parser(Scanner scanner) {
		super();
		this.scanner = scanner;
	}
	
	private Token match(TokenType type) throws LexicalException, IOException, SyntacticException {
		Token tk = scanner.peekToken();
		
		if (type.equals(tk.getTipo()))
			return scanner.nextToken();
		else
			throw new SyntacticException("Previsto token '" + type.toString() + "' alla riga " + tk.getRiga() + ".");
	}
	
	public void parse() throws SyntacticException, LexicalException, IOException {
		this.parsePrg();
	}

	private void parsePrg() throws SyntacticException, LexicalException, IOException {
		Token tk = scanner.peekToken();
		System.out.println("Prg: " + tk.getTipo());
		switch (tk.getTipo()) {
			case TYFLOAT, TYINT, ID, PRINT, EOF:
				parseDSs();
				match(TokenType.EOF);
				return;
			default:
				throw new SyntacticException("Errore a riga " + tk.getRiga() + ": dovrebbe esserci un token del tipo TYFLOAT, TYINT, ID, PRINT o EOF.");
		}
	}

	private void parseDSs() throws SyntacticException, LexicalException, IOException {
		Token tk = scanner.peekToken();
		System.out.println("DSs: " + tk.getTipo());
		switch (tk.getTipo()) {
			case TYFLOAT, TYINT:
				parseDcl();
				parseDSs();
				return;
			case ID, PRINT:
				parseStm();
				parseDSs();
				return;
			case EOF:
				return;
			default:
				throw new SyntacticException("Errore a riga " + tk.getRiga() + ": dovrebbe esserci un token del tipo TYFLOAT, TYINT, ID, PRINT o EOF.");
		}
	}

	private void parseStm() throws LexicalException, IOException, SyntacticException {
		Token tk = scanner.peekToken();
		System.out.println("Stm: " + tk.getTipo());
		switch (tk.getTipo()) {
			case ID:
				match(TokenType.ID);
				match(TokenType.OP_ASSIGN);
				parseExp();
				match(TokenType.SEMI);
				return;
			case PRINT:
				match(TokenType.PRINT);
				match(TokenType.ID);
				match(TokenType.SEMI);
				return;
			default:
				throw new SyntacticException("Errore a riga " + tk.getRiga() + ": dovrebbe esserci un token del tipo ID o PRINT.");
		}
	}

	private void parseDcl() throws LexicalException, IOException, SyntacticException {
		Token tk = scanner.peekToken();
		System.out.println("Dcl: " + tk.getTipo());
		switch (tk.getTipo()) {
			case TYFLOAT, TYINT:
				parseTy();	
				match(TokenType.ID);
				parseDclP();
				return;
			default:
				throw new SyntacticException("Errore a riga " + tk.getRiga() + ": dovrebbe esserci un token del tipo TYFLOAT o TYINT.");
		}
	}

	private void parseDclP() throws LexicalException, IOException, SyntacticException {
		Token tk = scanner.peekToken();
		System.out.println("DclP: " + tk.getTipo());
		switch (tk.getTipo()) {
			case SEMI:
				match(TokenType.SEMI);
				return;
			case OP_ASSIGN:
				match(TokenType.OP_ASSIGN);
				parseExp();
				match(TokenType.SEMI);
				return;
			default:
				throw new SyntacticException("Errore a riga " + tk.getRiga() + ": dovrebbe esserci un token del tipo SEMI o OP_ASSIGN.");
		}
	}

	private void parseExp() throws LexicalException, IOException, SyntacticException {
		Token tk = scanner.peekToken();
		System.out.println("Exp: " + tk.getTipo());
		switch (tk.getTipo()) {
			case ID, FLOAT, INT:
				parseTr();
				parseExpP();
				return;
			default:
				throw new SyntacticException("Errore a riga " + tk.getRiga() + ": dovrebbe esserci un token del tipo ID, FLOAT o INT.");
		}
	}

	private void parseExpP() throws LexicalException, IOException, SyntacticException {
		Token tk = scanner.peekToken();
		System.out.println("ExpP: " + tk.getTipo());
		switch (tk.getTipo()) {
			case PLUS:
				match(TokenType.PLUS);
				parseTr();
				parseExpP();
				return;
			case MINUS:
				match(TokenType.MINUS);
				parseTr();
				parseExpP();
				return;
			case SEMI:
				return;
			default:
				throw new SyntacticException("Errore a riga " + tk.getRiga() + ": dovrebbe esserci un token del tipo PLUS, MINUS o SEMI.");
		}
	}

	private void parseTr() throws LexicalException, IOException, SyntacticException {
		Token tk = scanner.peekToken();
		System.out.println("Tr: " + tk.getTipo());
		switch (tk.getTipo()) {
			case ID, FLOAT, INT:
				parseVal();
				parseTrP();
				return;
			default:
				throw new SyntacticException("Errore a riga " + tk.getRiga() + ": dovrebbe esserci un token del tipo ID, FLOAT o INT.");
		}
	}

	private void parseVal() throws LexicalException, IOException, SyntacticException {
		Token tk = scanner.peekToken();
		System.out.println("Val: " + tk.getTipo());
		switch (tk.getTipo()) {
			case INT:
				match(TokenType.INT);
				return;
			case FLOAT:
				match(TokenType.FLOAT);
				return;
			case ID:
				match(TokenType.ID);
				return;
			default:
				throw new SyntacticException("Errore a riga " + tk.getRiga() + ": dovrebbe esserci un token del tipo INT, FLOAT o ID.");
		}
	}

	private void parseTrP() throws LexicalException, IOException, SyntacticException {
		Token tk = scanner.peekToken();
		System.out.println("TrP: " + tk.getTipo());
		switch (tk.getTipo()) {
			case TIMES:
				match(TokenType.TIMES);
				parseVal();
				parseTrP();
				return;
			case DIVIDE:
				match(TokenType.DIVIDE);
				parseVal();
				parseTrP();
				return;
			case MINUS, PLUS, SEMI:
				return;
			default:
				throw new SyntacticException("Errore a riga " + tk.getRiga() + ": dovrebbe esserci un token del tipo TIMES, DIVIDE, MINUS, PLUS o SEMI.");
		}
	}

	private void parseTy() throws LexicalException, IOException, SyntacticException {
		Token tk = scanner.peekToken();
		System.out.println("Ty: " + tk.getTipo());
		switch (tk.getTipo()) {
			case TYFLOAT:
				match(TokenType.TYFLOAT);
				return;
			case TYINT:
				match(TokenType.TYINT);
				return;
			default:
				throw new SyntacticException("Errore a riga " + tk.getRiga() + ": dovrebbe esserci un token del tipo TYFLOAT o TYINT.");
		}
	}
}
