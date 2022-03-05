package edu.harvard.extension;

import java.util.List;

/**
 * This class implements the classic merge sort algorithm tailored
 * to our use case
 */
public class MergeSort implements IMergeSort {

    /**
     * Driver method for merge sort
     * @param edges
     * @param left
     * @param right
     */
    @Override
    public void mergeSort(List<Edge> edges, int left, int right) {

        if(left < right){
            int middle = left + (right - left) / 2;
            mergeSort(edges, left, middle);
            mergeSort(edges, middle + 1, right);
            merge(edges, left, middle, right);
        }
    }

    /**
     * Combines the edges to be sorted by weight
     * @param edges
     * @param left
     * @param middle
     * @param right
     */
    @Override
    public void merge(List<Edge> edges, int left, int middle, int right) {

        int leftSize = middle - left + 1;
        int rightSize = right - middle;

        Edge [] leftEdgeList = new Edge[leftSize];
        Edge [] rightEdgeList = new Edge[rightSize];

        for(int i = 0; i < leftSize; i++){
            leftEdgeList[i] = edges.get(left + i);
        }

        for(int i = 0; i < rightSize; i++){
            rightEdgeList[i] = edges.get(middle + 1 + i);
        }

        int leftRow = 0, rightRow = 0, mergedRow = left;

        while(leftRow < leftSize && rightRow < rightSize){
            if(edges.get(leftRow).getWeight() < edges.get(rightRow).getWeight()){
                edges.set(leftRow, leftEdgeList[leftRow++]);
            } else {
                edges.set(rightRow, rightEdgeList[rightRow++]);
            }
            mergedRow++;
        }

        while(leftRow < leftSize){
            edges.set(leftRow, leftEdgeList[leftRow++]);
            mergedRow++;
        }

        while(rightRow < rightSize){
            edges.set(rightRow, rightEdgeList[rightRow++]);
            mergedRow++;
        }
    }
}
