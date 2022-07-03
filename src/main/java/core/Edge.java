package core;
import org.jgrapht.graph.DefaultWeightedEdge;

public class Edge extends DefaultWeightedEdge {
	private float latency;
	private float vulnerability;
	private boolean link;
	
	public Edge(float latency, float vulnerability, boolean link) {
		this.latency = latency;
		this.vulnerability = vulnerability;
		this.link = link;
	}

	public Edge(boolean link) {
		this(0f,0f, link);
	}
	
	public boolean isLink() {
		return link;
	}

	public void setLink(boolean link) {
		this.link = link;
	}

	public float getLatency() {
		return latency;
	}

	public void setLatency(float latency) {
		this.latency = latency;
	}

	public float getVulnerability() {
		return this.vulnerability;
	}

	public void setVulnerability(float vulnerability) {
		this.vulnerability = vulnerability;
	}
	
	@Override
	public String toString() {
		return "(" + this.getSource() + " : " + this.getTarget() + ". L: " + this.getLatency() + ". V: " + this.getVulnerability() + ")";
	}
}
