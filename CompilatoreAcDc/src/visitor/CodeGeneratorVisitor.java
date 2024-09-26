package visitor;

import java.util.ArrayList;
import java.util.List;

import ast.NodeAssign;
import ast.NodeBinOp;
import ast.NodeConvert;
import ast.NodeCost;
import ast.NodeDecSt;
import ast.NodeDecl;
import ast.NodeDeref;
import ast.NodeId;
import ast.NodePrint;
import ast.NodeProgram;
import eccezioni.RegisterException;
import symbolTable.Registri;
import symbolTable.SymbolTable;
import symbolTable.SymbolTable.Attributes;

/**
 *  generazione codice
 * @author Nasima Barki
 *
 */
public class CodeGeneratorVisitor implements IVisitor{
	private String codiceDc; // mantiene il codice della visita
	private String log; // per l’eventuale errore nella generazione del codice
	List<String> codiceList;
	
	/**
	 * inizializzazione
	 */
	public CodeGeneratorVisitor() {
		super();
		SymbolTable.init();
		Registri.init();
		codiceDc = "";
		log = "";
		codiceList = new ArrayList<>();
	}

	/**
	 * ritorna il codice
	 * @return codice
	 */
	public String getCodice() {
		return String.join(" ", codiceList).strip();
	}

	/**
	 * ritorna log
	 * @return log
	 */
	public String getLog() {
		return log;
	}

	/**
	 * visita del nodo program
	 */
	@Override
	public void visit(NodeProgram node) {
		for(NodeDecSt decSt : node.getDecSts()) {
			if (log.isBlank()) {
				decSt.accept(this);
				codiceList.add(codiceDc);
				codiceDc = "";
			} else {
				codiceList.clear();;
			}
		}
	}

	/**
	 * visita del nodo id
	 */
	@Override
	public void visit(NodeId node) {
		String id = node.getName();
		codiceDc = String.valueOf(SymbolTable.lookup(id).getRegistro());
	}

	/**
	 * visita del nodo decl
	 */
	@Override
	public void visit(NodeDecl node) {
		String id = node.getId().getName();
		Attributes attribute = SymbolTable.lookup(id);
		char register;
		
		try {
			register = Registri.newRegister();
		} catch (RegisterException e) {
			log += e.getMessage();
			return;
		}
		
		attribute.setRegistro(register);
		
		if (node.getInit() != null) {
			node.getInit().accept(this);
			String init = codiceDc;

			node.getId().accept(this);
			id = codiceDc;

			codiceDc = init + " s" + id;
		}
	}

	/**
	 * visita del nodo operatore binario
	 */
	@Override
	public void visit(NodeBinOp node) {
		node.getLeft().accept(this);
		String leftCodice = codiceDc;
		node.getRight().accept(this);
		String rightCodice = codiceDc;
		
		String op = "";
		
		switch(node.getOp()) {
		case PLUS:
			op = " +";
			break;
		case DIVIDE:
			op = " /";
			break;
		case MINUS:
			op = " -";
			break;
		case TIMES:
			op = " *";
			break;
		default:
			break;
		}
		
		codiceDc = leftCodice + " "  + rightCodice + op;
	}

	/**
	 * visita del nodo print
	 */
	@Override
	public void visit(NodePrint node) {
		node.getId().accept(this);
		codiceDc = "l" + codiceDc + " p P";
	}

	/**
	 * visita del nodo assign
	 */
	@Override
	public void visit(NodeAssign node) {
		node.getExpr().accept(this);
		String exp = codiceDc;
		
		node.getId().accept(this);
		String id = codiceDc;
		
		codiceDc = exp + " s" + id;
	}

	/**
	 * visita del nodo convert
	 */
	@Override
	public void visit(NodeConvert node) {
		node.getExp().accept(this);
		codiceDc += " 5 k";
	}

	/**
	 * visita del nodo cost
	 */
	@Override
	public void visit(NodeCost node) {
		String value = node.getValue();
		codiceDc = value;
	}

	/**
	 * visita del nodo deref
	 */
	@Override
	public void visit(NodeDeref node) {
		node.getId().accept(this);
		codiceDc = "l" + codiceDc;
	}

}
