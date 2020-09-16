import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class FantasyFootball {

    public FantasyFootball() {
        File file = new File("FFL Draft Averages.txt");
        try {
            BufferedReader input = new BufferedReader(new FileReader(file));
            String[] h = new String[9]; // headers
            ArrayList<FootballPlayer> players = new ArrayList<FootballPlayer>();
            String text;
            boolean firstLine = true;
            while ((text = input.readLine()) != null) {
                if (firstLine) {
                    h = text.split(";");
                    firstLine = false;
                }
                else {
                    players.add(new FootballPlayer(text.split(";")));
                }
            }
            Collections.sort(players);
            System.out.printf("%-8s %-24s %-8s %-8s %-8s %-8s %-8s %-8s %-8s %-16s %-8s \n",
                    h[0], h[1], h[2], h[3], h[4], h[5], h[6], h[7], h[8], h[9], "PickConsistency");
            for (FootballPlayer player : players)
                System.out.println(player);
        } catch (IOException e) {
            System.out.println("File not found.");
        }
    }

    public static void main(String[] args) {
        FantasyFootball app = new FantasyFootball();
    }

}
