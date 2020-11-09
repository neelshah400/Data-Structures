import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class GolferDemo {

    public GolferDemo() {
        useQueue();
        usePriorityQueue();
    }

    public void useQueue() {
        Queue<Golfer> q = new LinkedList<Golfer>();
        Golfer tiger = new Golfer("Tiger Woods", 108);
        Golfer phil = new Golfer("Phil Mickelson", 61);
        Golfer ernie = new Golfer("Ernie Els", 69);
        Golfer dustin = new Golfer("Dustin Johnson", 66);
        q.add (tiger);
        q.add (phil);
        q.add (ernie);
        q.add(dustin);
        while (!q.isEmpty()) {
            Golfer golfer = q.poll();
            System.out.println(golfer);
        }
        System.out.println();
    }

    public void usePriorityQueue() {
        PriorityQueue<Golfer> pq = new PriorityQueue<Golfer>();
        Golfer tiger = new Golfer ("Tiger Woods", 88);
        Golfer al = new Golfer ("Al Mickelson", 57);
        Golfer ernie = new Golfer ("Ernie Els", 57);
        Golfer dustin = new Golfer ("Dustin Johnson", 64);
        pq.add (tiger);
        pq.add (dustin);
        pq.add (ernie);
        pq.add (al);
        while (!pq.isEmpty()) {
            Golfer golfer = pq.poll();
            System.out.println(golfer);
        }
        System.out.println();
    }

    public static void main(String[] args) {
        GolferDemo app = new GolferDemo();
    }

    public class Golfer implements Comparable<Golfer> {

        private String name;
        private int score;

        public Golfer(String name, int score) {
            this.name = name;
            this.score = score;
        }

        public String toString() {
            return name;
        }

        public int compareTo(Golfer otherGolfer) {
            int a = score;
            int b = otherGolfer.score;
            if (a > b)
                return 1;
            if (a < b)
                return -1;
            return name.compareTo(otherGolfer.name);
        }

    }

}