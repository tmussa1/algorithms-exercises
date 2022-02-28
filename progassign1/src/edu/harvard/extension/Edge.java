package edu.harvard.extension;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Edge edge = (Edge) o;
        return Objects.equals(vertex1, edge.vertex1) && Objects.equals(vertex2, edge.vertex2);
    }

    @Override
    public int hashCode() {
        int result = 19;
        result = 31 * result + (vertex1 == null ? 0 : vertex1.hashCode());
        result = 31 * result + (vertex2 == null ? 0 : vertex2.hashCode());
        return result;
    }
}
