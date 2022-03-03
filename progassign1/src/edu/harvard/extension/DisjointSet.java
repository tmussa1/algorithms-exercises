package edu.harvard.extension;

/**
 * Implements union find to be used for Kruskal's algorithm
 */
public class DisjointSet implements IDisjointSet {

    @Override
    public Node makeSet(int val) {
        Node newNode = new Node();
        newNode.setParent(newNode);
        newNode.setRank(0);
        newNode.setVal(val);
        return newNode;
    }

    @Override
    public Node findNodeParent(Node node) {
        if(!node.equals(node.getParent())){
            node.setParent(findNodeParent(node.getParent()));
        }
        return node.getParent();
    }

    @Override
    public void union(Node node1, Node node2) {
        this.link(findNodeParent(node1), findNodeParent(node2));
    }

    @Override
    public void link(Node node1, Node node2) {
        if(node1.getRank() > node2.getRank()){
            link(node2, node1);
            return;
        } else if(node1.getRank() == node2.getRank()){
            node2.setRank(node2.getRank() + 1);
        }
        node1.setParent(node2);
    }
}
