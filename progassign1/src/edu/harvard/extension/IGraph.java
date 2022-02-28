package edu.harvard.extension;

import java.util.Set;

public interface IGraph {

    // Used sets to avoid duplication
    Set<Edge> generate0DimensionalGraph(int numVertices); // TODO - pass dimensions
    Set<Edge> generateHigherDimensionalGraph(int numVertices, int dimension);
}
