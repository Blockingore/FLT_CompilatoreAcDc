package exception;

public class LexicalException extends Exception {
   
	// Costruttore predefinito
    public LexicalException() {
        super("Errore lessicale non specificato.");
    }

    // Costruttore con messaggio personalizzato
    public LexicalException(String message) {
        super(message);
    }

}
