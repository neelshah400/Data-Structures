public class TreeSet<E extends Comparable<E>> {

    private TreeNode<E> root;
    private int size;
    private String str;

    public TreeSet() {
        root = null;
        size = 0;
        str = "";
    }

    public void add(E value) {
        if (root == null)
            root = new TreeNode<E>(value);
        else
            add(root, value);
    }

    private void add(TreeNode<E> node, E value) {
        if (value.compareTo(node.getValue()) == 0)
            return;
        if (value.compareTo(node.getValue()) < 0) {
            if (node.getLeft() == null) {
                node.setLeft(new TreeNode<E>(value));
                size++;
                return;
            }
            add(node.getLeft(), value);
        }
        if (value.compareTo(node.getValue()) > 0) {
            if (node.getRight() == null) {
                node.setRight(new TreeNode<E>(value));
                size++;
                return;
            }
            add(node.getRight(), value);
        }
    }

    public void remove(E value) {

    }

    public int size() {
        return size;
    }

    public E root() {
        return null;
    }

    public String inOrder() {
        if (size == 0)
            return "[]";
        str = "";
        inOrder(root);
        return "[" + str.substring(0, str.length() - 2) + "]";
    }

    private void inOrder(TreeNode<E> node) {
        if (node != null) {
            inOrder(node.getLeft());
            str += node.getValue() + ", ";
            inOrder(node.getRight());
        }
    }

    public String preOrder() {
        if (size == 0)
            return "[]";
        str = "";
        preOrder(root);
        return "[" + str.substring(0, str.length() - 2) + "]";
    }

    private void preOrder(TreeNode<E> node) {
        if (node != null) {
            str += node.getValue() + ", ";
            preOrder(node.getLeft());
            preOrder(node.getRight());
        }
    }

    public String postOrder() {
        if (size == 0)
            return "[]";
        str = "";
        postOrder(root);
        return "[" + str.substring(0, str.length() - 2) + "]";
    }

    private void postOrder(TreeNode<E> node) {
        if (node != null) {
            postOrder(node.getLeft());
            postOrder(node.getRight());
            str += node.getValue() + ", ";
        }
    }

    public void rotateLeft() {

    }

    private void rotateLeft(TreeNode<E> node) {

    }

    public void rotateRight() {

    }

    private void rotateRight(TreeNode<E> node) {

    }

    public class TreeNode<E> {

        private E value;
        private TreeNode<E> left;
        private TreeNode<E> right;

        public TreeNode(E value) {
            this.value = value;
            left = null;
            right = null;
        }

        public E getValue() {
            return value;
        }

        public TreeNode<E> getLeft() {
            return left;
        }

        public void setLeft(TreeNode<E> left) {
            this.left = left;
        }

        public TreeNode<E> getRight() {
            return right;
        }

        public void setRight(TreeNode<E> right) {
            this.right = right;
        }

        public String toString() {
            return value.toString();
        }

    }

}
