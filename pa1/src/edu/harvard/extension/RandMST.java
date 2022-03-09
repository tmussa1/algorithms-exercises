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
    	
        int operation = Integer.parseInt(args[0]);
        int numVertices = Integer.parseInt(args[1]); 
        int numTrials = Integer.parseInt(args[2]);
        int dimension = Integer.parseInt(args[3]);
        
        
        // Example of command line input - 0 numpoints numtrials dimension

        IGraph graph = new Graph();
        IMinimumSpanningTree mst = new MinimumSpanningTree();

        double total;

        for(int j = 16; j <= numVertices; j *= 2) {
        	
            total = 0.0;
            long startTime = System.currentTimeMillis(), endTime;
            
            for(int i = 0; i < numTrials; i++){
                List<Edge> graphs = graph.generateGraphDriver(j, dimension);
                List<Edge> spanningTrees = mst.generateMinimumSpanningTree(j, dimension, graphs);
                
                if(operation == 0) { // Calculate total weight
                    total += calculateTotalWeight(spanningTrees);
                } else { // Calculate max weight used for determining how to throw out edges
                    total += calculateMaxWeight(spanningTrees);
                }
           

            }
            
            endTime = System.currentTimeMillis();
            
            if(operation == 0) { // Printing
            	System.out.println((total / numTrials) + "," + j + "," + numTrials + "," + dimension + "," +
                        ((endTime - startTime) / 1000));
            } else {
            	System.out.println(j + "," + ((total / numTrials)));
            }
        }
    }
}
