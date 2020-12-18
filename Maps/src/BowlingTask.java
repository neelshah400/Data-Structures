import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
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

        String str = "{";
        Iterator<Integer> itr = map.keySet().iterator();
        while (itr.hasNext()) {
            int key = itr.next();
            str += key + "=" + pqToString(map.get(key)) + (itr.hasNext() ? ", " : "}");
        }
        System.out.println(str);

        System.out.println("+++++++++++++++++++++++++++++++++++");
        System.out.println("+++++++++++++++KEYS++++++++++++++++");
        System.out.println("+++++++++++++++++++++++++++++++++++");
        for (int key : map.keySet())
            System.out.println(key);

        System.out.println("+++++++++++++++++++++++++++++++++++");
        System.out.println("+++++++++++++ENTRY SET+++++++++++++");
        System.out.println("+++++++++++++++++++++++++++++++++++");
        for (int key : map.keySet())
            System.out.println(key + "=" + pqToString(map.get(key)));

        System.out.println("+++++++++++++++++++++++++++++++++++");
        System.out.println("++++++++++++++VALUES+++++++++++++++");
        System.out.println("+++++++++++++++++++++++++++++++++++");
        for (PriorityQueue<Bowler> pq : map.values())
            System.out.println(pqToString(pq));

    }

    public String pqToString(PriorityQueue<Bowler> pq) {
        PriorityQueue<Bowler> copy = new PriorityQueue<Bowler>(pq);
        String str = "[";
        while (!copy.isEmpty())
            str += copy.poll() + (copy.isEmpty() ? "]" : ", ");
        return str;
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
            return firstName + " " + lastName;
        }

        public int compareTo(Bowler o) {
            if (lastName.compareToIgnoreCase(o.lastName) == 0)
                return firstName.compareToIgnoreCase(o.firstName);
            return lastName.compareToIgnoreCase(o.lastName);
        }

    }

}