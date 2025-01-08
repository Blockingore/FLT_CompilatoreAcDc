package symbolTable;

import java.lang.invoke.TypeDescriptor;
import java.util.HashMap;

import ast.LangType;

public class SymbolTable {

    public static HashMap<String, Attributes>symbolMap;
        
        public static class Attributes{
            
            private LangType tipo;

            public Attributes(LangType tipo){
                this.tipo = tipo;
            }

            public LangType getTipo(){
                return this.tipo;
            }
        }
    
    //crea e inizializza la symbolTable 
    public static void init() {
        symbolMap = new HashMap<>();
    }

    //inserisce nella simbolTable se non c'è un'altra occorrenza
    public static boolean enter(String id, Attributes entry) {
    
        if (symbolMap.containsKey(id)) {
            return false;
        } else {
            symbolMap.put(id, entry);
            return true;
        }
    }

    //se l'id è contenuto 
    public static Attributes lookup(String id) {
        return symbolMap.get(id);
    }

    public static String toStr() {

        StringBuilder sb = new StringBuilder();

        for (String key : symbolMap.keySet()) { //vedi bene keyset()
            sb.append(key).append(": ").append(symbolMap.get(key).getTipo()).append("\n");
        }
        return sb.toString();

    }
    public static int size() {
        return symbolMap.size();
    }
        
}
