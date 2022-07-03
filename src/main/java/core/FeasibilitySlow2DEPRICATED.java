package core;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.jgrapht.graph.DirectedWeightedMultigraph;
import nodes.AbstractNode;
import nodes.Node;

public class FeasibilitySlow2DEPRICATED {

	public static void main(String[] args) {
		
		String file = "traversalvGapSlow.csv";
		FileWriter fw;
			try {
				fw = new FileWriter(file);
				PrintWriter pw = new PrintWriter(fw, true); 
				
				for (int vGap = 0; vGap <= 1000; vGap += 100) {
					int size = 21;
					List<Float> vs = new ArrayList<>();
					for (int sample = 0; sample < 50; sample++) {
						long startTime = System.nanoTime();
						AbstractGraph abstracted = CreateRandomNetwork(size, vGap);
						DirectedWeightedMultigraph<Node, Edge> expandedGraph = NetworkExpander.generateExpandedGraph(abstracted, 5);
						long expansionTime = System.nanoTime();
						List<Path> validPaths = Traversal.TraverseGraph(expandedGraph, abstracted.getProducer().getProcessNode(), abstracted.getConsumer().getProcessNode(), 14000, false, new ArrayList<Edge>(), 0);
						long traversalTime = System.nanoTime();
						
						int bestPathIndex = -1;
						Path bestPath = null;
						if (validPaths.size() > 0) {
							bestPathIndex = Optimizer.getMostSecuePath(expandedGraph, validPaths);	
							bestPath = validPaths.get(bestPathIndex);
							vs.add(bestPath.getVulnerability());
						}
						
						long endTime = System.nanoTime();
						long dExpansionTime = (expansionTime - startTime)/1000000;
						long dTraversalTime = (traversalTime - expansionTime)/1000000;
						long dBestPathTime = (endTime - traversalTime)/1000000;
						long importantTime = dTraversalTime + dBestPathTime;
						
						if (bestPathIndex >= 0) {					
							System.out.println("vGap: " + vGap + " | Sample: " + sample + " | Generated expanded graph in " + dExpansionTime + " | Traversed path in " + dTraversalTime + " | Found best path in " + dBestPathTime + " | Total time: " + importantTime + " | Number of valid paths: " + validPaths.size() + " | Best path vulnerability: " + validPaths.get(bestPathIndex).getVulnerability() + " | Best path: " );
						} else {
							System.out.println("vGap: " + vGap + " | Sample: " + sample + " | Generated expanded graph in " + dExpansionTime + " | Traversed path in " + dTraversalTime + " | Found best path in " + dBestPathTime + " | Total time: " + importantTime + " | Number of valid paths: " + validPaths.size() + " | Best path: None");
						}
					}
					
					pw.print(vGap + ",");
					for (float v : vs) {
						pw.print(v);
						pw.print(",");
					}
					pw.println();
				}
				
				pw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		
//		System.out.println("Valid paths:");
//		for (Path p : validPaths) {
//			for (Node n : p.getNodes()) {
//				System.out.print(abstracted.getNodeDescription(n) + "; ");
//			}
//			System.out.println();
//		}
	}
	
	private static AbstractGraph CreateRandomNetwork(int size, int vulnerabilityRange){
		Random r = new Random();
		AbstractGraph result = new AbstractGraph(Edge.class);
		
		// Encryption Schemes
		EncryptionScheme scheme1 = new EncryptionScheme(-vulnerabilityRange/4);
		EncryptionScheme scheme2 = new EncryptionScheme(-vulnerabilityRange/2);
		
		// Create nodes and edges such that the network is connected.
		for (int i = 0; i < size; i++) {
			// Node
			AbstractNode node = new AbstractNode();
			
			// Processor
			Processor processor = new Processor() {
				float minProcessingLatency = 400f, maxProcessingLatency = 700f;
				Random r = new Random();
				float processingLatency = r.nextFloat() * (maxProcessingLatency - minProcessingLatency) + minProcessingLatency;
				
				@Override protected float GetProcessingLatency(int b) {return processingLatency*b;}
			};
			
			r.nextFloat();
			
			node.setProcessor(processor);
			node.addEncryptionScheme(scheme1, r.nextFloat() * 50 + 300 - 25);
			node.addEncryptionScheme(scheme2, r.nextFloat() * 50 + 600 - 25);
			node.SetEnd(false);
			result.addVertex(node);
			
			if (result.vertexSet().size() > 1) {				
				ConnectVertexToRandomVertexInGraph(result, node, new ArrayList<AbstractNode>(), vulnerabilityRange);
			}
		}
		
		// Create end nodes
		List<AbstractNode> endNodes = new ArrayList<>();
		for (int i = 0; i < 2; i++) {
			// Node
			AbstractNode node = new AbstractNode();
			
			// Processor
			Processor processor = new Processor() {
				@Override protected float GetProcessingLatency(int b) {return 100*b;}
			};
			
			node.setProcessor(processor);
			node.addEncryptionScheme(scheme1, r.nextFloat() * 50 + 400 - 25);
			node.addEncryptionScheme(scheme2, r.nextFloat() * 100 + 800 - 50);
			node.SetEnd(true);
			node.setName("a"+i);
			endNodes.add(node);
			result.addVertex(node);
			
			ConnectVertexToRandomVertexInGraph(result, node, endNodes, vulnerabilityRange);
		}
		result.setProducer(endNodes.get(0));
		result.setConsumer(endNodes.get(1));
		
		// Saturate the network with more edges randomly
//		int numberOfRandomEdges = 0;
//		for (AbstractNode n1 : result.vertexSet()) {
//			for (AbstractNode n2 : result.vertexSet()) {
//				if (n1 != n2 && !(n1.IsEnd() && n1.IsEnd())) {
//					float roll = r.nextFloat();
//					if (roll <= 0.05) {
//						numberOfRandomEdges++;
//						
//						float latency = r.nextFloat() * 2000;
//						float v = r.nextFloat() * 100;
//						Edge e1 = new Edge(latency, v, true);	
//						Edge e2 = new Edge(latency, v, true);	
//						result.addEdge(n1, n2, e1); result.addEdge(n2, n1, e2);
//					}
//				}
//			}
//		}
		// System.out.println("Number of random edges: " + numberOfRandomEdges);
		
		// Cloud
		AbstractNode cloud = new AbstractNode();
		cloud.addEncryptionScheme(scheme1, r.nextFloat() * 20 + 50 - 10);
		cloud.addEncryptionScheme(scheme2, r.nextFloat() * 20 + 100 - 10);
		cloud.setProcessor(new Processor() {@Override protected float GetProcessingLatency(int b) {return 100*b;}});
		result.addVertex(cloud);
		
		ConnectVertexToRandomVertexInGraph(result, cloud, endNodes, vulnerabilityRange);
		
		
		return result;
	}
	
	private static void ConnectVertexToRandomVertexInGraph(AbstractGraph graph, AbstractNode node, List<AbstractNode> exceptions, int vulnerabilityRange) {
		Random random = new Random();
		
		AbstractNode connectTo = null;
		int randomIndex = random.nextInt(graph.vertexSet().size());
		int j = 0;
		for (AbstractNode n : graph.vertexSet()) {
			if (n == node || exceptions.contains(n)) {
				ConnectVertexToRandomVertexInGraph(graph, node, exceptions, vulnerabilityRange);
				return;					
			}
			if (j == randomIndex){
				connectTo = n;
				break;
			}
			j++;
		}
		
		float latency = random.nextFloat() * 50 + 100;
		float v = random.nextFloat() * vulnerabilityRange + vulnerabilityRange/2;
		Edge e1 = new Edge(latency, v, true), e2 = new Edge(latency, v, true);				
		graph.addEdge(node, connectTo, e1);
		graph.addEdge(connectTo, node, e2);
	}

}