package nodes;

public abstract class Node {
	private static int idCounter = 0;
	private int id;
	private String name;
	private AbstractNode abstractNode;
	
	public AbstractNode getAbstractNode() {
		return abstractNode;
	}

	public void setAbstractNode(AbstractNode abstractNode) {
		this.abstractNode = abstractNode;
	}

	private float latency;
	
	public Node(float latency) {
		this.id = idCounter++;
		this.latency = latency;
	}
	
	public Node() {
		this(0);
	}
	public int GetId() {
		return this.id;
	}
	@Override
	public String toString() {
		String name = this.getName();
		if (name != null) {
			return name;
		}
		return String.valueOf(id);
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public float getLatency() {
		return latency;
	}

	public void setLatency(float latency) {
		this.latency = latency;
	}
}
