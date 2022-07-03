package core;

import java.util.List;

import org.jgrapht.graph.DirectedWeightedMultigraph;

import nodes.Node;

public class Optimizer {
	public static int getMostSecuePath(DirectedWeightedMultigraph<Node, Edge> graph, List<Path> paths) {
		
		float bestVulnerability = 999999;
		int bestPathIndex = 0;
		for (int i = 0; i < paths.size(); i++) {
			Path path = paths.get(i);
			float v = path.getVulnerability();
			if (v < bestVulnerability) {
				bestPathIndex = i;
				bestVulnerability = v;
			}
		}
		
		return bestPathIndex;
	}
}
