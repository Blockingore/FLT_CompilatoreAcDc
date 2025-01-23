package token;

/**
 * Implementa i Token utilizzati durante l'analisi lessicale 
 *  
 * @author Luca Iacobucci, 20035727
 * @author Andrija Jovic, 20034244
 */
public class Token {

	private int riga;
	private TokenType tipo;
	private String val;

	/**
	 * Costruttore per Token.
	 * 
	 * @param tipo il tipo assegnato al Token
	 * @param riga la riga assegnata al Token
	 * @param val  il valore assegnato al Token
	 */
	public Token(TokenType tipo, int riga, String val) {
		this.tipo = tipo;
		this.riga = riga;
		this.val = val;
	}
	
	/**
	 * Costruttore per Token.
	 * 
	 * @param tipo il tipo assegnato al Token
	 * @param riga la riga assegnata al Token
	 */
	public Token(TokenType tipo, int riga) {
		this.tipo = tipo;
		this.riga = riga;
	}

    
	/**
	 * Restituisce la riga assegnata al Token.
	 * 
	 * @return la riga
	 */
	public int getRiga() {
		return riga;
	}

	/**
	 * Restituisce il tipo assegnato al Token.
	 * 
	 * @return il tipo
	 */
	public TokenType getTipo() {
		return tipo;
	}

	/**
	 * Restituisce il valore assegnato al Token.
	 * 
	 * @return il valore
	 */
	public String getVal() {
		return val;
	}

	@Override
	public String toString() {
		if(val != null) {
			return this.tipo + ", riga: " + this.riga + ", " + this.val;			
		}
		else {
			return this.tipo + ", riga: " + this.riga ;
		}
	}
}
