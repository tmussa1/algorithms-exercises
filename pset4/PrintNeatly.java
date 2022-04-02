import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class PrintNeatly {

    public static void main(String [] args){
        try (Stream<String> stream = Files.lines(Paths.get(String.valueOf(new File("PrintNeatlyText.txt"))))) {
            stream.forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}