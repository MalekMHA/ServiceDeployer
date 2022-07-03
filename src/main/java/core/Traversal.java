package core;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.interfaces.ShortestPathAlgorithm.SingleSourcePaths;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;

import nodes.AbstractNode;
import nodes.Node;
import nodes.ProcessNode;

public class Traversal{
	public static List<Path> TraverseGraph(Graph<Node, Edge> g, Node start, Node end, float latencyBudget, boolean visitedProcessNode, List<Edge> visitedLinks, int hops) {
		List<Path> paths = new ArrayList<Path>();
		Set<Edge> edges = g.outgoingEdgesOf(start);
		
		if (hops > g.vertexSet().size()) {
			return paths;
		}
		int newHops = hops + 1;
		
		for (Edge e : edges) {
			Node n = g.getEdgeTarget(e);
			float newLatencyBudget = latencyBudget - e.getLatency();
			boolean newVisitedProcessNode = visitedProcessNode;
			boolean isProcess = n instanceof ProcessNode;
			
			if (newLatencyBudget < 0) {continue;}
			if (isProcess && visitedProcessNode && n != end) {continue;}
			if (e.isLink() && visitedLinks.contains(e)) {continue;}
			
			if (isProcess && !visitedProcessNode && n != end) {newVisitedProcessNode = true;}
			
			List<Edge> newVisitedLinks = new ArrayList<>();
			newVisitedLinks.addAll(visitedLinks);
			if (e.isLink()) {newVisitedLinks.add(e);}
			
			
			Path path = new Path(g);
			path.addNode(n);
			
			if (n == end && visitedProcessNode) {
				paths.add(path);
				break;
			} else if (n != end) {
				List<Path> nPaths = TraverseGraph(g, n, end, newLatencyBudget, newVisitedProcessNode, newVisitedLinks, newHops);
				for (Path nPath : nPaths) {
					Path newPath = path.add(nPath);
					paths.add(newPath);
				}
			}
			
		}
		return paths;
	}
	
	public static List<Path> TraverseSP(Graph<Node, Edge> g, AbstractGraph ag, Node start, Node end, float latencyBudget) {
		DijkstraShortestPath<Node, Edge> dijkstraAlg = new DijkstraShortestPath<>(g);
		SingleSourcePaths<Node, Edge> iPaths = dijkstraAlg.getPaths(start);
		
		List<Path> paths = new ArrayList<Path>();
		for (AbstractNode n : ag.vertexSet()) {
			if (n.IsEnd()) { continue; }
			GraphPath<Node,Edge> gp = iPaths.getPath(n.getProcessNode());
			GraphPath<Node,Edge> gp2 = DijkstraShortestPath.findPathBetween(g, n.getProcessNode(), end);
			
			Path path = new Path(g);
			for (Node node : gp.getVertexList()) {
				path.addNode(node);
			}
			int i = 0;
			for (Node node : gp2.getVertexList()) {
				if (i != 0) {					
					path.addNode(node);
				}
				i++;
			}
			
			if (path.getLatency() <= latencyBudget) {
				paths.add(path);
			}
		}
		
		return paths;
		
	}
}
