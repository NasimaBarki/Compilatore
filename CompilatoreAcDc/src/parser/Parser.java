package parser;

import java.io.IOException;
import java.util.ArrayList;

import ast.LangOper;
import ast.LangType;
import ast.NodeAssign;
import ast.NodeBinOp;
import ast.NodeCost;
import ast.NodeDecSt;
import ast.NodeDecl;
import ast.NodeDeref;
import ast.NodeExpr;
import ast.NodeId;
import ast.NodePrint;
import ast.NodeProgram;
import ast.NodeStm;
import eccezioni.LexicalException;
import eccezioni.SyntacticException;
import scanner.Scanner;
import token.Token;
import token.TokenType;

/**
 *   parser del compilatore AcDc
 * @author Nasima Barki
 *
 */
public class Parser {
	private Scanner scanner;

	/**
	 *   costruttore
	 * @param scanner scanner del compilatore AcDc
	 */
	public Parser(Scanner scanner) {
		super();
		this.scanner = scanner;
	}
	
	/**
	 *   controlla che il token corrisponda al tipo specificato
	 * @param type il tipo da controllare
	 * @return il token corrispondente
	 * @throws LexicalException se il token non corrisponde al tipo specificato
	 * @throws IOException se ci sono errori di I/O
	 * @throws SyntacticException se ci sono errori di sintassi
	 */
	private Token match(TokenType type) throws LexicalException, IOException, SyntacticException {
		Token tk = scanner.peekToken();
		
		if (type.equals(tk.getTipo()))
			return scanner.nextToken();
		else
			throw new SyntacticException("Previsto token '" + type.toString() + "' alla riga " + tk.getRiga() + ".");
	}
	
	/**
	 *   parser del compilatore AcDc
	 * @return il nodo root del compilatore
	 * @throws SyntacticException se ci sono errori di sintassi
	 * @throws LexicalException se ci sono errori di I/O
	 * @throws IOException se ci sono errori di I/O
	 */
	public NodeProgram parse() throws SyntacticException, LexicalException, IOException {
		return this.parsePrg();
	}

	/**
	 *   Regola 0
	 * @return il nodo root del compilatore
	 * @throws SyntacticException
	 * @throws LexicalException
	 * @throws IOException
	 */
	private NodeProgram parsePrg() throws SyntacticException, LexicalException, IOException {
		Token tk;
		
		try {
			tk = this.scanner.peekToken();
		} catch (LexicalException | IOException e) {
			throw new SyntacticException("Lexical Exception", e);
		}
		
		ArrayList<NodeDecSt> nodeDecSt;
		
		switch (tk.getTipo()) {
			case TYFLOAT, TYINT, ID, PRINT, EOF:
				nodeDecSt = parseDSs();
				match(TokenType.EOF);
				return new NodeProgram(nodeDecSt);
			default:
				throw new SyntacticException("Errore a riga " + tk.getRiga() + ": dovrebbe esserci un token del tipo TYFLOAT, TYINT, ID, PRINT o EOF.");
		}
	}

	/**
	 *  Dalla regola 1 alla regola 3
	 * @return la lista dei nodi decSt
	 * @throws SyntacticException se ci sono errori di sintassi
	 * @throws LexicalException se ci sono errori di I/O
	 * @throws IOException se ci sono errori di I/O
	 */
	private ArrayList<NodeDecSt> parseDSs() throws SyntacticException, LexicalException, IOException {
		Token tk;
		
		try {
			tk = this.scanner.peekToken();
		} catch (LexicalException | IOException e) {
			throw new SyntacticException("Lexical Exception", e);
		}
		
		NodeDecl nodeDecl;
		ArrayList<NodeDecSt> nodeDecSt = new ArrayList<NodeDecSt>();
		NodeStm nodeStm;
		
		switch (tk.getTipo()) {
			case TYFLOAT, TYINT:
				nodeDecl = parseDcl();
				nodeDecSt = parseDSs();
				nodeDecSt.add(0, nodeDecl); //se non lo metti all'inizio dell'arraylist l'ast è al "contrario"
				return nodeDecSt;
			case ID, PRINT:
				nodeStm = parseStm();
				nodeDecSt = parseDSs();
				nodeDecSt.add(0, nodeStm);
				return nodeDecSt;
			case EOF:
				return nodeDecSt;
			default:
				throw new SyntacticException("Errore a riga " + tk.getRiga() + ": dovrebbe esserci un token del tipo TYFLOAT, TYINT, ID, PRINT o EOF.");
		}
	}

