import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class WordQueue {

    public WordQueue() {

        Queue<Word> q = new LinkedList<Word>();
        PriorityQueue<Word> pq = new PriorityQueue<Word>();

        File file = new File("Words.txt");
        try {
            BufferedReader input = new BufferedReader(new FileReader(file));
            String text;
            while ((text = input.readLine()) != null) {
                text = text.replaceAll("[^a-zA-Z\\d\\s]", "");
                String[] words = text.split(" ");
                for (String s : words) {
                    Word word = new Word(s);
                    q.add(word);
                    pq.add(word);
                }
            }
        } catch (IOException e) {
            System.out.println("File not found.");
        }

        while (!q.isEmpty()) {
            System.out.printf("%-48s %-48s \n", q.poll(), pq.poll());
        }

    }

    public static void main(String[] args) {
        WordQueue app = new WordQueue();
    }

    public class Word implements Comparable<Word> {

        private String text;

        public Word(String text) {
            this.text = text;
        }

        public String toString() {
            return text;
        }

        public int compareTo(Word otherWord) {
//            return -1 * text.compareTo(otherWord.text); // ascending
            return -1 * text.toLowerCase().compareTo(otherWord.text.toLowerCase()); // descending
        }

    }

}
