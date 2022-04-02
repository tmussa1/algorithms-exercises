import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class PrintNeatly {


    private String printNeatly(String [] words, int maximumLength){

        int length = words.length;

        int [] [] extraSpaces = new int[length][length];

        for(int i = 0; i < length; i++){
            extraSpaces[i][i] = maximumLength - words[i].length();
            for(int j = i + 1; j < length; j++){
                extraSpaces[i][j] = extraSpaces[i][j - 1] - words[j].length() - 1;
            }
        }

        int totalPenalty = 0;

        for(int i = 0; i < length; i++){
            for(int j = i; j < length; j++){
                if(extraSpaces[i][j] < 0){
                    extraSpaces[i][j] = Integer.MAX_VALUE;
                } else {
                    extraSpaces[i][j] = penalty(extraSpace(words, maximumLength, i, j));
                    if(j < length - 1 && i < length - 1){
                        totalPenalty += extraSpaces[i][j];
                    }
                }
            }
        }

        System.out.println("Penalty " + totalPenalty);

        int [] finalCost = new int[length];
        int [] minCost = new int[length];

        for(int i = length - 1; i >= 0; i--){

            finalCost[i] = length;
            minCost[i] = extraSpaces[i][length - 1];

            for(int j = length - 1; j > i; j--){
                if(extraSpaces[i][j - 1] == Integer.MAX_VALUE){
                    continue;
                }
                if(minCost[i] > minCost[j] + extraSpaces[i][j - 1]){
                    minCost[i] = minCost[j] + extraSpaces[i][j - 1];
                    finalCost[i] = j;
                }
            }
        }

        StringBuilder result = new StringBuilder();

        int i = 0;
        int j;

        do {
            j = finalCost[i];

            for(int k = i; k < j; k++){
                result.append(words[k] + " ");
            }

            result.append("\n");
            i = j;
        } while(j < length);

        return result.toString();
    }

    private int penalty(int space){
        return space * space * space;
    }

    private int extraSpace(String [] words, int maximumLength, int i, int j){

        int sumOfWordLength = 0;

        for(int k = i; k <= j; k++){
            sumOfWordLength += words[k].length();
        }

        return maximumLength - (j - i) - sumOfWordLength;
    }

    public static void main(String [] args){

        StringBuilder builder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(String.valueOf(new File("PrintNeatlyText.txt"))))) {
            stream.forEach((word) -> builder.append(word));
        } catch (IOException e) {
            e.printStackTrace();
        }

        String [] words = builder.toString().split(" ");

        String result = new PrintNeatly().printNeatly(words, 72);

        System.out.println(result);
    }
}