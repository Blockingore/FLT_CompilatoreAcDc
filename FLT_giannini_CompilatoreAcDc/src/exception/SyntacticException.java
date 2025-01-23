package exception;

/**
 * Eccezione durante l'analisi sintattica.
 * 
 * @author Luca Iacobucci, 20035727
 * @author Andrija Jovic, 20034244
 */
public class SyntacticException extends Exception {

	/**
	 * Costruttore per SyntacticException con messaggio.
	 * 
	 * @param message il messaggio dell'eccezione
	 */
	public SyntacticException(String message) {
		super(message);
	}
}
