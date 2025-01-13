package ast;


public class NodeAssign extends NodeStm {
	private NodeId id;
	private NodeExpr expr;
	
	public NodeAssign(NodeId id, NodeExpr expr) {
		this.id = id;
		this.expr = expr;
	}

	public NodeId getId() {
		return id;
	}

	public NodeExpr getExpr() {
		return expr;
	}

	@Override
	public String toString() {
		return "NodeAssign -> [id=" + id + ", expr=" + expr + "];\t";
	}

	@Override
	public TypeDescriptor calcResType() {

		TypeDescriptor idType = id.calcResType();
		TypeDescriptor exprType = expr.calcResType();
		
	
		if (idType.getTipo() != exprType.getTipo() ) {
			
			if (idType.getTipo() == TipoTD.FLOAT && exprType.getTipo() == TipoTD.INT) {
				return new TypeDescriptor(TipoTD.FLOAT);
			}

			return new TypeDescriptor(TipoTD.ERROR, "Impossibile assegnare un valore di tipo " + exprType + " a una variabile di tipo " + idType);
		}


		return new TypeDescriptor(idType.getTipo());
	}

	@Override
	public String calcCodice() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'calcCodice'");
	}

	
}
