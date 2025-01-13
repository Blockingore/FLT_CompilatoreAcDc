package ast;

public class NodeId extends NodeAST {
	private String name;
	
	public NodeId(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return "NodeId -> [names=" + name + "]; \t";
	}


	//OK MESSO A CASO
	@Override
	public TypeDescriptor calcResType(){
		return new TypeDescriptor(TipoTD.OK);		
	}

	@Override
    public String calcCodice(){

		return null;
	}
}