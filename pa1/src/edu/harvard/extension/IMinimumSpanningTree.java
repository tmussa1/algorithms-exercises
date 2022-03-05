package edu.harvard.extension;

import java.util.List;

/**
 * Interface for generating minimum spanning tree
 */
public interface IMinimumSpanningTree {

    List<Edge> generateMinimumSpanningTree(int numVertices, int dimension, List<Edge> edgeList);
}
