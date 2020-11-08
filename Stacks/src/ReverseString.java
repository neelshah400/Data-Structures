import java.util.Stack;

public class ReverseString {

    public ReverseString() {
        reverse("Hello! My name is Neel Shah.");
        reverse("We the people of the United States, in order to form a more perfect union, establish justice, insure domestic tranquility, provide for the common defense, promote the general welfare, and secure the blessings of liberty to ourselves and our posterity, do ordain and establish this Constitution for the United States of America.");
    }

    public void reverse(String s) {
        System.out.print(s + " --> ");
        Stack<Character> stack = new Stack<Character>();
        while (s.length() > 0) {
            stack.push(s.charAt(0));
            s = s.substring(1);
        }
        while (!stack.isEmpty())
            System.out.print(stack.pop());
        System.out.println();
    }

    public static void main(String[] args) {
        ReverseString app = new ReverseString();
    }

}
