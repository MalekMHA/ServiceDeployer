package core;
//package core;
//import java.awt.Color;
//import java.awt.image.BufferedImage;
//import java.io.File;
//import java.io.IOException;
//import java.util.List;
//
//import javax.imageio.ImageIO;
//
//import org.jgrapht.ext.JGraphXAdapter;
//import org.jgrapht.graph.DirectedWeightedMultigraph;
//import org.jgrapht.graph.SimpleWeightedGraph;
//
//import com.mxgraph.layout.mxCircleLayout;
//import com.mxgraph.layout.mxIGraphLayout;
//import com.mxgraph.util.mxCellRenderer;
//
//import nodes.AbstractNode;
//import nodes.Node;
//
//public class Main {
//	public static void main(String[] args) {
//		AbstractGraph abstracted = CreateAbstractGraph();
//		DirectedWeightedMultigraph<Node, Edge> expandedGraph = NetworkExpander.generateExpandedGraph(abstracted, 100);
//		
//		List<Path> validPaths = Traversal.TraverseGraph(expandedGraph, abstracted.getProducer().getProcessNode(), abstracted.getConsumer().getProcessNode(), 10000, false);
//		
//		System.out.println("Valid paths:");
//		for (Path p : validPaths) {
//			for (Node n : p.getNodes()) {
//				System.out.print(abstracted.getNodeDescription(n) + "; ");
//			}
//			System.out.println();
//		}
//
//		Path bestPath = validPaths.get(Optimizer.getMostSecuePath(expandedGraph, validPaths));
//		System.out.println("Best path:");
//		for (Node n : bestPath.getNodes()) {
//			System.out.print(abstracted.getNodeDescription(n) + "; ");
//		}
//		//VisualizeGraph(expandedGraph);
//	}
//	
//	/*
//	 * Change this method to change the abstracted graph.
//	 * returns: Graph<AbstractNode, Edge> abstract graph.
//	 */
//	private static AbstractGraph CreateAbstractGraph(){
//		// Encryption Schemes
//		EncryptionScheme scheme1 = new EncryptionScheme(-25);
//		EncryptionScheme scheme2 = new EncryptionScheme(-50);
//				
//		// Define processors
//		Processor n0Processor = new Processor() {
//			@Override protected float GetProcessingLatency(int b) {return 2*b;} // 2ms per byte
//		};Processor n1Processor = new Processor() {
//			@Override protected float GetProcessingLatency(int b) {return 3*b;} // 3ms per byte.
//		};Processor n2Processor = new Processor() {
//			@Override protected float GetProcessingLatency(int b) {return 1.1f*b;} // 1.1ms per byte.
//		};Processor n3Processor = new Processor() {
//			@Override protected float GetProcessingLatency(int b) {return 3.14f*b;} // 3.14ms per byte.
//		};Processor a0Processor = new Processor() {
//			@Override protected float GetProcessingLatency(int b) {return 9f*b;} // 9.0ms per byte.
//		};Processor a1Processor = new Processor() {
//			@Override protected float GetProcessingLatency(int b) {return 9f*b;} // 9.0ms per byte.
//		};
//		
//		// Create edge nodes
//		AbstractNode n0 = new AbstractNode(), n1 = new AbstractNode(),
//				n2 = new AbstractNode(), n3 = new AbstractNode();
//		n0.setProcessor(n0Processor); n1.setProcessor(n1Processor);
//		n2.setProcessor(n2Processor); n3.setProcessor(n3Processor);
//		n0.setName("n0");n1.setName("n1");n2.setName("n2");n3.setName("n3");
//		
//		// Create end nodes
//		AbstractNode a0 = new AbstractNode(), a1 = new AbstractNode();
//		a0.SetEnd(true); a1.SetEnd(true);
//		a0.setProcessor(a0Processor);a1.setProcessor(a1Processor);
//		a0.setName("a0");a1.setName("a1");
//		
//		// Add encryption schemes to nodes.
//		n0.addEncryptionScheme(scheme1);n0.addEncryptionScheme(scheme2);
//		n1.addEncryptionScheme(scheme1);n1.addEncryptionScheme(scheme2);
//		n2.addEncryptionScheme(scheme1);n2.addEncryptionScheme(scheme2);
//		n3.addEncryptionScheme(scheme1);n3.addEncryptionScheme(scheme2);
//		a0.addEncryptionScheme(scheme1);a0.addEncryptionScheme(scheme2);
//		a1.addEncryptionScheme(scheme1);a1.addEncryptionScheme(scheme2);
//		
//		// Create abstract graph
//		AbstractGraph result = new AbstractGraph(Edge.class);
//		result.addVertex(n0);
//		result.addVertex(n1);
//		result.addVertex(n2);
//		result.addVertex(n3);
//		result.addVertex(a0);
//		result.addVertex(a1);
//		
//		result.addEdge(a0, n0, new Edge(true));
//		result.addEdge(a0, n1);
//		result.addEdge(n0, n2);
//		result.addEdge(n1, n3);
//		result.addEdge(n1, n2);
//		result.addEdge(n2, a1);
//		result.addEdge(n3, a1);
//		
//		result.setProducer(a0);
//		result.setConsumer(a1);
//		
//		return result;
//	}
//	
//	/*
//	 * This is a copy paste sample code to visualize the graph. It's ununsed because of very bad visualizer.
//	 */
//	private static void VisualizeGraph(DirectedWeightedMultigraph<Node, Edge> expandedGraph) {
//		// Copy pasted code to show graph:
//		JGraphXAdapter<Node, Edge> graphAdapter = 
//			      new JGraphXAdapter<Node, Edge>(expandedGraph);
//		
//		mxIGraphLayout layout = new mxCircleLayout(graphAdapter);
//		    layout.execute(graphAdapter.getDefaultParent());
//		    
//	    BufferedImage image = mxCellRenderer.createBufferedImage(graphAdapter, null, 2, Color.WHITE, true, null);
//	    File imgFile = new File("src/test/resources/graph.png");
//	    try {
//			ImageIO.write(image, "PNG", imgFile);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//}
