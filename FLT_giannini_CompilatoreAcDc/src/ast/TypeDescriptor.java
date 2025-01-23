package ast;

/**
 * Implementa un TypeDescriptor, che memorizza informazioni ottenute durante
 * l'analisi semantica.
 * 
 * @author Luca Iacobucci, 20035727
 * @author Andrija Jovic, 20034244
 */
public class TypeDescriptor {

    private TipoTD tipo;
    private String msg;
    
    /**
	 * Costruttore per TypeDescriptor, utilizzato in caso di errore.
	 * 
	 * @param tipo il tipo del TypeDescriptor
	 * @param msg  il messaggio d'errore
	 */
    public TypeDescriptor(TipoTD tipo, String msg) {
        this.tipo = tipo;
        this.msg = msg; 
    }

    /**
	 * Costruttore per TypeDescriptor.
	 * 
	 * @param tipo il tipo del TypeDescriptor
	 */
    public TypeDescriptor(TipoTD tipo) {
        this.tipo = tipo;
        this.msg = "";
    }

    /**
	 * Restituisce il tipo assegnato al TypeDescriptor.
	 * 
	 * @return il tipo
	 */
    public TipoTD getTipo() {
        return tipo;
    }
    
    /**
	 * Restituisce il messaggio assegnato al TypeDescriptor.
	 * 
	 * @return il messaggio
	 */
    public String getMsg() {
        return msg;
    }
    
    /**
	 * Controlla se due TypeDescriptor sono compatibili tra loro.
	 * 
	 * @param tD il TypeDescriptor da confrontare con this
	 * @return true se il TypeDescriptor è compatibile, false altrimenti
	 */
    public boolean compatibile(TypeDescriptor tD) {
        if (this.getTipo() == TipoTD.FLOAT && tD.getTipo() == TipoTD.INT) {
			return false;
		}
		return true;
    }
}