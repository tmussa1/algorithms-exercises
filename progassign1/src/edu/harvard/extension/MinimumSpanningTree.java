package edu.harvard.extension;

import java.util.ArrayList;
import java.util.List;

/**
 * Implements Kruskal's algorithm to find minimum spanning tree
 */
public class MinimumSpanningTree implements IMinimumSpanningTree {

    private static IDisjointSet disjointSet = new DisjointSet();

    private static IMergeSort mergeSort = new MergeSort();

    @Override
    public List<Edge> generateMinimumSpanningTree(int numVertices, int dimension, List<Edge> edgeList) {

        List<Edge> spanningTreeEdges = new ArrayList<>();
        int edgeListLength = edgeList.size();
        Node [] nodes = new Node[numVertices];

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
                spanningTreeEdges.add(edgeList.get(i));
                disjointSet.union(nodes[edgeList.get(i).getVertex1()],
                        nodes[edgeList.get(i).getVertex2()]);
                if(spanningTreeEdges.size() >= numVertices){
                    break;
                }
            }
        }

        return spanningTreeEdges;
    }
}
