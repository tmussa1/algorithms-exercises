import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

public class Strassen {

    private static final int CROSS_OVER_POINT = 20; // TODO

    public static int [] [] strassenMultiply(int [] [] A, int [][] B, int dimension){

        int [] [] result = new int [dimension][dimension];
        int halfDimension = dimension / 2;

        if(dimension <= CROSS_OVER_POINT){
            return multiplyMatrices(A, B);
        }

        if(dimension % 2 == 0){

            int [] [] A1 = createMatrix(halfDimension);
            int [] [] A2 = createMatrix(halfDimension);
            int [] [] A3 = createMatrix(halfDimension);
            int [] [] A4 = createMatrix(halfDimension);
            int [] [] B1 = createMatrix(halfDimension);
            int [] [] B2 = createMatrix(halfDimension);
            int [] [] B3 = createMatrix(halfDimension);
            int [] [] B4 = createMatrix(halfDimension);

            for(int i = 0; i < halfDimension; i++){
                for(int j = 0; j < halfDimension; j++){
                    A1[i][j] = A[i][j];
                    A2[i][j] = A[i + halfDimension][j];
                    A3[i][j] = A[i][j + halfDimension];
                    A4[i][j] = A[i + halfDimension][j + halfDimension];
                    B1[i][j] = B[i][j];
                    B2[i][j] = B[i + halfDimension][j];
                    B3[i][j] = B[i][j + halfDimension];
                    B4[i][j] = B[i + halfDimension][j + halfDimension];
                }
            }

            int [] [] s1 = strassenMultiply(A1, subtractMatrices(B3, B4), halfDimension);
            int [] [] s2 = strassenMultiply(addMatrices(A1, A3), B4, halfDimension);
            int [] [] s3 = strassenMultiply(addMatrices(A2, A4), B1, halfDimension);
            int [] [] s4 = strassenMultiply(A4, subtractMatrices(B2, B1), halfDimension);
            int [] [] s5 = strassenMultiply(addMatrices(A1, A4), addMatrices(B1, B4), halfDimension);
            int [] [] s6 = strassenMultiply(subtractMatrices(A3, A4), addMatrices(B2, B4), halfDimension);
            int [] [] s7 = strassenMultiply(subtractMatrices(A1, A2), addMatrices(B1, B3), halfDimension);

            int [] [] C1 = addMatrices(s5, addMatrices(subtractMatrices(s4, s2), s6));
            int [] [] C2 = addMatrices(s3, s4);
            int [] [] C3 = addMatrices(s1, s2);
            int [] [] C4 = addMatrices(s5, subtractMatrices(s1, addMatrices(s3, s7)));

            for(int i = 0; i < halfDimension; i++){
                for(int j = 0; j < halfDimension; j++){
                    result[i][j] = C1[i][j];
                    result[i][j + halfDimension] = C2[i][j];
                    result[i + halfDimension][j] = C3[i][j];
                    result[i + halfDimension][j + halfDimension] = C4[i][j];
                }
            }

            return result;

        } else {

            // Padding zeroes for odd case
            int augmentedDimension = dimension + 1;

            int [] [] NEW_A = new int [augmentedDimension][augmentedDimension];
            int [] [] NEW_B = new int [augmentedDimension][augmentedDimension];

            for(int i = 0; i < dimension; i++){
                for(int j = 0; j < dimension; j++){
                    NEW_A[i][j] = A[i][j];
                    NEW_B[i][j] = B[i][j];
                }
            }

            int [] [] oddResult = strassenMultiply(NEW_A, NEW_B, augmentedDimension);

            for(int i = 0; i < dimension; i++){
                for(int j = 0; j < dimension; j++){
                    result[i][j] = oddResult[i][j];
                }
            }

            return result;
        }
    }

    private static int [] [] createMatrix(int dimension){
        return new int [dimension] [dimension];
    }

    private static int [] [] multiplyMatrices(int [] [] A, int [] [] B){
        int length = A.length;
        int [] [] C = new int[length] [length];

        for(int i = 0; i < length; i++){
            for(int j = 0; j < length; j++){
                for(int k = 0; k < length; k++){
                    C[i][j] += A[i][k] * B[k][j];
                }
            }
        }

        return C;
    }

    private static int [] [] addMatrices(int [] [] A, int [] [] B){
        int length = A.length;
        int [] [] C = new int[length] [length];

        for(int i = 0; i < length; i++){
            for(int j = 0; j < length; j++){
                C[i][j] = A[i][j] + B[i][j];
            }
        }

        return C;
    }

    private static int [] [] subtractMatrices(int [] [] A, int [] [] B){
        int length = A.length;
        int [] [] C = new int[length] [length];

        for(int i = 0; i < length; i++){
            for(int j = 0; j < length; j++){
                C[i][j] = A[i][j] - B[i][j];
            }
        }

        return C;
    }

    public static void main(String [] args) throws IOException {

        if(args.length != 3){
            throw new RuntimeException("Pass: Strassen.java <option> <dimension> <filename>");
        }

        String fileName = args[2];
        Integer dimension = Integer.parseInt(args[1]);
        List<String> lines = new ArrayList<>();

        try (Stream<String> stream = Files.lines(Paths.get(String.valueOf(new File(fileName))))) {
            stream.forEach((line) -> lines.add(line));
        } catch (IOException e) {
            e.printStackTrace();
        }

        int [] [] matrix1 = new int[dimension][dimension];
        int [] [] matrix2 = new int[dimension][dimension];

        int lineCount = 0;

        for(int i = 0; i < dimension; i++){
            for(int j = 0; j < dimension; j++){
               matrix1[i][j] = Integer.parseInt(lines.get(lineCount));
               lineCount++;
            }
        }

        for(int i = 0; i < dimension; i++){
            for(int j = 0; j < dimension; j++){
                matrix2[i][j] = Integer.parseInt(lines.get(lineCount));
                lineCount++;
            }
        }

        int [] [] result = strassenMultiply(matrix1, matrix2, dimension);

        for(int i = 0; i < dimension; i++){
            System.out.println(result[i][i]);
        }

//        int dimension = 4;
//
//        String fileName = "dimension_4_data.txt";
//
//        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
//
//        for(int i = 0; i < (2 * dimension * dimension); i++){
//            Integer randomInt = new Random().nextInt(10);
//            writer.write(String.valueOf(randomInt));
//            writer.write("\n");
//        }
//
//        writer.close();
    }
}