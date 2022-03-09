package edu.harvard.extension;

import java.util.List;

/**
 * Interface for graphs
 */
public interface IGraph {

    List<Edge> generateGraphDriver(int numVertices, int dimension);
    List<Edge> generate0DimensionalGraph(int numVertices); // TODO - pass dimensions
    List<Edge> generateHigherDimensionalGraph(int numVertices, int dimension);
}
