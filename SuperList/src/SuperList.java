import java.util.EmptyStackException;

public class SuperList<E> {

    private ListNode<E> root;
    private ListNode<E> end;
    private int size;

    public SuperList() {
        root = null;
        end = null;
        size = 0;
    }

    public SuperList(E value) {
        root = new ListNode<E>(value);
        end = root;
        size = 1;
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
        if (isEmpty()) {
            root = new ListNode<E>(value);
            end = root;
        } else {
            ListNode<E> newNode = new ListNode<E>(value);
            newNode.setPrevious(end);
            end.setNext(newNode);
            end = newNode;
        }
        size++;
    }

    public void add(int index, E value) {
        if (index < 0 || index > size)
            throw new ArrayIndexOutOfBoundsException();
        ListNode<E> newNode = new ListNode<E>(value);
        if (index == 0) {
            if (isEmpty()) {
                root = new ListNode<E>(value);
                end = root;
            } else {
                newNode.setNext(root);
                root.setPrevious(newNode);
                root = newNode;
            }
            size++;
        } else if (index == size)
            add(value);
        else {
            ListNode<E> previousNode = root;
            for (int i = 0; i < index - 1; i++)
                previousNode = previousNode.getNext();
            ListNode<E> nextNode = previousNode.getNext();
            newNode.setPrevious(previousNode);
            newNode.setNext(nextNode);
            previousNode.setNext(newNode);
            nextNode.setPrevious(newNode);
            size++;
        }
    }

    public void push(E value) {
        add(value);
    }

    public E pop() {
        if (isEmpty())
            throw new EmptyStackException();
        E value = end.getValue();
        if (end.hasPrevious()) {
            end = end.getPrevious();
            end.setNext(null);
        } else {
            end = null;
            root = null;
        }
        size--;
        return value;
    }

    public E poll() {
        if (isEmpty())
            return null;
        E value = root.getValue();
        if (root.hasNext()) {
            root = root.getNext();
            root.setPrevious(null);
        } else {
            root = null;
            end = null;
        }
        size--;
        return value;
    }

    // In the instructions, you named the methods stackPeek() and queuePeek(),
    // but in the sample runners you provided, you called them peekStack() and
    // peekQueue(). I decided to make duplicate versions for ease of testing
    // the code.

    public E stackPeek() {
        return end == null ? null : end.getValue();
    }

    public E peekStack() {
        return stackPeek();
    }

    public E queuePeek() {
        return root == null ? null : root.getValue();
    }

    public E peekQueue() {
        return queuePeek();
    }

    public E get(int index) {
        if (index < 0 || index > size - 1)
            throw new ArrayIndexOutOfBoundsException();
        ListNode<E> node = root;
        for (int i = 0; i < index; i++) {
            if (node.hasNext())
                node = node.getNext();
        }
        return node.getValue();
    }

    public int size() {
        return size;
    }

    public E remove(int index) {
        if (index < 0 || index > size - 1)
            throw new ArrayIndexOutOfBoundsException();
        else if (index == 0)
            return poll();
        else if (index == size - 1)
            return pop();
        ListNode<E> node = root;
        for (int i = 0; i < index; i++) {
            if (node.hasNext())
                node = node.getNext();
        }
        ListNode<E> previousNode = node.getPrevious();
        ListNode<E> nextNode = node.getNext();
        previousNode.setNext(nextNode);
        nextNode.setPrevious(previousNode);
        size--;
        return node.getValue();
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void clear() {
        root = null;
        end = null;
        size = 0;
    }

    public boolean contains(E value) {
        for (ListNode<E> node = root; node.hasNext(); node = node.getNext()) {
            if (node.getValue().equals(value))
                return true;
        }
        return end.getValue().equals(value);
    }

    public String toString() {
        String str = "[";
        if (!isEmpty()) {
            for (ListNode<E> node = root; node.hasNext(); node = node.getNext())
                str += node.getValue() + ", ";
            str += end.getValue();
        }
        str += "]";
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
