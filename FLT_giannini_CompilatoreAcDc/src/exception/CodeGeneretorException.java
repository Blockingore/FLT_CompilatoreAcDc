package exception;

/**
 * Eccezione durante la generazione del codice.
 * 
 * @author Luca Iacobucci, 20035727
 * @author Andrija Jovic, 20034244
 */
public class CodeGeneretorException extends Exception {

    /**
	 * Costruttore per CodeGeneratorException con messaggio.
	 * 
	 * @param message il messaggio dell'eccezione
	 */
    public CodeGeneretorException(String message) {
        super(message);
    }
}
