package ast;

public class NodeBinOp extends NodeExpr {
	
	private LangOper op;
	private NodeExpr left;
	private NodeExpr right;
	
	public NodeBinOp(LangOper op, NodeExpr left, NodeExpr right) {
		this.op = op;
		this.left = left;
		this.right = right;
	}

	public LangOper getOp() {
		return op;
	}

	public NodeExpr getLeft() {
		return left;
	}

	public NodeExpr getRight() {
		return right;
	}

	@Override
	public String toString() {
		return "NodeBinOp -> [op=" + op + ", left=" + left + ", right=" + right + "];\t";
	}

	@Override
	public TypeDescriptor calcResType() {
		
		TypeDescriptor leftTD = left.calcResType();//descrittore di tipo della espressione sinistra
		TypeDescriptor rightTD = right.calcResType();//descrittore di tipo della espressione destra

		// se i due descrittori sono di tipo int allora il risultato è int
		if ( leftTD.getTipo() == TipoTD.INT && rightTD.getTipo() == TipoTD.INT ) 
			return new TypeDescriptor(TipoTD.INT);

		// se uno dei due descrittori è di tipo float allora il risultato è float
		if ( (leftTD.getTipo() == TipoTD.INT && rightTD.getTipo() == TipoTD.FLOAT) ||  (leftTD.getTipo() == TipoTD.FLOAT && rightTD.getTipo() == TipoTD.FLOAT)  || (leftTD.getTipo() == TipoTD.FLOAT && rightTD.getTipo() == TipoTD.INT) )
			return new TypeDescriptor(TipoTD.FLOAT);
		
		// se uno dei due descrittori è di tipo errore allora il risultato è errore
		if ( (leftTD.getTipo() == TipoTD.ERROR || rightTD.getTipo() == TipoTD.ERROR) ){

			StringBuilder errorMessage = new StringBuilder() ;
			
			if (leftTD.getTipo() == TipoTD.ERROR) {
				errorMessage.append("Errore da leftTD") ;
			}else if(leftTD.getTipo() == TipoTD.ERROR && rightTD.getTipo() == TipoTD.ERROR) {
				errorMessage.append(" - Errore da rightTD") ;
			}
			else if (rightTD.getTipo() == TipoTD.ERROR) {
				errorMessage.append("Errore da rightTD") ;
			}
			return new TypeDescriptor(TipoTD.ERROR, errorMessage.toString());
		}
		return new TypeDescriptor(TipoTD.ERROR, "Errore da NodeBinOp (calcResType)");
}

	@Override
	public String calcCodice() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'calcCodice'");
	}

}

