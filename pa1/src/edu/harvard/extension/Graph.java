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

        // Used sets to avoid duplication
        Edge [] edges = new Edge[numVertices * (numVertices - 1) / 2];
        double throwOutBeyond = throwOutBeyond(numVertices, 0); // Throw out an edge beyond this value
        int count = 0;
        // Cut the graph by a constant factor of half by considering only the bottom triangle
        for(int i = 0; i < numVertices; i++){
            for(int j = 0; j < i; j++){
                // TODO - reseed
                double weight = this.newRandom();

//                if(weight > Math.abs(throwOutBeyond)){
//                    continue;
//                }

                // Add it if in bound and the vertices hasn't been generated before
//                if(weight <= throwOutBeyond){
                    Edge edge = new Edge(i, j);
//                    if(!edges.contains(edge)){
                        edge.setWeight(weight);
                edges[count++] = edge;
//                    }
//                }
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

        Edge [] edges = new Edge[numVertices * (numVertices - 1) / 2];
        double throwOutBeyond = throwOutBeyond(numVertices, dimension);

        double[][] vertices = this.populateVertexMatrix(numVertices, dimension); // Throw out an edge beyond this value
        int count = 0;
        Edge edge = null;

        // Cut the graph by a constant factor of half by considering only the bottom triangle
        for(int i = 0; i < numVertices; i++){
            for(int j = 0; j < i; j++){

//                for(int k = 0; k < dimension; k++){
//                    if(Math.abs(vertices[i][k] - vertices[j][k]) > Math.abs(throwOutBeyond)){
//                        continue;
//                    }
//                }

                double weight = this.calculateEuclideanDistance(dimension,
                        vertices[i], vertices[j]);

                // Add it if in bound and the vertices hasn't been generated before
//               if(weight <= throwOutBeyond) {
                	edge = new Edge(i, j);
                    edge.setWeight(weight);
                    edges[count++] = edge;
//                }
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
    private double newRandom() {
        return new Random().nextDouble();
    }

    /**
     * Calculates Euclidean distance for higher dimensions
     * @param dimension
     * @param vertex1
     * @param vertex2
     * @return
     */
    private double calculateEuclideanDistance(int dimension, double [] vertex1, double [] vertex2) {
        double total = 0.0;
        for(int i = 0; i < dimension; i++){
            total += Math.pow((vertex1[i] - vertex2[i]), 2);
        }
        return Math.sqrt(total);
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
            return (0.80529 * Math.pow(numVertices, 2)) + (0.0065 * numVertices) - (4.0 * Math.pow(10, -5));
        } else if(dimension == 3){
            return 1.4905761673706 + (0.00029932231431746 * numVertices);
        } else if(dimension == 4){
            return 1.7811528561393 + (0.00032871934623935 * numVertices);
        }

        return 0.92937887536493 + (3.8098051419887 * Math.pow(10, -5) * numVertices);
    }
}
