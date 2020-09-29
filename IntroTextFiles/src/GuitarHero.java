import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class GuitarHero {

    public GuitarHero() {
        File file = new File("GuitarTabFile.txt");
        try {

            int[][] helperArray = new int[5][6];
            int count  = 1;
            for (int j = helperArray[0].length - 1; j >= 0; j--) {
                for (int i = helperArray.length - 1; i >= 0; i--) {
                    helperArray[i][j] = count;
                    if (i != 0 || j != 4)
                        count++;
                }
            }

            String[][] inputArray = null;
            int line = 0;

            BufferedReader input = new BufferedReader(new FileReader(file));
            String text;
            while ((text = input.readLine()) != null) {
                String[] measures = text.split(",");
                if (inputArray == null)
                    inputArray = new String[5][measures.length];
                for (int j = 0; j < measures.length; j++)
                    inputArray[line][j] = measures[j];
                line++;
            }

            String[][] outputArray = new String[30][inputArray[0].length + 1];
            String[] notes = { "Measure", "G#", "G", "F#", "F", "E", "D#", "D", "C#", "C", "B", "A#", "A", "G#", "G", "F#", "F", "E", "D#", "D", "C#", "C", "B", "A#", "A", "G#", "G", "F#", "F", "E" };
            for (int i = 0; i < outputArray.length; i++) {
                for (int j = 0; j < outputArray[0].length; j++) {
                    if (i == 0) {
                        if (j == 0)
                            outputArray[i][j] = "Measure";
                        else
                            outputArray[i][j] = j + "";
                    }
                    else if (j == 0) {
                        outputArray[i][j] = notes[i];
                    }
                }
            }

            for (int j = 0; j < inputArray[0].length; j++) {
                for (int i = 0; i < inputArray.length; i++) {
                    for (int k = 0; k < 6; k++) {
                        String s = inputArray[i][j].charAt(k) + "";
                        if (s.equals("o") || s.equals("*"))
                            outputArray[helperArray[i][k]][j + 1] = "o";
                        else if (outputArray[helperArray[i][k]][j + 1] == null)
                            outputArray[helperArray[i][k]][j + 1] = "";
                    }
                }
            }

            for (int i = 0; i < outputArray.length; i++) {
                for (int j = 0; j < outputArray[0].length; j++)
                    System.out.printf(j == 0 ? "%-16s" : "%-8s", outputArray[i][j]);
                System.out.println();
            }

        } catch (IOException e) {
            System.out.println("File not found.");
        }
    }

    public static void main(String[] args) {
        GuitarHero app = new GuitarHero();
    }

}
