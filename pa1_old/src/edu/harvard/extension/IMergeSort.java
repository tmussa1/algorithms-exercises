package edu.harvard.extension;

import java.util.List;

/**
 * Interface for merge sort
 */
public interface IMergeSort {

    void mergeSort(List<Edge> edges, int left, int right);
    void merge(List<Edge> edges, int left, int middle, int right);
}
