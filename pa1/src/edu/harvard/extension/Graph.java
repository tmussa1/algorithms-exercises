package edu.harvard.extension;

import java.util.*;

/**
 * Contains methods that generate the graphs
 */
public class Graph implements IGraph {

    /**
     * Driver method to generate graphs
     * @param numVertices
     * @param dimension
     * @return List<Edge>
     */
    @Override
    public List<Edge> generateGraphDriver(int numVertices, int dimension) {
        if(dimension == 0  || dimension == 2 || dimension == 3 || dimension == 4){
            return dimension == 0 ? generate0DimensionalGraph(numVertices) : generateHigherDimensionalGraph(numVertices, dimension);
        }
        throw new RuntimeException("Dimensions are invalid");
    }

    /**
     * Generates 0 dimensional grpah. Throws out edges beyond what is predicted
     * by the regression line of the max weight claimed by the spanning tree
     * @param numVertices
     * @return List<Edge>
     */
    @Override
    public List<Edge> generate0DimensionalGraph(int numVertices) {

        Edge [] edges = new Edge[numVertices * (numVertices - 1) / 2];
        double throwOutBeyond = throwOutBeyond(numVertices, 0); // Throw out an edge beyond this value
        int count = 0;

        // Cut the graph by a constant factor of half by considering only the bottom triangle
        for(int i = 0; i < numVertices; i++){
            for(int j = 0; j < i; j++){
                // TODO - reseed
                float weight = this.newRandom();

                if(weight > Math.abs(throwOutBeyond)){
                    continue;
                }

                // Add it if in bound and the vertices hasn't been generated before
                if(weight <= throwOutBeyond){
                    Edge edge = new Edge(i, j);
                    edge.setWeight(weight);
                    edges[count++] = edge;
                }
            }
        }

        return Arrays.asList(Arrays.copyOfRange(edges, 0, count));
    }

    /**
     * Generates graph for dimensions 2 through 4
     * @param numVertices
     * @param dimension
     * @return List<Edge>
     */
    @Override
    public List<Edge> generateHigherDimensionalGraph(int numVertices, int dimension) {

        Edge [] edges = new Edge[numVertices * 2];
        double throwOutBeyond = throwOutBeyond(numVertices, dimension);

        // Throw out an edge beyond this value
        double[][] vertices = this.populateVertexMatrix(numVertices, dimension);
        int count = 0;

        // Cut the graph by a constant factor of half by considering only the bottom triangle
        for(int i = 0; i < numVertices; i++){
            for(int j = 0; j < i; j++){

                for(int k = 0; k < dimension; k++){
                    if(Math.abs(vertices[i][k] - vertices[j][k]) > Math.abs(throwOutBeyond)){
                        continue;
                    }
                }

                float weight = this.calculateEuclideanDistance(dimension,
                        vertices[i], vertices[j]);

                // Add it if in bound and the vertices hasn't been generated before
               if(weight <= throwOutBeyond){

                	Edge edge = new Edge(i, j);
                    edge.setWeight(weight);

                    if(count >= edges.length){
                        edges = Arrays.copyOf(edges, count * 2);
                    }

                    edges[count++] = edge;
               }
            }
        }


        return Arrays.asList(Arrays.copyOfRange(edges, 0, count));
    }

    /**
     * Populates the weights of the vertices
     * @param numVertices
     * @param dimensions
     * @return vertices with weight populated
     */
    private double[][] populateVertexMatrix(int numVertices, int dimensions){

        double [] [] vertices = new double[numVertices][dimensions];

        for(int row = 0; row < numVertices; row++){
            for(int col = 0; col < dimensions; col++){
                vertices[row][col] = this.newRandom();
            }
        }

        return vertices;
    }

    /**
     * Random number generator
     * @return rand number between 0 and 1
     */
    private float newRandom() {
        return new Random().nextFloat();
    }

    /**
     * Calculates Euclidean distance for higher dimensions
     * @param dimension
     * @param vertex1
     * @param vertex2
     * @return
     */
    private float calculateEuclideanDistance(int dimension, double [] vertex1, double [] vertex2) {
        double total = 0.0;
        for(int i = 0; i < dimension; i++){
            total += Math.pow((vertex1[i] - vertex2[i]), 2);
        }
        return (float) Math.sqrt(total);
    }

    /**
     * Anything beyond the weight returned here is unlikely to be
     * part of the spanning tree and ignored
     * @param numVertices
     * @param dimension
     * @return weight
     */
    private double throwOutBeyond(int numVertices, int dimension){

        // These values were experimentally calculated
        // They depend on both dimension and number of vertices
        if(dimension == 2){
            return  0.80018287952212674 +
                    4.7614690479836335 * Math.pow(10, -3) * numVertices -
                    3.7723858534382249 * Math.pow(10, -5) * Math.pow(numVertices, 2) +
                    9.7682894763278053 * Math.pow(10, -8) * Math.pow(numVertices, 3);
        } else if(dimension == 3){
            return  (0.99494038957085906) +
                    (3.7949020641474132 * Math.pow(10, -3) * numVertices) -
                    (1.9296136181377216 * Math.pow(10, -5) * numVertices * numVertices) +
                    (3.2498446060161502 * Math.pow(10, -8) * numVertices * numVertices * numVertices);
        } else if(dimension == 4){
            return  (1.1144164132723846) +
                    (3.6393587158792796 * Math.pow(10, -3) * numVertices) -
                    (1.7384028258968941 * Math.pow(10, -5) * numVertices * numVertices) +
                    (2.7652157861328113 * Math.pow(10, -8) * numVertices * numVertices * numVertices);
        }

        return  (0.89581146548621804) +
                (1.8269286203337126 * Math.pow(10, -3) * numVertices) -
                (9.9612573963010997 * Math.pow(10, -6) * numVertices * numVertices) +
                (1.6586622134495306 * Math.pow(10, -8) * numVertices * numVertices * numVertices);
    }
}
