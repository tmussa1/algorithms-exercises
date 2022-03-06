package edu.harvard.extension;

import java.util.Arrays;
import java.util.List;

/**
 * Implements Kruskal's algorithm to find minimum spanning tree
 */
public class MinimumSpanningTree implements IMinimumSpanningTree {

    private static IDisjointSet disjointSet = new DisjointSet();

    @Override
    public List<Edge> generateMinimumSpanningTree(int numVertices, int dimension, List<Edge> edgeList) {

        IMergeSort mergeSort = new MergeSort();
        int edgeListLength = edgeList.size();
        Edge [] spanningTreeEdges = new Edge[numVertices - 1];
        Node [] nodes = new Node[numVertices];
        int count = 0;
        Edge [] graphEdges = edgeList.toArray(new Edge [edgeListLength]);

        // Sort edges by weight
        mergeSort.mergeSort(graphEdges, 0, edgeListLength - 1);

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
