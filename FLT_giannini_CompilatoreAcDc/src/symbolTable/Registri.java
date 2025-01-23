package symbolTable;

import java.util.ArrayList;

import exception.*;

/**
 * Implementa i Registri, che servono a memorizzare le variabili. 
 *  
 * @author Luca Iacobucci, 20035727
 * @author Andrija Jovic, 20034244
 */
public class Registri {
    
    static ArrayList<Character> caratteri;

    /**
	 * Inizializza i registri, uno per lettera dell'alfabeto.
	 */
    public static void init() {
        caratteri = new ArrayList<Character>();
        for (char c = 'a'; c <= 'z'; c++) {
            caratteri.add(c);
        }
    }

    /**
	 * Restituisce un nuovo registro.
	 * 
	 * @return un nuovo registro
	 * @throws CodeGeneratorException se non ci sono più registri diponibili
	 */
    public static char newRegister() throws CodeGeneretorException {
        if(caratteri.isEmpty()){
            throw new CodeGeneretorException("impossibile aggiungere altri registri");
        }
        return caratteri.remove(0);
    }
}
