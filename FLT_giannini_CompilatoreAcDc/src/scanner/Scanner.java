package scanner;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PushbackReader;
import java.util.ArrayList;
import java.util.HashMap;

import token.*;

public class Scanner {
	final char EOF = (char) -1; 
	private int riga;
	private PushbackReader buffer;

	
	// skpChars: insieme caratteri di skip (include EOF) e inizializzazione
	private ArrayList<Character> skpChars;
	// letters: insieme lettere 
	private ArrayList<Character> letters;
	// digits: cifre 
	private ArrayList<Character> digits;

	// operTkType: mapping fra caratteri '+', '-', '*', '/'  e il TokenType corrispondente
	HashMap<Character , TokenType> operTkType;
	
	// delimTkType: mapping fra caratteri '=', ';' e il e il TokenType corrispondente
	HashMap<Character, TokenType > delimTkType;

	// keyWordsTkType: mapping fra le stringhe "print", "float", "int" e il TokenType  corrispondente
	HashMap<String, TokenType  > keyWordsTkType;
	
	
	private Token nextTk = null;
	
	public void fillSkippers() {
		skpChars = new ArrayList<Character>();
		skpChars.add(' ');
		skpChars.add('\n');
		skpChars.add('\t');
		skpChars.add('\r');
		skpChars.add(EOF);		 
	}
	
	public void fillLetters() {
		letters = new ArrayList<Character>();
		
		for(char c = 'a'; c <= 'z'; c++ ) {
			letters.add(c);
		}
		for(char c = 'A'; c <= 'Z'; c++ ) {
			letters.add(c);
		}
	
	}
	
	public void fillDigits() {
		digits = new ArrayList<Character>();
		
		for(char i = '0'; i <= '9'; i++) {
			digits.add(i);
		}
	}	
	
	public Scanner(String fileName) throws FileNotFoundException {

		this.buffer = new PushbackReader(new FileReader(fileName));
		riga = 1;
		// inizializzare campi che non hanno inizializzazione
		fillSkippers(); // inizializzo skippers
		fillDigits();  //inizializzo digits
		fillLetters(); //inizializzo letters
		
		operTkType = new HashMap<Character, TokenType>();
		keyWordsTkType = new HashMap< String, TokenType>();
		delimTkType = new HashMap<Character, TokenType>();
		

		keyWordsTkType.put( "float", TokenType.TYFLOAT  );
		keyWordsTkType.put( "int", TokenType.TYINT  );
		keyWordsTkType.put( "print", TokenType.PRINT  );
		
		operTkType.put( '/', TokenType.DIV  );
		operTkType.put( '+', TokenType.PLUS  );
		operTkType.put( '-', TokenType.MINUS  );
		operTkType.put( '*', TokenType.TIMES  );
		
		delimTkType.put( '=', TokenType.ASSIGN  );
		delimTkType.put( ';', TokenType.SEMI  );
		
		
	}

	public Token nextToken() throws LexicalException {
		
		if(nextTk != null) {
			Token t = nextTk;
			nextTk = null;
			return t;
		}
		
			
		// nextChar contiene il prossimo carattere dell'input (non consumato).
		char nextChar = peekChar();
			
		// Avanza nel buffer leggendo i carattere in skipChars
		// incrementando riga se leggi '\n'.
		while(skpChars.contains(nextChar)) {
			
			if(nextChar == '\n') {
				riga++;
			}			
			
			// Se raggiungi la fine del file ritorna il Token EOF
			if(nextChar == EOF) {
				readChar();
				return new Token(TokenType.EOF, riga);
			}
			
			readChar();
	
			nextChar = peekChar();
		}
			
		// Se nextChar e' in letters
		// return scanId()
		// che deve generare o un Token ID o parola chiave
		
		if(letters.contains(nextChar)) {
			return scanId(nextChar);
		}

		// Se nextChar e' o in operators oppure delimitatore
		// ritorna il Token associato con l'operatore o il delimitatore
		// Attenzione agli operatori di assegnamento!

		if(operTkType.containsKey(nextChar)){
			readChar();
			if(peekChar() == '=') {
				readChar();
				return new Token(TokenType.OP_ASSIGN,riga, nextChar + "=");
			}
			return new Token(operTkType.get(nextChar), riga );
		} 
		
		
		
		// Se nextChar e' ; o = 
		// ritorna il Token associato
		
		if( delimTkType.containsKey(nextChar)) {
			readChar();
			return new Token(delimTkType.get(nextChar), riga );
		}
		
		
		// Se nextChar e' in digits
		// return scanNumber()
		// che legge sia un intero che un float e ritorna il Token INUM o FNUM
		// i caratteri che leggete devono essere accumulati in una stringa
		// che verra' assegnata al campo valore del Token

		if(digits.contains(nextChar)) {
			return scanNumber(nextChar);
		}
		
		
		// Altrimenti il carattere NON E' UN CARATTERE LEGALE sollevate una
		// eccezione lessicale dicendo la riga e il carattere che la hanno
		// provocata.
		readChar();
		throw new LexicalException("il carattere: '" + nextChar  + "' non e' un carattere ammesso");
		
		}

