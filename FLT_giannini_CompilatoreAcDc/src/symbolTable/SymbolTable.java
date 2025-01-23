package symbolTable;

import java.util.HashMap;

import ast.LangType;

/**
 * Implementa la Sybol Table, che contiene i nomi utilizzati.
 *  
 * @author Luca Iacobucci, 20035727
 * @author Andrija Jovic, 20034244
 */
public class SymbolTable {

    public static HashMap<String, Attributes>symbolMap;
        
    /**
	 * Implementa gli attributi che andranno inseriti nella Symbol Table.
	 */
    public static class Attributes{

        private char registro;
        private LangType tipo;

        public Attributes(LangType tipo){
            this.tipo = tipo;
        }

        public LangType getTipo(){
            return this.tipo;
        }

        public char getRegistro() {
            return registro;
        }

        public void setRegistro(char registro) {
            this.registro = registro;
        }

        public void setTipo(LangType tipo) {
            this.tipo = tipo;
        }
        
    }
    
    /**
	 * Inizializza l'hashmap della Symbol Table.
	 */
    public static void init() {
        symbolMap = new HashMap<>();
    }

    /**
	 * Inserisce un attributo nella Symbol Table.
	 * 
	 * @param id l'id da inserire
	 * @param entry l'attributo da inserire
	 * @return true se l'attributo è stato inserito correttamente, false altrimenti
	 */
    public static boolean enter(String id, Attributes entry) {
    
        if (symbolMap.containsKey(id)) {
            return false;
        } else {
            symbolMap.put(id, entry);
            return true;
        }
    }

    /**
	 * Restituisce un attributo.
	 * 
	 * @param id l'id dell'attributo da cercare
	 * @return l'attributo cercato
	 */
    public static Attributes lookup(String id) {
        return symbolMap.get(id);
    }

    /**
	 * Restituisce la size della Symbol Table.
	 * 
	 * @return size della Symbol Table.
	 */
    public static int size() {
        return symbolMap.size();
    }
        
}
