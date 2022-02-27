package edu.harvard.extension;

import java.util.Arrays;

public class Main {


    public static long fibonacciMatrix(long n){

        long [] [] result = {{1, 1}, {1, 0}};
        long [] [] intermediate = new long[2][2];

        for(int i = 0; i < result.length; i++){
            intermediate[i] = Arrays.copyOf(result[i], result[i].length);
        }

        if(n < 2){
            return n;
        }

        if(n == 2) return 1;

        while(n > 0){
            if( n % 2 == 1) result = matrixMultiply(result, intermediate);
            intermediate = matrixMultiply(intermediate, intermediate);
            n /= 2;
        }

        return result[1][1];
    }

    public static long [][] matrixMultiply(long [][] matrix1, long [] [] matrix2){
        return new long [] [] {
                {
                        (matrix1[0][0] % 65536) * (matrix2[0][0] % 65536) + (matrix1[0][1] % 65536) * (matrix2[1][0] % 65536),
                        (matrix1[0][0] % 65536) * (matrix2[0][1] % 65536) + (matrix1[0][1] % 65536) * (matrix2[1][1] % 65536)
                },
                {
                        (matrix1[1][0] % 65536) * (matrix2[0][0] % 65536) + (matrix1[1][1] % 65536) * (matrix2[1][0] % 65536) ,
                        (matrix1[1][0] % 65536) * (matrix2[0][1] % 65536) + (matrix1[1][1] % 65536) * (matrix2[1][1] % 65536)
                }
        };
    }

    public static int fibRecursive(int n){
        if(n < 2){
            return n;
        }

        return fibRecursive((n - 1) % 65536) + fibRecursive((n - 2) % 65536);
    }

    public static int fibIterative(int n){

        if(n < 2) return n;

        int result = 0, prev = 0, current = 1;

        for(int i = 1; i < n; i++){
            result = (current % 65536) + (prev % 65536);
            prev = (current % 65536) ;
            current = (result % 65536) ;
        }

        return result;
    }

    public static void main(String[] args) {

        long start = System.currentTimeMillis(), end = 0;
        int i = 1;

        while(end - start <= 60000L){
            long start1 = System.currentTimeMillis();
            System.out.println("Fib Recursive of " + i + " is " + fibRecursive(i));
            long end1 = System.currentTimeMillis();
            System.out.println("Finished in " + (end1 - start1) + " milliseconds");
            i++;
            end = System.currentTimeMillis();
        }

//        for(int i = 1; i < Integer.MAX_VALUE; i++) {
//
//            long start1 = System.currentTimeMillis();
//            System.out.println("Fib Matrix exponentiation of " + i + " is " + fibonacciMatrix(i));
//            long end1 = System.currentTimeMillis();
//            System.out.println("Finished in " + (end1 - start1) + " milliseconds");
//
////            if(fibonacciMatrix(i) >= 2147483648L){
////                break;
////            }
//
//            long start2 = System.currentTimeMillis();
//            System.out.println("Fib Iterative " + i + " is " + fibIterative(i));
//            long end2 = System.currentTimeMillis();
//            System.out.println("Finished in " + (end2 - start2) + " milliseconds");
//
//            long start3 = System.currentTimeMillis();
//            System.out.println("Fib Recursive " + i + " is " + fibRecursive(i));
//            long end3 = System.currentTimeMillis();
//            System.out.println("Finished in " + (end3 - start3) + " milliseconds");
//        }

    }
}
