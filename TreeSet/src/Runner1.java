public class Runner1 {

    String f;

    public Runner1() {

        f = "%-32s%-32s%s\n";

        TreeSet<Character> set = new TreeSet<Character>();
        for (int i = 0; i < 26; i++) {
            char c = (char) (Math.random() * ('z' - 'a' + 1) + 'a');
            set.add(c);
        }

        char[] characters = characters(set.preOrder());
        char sum = 0;
        for (char c : characters)
            sum += c;
        char avg = (char) (sum / set.size());

        System.out.printf(f, "Original Set", "Average ASCII Value", (int) avg);
        System.out.printf(f, "Original Set", "Average ASCII Character", avg);
        System.out.printf(f, "Original Set", "Size", set.size());
        System.out.println()
        ;
        display("Original Set", set);
        display("PreOrder Copy", copy(set.preOrder()));
        display("InOrder Copy", copy(set.inOrder()));
        display("PostOrder Copy", copy(set.postOrder()));

        for (int i = 1; i <= 3; i++) {
            set.rotateRight();
            display("Rotate Right: " + i, set);
        }
        for (int i = 1; i <= 3; i++) {
            set.rotateLeft();
            display("Rotate Left: " + i, set);
        }

    }

    public void display(String name, TreeSet<Character> set) {
        System.out.printf(f, name, "PreOrder Traversal", set.preOrder());
        System.out.printf(f, name, "InOrder Traversal", set.inOrder());
        System.out.printf(f, name, "PostOrder Traversal", set.postOrder());
        System.out.println();
    }

    public TreeSet<Character> copy(String traversal) {
        char[] characters = characters(traversal);
        TreeSet<Character> copy = new TreeSet<Character>();
        for (char c : characters)
            copy.add(c);
        return copy;
    }

    public char[] characters(String traversal) {
        return traversal.substring(1, traversal.length() - 1).replaceAll(", ", "").toCharArray();
    }

    public static void main(String[] args) {
        Runner1 app = new Runner1();
    }

}