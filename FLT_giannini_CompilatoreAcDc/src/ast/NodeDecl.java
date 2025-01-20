package ast;

import visitor.IVisitor;

public class NodeDecl extends NodeDecSt {

	private NodeId id;
	private LangType type;
	private NodeExpr init;
	
	public NodeDecl(NodeId id, LangType type, NodeExpr init) 
	{
		this.id = id;
		this.type = type;
		this.init = init;
	}

	public NodeId getId() {
		return id;
	}

	public LangType getType() {
		return type;
	}

	public NodeExpr getInit() {
		return init;
	}

	@Override
	public String toString() {
		return "\tNodeDecl -> [id=" + id.toString() + ", type=" + type + ", init=" + init + "];\t";
	}

	@Override
	public String calcCodice() {
		if(init == null)
			return "";
		else
		return init.calcCodice() + " s" + id.calcCodice();
	}

	@Override
	public void accept(IVisitor visitor) {
		visitor.visit(this);
	}


}
