package edu.harvard.extension;

public class Edge {

    private Integer vertex1;
    private Integer vertex2;
    private double weight;

    public Edge() {
    }

    public Edge(Integer vertex1, Integer vertex2) {
        this.vertex1 = vertex1;
        this.vertex2 = vertex2;
    }

    public Integer getVertex1() {
        return vertex1;
    }

    public Integer getVertex2() {
        return vertex2;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
}
