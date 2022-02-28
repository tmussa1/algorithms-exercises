package edu.harvard.extension;

import java.util.List;

public interface IGraph {

    List<Edge> generate0DimensionalGraph(int numVertices); // TODO - pass dimensions
    List<Edge> generateHigherDimensionalGraph(int numVertices, int dimension);
}
