package visitor;

import ast.LangType;
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
import ast.TipoTD;
import ast.TypeDescriptor;
import symbolTable.SymbolTable;
import symbolTable.SymbolTable.Attributes;

/**
 *   pattern visitor
 * @author Nasima Barki
 *
 */
public class TypeCheckingVisitor implements IVisitor{
	private TypeDescriptor resType; // mantiene il risultato della visita
	private String errorMsg = "";
	
	/**
	 * inizializzazione
	 */
	public TypeCheckingVisitor() {
		super();
		SymbolTable.init();
	}

	/**
	 * ritorna un typeDescriptor
	 * @return resType
	 */
	public TypeDescriptor getResType() {
		return resType;
	}

	/**
	 * visita del nodo program
	 */
	@Override
	public void visit(NodeProgram node) {
		for(NodeDecSt decSt : node.getDecSts()) {
			decSt.accept(this);
		}
	}

	/**
	 * visita del nodo id
	 */
	@Override
	public void visit(NodeId node) {
		String id = node.getName();
		Attributes type = SymbolTable.lookup(id);
		if(type != null) {
			switch(type.getTipo()) {
			case TYINT:
				resType = new TypeDescriptor(TipoTD.OK);
			case TYFLOAT:
				resType = new TypeDescriptor(TipoTD.FLOAT);
			}
		} else {
			errorMsg += "La variabile '" + id + "' non e' ancora stata dichiarata.\n";
			resType = new TypeDescriptor(TipoTD.ERROR, errorMsg);
			return;
		}
	}

	/**
	 * visita del nodo decl
	 */
	@Override
	public void visit(NodeDecl node) {
		String id = node.getId().getName();
		Attributes entry = new Attributes(node.getType());
		
		TypeDescriptor leftType = null;
		TypeDescriptor rightType;
		
		if(SymbolTable.enter(id, entry)) {
			switch(node.getType()) {
			case TYINT:
				leftType = new TypeDescriptor(TipoTD.INT);
				break;
			case TYFLOAT:
				leftType = new TypeDescriptor(TipoTD.FLOAT);
				break;
			default:
				leftType = new TypeDescriptor(TipoTD.ERROR);
				break;
			}
		} else {
			errorMsg += "La variabile '" + id + "' e' gia' stata dichiarata.\n";
			resType = new TypeDescriptor(TipoTD.ERROR, errorMsg);
			return;
		}
		
		if (node.getInit() == null) {
			resType = new TypeDescriptor(TipoTD.OK);
			return;
		} else {
			node.getInit().accept(this);
			
			rightType = resType;
			
			if(leftType.getTipo() == TipoTD.INT && rightType.getTipo() == TipoTD.FLOAT) {
				errorMsg += "Alla variabile intera'" + id + "' non e' possibile assegnare un valore di tipo float.\n";
				resType = new TypeDescriptor(TipoTD.ERROR, errorMsg);
			} else {
				resType = new TypeDescriptor(TipoTD.OK);
			}
		}
	}

	/**
	 * visita del nodo operatore binario
	 */
	@Override
	public void visit(NodeBinOp node) {
		node.getLeft().accept(this);
		TypeDescriptor leftTD = resType;
		node.getRight().accept(this);
		TypeDescriptor rightTD = resType;
		
		if(leftTD.getTipo() == TipoTD.ERROR) {
			resType = leftTD;
			return;
		}
		if(rightTD.getTipo() == TipoTD.ERROR) {
			resType = rightTD;
			return;
		}
		if(leftTD.getTipo() == rightTD.getTipo()) {
			switch(leftTD.getTipo()) {
			case INT:
				resType = new TypeDescriptor(TipoTD.INT);
			case FLOAT:
				resType = new TypeDescriptor(TipoTD.FLOAT);
			}
		} else {
			if (leftTD.getTipo() == TipoTD.INT) {
				node.setLeft(new NodeConvert(node.getLeft()));
			}
			else {
				node.setRight(new NodeConvert(node.getRight()));
			}
			resType = new TypeDescriptor(TipoTD.FLOAT);
		}
	}

	/**
	 * visita del nodo print
	 */
	@Override
	public void visit(NodePrint node) {
		node.getId().accept(this);
		
		if(resType.getTipo() != TipoTD.ERROR) {
			resType = new TypeDescriptor(TipoTD.OK);
		}
	}

	/**
	 * visita del nodo assign
	 */
	@Override
	public void visit(NodeAssign node) {
		node.getId().accept(this);
		TypeDescriptor leftTD = resType;
		node.getExpr().accept(this);
		TypeDescriptor rightTD = resType;
		
		String id = node.getId().getName();
		
		if(leftTD.getTipo() == TipoTD.ERROR) {
			resType = leftTD;
			return;
		}
		if(rightTD.getTipo() == TipoTD.ERROR) {
			resType = rightTD;
			return;
		}
		if(leftTD.compatibile(rightTD)) {
			resType = new TypeDescriptor(TipoTD.OK);
		} else {
			errorMsg += "Alla variabile intera '" + id + "' non e' possibile assegnare un valore di tipo float.\n";
			resType = new TypeDescriptor(TipoTD.ERROR, errorMsg);
			return;
		}
	}

	/**
	 * visita del nodo convert
	 */
	@Override
	public void visit(NodeConvert node) {
		resType = new TypeDescriptor(TipoTD.FLOAT);
	}

	/**
	 * visita del nodo cost
	 */
	@Override
	public void visit(NodeCost node) {
		LangType type = node.getType();
		
		switch(type) {
		case TYINT:
			resType = new TypeDescriptor(TipoTD.INT);
			break;
		case TYFLOAT:
			resType = new TypeDescriptor(TipoTD.FLOAT);
			break;
		}
	}

	/**
	 * visita del nodo deref
	 */
	@Override
	public void visit(NodeDeref node) {
		node.getId().accept(this);
	}

}
