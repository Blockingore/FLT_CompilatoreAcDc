package symbolTable;

import java.util.ArrayList;

import exception.*;

public class Registri {
    
    static ArrayList<Character> caratteri = new ArrayList<Character>();

    public static void init() {
        caratteri.removeAll(caratteri);
        for (char c = 'a'; c <= 'z'; c++) {
            caratteri.add(c);
        }
    }

    public static char newRegister() throws CodeGeneretorException {
        if(caratteri.isEmpty()){
            throw new CodeGeneretorException("impossibile aggiungere altri registri");
        }
        return caratteri.remove(0);
    }
}
