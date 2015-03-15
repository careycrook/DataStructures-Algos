/**
 * Doubly linked list implementation
 * @author Carey Crook
 * @version 1.1
 */
public class DoublyLinkedList<T> implements LinkedListInterface<T> {

    private int size = 0;
    private Node<T> head = null;
    private Node<T> tail = null;

    private Node<T> getNode(int index) {
        if (index == 0) {
            return head;
        } else if (index == size - 1) {
            return tail;
        } else {
            Node<T> temp = head;
            for (int i = 0; i < index; i++) {
                temp = temp.getNext();
            }
            return temp;
        }
    }

    @Override
    public void addAtIndex(int index, T data) {
        if (index < 0
                || index > size()) { throw new IndexOutOfBoundsException(); }
        Node<T> aNode = new Node<T>(data);
        if (size == 0) {
            head = aNode;
            tail = aNode;
            aNode.setNext(null);
            aNode.setPrevious(null);
        }
        if (index == 0) {
            aNode.setNext(head);
            aNode.setPrevious(null);
            head.setPrevious(aNode);
            head = aNode;
        } else if (index == size) {
            aNode.setNext(null);
            aNode.setPrevious(tail);
            tail.setNext(aNode);
            tail = aNode;
        } else {
            Node<T> temp = getNode(index);
            temp.getPrevious().setNext(aNode);
            aNode.setPrevious(temp.getPrevious());
            aNode.setNext(temp);
            temp.setPrevious(aNode);
        }
        size++;
    }

    @Override
    public T get(int index) {
        if (index < 0
                || index > size()) { throw new IndexOutOfBoundsException(); }
        if (index == 0) {
            return head.getData();
        } else {
            return getNode(index).getData();
        }
    }

    @Override
    public T removeAtIndex(int index) {
        if (index < 0
                || index >= size()) { throw new IndexOutOfBoundsException(); }
        if (size == 0) { return null; }
        Node<T> temp;
        if (size == 1) {
            temp = head;
            head = null;
            tail = null;
            size--;
            return temp.getData();
        }
        if (index == 0) {
            temp = head;
            head.getNext().setPrevious(null);
            head = head.getNext();
        } else if (index == size - 1) {
            temp = tail;
            tail.getPrevious().setNext(null);
            tail = tail.getPrevious();
        } else {
            Node<T> tempB = getNode(index - 1);
            temp = tempB.getNext();
            tempB.setNext(temp.getNext());
            temp.getNext().setPrevious(tempB);
        }
        size--;
        return temp.getData();
    }

    @Override
    public void addToFront(T t) {
        addAtIndex(0, t);
    }

    @Override
    public void addToBack(T t) {
        addAtIndex(size, t);
    }

    @Override
    public T removeFromFront() {
        if (size == 0) { return null; }
        return removeAtIndex(0);
    }

    @Override
    public T removeFromBack() {
        if (size == 0) { return null; }
        return removeAtIndex(size - 1);
    }

    @Override
    public Object[] toArray() {
        Object[] tempArray;
        tempArray = new Object[size];
        if (size == 0) { return tempArray; }
        Node<T> tempNode = head;
        int counter = 0;
        do {
            tempArray[counter] = tempNode.getData();
            tempNode = tempNode.getNext();
            counter++;
        } while (counter != size);
        return tempArray;
    }

    @Override
    public boolean isEmpty() { return (size == 0); }

    @Override
    public int size() { return size; }

    @Override
    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }
}
