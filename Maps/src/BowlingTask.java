import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.PriorityQueue;
import java.util.TreeMap;

public class BowlingTask {

    public BowlingTask() {

        TreeMap<Integer, PriorityQueue<Bowler>> map = new TreeMap<Integer, PriorityQueue<Bowler>>();

        File file = new File("BowlingData.txt");
        try {
            BufferedReader input = new BufferedReader(new FileReader(file));
            String text;
            while ((text = input.readLine()) != null) {
                String[] items = text.split(" ");
                int score = Integer.parseInt(items[2]);
                PriorityQueue<Bowler> pq = map.containsKey(score) ? map.get(score) : new PriorityQueue<Bowler>();
                pq.add(new Bowler(items[1], items[0]));
                map.put(score, pq);
            }
        } catch (IOException e) {
            System.out.println("File not found.");
        }

        for (int score : map.keySet()) {
            System.out.println(score + ":");
            PriorityQueue<Bowler> pq = map.get(score);
            while (!pq.isEmpty())
                System.out.println("\t" + pq.poll());
        }

    }

    public static void main(String[] args) {
        BowlingTask app = new BowlingTask();
    }

    public class Bowler implements Comparable<Bowler> {

        private String lastName;
        private String firstName;

        public Bowler(String lastName, String firstName) {
            this.lastName = lastName;
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public String getFirstName() {
            return firstName;
        }

        public String toString() {
            return lastName + ", " + firstName;
        }

        public int compareTo(Bowler o) {
            if (lastName.compareToIgnoreCase(o.lastName) == 0)
                return firstName.compareToIgnoreCase(o.firstName);
            return lastName.compareToIgnoreCase(o.lastName);
        }

    }

}