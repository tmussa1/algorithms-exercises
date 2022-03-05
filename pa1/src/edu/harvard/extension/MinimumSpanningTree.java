package edu.harvard.extension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Implements Kruskal's algorithm to find minimum spanning tree
 */
public class MinimumSpanningTree implements IMinimumSpanningTree {

    private static IDisjointSet disjointSet = new DisjointSet();

    private static IMergeSort mergeSort = new MergeSort();

    @Override
    public List<Edge> generateMinimumSpanningTree(int numVertices, int dimension, List<Edge> edgeList) {
      
        int edgeListLength = edgeList.size();
        Edge [] spanningTreeEdges = new Edge[numVertices - 1];
        Node [] nodes = new Node[numVertices];
        int count = 0;

        // Sort edges by weight
        mergeSort.mergeSort(edgeList, 0, edgeListLength - 1);

        // Make an individual set for each node
        for(int i = 0; i < numVertices; i++){
            nodes[i] = disjointSet.makeSet(i);
        }

        /**
         * Combine edges if they don't form a cycle
         */
        for(int i = 0; i < edgeListLength; i++){
            if(disjointSet.findNodeParent(nodes[edgeList.get(i).getVertex1()])
                    != disjointSet.findNodeParent(nodes[edgeList.get(i).getVertex2()])){
                spanningTreeEdges[count++] = edgeList.get(i);
                disjointSet.union(nodes[edgeList.get(i).getVertex1()],
                        nodes[edgeList.get(i).getVertex2()]);
                if(count > numVertices - 1){
                    break;
                }
            }
        }

        return Arrays.asList(Arrays.copyOfRange(spanningTreeEdges, 0, count));
    }
}
