package scanner;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PushbackReader;
import java.util.ArrayList;
import java.util.HashMap;

import exception.LexicalException;
import token.*;

/**
 * Implementa uno scanner che legge i caratteri da un file e
 * restituisce dei token rappresentanti quei caratteri.
 * 
 * @author Luca Iacobucci, 20035727
 * @author Andrija Jovic, 20034244
 */
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

	/*
	 * Rimepie l'arraylist di caratteri che sono da skippare
	 */
	public void fillSkippers() {
		skpChars = new ArrayList<Character>();
		skpChars.add(' ');
		skpChars.add('\n');
		skpChars.add('\t');
		skpChars.add('\r');
		skpChars.add(EOF);		 
	}
	
	/*
	 * Rimepie l'arraylist di lettere
	 */
	public void fillLetters() {
		letters = new ArrayList<Character>();
		
		for(char c = 'a'; c <= 'z'; c++ ) {
			letters.add(c);
		}
		for(char c = 'A'; c <= 'Z'; c++ ) {
			letters.add(c);
		}
	}
	
	/*
	 * Rimepie l'arraylist di caratteri che sono da skippare
	 */
	public void fillDigits() {
		digits = new ArrayList<Character>();
		
		for(char i = '0'; i <= '9'; i++) {
			digits.add(i);
		}
	}	
	
	/**
	 * Costruisce dello scanner e lo inizializza
	 * @param fileName file da dove leggere i caratteri
	 * @return il carattere letto
	 * @throws LexicalException se non è possibile leggere dal buffer
	 */
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

	/**
	 * Ritorna il prossimo token riconosciuto, consumandolo.
	 * 
	 * @return il token riconosciuto
	 * @throws LexicalException se il carattere non è ammesso
	 *                          
	 */
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
			return new Token(operTkType.get(nextChar), riga, String.valueOf(nextChar)  );
		} 
		
		
		
		// Se nextChar e' ; o = 
		// ritorna il Token associato
		
		if( delimTkType.containsKey(nextChar)) {
			readChar();
			return new Token(delimTkType.get(nextChar), riga, String.valueOf(nextChar));
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

	/**
	 * Ritorna il prossimo token riconosciuto, senza consumarlo.
	 * 
	 * @return il token riconosciuto
	 * @throws LexicalException se il carattere non è ammesso
	 */
	public Token peekToken() throws LexicalException {
		
		if(nextTk == null) {
			nextTk = nextToken();
		}
		return nextTk;
	}

	/**
	 * Legge eventuali caratteri riconosciuti come numeri e
	 * costruisce il numero finale ritornando il token (INT o FLOAT) corrispondente
	 * 
	 * @param il carattere letto
	 * @return il token corrispondente con il valore letto
	 * @throws LexicalException se il numero è in formato non ammesso
	 */
	private Token scanNumber(char nextChar) throws LexicalException {
		
	    StringBuilder numero = new StringBuilder(); // Accumula i caratteri del numero
	   
		boolean isFloat = false; 
	    int numbCounter = 0;
	    
		//ciclo per eliminare eventuali zeri presenti all'inizio del numero tranne uno (potrebbe essere 0.xxx)
	    while (nextChar == '0') {
	    	if(numero.length() < 1){
				numero.append('0'); //se è il primo che trovo lo mantengo
			}
	    	readChar(); 
	        nextChar = peekChar();
			if (nextChar == '.'){ // Caso di "0." per float, esco dal ciclo
				break;
			}
	    }
	   
	    while (Character.isDigit(nextChar) || nextChar == '.') { // cicliamo finchè il prossimo carattere è un numero o un '.'
	    	
	    	if (nextChar == '.') {  
	            if (isFloat) { // se legge più di un punto 
	            	readChar();
	                throw new LexicalException("Numero non valido (doppio punto) alla riga " + riga);
	            }
	            isFloat = true; // Indichiamo che il numero è un FLOAT
	        }
	        
	        if(isFloat && nextChar != '.') {
	        	numbCounter++;
	        }
	        
	        numero.append(nextChar); // Aggiungo il carattere al numero
	        readChar(); 
	        nextChar = peekChar();

			/*
	        //non accetto 5. come float;
	        if(isFloat && !Character.isDigit(nextChar) && numero.charAt(numero.length()-1) == '.') {
            	throw new LexicalException("Numero non valido alla riga " + riga + "; non c'è niente dopo il punto");
            }
			*/
	    }//fine while
	    
	    //accetto 5. come float e lo salvo come 5.0;
	    if(numero.length() > 0 && isFloat && numero.charAt(numero.length()-1) == '.') { // se l'ultimo elemento del numero è '.'
	    	numero.append('0');
	    	numbCounter = 1;
	    }

	    //controllo che non ci sia uno zero iniziale ad eccezione di 0.xxx
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
	
	/**
	 * Legge eventuali caratteri riconosciuti come lettere, 
	 * costruisce la stringa finale ritornando il token corrispondente
	 * 
	 * @return il token corrispondente con il valore letto
	 * @throws LexicalException se trova un carattere non ammesso
	 */
	private Token scanId(char nextChar) throws LexicalException {

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

	/**
	 * Legge un carattere consumandolo
	 * 
	 * @return il carattere letto
	 * @throws LexicalException se non è possibile leggere dal buffer
	 */
	private char readChar() throws LexicalException {
		try {
			return ((char) this.buffer.read());
		} catch (IOException e) {
			throw new LexicalException("Errore durante la lettura del carattere");
		}
	}

	/**
	 * Legge un carattere senza consumarlo
	 * 
	 * @return il carattere letto
	 * @throws LexicalException se non è possibile leggere dal buffer
	 */
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