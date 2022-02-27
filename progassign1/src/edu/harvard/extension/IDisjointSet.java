package edu.harvard.extension;

public interface IDisjointSet {

    Node makeSet(int val);
    Node findNodeParent(Node node);
    void union(Node node1, Node node2);
    void link(Node node1, Node node2);
}
