import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class CelebrityIdentification {

    public CelebrityIdentification() {
        // output(fileToString("asciiPhoto1.txt"), 161); // Bruno Mars
        // 2: Albert Einstein
        // 3: Barack Obama
        // 4: Daniel Radcliffe (Harry Potter)
        for (int i = 150; i <= 160; i++)
            output(fileToString("asciiPhoto5.txt"), i);

    }

    public String fileToString(String fileName) {
        String string = "";
        File file = new File(fileName);
        try {
            BufferedReader input = new BufferedReader(new FileReader(file));
            String text;
            while ((text = input.readLine()) != null) {
                string = text;
            }
        } catch (IOException e) {
            System.out.println("File not found.");
        }
        return string;
    }

    public void output(String string, int charsPerLine) {
        System.out.println(charsPerLine + "\n\n");
        String regex = "(?<=\\G.{" + charsPerLine + "})";
        String[] lines = string.split(regex);
        for (String s : lines)
            System.out.println(s);
        System.out.println("\n");
    }

    public static void main(String[] args) {
        CelebrityIdentification app = new CelebrityIdentification();
    }

}