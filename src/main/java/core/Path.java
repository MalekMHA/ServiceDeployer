package core;

import java.util.ArrayList;
import java.util.List;

import org.jgrapht.Graph;

import nodes.Node;

public class Path {
	private List<Node> nodes;
	private Graph<Node, Edge> graph;
	
	public Path(Graph<Node,Edge> graph) {
		this.nodes = new ArrayList<>();
		this.graph = graph;
	}
	
	public float getLatency() {
		float latency = 0;
		for (int i = 0; i < this.getNodes().size() - 1; i++) {
			latency += this.graph.getEdge(this.getNodes().get(i), this.getNodes().get(i+1)).getLatency();
		}
		return latency;
	}
	
	public float getVulnerability() {
		float vulerability = 0;
		for (int i = 0; i < this.getNodes().size() - 1; i++) {
			vulerability += this.graph.getEdge(this.getNodes().get(i), this.getNodes().get(i+1)).getVulnerability();
		}
		return vulerability;
	}
	
	public void addNode(Node n) {
		nodes.add(n);
	}
	public List<Node> getNodes(){
		return this.nodes;
	}
	public Path add(Path otherPath) {
		Path newPath = new Path(this.graph);
		for (Node n : this.getNodes()) {
			newPath.addNode(n);
		}
		for (Node n : otherPath.getNodes()) {
			newPath.addNode(n);
		}
		return newPath;
	}
	
	public void printVulnerabilities() {
		for (int i = 0; i < this.getNodes().size() - 1; i++) {
			Node n = this.getNodes().get(i);
			float vulerability = this.graph.getEdge(this.getNodes().get(i), this.getNodes().get(i+1)).getVulnerability();
			System.out.print("Leaving " + n.getAbstractNode().getNodeDescription(n) + " with v: " + vulerability + ";");
		}
		System.out.println();
	}
	
	@Override
	public String toString() {
		String result = "";
		for (Node n : this.getNodes()) {
			result = result + n.getAbstractNode().getNodeDescription(n) + ";";
		}
		return result;
	}
}
