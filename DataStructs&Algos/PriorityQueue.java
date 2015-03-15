public class PriorityQueue<T extends Comparable<? super T>> implements
       PriorityQueueInterface<T>, Gradable<T> {

    private Heap<T> back = new Heap<T>();

    @Override
    public void insert(T item) {
        if (item == null) { throw new IllegalArgumentException(); }
        back.add(item);
    }

    @Override
    public T findMin() {
        if (back.isEmpty()) { return null; }
        return back.peek();
    }

    @Override
    public T deleteMin() {
        return back.remove();
    }

    @Override
    public boolean isEmpty() {
        return back.isEmpty();
    }

    @Override
    public void makeEmpty() {
        back = new Heap<T>();
    }

    @Override
    public T[] toArray() {
        return back.toArray();
    }
}
