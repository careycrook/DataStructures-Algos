public class Heap<T extends Comparable<? super T>> implements HeapInterface<T> {

    private T tempData;

    /**
     * This is the private class for the array that backs
     * the heap
     */
    private class HeapArray {

        private int size, count;
        private T[] back = (T[]) new Comparable[10];
        private T[] temp;

        /**
         * Constructor for the private class HeapArray
         */
        public HeapArray() {
            size = 10;
            count = 1;
            back[0] = null;
        }

        /**
         * Used to get the left child of the index.
         * @param index the parent's index
         * @return the node to the left of the index. Returns null if
         * the node does not have a left child.
         */
        public T getLeft(int index) {
            if ((2 * index) >= size) { return null; }
            return get(2 * index);
        }

        /**
         * Used to get the right child of the index.
         * @param index the parent's index
         * @return the node to the right of the index. Returns null if
         * the node does not have a right child.
         */
        public T getRight(int index) {
            if (((2 * index) + 1) >= size) { return null; }
            return get((2 * index) + 1);
        }

        /**
         * Used to access the data at a certain index in the HeapArray
         * @param index the index of desired data
         * @return the data at back[index]
         */
        public T get(int index) {
            if (index >= size) { throw new IndexOutOfBoundsException(); }
            return back[index];
        }

        /**
         * Used to set the data of an index
         * @param index the index that is being reset
         * @param data the desired value at the index
         */
        public void set(int index, T data) {
            if (index >= size) { throw new IndexOutOfBoundsException(); }
            back[index] = data;
        }

        /**
         * Adds data to the array at the next open spot. If the array is full,
         * doubles the size of the array.
         * @param data the data that is being added to the array
         */
        public void append(T data) {
            if (count == size) {
                temp = back;
                size = size * 2;
                back = (T[]) new Comparable[size];
                back[0] = null;
                for (int i = 1; i < count; i++) {
                    back[i] = temp[i];
                }
            }
            back[count] = data;
            count++;
        }

        /**
         * Removes the last non-null value in the array.
         * @return the data that is removed. Returns null if the array is empty
         */
        public T removeLast() {
            if (count == 1) {
                return null;
            }
            tempData = back[count - 1];
            back[count - 1] = null;
            count--;
            return tempData;
        }

        /**
         * Gives the size of the array minus the number of null spaces starting
         * from the back
         * @return the size of the array
         */
        public int size() {
            return count;
        }

        /**
         * Gives the backing array of the HeapArray
         * @return the backing array
         */
        public T[] toArray() { return back; }

    }

    /**
     * Recursively moves the indexed node up the tree to its correct
     * position. Used to add to a heap
     * @param hAr the HeapArray that is going to be heapified
     * @param index the index that is being upheaped
     */
    private void upHeap(HeapArray hAr, int index) {
        if (index == 0) { return; }
        if (hAr.get(index) == null) { return; }
        if (index == 1) { return; }
        if (index % 2 == 0) {
            if (hAr.get(index).compareTo(hAr.get(index / 2)) < 0) {
                tempData = hAr.get(index / 2);
                hAr.set(index / 2, hAr.get(index));
                hAr.set(index, tempData);
                upHeap(hAr, index / 2);
            }
        } else if (index % 2 == 1) {
            if (hAr.get(index).compareTo(hAr.get((index - 1) / 2)) < 0) {
                tempData = hAr.get((index - 1) / 2);
                hAr.set((index - 1) / 2, hAr.get(index));
                hAr.set(index, tempData);
                upHeap(hAr, (index - 1) / 2);
            }
        }
    }

    /**
     * Recursively moves the indexed node down the tree to its correct
     * position. Used to remove from a heap
     * @param hAr the HeapArray that is going to be heapified
     * @param index the index that is being downheaped
     */
    private void downHeap(HeapArray hAr, int index) {
        if (index == 0) { return; }
        if (hAr.getLeft(index) == null
                && hAr.getRight(index) == null) {
            return;
        } else if (hAr.getRight(index) == null) {
            if (hAr.get(index).compareTo(hAr.getLeft(index)) > 0) {
                tempData = hAr.get(index);
                hAr.set(index, hAr.getLeft(index));
                hAr.set(index * 2, tempData);
                downHeap(hAr, index * 2);
            }
        } else {
            if (hAr.getRight(index).compareTo(hAr.getLeft(index)) >= 0) {
                if (hAr.get(index).compareTo(hAr.getLeft(index)) > 0) {
                    tempData = hAr.get(index);
                    hAr.set(index, hAr.getLeft(index));
                    hAr.set(index * 2, tempData);
                    downHeap(hAr, index * 2);
                }
            } else {
                if (hAr.get(index).compareTo(hAr.getRight(index)) > 0) {
                    tempData = hAr.get(index);
                    hAr.set(index, hAr.getRight(index));
                    hAr.set((index * 2) + 1, tempData);
                    downHeap(hAr, (index * 2) + 1);
                }
            }
        }
    }

    private HeapArray hR = new HeapArray();

    @Override
    public void add(T item) {
        if (item == null) { throw new IllegalArgumentException(); }
        hR.append(item);
        upHeap(hR, hR.size() - 1);
    }

    @Override
    public boolean isEmpty() {
        return (hR.size() == 1);
    }

    @Override
    public T peek() {
        if (hR.size() != 1) {
            return hR.get(1);
        } else { return null; }
    }

    @Override
    public T remove() {
        if (hR.size() == 1) { return null; }
        T res = peek();
        if (hR.size() == 2) {
            hR.removeLast();
            return res;
        }
        tempData = hR.get(hR.size() - 1);
        hR.set(1, tempData);
        hR.removeLast();
        downHeap(hR, 1);
        return res;
    }

    @Override
    public int size() {
        return hR.size() - 1;
    }

    @Override
    public T[] toArray() {
        return hR.toArray();
    }
}