	public Token peekToken() throws LexicalException {
		
		if(nextTk == null) {
			nextTk = nextToken();
		}
		return nextTk;
	}

	private Token scanNumber(char nextChar) throws LexicalException {
		
	    StringBuilder numero = new StringBuilder(); // Accumula i caratteri del numero
	   
		boolean isFloat = false; // Per distinguere tra INT e FLOAT
	    int numbCounter = 0;
	    
	    while (nextChar == '0') {
	    	if(numero.length() < 1)
	        	numero.append('0'); // Manteniamo uno zero
	    	readChar(); // Consuma lo zero
	        nextChar = peekChar();
	        	        if (nextChar == '.') { // Caso di "0." per float
	        		break;
	        	}
	    }
	   
	    while (Character.isDigit(nextChar) || nextChar == '.') { // cicliamo finchè il prossimo carattere è un numero o un '.'
	    	
	    	if (nextChar == '.') {  
	            if (isFloat) { // se legge più di un punto 
	            	readChar();
	                throw new LexicalException("Numero non valido (doppio punto) alla riga " + riga); // Se troviamo un secondo punto, è un errore lessicale
	            }
	            isFloat = true; // Indichiamo che il numero è un FLOAT
	        }
	        
	        if(isFloat && nextChar != '.') {
	        	numbCounter++;
	        }
	        
	        numero.append(nextChar); // Aggiungiamo il carattere al numero
	        readChar(); // Consumiamo il carattere
	        nextChar = peekChar(); // Leggiamo il prossimo carattere
	        /*
	        //non accetto 5. come float;
	        if(isFloat && !Character.isDigit(nextChar)) {
            	throw new LexicalException("Numero non valido alla riga " + riga + "; non c'è niente dopo il punto");
            }*/
	    }//fine while
	    
	    //accetto 5. come float e lo salvo come 5.0;
	    if(numero.length() > 0 && isFloat && numero.charAt(numero.length()-1) == '.') { // se l'ultimo elemento del numero è '.'
	    	numero.append('0');
	    	numbCounter = 1;
	    }
	    
	    if(numero.charAt(0) == '0' && numero.length() > 1 && numero.charAt(1) != '0' && numero.charAt(1) != '.'  ) {
	    	numero.deleteCharAt(0);	    	
	    }
	    
	    if( numbCounter > 5) {
	    	throw new LexicalException("Numero non valido alla riga " + riga + "; ci sono più di 5 cifre dopo il '.'");
	    }	  
	    
	    if (isFloat) {
	        return new Token(TokenType.FLOAT, riga, numero.toString());
	    } else {	
	        return new Token(TokenType.INT, riga, numero.toString());
	    }
	}
	
	private Token scanId(char nextChar) throws LexicalException {
	    // Inizializziamo un oggetto StringBuilder per costruire dinamicamente la stringa dell'identificatore
	    StringBuilder stringa_di_appoggio = new StringBuilder();

	    // Ciclo che continua fino a quando il carattere successivo fa parte di letters
	    while (letters.contains(nextChar)) {
	        // Aggiunge il carattere corrente alla stringa in costruzione
	        stringa_di_appoggio.append(nextChar);
	        // Legge e consuma il carattere corrente dal buffer
	        readChar();
	        // Ottiene il prossimo carattere dal buffer senza consumarlo
	        nextChar = peekChar();
	    }

	    // Converte la stringa costruita in Stringa e verifica se corrisponde a una parola chiave
	    if (keyWordsTkType.get(stringa_di_appoggio.toString()) != null) {
	        // Se è una parola chiave(print,int,float), restituisce il Token corrispondente con il tipo della parola chiave
	        return new Token(keyWordsTkType.get(stringa_di_appoggio.toString()), riga);
	    }

	    // Se non è una parola chiave, restituisce un Token di tipo ID con la stringa costruita
	    return new Token(TokenType.ID, riga, stringa_di_appoggio.toString());
	}

	private char readChar() throws LexicalException {
		try {
			return ((char) this.buffer.read());
		} catch (IOException e) {
			throw new LexicalException("Errore durante la lettura del carattere");
		}
	}

	private char peekChar() throws LexicalException {
		try {
			char c = (char) buffer.read();
			buffer.unread(c);
			return c;
		} catch (IOException e) {
			throw new LexicalException("Errore durante la lettura del carattere");
		}
		
	}
}