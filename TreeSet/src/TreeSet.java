public class TreeSet<E extends Comparable<E>> {

    private TreeNode<E> root;
    private int size;
    private String str;

    public TreeSet() {
        root = null;
        size = 0;
        str = "";
    }

    public E root() {
        return root.getValue();
    }

    public int size() {
        return size;
    }

    public void add(E value) {
        if (root == null) {
            root = new TreeNode<E>(value);
            size++;
        }
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
        if (root == null)
            return;
        if (contains(root, value)) {
            if (root.getLeft() == null && root.getRight() == null) {
                root = null;
                size = 0;
                return;
            }
            size--;
            root = remove(root, value);
        }
    }

    private TreeNode<E> remove(TreeNode<E> node, E value) {
        if (node == null)
            return null;
        if (value.compareTo(node.getValue()) < 0)
            node.setLeft(remove(node.getLeft(), value));
        else if (value.compareTo(node.getValue()) > 0)
            node.setRight(remove(node.getRight(), value));
        else {
            if (node.getLeft() == null && node.getRight() == null)
                node = null;
            else if (node.getLeft() == null)
                return node.getRight();
            else if (node.getRight() == null)
                return node.getLeft();
            else {
                E minValue = minValue(node.getRight());
                node.setValue(minValue);
                node.setRight(remove(node.getRight(), minValue));
            }
        }
        return node;
    }

    private boolean contains(TreeNode<E> node, E value) {
        if (node == null)
            return false;
        if (value.compareTo(node.getValue()) == 0)
            return true;
        if (value.compareTo(node.getValue()) < 0)
            return contains(node.getLeft(), value);
        if (value.compareTo(node.getValue()) > 0)
            return contains(node.getRight(), value);
        return false;
    }

    private E minValue(TreeNode<E> node) {
        if (node.getLeft() == null)
            return node.getValue();
        return minValue(node.getLeft());
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
        if (root == null || root.getRight() == null)
            return;
        rotateLeft(root);
    }

    private void rotateLeft(TreeNode<E> node) {
        TreeNode<E> temp = node.getRight();
        node.setRight(temp.getLeft());
        temp.setLeft(node);
        root = temp;
    }

    public void rotateRight() {
        if (root == null || root.getLeft() == null)
            return;
        rotateRight(root);
    }

    private void rotateRight(TreeNode<E> node) {
        TreeNode<E> temp = node.getLeft();
        node.setLeft(temp.getRight());
        temp.setRight(node);
        root = temp;
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

        public void setValue(E value) {
            this.value = value;
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