	/**
	 *  Dalla regola 7 alla regola 8
	 * @return il nodo Stm
	 * @throws LexicalException se ci sono errori di I/O
	 * @throws IOException se ci sono errori di I/O
	 * @throws SyntacticException se ci sono errori di sintassi
	 */
	private NodeStm parseStm() throws LexicalException, IOException, SyntacticException {
		Token tk;

		try {
			tk = this.scanner.peekToken();
		} catch (LexicalException | IOException e) {
			throw new SyntacticException("Lexical Exception", e);
		}
		
		NodePrint nodePrint;
		NodeId nodeId;
		NodeExpr nodeExpr;
		
		switch (tk.getTipo()) {
			case ID:
				nodeId = new NodeId(match(TokenType.ID).getVal());
				Token op = match(TokenType.OP_ASSIGN);
				nodeExpr = parseExp();
				
				switch(op.getVal()) {
				case "+=":
					nodeExpr = new NodeBinOp(LangOper.PLUS, new NodeDeref(nodeId), nodeExpr);
					break;
				case "-=":
					nodeExpr = new NodeBinOp(LangOper.MINUS, new NodeDeref(nodeId), nodeExpr);
					break;
				case "*=":
					nodeExpr = new NodeBinOp(LangOper.TIMES, new NodeDeref(nodeId), nodeExpr);
					break;
				case "/=":
					nodeExpr = new NodeBinOp(LangOper.DIVIDE, new NodeDeref(nodeId), nodeExpr);
					break;
				}
				
				match(TokenType.SEMI);	
				return new NodeAssign(nodeId, nodeExpr);
			case PRINT:
				match(TokenType.PRINT);
				nodeId = new NodeId(match(TokenType.ID).getVal());
				match(TokenType.SEMI);
				nodePrint = new NodePrint(nodeId);
				return nodePrint;
			default:
				throw new SyntacticException("Errore a riga " + tk.getRiga() + ": dovrebbe esserci un token del tipo ID o PRINT.");
		}
	}

	/**
	 *   Regola 4
	 * @return il nodo decl
	 * @throws LexicalException se ci sono errori di I/O
	 * @throws IOException	se ci sono errori di I/O
	 * @throws SyntacticException se ci sono errori di sintassi
	 */
	private NodeDecl parseDcl() throws LexicalException, IOException, SyntacticException {
		Token tk;
		
		try {
			tk = this.scanner.peekToken();
		} catch (LexicalException | IOException e) {
			throw new SyntacticException("Lexical Exception", e);
		}

		LangType type;
		NodeExpr init;
		
		switch (tk.getTipo()) {
			case TYFLOAT, TYINT:
				type = parseTy();	
				NodeId nodeId = new NodeId(match(TokenType.ID).getVal());
				init = parseDclP();
				return new NodeDecl(nodeId, type, init);
			default:
				throw new SyntacticException("Errore a riga " + tk.getRiga() + ": dovrebbe esserci un token del tipo TYFLOAT o TYINT.");
		}
	}

	/**
	 *  Dalla regola 5 alla regola 6
	 * @return il nodo dclP
	 * @throws LexicalException se ci sono errori di I/O
	 * @throws IOException se ci sono errori di I/O
	 * @throws SyntacticException se ci sono errori di sintassi
	 */
	private NodeExpr parseDclP() throws LexicalException, IOException, SyntacticException {
		Token tk;

		try {
			tk = this.scanner.peekToken();
		} catch (LexicalException | IOException e) {
			throw new SyntacticException("Lexical Exception", e);
		}
		
		NodeExpr nodeExpr = null;
		switch (tk.getTipo()) {
			case SEMI:
				match(TokenType.SEMI);
				return null;
			case OP_ASSIGN:
				match(TokenType.OP_ASSIGN);
				nodeExpr = parseExp();
				match(TokenType.SEMI);
				return nodeExpr;
			default:
				throw new SyntacticException("Errore a riga " + tk.getRiga() + ": dovrebbe esserci un token del tipo SEMI o OP_ASSIGN.");
		}
	}

	/**
	 *   Regola 9
	 * @return il nodo expr
	 * @throws SyntacticException se ci sono errori di sintassi
	 * @throws LexicalException se ci sono errori di I/O
	 * @throws IOException se ci sono errori di I/O
	 */
	private NodeExpr parseExp() throws SyntacticException, LexicalException, IOException {
		Token tk;
		
		try {
			tk = this.scanner.peekToken();
		} catch (LexicalException | IOException e) {
			throw new SyntacticException("Lexical Exception", e);
		}
		
		switch (tk.getTipo()) {	
			case ID, FLOAT, INT:
				NodeExpr left = parseTr();
				NodeExpr expl = parseExpP(left);
				return expl;
			default:
				throw new SyntacticException("Errore a riga " + tk.getRiga() + ": dovrebbe esserci un token del tipo ID, FLOAT o INT.");
		}
	}

