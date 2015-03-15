/**
 * CircularLinkedList implementation
 * @author Carey Crook
 * @version 1.0
 */

public class CircularLinkedList<T> implements LinkedListInterface<T> {

    private Node<T> head;
    private Node<T> tail;
    private int size = 0;

    /**
     * This method simply traverses through the linked list and finds an indexed Node
     *
     * @param index the position of the Node that is being returned
     * @return a Node of type <T>
     */
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
        if (index < 0 || index > size()) { throw new IndexOutOfBoundsException(); }
        Node<T> aNode = new Node<T>(data);
        if (size == 0) {
            head = aNode;
            tail = aNode;
        }
        if (index == 0) {
            tail.setNext(aNode);
            aNode.setNext(head);
            head = aNode;
        } else if (index == size) {
            tail.setNext(aNode);
            aNode.setNext(head);
            tail = aNode;
        } else {
            Node<T> temp = getNode(index - 1);
            aNode.setNext(temp.getNext());
            temp.setNext(aNode);
        }
        size++;
    }

    @Override
    public T get(int index) {
        if (index < 0 || index > size()) { throw new IndexOutOfBoundsException(); }
        if (index == 0) {
            return head.getData();
        } else {
            return getNode(index).getData();
        }
    }

    @Override
    public T removeAtIndex(int index) {
        if (index < 0 || index >= size()) { throw new IndexOutOfBoundsException(); }
        if (size == 0) { return null; }
        Node<T> temp;
        if (index == 0) {
            temp = head;
            tail.setNext(head.getNext());
            head = head.getNext();
        } else {
            Node<T> tempB = getNode(index - 1);
            temp = tempB.getNext();
            tempB.setNext(temp.getNext());
            if (index == size - 1) { tail = tempB; }
        }
        size--;
        return temp.getData();
    }

    @Override
    public void addToFront(T t) {
        //addAtIndex(0, t);
        Node<T> temp = new Node<T>(t);
        if (size == 0) {
            tail = temp;
            head = temp;
        }
        tail.setNext(temp);
        temp.setNext(head);
        head = temp;
        size++;
    }

    @Override
    public void addToBack(T t) {
        Node<T> temp = new Node<T>(t);
        if (size == 0) {
            head = temp;
            tail = temp;
        }
        tail.setNext(temp);
        tail = temp;
        temp.setNext(head);
        size++;
    }

    @Override
    public T removeFromFront() {
        if (size == 0) { return null; }
        return removeAtIndex(0);
    }

    @Override
    public T removeFromBack() {
        if (size == 0) { return null; }
        else { return removeAtIndex(size - 1); }
    }

    @Override
    public T[] toList() {
        Object[] tempArray;
        tempArray = new Object[size];
        Node<T> tempNode = tail;
        for (int i = 0; i < size; i++) {
            tempNode = tempNode.getNext();
            tempArray[i] = tempNode.getData();
        }
        return (T[]) tempArray;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }
}

