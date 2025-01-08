package ast;

import symbolTable.IVisitor;

public class NodeId extends NodeAST {
	private String name;
	
	public NodeId(String name) {
		this.name = name;
	}
	public String getN() {
		return name;
	}

	@Override
	public String toString() {
		return "NodeId -> [names=" + name + "]; \t";
	}


	@Override
	public TypeDescriptor calcResType(){

		return null;
	}

	@Override
    public String calcCodice(){

		return null;
	}
	@Override
	public void accept(IVisitor visitor) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'accept'");
	}
}