import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Spiraling {

    public Spiraling() {
        File file = new File("Spiraling.txt");
        try {
            BufferedReader input = new BufferedReader(new FileReader(file));
            String text;
            while ((text = input.readLine()) != null) {
                int n = Integer.parseInt(text);
                String[][] array = new String[n][n];

                for (int i = 0; i < n; i++) {
                    for (int j = 0; j < n; j++) {
                        if (array[i][j] == null)
                            array[i][j] = "-";
                        System.out.print(array[i][j] + " ");
                    }
                    System.out.println();
                }
                System.out.println();
            }
        } catch (IOException e) {
            System.out.println("File not found.");
        }
    }

    public static void main(String[] args) {
        Spiraling app = new Spiraling();
    }

}
