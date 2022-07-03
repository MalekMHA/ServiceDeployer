package core;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.graph.DirectedWeightedMultigraph;

import nodes.DecryptNode;
import nodes.EncryptNode;
import nodes.EncryptionAssociatedNode;
import nodes.InputNode;
import nodes.AbstractNode;
import nodes.Node;
import nodes.OutputNode;
import nodes.ProcessNode;

/*
 * Complexity: N * 2K
 */
public class NetworkExpander {
	public static DirectedWeightedMultigraph<Node, Edge> generateExpandedGraph(Graph<AbstractNode, Edge> abstracted, int dataSize) {
		DirectedWeightedMultigraph<Node, Edge> result = new DirectedWeightedMultigraph<>(Edge.class);
		
		// Loop over each abstracted node and generate its expanded node cluster.
		Set<AbstractNode> nodes = abstracted.vertexSet();
		for (AbstractNode n : nodes) {
			// Processing Node
			Node processNode = new ProcessNode(); n.setProcessNode(processNode);
			processNode.setAbstractNode(n);
			result.addVertex(processNode);
			
			// Rest of the nodes
			for (EncryptionScheme encryptionScheme : n.getEncryptionSchemes()) {
				Node encryptNode = new EncryptNode(encryptionScheme); result.addVertex(encryptNode); n.addEncryptNode(encryptionScheme, encryptNode);
				Node decryptNode = new DecryptNode(encryptionScheme); result.addVertex(decryptNode); n.addDecryptNode(encryptionScheme, decryptNode);
				Node inputNode = new InputNode(encryptionScheme); result.addVertex(inputNode); n.addInputNode(encryptionScheme, inputNode);
				Node outputNode = new OutputNode(encryptionScheme); result.addVertex(outputNode); n.addOutputNode(encryptionScheme, outputNode);
				encryptNode.setAbstractNode(n);
				decryptNode.setAbstractNode(n);
				inputNode.setAbstractNode(n);
				outputNode.setAbstractNode(n);
				
				// Add edge between process node and encrypt node.
				Edge processToEncryptEdge = new Edge(false); processToEncryptEdge.setLatency(n.getEncryptionSchemeSpeed(encryptionScheme) * dataSize);
				result.addEdge(processNode, encryptNode, processToEncryptEdge);
				result.setEdgeWeight(processToEncryptEdge, processToEncryptEdge.getLatency());
				//System.out.println(processToEncryptEdge.getLatency());
				
				// Add edge between input node and decrypt node.
				Edge inputToDecryptEdge = new Edge(false); inputToDecryptEdge.setLatency(n.getEncryptionSchemeSpeed(encryptionScheme) * dataSize);
				result.addEdge(inputNode, decryptNode, inputToDecryptEdge);
				result.setEdgeWeight(inputToDecryptEdge, inputToDecryptEdge.getLatency());
				//System.out.println(inputToDecryptEdge.getLatency());
				
				// Add edge between decrypt node and process node.
				Edge decryptToProcessEdge = new Edge(false); decryptToProcessEdge.setLatency(n.getProcessor().GetProcessingLatency(dataSize));
				result.addEdge(decryptNode, processNode, decryptToProcessEdge);
				result.setEdgeWeight(decryptToProcessEdge, decryptToProcessEdge.getLatency());
				// System.out.println(decryptToProcessEdge.getLatency());
				
				// Add edge between encrypt node and output node.
				Edge encryptToOutputEdge = new Edge(false); encryptToOutputEdge.setLatency(0); encryptToOutputEdge.setVulnerability(encryptionScheme.getVulnerability());
				result.addEdge(encryptNode, outputNode, encryptToOutputEdge);
				result.setEdgeWeight(encryptToOutputEdge, encryptToOutputEdge.getLatency());
				
				// Add edge between input node and output node.
				Edge inputToOutputEdge = new Edge(false); inputToOutputEdge.setLatency(0); inputToOutputEdge.setVulnerability(encryptionScheme.getVulnerability());
				result.addEdge(inputNode, outputNode, inputToOutputEdge);
				result.setEdgeWeight(inputToOutputEdge, inputToOutputEdge.getLatency());
				// System.out.println(inputToOutputEdge.getLatency());
			}
			
		
			// Add edges between decrypt nodes and encrypt nodes.
			for (Node decryptNode : n.getDecryptNodes().values()) {
				EncryptionAssociatedNode decryptNodeCast = (EncryptionAssociatedNode) decryptNode;
				for (Node encryptNode : n.getEncryptNodes().values()) {
					EncryptionAssociatedNode encryptNodeCast = (EncryptionAssociatedNode) encryptNode;
					
					if (decryptNodeCast.getEncryptionScheme() != encryptNodeCast.getEncryptionScheme()) {
						Edge edge = new Edge(false); edge.setLatency(n.getEncryptionSchemeSpeed(encryptNodeCast.getEncryptionScheme()) * dataSize);
						result.addEdge(decryptNode, encryptNode, edge);
						result.setEdgeWeight(edge, edge.getLatency());
						// System.out.println(edge.getLatency());
					}
				}
			}
		}
		
		//Add edges between edge nodes
		Set<Edge> edges = abstracted.edgeSet();
		for (Edge e : edges) {
			AbstractNode from = abstracted.getEdgeSource(e);
			AbstractNode to = abstracted.getEdgeTarget(e);
			
			for (Node outputNode : from.getOutputNodes().values()) {
				for (Node inputNode : to.getInputNodes().values()) {
					if (((EncryptionAssociatedNode) outputNode).getEncryptionScheme() == ((EncryptionAssociatedNode) inputNode).getEncryptionScheme()) {
						Edge edge = new Edge(true);
						edge.setLatency(e.getLatency() * dataSize);
						edge.setVulnerability(e.getVulnerability());
						result.addEdge(outputNode, inputNode, edge);
						result.setEdgeWeight(edge, edge.getLatency());
						// System.out.println(edge.getLatency());
					}
				}
			}
		}
		
		return result;
		
	}
}
