import java.util.Stack;

public class DecimalToBinary {

    public DecimalToBinary() {
        convert(2);
        convert(10);
        convert(99);
        convert(463);
    }

    public void convert(int decimal) {
        System.out.print(decimal + " in decimal = ");
        Stack<Integer> stack = new Stack<Integer>();
        while (decimal != 0) {
            stack.push(decimal % 2);
            decimal /= 2;
        }
        while (!stack.isEmpty())
            System.out.print(stack.pop());
        System.out.println(" in binary");
    }

    public static void main(String[] args) {
        DecimalToBinary app = new DecimalToBinary();
    }

}
