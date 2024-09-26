package ast;

/**
 *   descrittore del tipo
 * @author Nasima Barki
 *
 */
public class TypeDescriptor {
	private TipoTD tipo;
	private String msg;
	
	/**
	 *   costruttore
	 * @param tipo il tipo
	 */
	public TypeDescriptor(TipoTD tipo) {
		super();
		this.tipo = tipo;
	}

	/**
	 *   costruttore
	 * @param tipo il tipo
	 * @param msg il messaggio (solitamente di errore)
	 */
	public TypeDescriptor(TipoTD tipo, String msg) {
		super();
		this.tipo = tipo;
		this.msg = msg;
	}

	/**
	 *   ritorna il tipo
	 * @return il tipo
	 */
	public TipoTD getTipo() {
		return tipo;
	}

	/**
	 *   ritorna il messaggio
	 * @return il messaggio
	 */
	public String getMsg() {
		return msg;
	}

	/**
	 *   ritorna true se il tipo compatibile
	 * @param tD il tipo da controllare
	 * @return true se il tipo compatibile
	 */
	public boolean compatibile(TypeDescriptor tD) {
		if(tD.getTipo() == TipoTD.FLOAT && this.getTipo() == TipoTD.INT) {
			return true;
		}
		return false;
	}
}
