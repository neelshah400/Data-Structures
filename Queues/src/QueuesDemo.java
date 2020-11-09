import java.util.LinkedList;
import java.util.Queue;

public class QueuesDemo {

    public QueuesDemo() {
        Queue<Integer> queue = new LinkedList<Integer>();
        for(int x = 0; x < 10; x++)
            queue.add(x);
        System.out.print("[");
        while (!queue.isEmpty()) {
            System.out.print(queue.poll());
            if (queue.peek() != null)
                System.out.print(", ");
        }
        System.out.println("]");
    }

    public static void main(String[] args) {
        QueuesDemo app = new QueuesDemo();
    }

}