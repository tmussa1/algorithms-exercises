package edu.harvard.extension;

import java.util.*;

public class Graph implements IGraph {


    @Override // TODO - discuss modularity
    public List<Edge> generate0DimensionalGraph(int numVertices) {

        Set<Edge> edges = new HashSet<>();

        // Cut the graph by a constant factor of half
        for(int i = 0; i < numVertices; i++){
            for(int j = 0; j < i; j++){
                // TODO - reseed
                Edge edge = new Edge(i, j);

                if(!edges.contains(edge)){
                    edge.setWeight(this.newRandom());
                    edges.add(edge);
                }
            }
        }

        return new ArrayList<>(edges);
    }

    @Override
    public List<Edge> generateHigherDimensionalGraph(int numVertices, int dimension) {

        Set<Edge> edges = new HashSet<>();

        double[][] vertices = this.populateVertexMatrix(numVertices, dimension);

        for(int i = 0; i < numVertices; i++){
            for(int j = 0; j < i; j++){
                Edge edge = new Edge(i, j);

                if(!edges.contains(edge)) {
                    edge.setWeight(this.calculateEuclideanDistance(dimension,
                            vertices[i], vertices[j]));
                    edges.add(edge); // TODO - optimization
                }
            }
        }


        return new ArrayList<>(edges);
    }

    private double[][] populateVertexMatrix(int numVertices, int dimensions){

        double [] [] vertices = new double[numVertices][dimensions];

        for(int row = 0; row < numVertices; row++){
            for(int col = 0; col < dimensions; col++){
                vertices[row][col] = this.newRandom();
            }
        }

        return vertices;
    }

    private double newRandom() {
        return new Random().nextDouble();
    }

    private double calculateEuclideanDistance(int dimension, double [] vertex1, double [] vertex2) {

        double total = 0.0;

        for(int i = 0; i < dimension; i++){
            total += Math.pow((vertex1[i] - vertex2[i]), 2);
        }

        return total;
    }
}
