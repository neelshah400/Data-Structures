public class TreeSetRunner {

    public TreeSetRunner() {

        String f = "%-16s%s\n";

        TreeSet<Integer> tree = new TreeSet<Integer>();
        tree.add(10);
        tree.add(6);
        tree.add(12);
        tree.add(3);
        tree.add(7);
        tree.add(15);
        tree.add(4);
        tree.add(5);
        tree.add(10);
        tree.add(11);
        tree.add(19);

        System.out.printf(f, "inOrder:", tree.inOrder());
        System.out.printf(f, "preOrder:", tree.preOrder());
        System.out.printf(f, "postOrder:", tree.postOrder());

        System.out.println();

        // later stuff
        TreeSet<Character> set = new TreeSet<Character>();
        for (int i = 0; i < 26; i++) {
            char c = (char) (Math.random() * ('z' - 'a' + 1) + 'a');
            set.add(c);
        }

    }

    public static void main(String[] args) {
        TreeSetRunner app = new TreeSetRunner();
    }

}