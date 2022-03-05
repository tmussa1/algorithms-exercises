package edu.harvard.extension;

/**
 * Node used to store the values needed for union find to operate
 */
public class Node {

    private int val;
    private int rank;
    private Node parent;

    public int getVal() {
        return val;
    }

    public void setVal(int val) {
        this.val = val;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }
}
