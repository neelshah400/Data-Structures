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

                for (int row = 0; row < n; row++) {
                    for (int col = 0; col < n; col++)
                        array[row][col] = "-";
                }

                int startRow = 0;
                int startCol = 0;
                int endRow = n - 1;
                int endCol = n - 1;

                while (startRow <= endRow && startCol <= endCol) {

                    // right
                    for (int col = startCol; col <= endCol; col++)
                        array[startRow][col] = "*";
                    startRow++;
                    if (startCol >= 1)
                        startCol++;

                    // down
                    for (int row = startRow; row <= endRow; row++)
                        array[row][endCol] = "*";
                    endCol--;
                    startRow++;

                    // left
                    for(int col = endCol; col >= startCol; col--)
                        array[endRow][col] = "*";
                    endRow--;
                    endCol--;

                    // up
                    for (int row = endRow; row >= startRow; row--)
                        array[row][startCol] = "*";
                    startCol++;
                    endRow--;

                }

                if (n % 4 == 2)
                    array[n / 2][(n - 1) / 2] = "-";

                for (int row = 0; row < n; row++) {
                    for (int col = 0; col < n; col++) {
                        System.out.print(array[row][col] + "  ");
                    }
                    System.out.println();
                }

            }
        } catch (IOException e) {
            System.out.println("File not found.");
        }
    }

    public static void main(String[] args) {
        Spiraling app = new Spiraling();
    }

}
