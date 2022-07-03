package core;

import org.jgrapht.graph.DefaultDirectedGraph;

import nodes.AbstractNode;
import nodes.Node;

@SuppressWarnings("serial")
public class AbstractGraph extends DefaultDirectedGraph <AbstractNode, Edge> {
	private AbstractNode producer, consumer;
	
	public AbstractGraph(Class<Edge> edgeClass) {
		super(edgeClass);
	}

	public AbstractNode getProducer() {
		return producer;
	}

	public void setProducer(AbstractNode producer) {
		this.producer = producer;
	}

	public AbstractNode getConsumer() {
		return consumer;
	}

	public void setConsumer(AbstractNode consumer) {
		this.consumer = consumer;
	}
	
	public String getNodeDescription(Node n) {
		for (AbstractNode an : this.vertexSet()) {
			String description = an.getNodeDescription(n);
			if (description != null) {
				return description;
			}
		}
		return null;
	}
	

}
