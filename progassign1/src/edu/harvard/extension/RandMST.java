package edu.harvard.extension;

import java.util.List;

public class RandMST {

    public static void main(String[] args) {
        int numTrials = 5; // TODO - get as arg
        int numVertices = 262144;
        int dimension = 2;

        IGraph graph = new Graph();
        IMinimumSpanningTree mst = new MinimumSpanningTree();

        for(int i = 0; i < numTrials; i++){

            for(int j = 4; j < numVertices; j *= 2) {

                long startTime = System.currentTimeMillis(), endTime = 0L;

                List<Edge> graphs = graph.generateHigherDimensionalGraph(j, dimension);

                endTime = System.currentTimeMillis();

                System.out.println("Generated " + dimension + " dimensional graph with " + j +
                        " vertices in " + ((endTime - startTime) / 1000) + " seconds");

                startTime = System.currentTimeMillis();

                List<Edge> spanningTrees = mst.generateMinimumSpanningTree(j, dimension, graphs);

                endTime = System.currentTimeMillis();

                System.out.println("Generated " + dimension + " dimensional spanning tree with " +  spanningTrees.size() +
                        " vertices in " + ((endTime - startTime) / 1000) + " seconds");
            }
        }
    }
}