	/**
	 *  Dalla regola 10 alla regola 12
	 * @param left il nodo left
	 * @return il nodo expr
	 * @throws LexicalException se ci sono errori di I/O
	 * @throws IOException se ci sono errori di I/O
	 * @throws SyntacticException se ci sono errori di sintassi
	 */
	private NodeExpr parseExpP(NodeExpr left) throws LexicalException, IOException, SyntacticException {
		Token tk;
		
		try {
			tk = this.scanner.peekToken();
		} catch (LexicalException | IOException e) {
			throw new SyntacticException("Lexical Exception", e);
		}

		NodeExpr exp1;
		NodeExpr exp2;
		
		switch (tk.getTipo()) {
			case PLUS:
				match(TokenType.PLUS);
				exp1 = parseTr();
				exp2 = parseExpP(exp1);
				return new NodeBinOp(LangOper.PLUS, left, exp2);
			case MINUS:
				match(TokenType.MINUS);
				exp1 = parseTr();
				exp2 = parseExpP(exp1);
				return new NodeBinOp(LangOper.MINUS, left, exp2);
			case SEMI:
				return left;
			default:
				throw new SyntacticException("Errore a riga " + tk.getRiga() + ": dovrebbe esserci un token del tipo PLUS, MINUS o SEMI.");
		}
	}

	/**
	 *   Regola 13
	 * @return il nodo expr
	 * @throws LexicalException se ci sono errori di I/O
	 * @throws IOException se ci sono errori di I/O
	 * @throws SyntacticException se ci sono errori di sintassi
	 */
	private NodeExpr parseTr() throws LexicalException, IOException, SyntacticException {
		Token tk;
		
		try {
			tk = this.scanner.peekToken();
		} catch (LexicalException | IOException e) {
			throw new SyntacticException("Lexical Exception", e);
		}
		
		NodeExpr exp1;
		NodeExpr exp2;

		switch (tk.getTipo()) {
			case ID, FLOAT, INT:
				exp1 = parseVal();
				exp2 = parseTrP(exp1);
				return exp2;
			default:
				throw new SyntacticException("Errore a riga " + tk.getRiga() + ": dovrebbe esserci un token del tipo ID, FLOAT o INT.");
		}
	}

	/**
	 *   Dalla regola 19 alla regola 21
	 * @return il nodo expr
	 * @throws LexicalException se ci sono errori di I/O
	 * @throws IOException	se ci sono errori di I/O
	 * @throws SyntacticException se ci sono errori di sintassi
	 */
	private NodeExpr parseVal() throws LexicalException, IOException, SyntacticException {
		Token tk;
		
		try {
			tk = this.scanner.peekToken();
		} catch (LexicalException | IOException e) {
			throw new SyntacticException("Lexical Exception", e);
		}

		String value;
		String id;
		
		switch (tk.getTipo()) {
			case INT:
				value = match(TokenType.INT).getVal();
				return new NodeCost(value, LangType.TYINT);
			case FLOAT:
				value = match(TokenType.FLOAT).getVal();
				return new NodeCost(value, LangType.TYFLOAT);
			case ID:
				id = match(TokenType.ID).getVal();
				return new NodeDeref(new NodeId(id));
			default:
				throw new SyntacticException("Errore a riga " + tk.getRiga() + ": dovrebbe esserci un token del tipo INT, FLOAT o ID.");
		}
	}

	/**
	 *   Dalla regola 14 alla regola 16
	 * @param left il nodo left
	 * @return il nodo expr
	 * @throws LexicalException se ci sono errori di I/O
	 * @throws IOException se ci sono errori di I/O
	 * @throws SyntacticException se ci sono errori di sintassi
	 */
	private NodeExpr parseTrP(NodeExpr left) throws LexicalException, IOException, SyntacticException {
		Token tk;
		
		try {
			tk = this.scanner.peekToken();
		} catch (LexicalException | IOException e) {
			throw new SyntacticException("Lexical Exception", e);
		}

		NodeExpr exp1;
		NodeExpr exp2;

		switch (tk.getTipo()) {
			case TIMES:
				match(TokenType.TIMES);
				exp1 = parseVal();
				exp2 = parseTrP(exp1);
				return new NodeBinOp(LangOper.TIMES, left, exp2);
			case DIVIDE:
				match(TokenType.DIVIDE);
				exp1 = parseVal();
				exp2 = parseTrP(exp1);
				return new NodeBinOp(LangOper.DIVIDE, left, exp2);
			case MINUS, PLUS, SEMI:
				return left;
			default:
				throw new SyntacticException("Errore a riga " + tk.getRiga() + ": dovrebbe esserci un token del tipo TIMES, DIVIDE, MINUS, PLUS o SEMI.");
		}
	}

	/**
	 * Dalla regola 17 alla regola 18
	 * @return il tipo
	 * @throws LexicalException se ci sono errori di I/O
	 * @throws IOException se ci sono errori di I/O
	 * @throws SyntacticException se ci sono errori di sintassi
	 */
	private LangType parseTy() throws LexicalException, IOException, SyntacticException {
		Token tk = scanner.peekToken();

		switch (tk.getTipo()) {
			case TYFLOAT:
				match(TokenType.TYFLOAT);
				return LangType.TYFLOAT;
			case TYINT:
				match(TokenType.TYINT);
				return LangType.TYINT;
			default:
				throw new SyntacticException("Errore a riga " + tk.getRiga() + ": dovrebbe esserci un token del tipo TYFLOAT o TYINT.");
		}
	}
}
