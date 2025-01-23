package exception;

/**
 * Eccezione durante l'analisi lessicale
 * 
 * @author Luca Iacobucci, 20035727
 * @author Andrija Jovic, 20034244
 */
public class LexicalException extends Exception {
   
    /**
	 * Costruttore per LexicalException con messaggio.
	 * 
	 * @param message il messaggio dell'eccezione
	 */
    public LexicalException(String message) {
        super(message);
    }

}
