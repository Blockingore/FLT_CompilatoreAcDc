package ast;

public class NodeCost extends NodeExpr {
	private String value;
	private LangType type;
	
	public NodeCost(String value, LangType type) {
		this.value = value;
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public LangType getType() {
		return type;
	}

	@Override
	public String toString() {
		return "NodeCost -> [value=" + value + ", type=" + type + "];\t";
	}

	@Override
	public TypeDescriptor calcResType() {
		return null;
	}

	@Override
	public String calcCodice() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'calcCodice'");
	}
	
}
