package edu.harvard.extension;

import java.util.Comparator;
import java.util.List;

public class RandMST {

    public static double calculateMaxWeight(List<Edge> edges){
        return edges.stream().max(Comparator.comparing(Edge::getWeight)).get().getWeight();
    }

    public static void main(String[] args) {
        int numTrials = 20; // TODO - get as arg, write to file, come up with formula, run out of heap spaces and threw out edges
        int numVertices = 4096; // TODO - estimate runtime, run 0 d and flag, take command line arguments
        int dimension = 4;
        int chunk = 100;

        IGraph graph = new Graph();
        IMinimumSpanningTree mst = new MinimumSpanningTree();

        double total = 0.0;

        for(int j = 2; j < numVertices; j = j + chunk) {

            total = 0.0;

            for(int i = 0; i < numTrials; i++){

                long startTime = System.currentTimeMillis(), endTime = 0L;

                // TODO - call with all dimensions, comment code
                List<Edge> graphs = graph.generate0DimensionalGraph(j);

                endTime = System.currentTimeMillis();

//                System.out.println("Generated " + dimension + " dimensional graph with " + j +
//                        " vertices in " + ((endTime - startTime) / 1000) + " seconds");

                startTime = System.currentTimeMillis();

                List<Edge> spanningTrees = mst.generateMinimumSpanningTree(j, dimension, graphs);

                endTime = System.currentTimeMillis();

//                System.out.println("Generated " + dimension + " dimensional spanning tree with " +  spanningTrees.size() +
//                        " vertices in " + ((endTime - startTime) / 1000) + " seconds" + " has max weight " + calculateMaxWeight(spanningTrees));

                total += calculateMaxWeight(spanningTrees);
            }
            System.out.println(j + "," + (total / numTrials));
        }
    }
}
