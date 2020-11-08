import java.util.Stack;

public class StacksDemo {

    public StacksDemo() {

        // Create a new, empty stack
        Stack<Integer> lifo = new Stack<Integer>();

        // Let's add some items to it
        for (int i = 1; i <= 10; i++)
            lifo.push(i);

        // LIFO (Last In First Out) means reverse order
        System.out.println("The size of the Stack is " + lifo.size() + ".");
        System.out.println("What's on top? " + lifo.peek());
        System.out.println("Unstack the stack!");
        while (!lifo.isEmpty()) {
            System.out.print(lifo.pop());
            if (!lifo.empty())
                System.out.print(", ");
            else
                System.out.println();
        }

    }

    public static void main(String[] args) {
        StacksDemo app = new StacksDemo();
    }

}
