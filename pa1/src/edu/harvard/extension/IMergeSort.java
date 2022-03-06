package edu.harvard.extension;

import java.util.List;

/**
 * Interface for merge sort
 */
public interface IMergeSort {

    void mergeSort(Edge [] edges, int left, int right);
    void merge(Edge []  edges, int left, int middle, int right);
}
