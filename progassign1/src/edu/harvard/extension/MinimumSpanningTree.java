package edu.harvard.extension;

import java.util.ArrayList;
import java.util.List;

public class MinimumSpanningTree implements IMinimumSpanningTree {

    private static IDisjointSet disjointSet = new DisjointSet();

    private static IMergeSort mergeSort = new MergeSort();

    @Override
    public List<Edge> generateMinimumSpanningTree(int numVertices, int dimension, List<Edge> edgeList) {

        List<Edge> spanningTreeEdges = new ArrayList<>();
        int edgeListLength = edgeList.size();
        Node [] nodes = new Node[numVertices];

        mergeSort.mergeSort(edgeList, 0, edgeListLength - 1);

        for(int i = 0; i < numVertices; i++){
            nodes[i] = disjointSet.makeSet(i);
        }

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
