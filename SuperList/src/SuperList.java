public class SuperList<E> {

    private ListNode<E> root;
    private ListNode<E> end;

    public SuperList() {
        root = new ListNode<E>(null);
        end = new ListNode<E>(null);
        root.setNext(end);
        end.setPrevious(root);
    }

    public SuperList(E value) {
        root = new ListNode<E>(value);
        end = new ListNode<E>(null);
        root.setNext(end);
        end.setPrevious(root);
    }

    public ListNode<E> getRoot() {
        return root;
    }

    public void setRoot(ListNode<E> root) {
        this.root = root;
    }

    public ListNode<E> getEnd() {
        return end;
    }

    public void setEnd(ListNode<E> end) {
        this.end = end;
    }

    public void add(E value) {
        if (root.getValue() == null)
            root.setValue(value);
        else {
            ListNode<E> newNode = new ListNode<E>(value);
            ListNode<E> previousNode = end.getPrevious();
            ListNode<E> nextNode = end;
            newNode.setPrevious(previousNode);
            newNode.setNext(nextNode);
            previousNode.setNext(newNode);
            nextNode.setPrevious(newNode);
        }
    }

    public void add(int index, E value) throws ArrayIndexOutOfBoundsException {
        ListNode<E> newNode = new ListNode<E>(value);
        ListNode<E> previousNode = root;
        for (int i = 0; i < index - 1; i++)
            previousNode = previousNode.getNext();
        ListNode<E> nextNode = previousNode.getNext();
        newNode.setPrevious(previousNode);
        newNode.setNext(nextNode);
        previousNode.setNext(newNode);
        nextNode.setPrevious(newNode);
    }

    public String toString() {
        String str = "[";
        for (ListNode<E> node = root; node.hasNext(); node = node.getNext())
            str += node.getValue() + ", ";
        str += end.getPrevious().getValue() + "]";
        return str;
    }

    public class ListNode<E> {

        private E value;
        private ListNode<E> previous;
        private ListNode<E> next;

        public ListNode(E value) {
            this.value = value;
        }

        public E getValue() {
            return value;
        }

        public void setValue(E value) {
            this.value = value;
        }

        public ListNode<E> getPrevious() {
            return previous;
        }

        public void setPrevious(ListNode<E> previous) {
            this.previous = previous;
        }

        public boolean hasPrevious() {
            return previous != null && previous.getValue() != null;
        }

        public ListNode<E> getNext() {
            return next;
        }

        public void setNext(ListNode<E> next) {
            this.next = next;
        }

        public boolean hasNext() {
            return next != null && next.getValue() != null;
        }

    }

}
