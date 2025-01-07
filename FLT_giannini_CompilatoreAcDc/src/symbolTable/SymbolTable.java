package symbolTable;

import ast.LangType;

public class SymbolTable {

    public static class Attributes{
        private LangType tipo;
        // .....
        
        }
        public static void init() {
        // .....
        
        }
        public static boolean enter(String id, Attributes entry) {
        // ......
        return false;
        }
        public static Attributes lookup(String id) {
        //......
        return null;
        }
        public static String toStr() {
            return null;
        // ......
        }
        public static int size() {
            return 0;
        // ......
        }
        

}
