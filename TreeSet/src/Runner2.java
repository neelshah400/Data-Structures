import java.util.ArrayList;

public class Runner2 {

    String f;

    public Runner2() {

        f = "%-32s%-32s%s\n";

        String str1 = "";
        TreeSet<Integer> set1 = new TreeSet<Integer>();
        for (int i = 0; i < 30; i++) {
            int n = (int) (Math.random() * 100) + 1;
            str1 += n + ", ";
            set1.add(n);
        }
        str1 = "[" + str1.substring(0, str1.length() - 2) + "]";
        System.out.printf(f, "Set 1 (Original Set)", "Values in Order of Generation", str1);
        System.out.printf(f, "Set 1 (Original Set)", "Size", set1.size());
        System.out.println();
        display("Set 1 (PreOrder Copy)", copy(numbers(set1.preOrder())), new String[] {
                "Initial value is smaller than final value; not in perfect ascending order",
                "Sorted in ascending order",
                "Initial value is smaller than final value; not in perfect ascending order"
        });
        display("Set 1 (InOrder Copy)", copy(numbers(set1.inOrder())), new String[] {
                "Sorted in ascending order",
                "Sorted in ascending order",
                "Sorted in descending order"
        });
        display("Set 1 (PostOrder Copy)", copy(numbers(set1.postOrder())), new String[] {
                "Initial value is smaller than final value; not in perfect ascending order",
                "Sorted in ascending order",
                "Initially ascending; then descending (after reaching maximum value)"
        });
        System.out.println("-".repeat(150) + "\n");

        String str2 = "";
        TreeSet<String> set2 = new TreeSet<String>();
        for (int i = 0; i < 20; i++) {
            char c = (char) (Math.random() * ('z' - 'a' + 1) + 'a');
            str2 += c + ", ";
            set2.add(c + "");
        }
        str2 = "[" + str2.substring(0, str2.length() - 2) + "]";
        System.out.printf(f, "Strings (Original Set)", "Values in Order of Generation", str2);
        System.out.println();
        display("Set 2 (Original Set)", set2);
        for (int i = 1; i <= 3; i++) {
            set2.rotateRight();
            display("Set 2 (Rotate Right: " + i + ")", set2);
        }
        for (int i = 1; i <= 3; i++) {
            set2.rotateLeft();
            display("Set 2 (Rotate Left: " + i + ")", set2);
        }
        System.out.println("-".repeat(150) + "\n");

        TreeSet<Integer> set3 = new TreeSet<Integer>();
        ArrayList<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i < 10; i++) {
            int n = (int) (Math.random() * 10) + 1;
            set3.add(n);
            list.add(n);
        }
        display("Set 3 (Original Set)", set3, list);
        while (!list.isEmpty()) {
            int index = (int) (Math.random() * list.size());
            int value = list.remove(index);
            set3.remove(value);
            display("Set 3 (Remove Index " + index + ": " + value + ")", set3, list);
        }

    }

    public void display(String name, TreeSet<Integer> set, ArrayList<Integer> list) {
        System.out.printf(f, name, "ArrayList", list);
        System.out.printf(f, name, "PreOrder Traversal", set.preOrder());
        System.out.printf(f, name, "InOrder Traversal", set.inOrder());
        System.out.printf(f, name, "PostOrder Traversal", set.postOrder());
        System.out.println();
    }

    public void display(String name, TreeSet<String> set) {
        System.out.printf(f, name, "PreOrder Traversal", set.preOrder());
        System.out.printf(f, name, "InOrder Traversal", set.inOrder());
        System.out.printf(f, name, "PostOrder Traversal", set.postOrder());
        System.out.println();
    }

    public void display(String name, TreeSet<Integer> set, String[] messages) {
        System.out.printf(f, name, "PreOrder Traversal", set.preOrder());
        System.out.printf(f, "", "", messages[0]);
        System.out.printf(f, name, "InOrder Traversal", set.inOrder());
        System.out.printf(f, "", "", messages[1]);
        System.out.printf(f, name, "PostOrder Traversal", set.postOrder());
        System.out.printf(f, "", "", messages[2]);
        System.out.println();
    }

    public TreeSet<Integer> copy(int[] numbers) {
        TreeSet<Integer> copy = new TreeSet<Integer>();
        for (int n : numbers)
            copy.add(n);
        return copy;
    }

    public String[] strings(String traversal) {
        return traversal.substring(1, traversal.length() - 1).split(", ");
    }

    public int[] numbers(String traversal) {
        String[] components = traversal.substring(1, traversal.length() - 1).split(", ");
        int[] numbers = new int[components.length];
        for (int i = 0; i < components.length; i++)
            numbers[i] = Integer.parseInt(components[i]);
        return numbers;
    }

    public static void main(String[] args) {
        Runner2 app = new Runner2();
    }

}