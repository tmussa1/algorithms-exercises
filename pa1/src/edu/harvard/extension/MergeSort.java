package edu.harvard.extension;

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
    public void mergeSort(Edge [] edges, int left, int right) {

        if(left < right){
            int middle = left + (right - left) / 2;
            mergeSort(edges, left, middle);
            mergeSort(edges, middle + 1, right);
            merge(edges, left, middle, right);
       }
    }

    /**
     * Combines the edges to be sorted by weight.
     * Inspired by https://www.geeksforgeeks.org/merge-sort/
     * @param edges
     * @param left
     * @param middle
     * @param right
     */
    @Override
    public void merge(Edge [] edges, int left, int middle, int right) {

        int n1 = middle - left + 1;
        int n2 = right - middle;

        Edge [] leftEdgeList = new Edge[n1];
        Edge [] rightEdgeList = new Edge[n2];

        for(int i = 0; i < n1; i++){
            leftEdgeList[i] = edges[left + i];
        }

        for(int j = 0; j < n2; j++){
            rightEdgeList[j] = edges[middle + 1 + j];
        }

        int i = 0, j = 0;
        int k = left;

        while(i < n1 && j < n2){
            if(leftEdgeList[i].getWeight() <= rightEdgeList[j].getWeight()){
                edges[k++] = leftEdgeList[i++];
            } else {
                edges[k++] = rightEdgeList[j++];
            }
        }

        while(i < n1){
            edges[k++] = leftEdgeList[i++];
        }

        while(j < n2){
            edges[k++] = rightEdgeList[j++];
        }

    }
}
