package ast;

public class NodeDeref extends NodeExpr{

	private NodeId id;
	
	public NodeDeref(NodeId id) {
		this.id = id;
	}

	public NodeId getId() {
		return id;
	}

	@Override
	public String toString() {
		return "NodeDeref -> [id=" + id + "];\t";
	}

	@Override
	public TypeDescriptor calcResType() {
		return new TypeDescriptor(id.calcResType().getTipo());
	}

	@Override
	public String calcCodice() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'calcCodice'");
	}

}
