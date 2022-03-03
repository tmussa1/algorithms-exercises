package edu.harvard.extension;

import java.util.Comparator;
import java.util.List;

public class RandMST {

    public static double calculateMaxWeight(List<Edge> edges){
        return edges.stream().max(Comparator.comparing(Edge::getWeight)).get().getWeight();
    }

    public static double calculateTotalWeight(List<Edge> edges){
        return edges.stream().mapToDouble(edge -> edge.getWeight()).sum();
    }

    public static void main(String[] args) {
        int numTrials = 20; // TODO - get as arg, write to file, come up with formula, run out of heap spaces and threw out edges
        int numVertices = 262144; // TODO - estimate runtime, run 0 d and flag, take command line arguments
        int dimension = 2;

        IGraph graph = new Graph();
        IMinimumSpanningTree mst = new MinimumSpanningTree();

        double total;

        for(int j = 16; j < numVertices; j *= 2) {
            total = 0.0;
            long startTime = System.currentTimeMillis(), endTime = 0L;
            for(int i = 0; i < numTrials; i++){
                // TODO - call with all dimensions, comment code
                List<Edge> graphs = graph.generateGraphDriver(j, dimension);
                List<Edge> spanningTrees = mst.generateMinimumSpanningTree(j, dimension, graphs);
                total += calculateTotalWeight(spanningTrees);
            }
            endTime = System.currentTimeMillis();
            System.out.println((total / numTrials) + "," + j + "," + numTrials + "," + dimension + "," +
                    ((endTime - startTime) / 1000));
        }
    }
}
