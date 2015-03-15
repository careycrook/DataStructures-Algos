import java.util.HashSet;
import java.util.Set;

public class SkipList<T extends Comparable<? super T>>
    implements SkipListInterface<T> {
    private CoinFlipper coinFlipper;
    private int size;
    private Node<T> head;


    /**
     * constructs a SkipList object that stores data in ascending order
     * when an item is inserted, the flipper is called until it returns a tails
     * if for an item the flipper returns n heads, the corresponding node has
     * n + 1 levels
     *
     * @param coinFlipper the source of randomness
     */
    public SkipList(CoinFlipper coinFlipper) {
        size = 0;
        head = new Node<T>(null, 0);
        this.coinFlipper = coinFlipper;
    }

    /**
     * Used to find the head node at a given level
     * @param level the level of the head node that will be returned
     * @return the head node at the given level
     */

    private Node<T> getHeadAt(int level) {
        Node<T> temp = head;
        while (temp.getLevel() > level) {
            temp = temp.getDown();
        }
        return temp;
    }

    @Override
    public T first() {
        if (size == 0) { return null; }
        return getHeadAt(0).getNext().getData();
    }

    @Override
    public T last() {
        if (size == 0) { return null; }
        Node<T> temp = head;
        while (temp.getDown() != null || temp.getNext() != null) {
            if (temp.getNext() != null) {
                temp = temp.getNext();
            } else if (temp.getDown() != null) {
                temp = temp.getDown();
            }
        }
        return temp.getData();
    }

    @Override
    public boolean contains(T data) {
        return (get(data) != null);
    }

    @Override
    public void put(T data) {
        if (data == null) { throw new IllegalArgumentException(); }
        int height = 0;
        while (coinFlipper.flipCoin().equals(CoinFlipper.Coin.HEADS)) {
            height++;
        }
        while (head.getLevel() <= height) {
            head = new Node<T>(null, head.getLevel() + 1, null, null, head);
        }
        Node<T> temp = head.getDown();
        Node<T> last = null;
        boolean flag = true;
        while (flag) {
            if (temp.getNext() != null && data.compareTo(
                    temp.getNext().getData()) > 0) {
                temp = temp.getNext();
            } else if (temp.getLevel() > height) {
                temp = temp.getDown();
            } else {
                Node<T> res = new Node<T>(
                        data, temp.getLevel(), temp.getNext(), last, null);
                if (last != null) {
                    last.setDown(res);
                }
                temp.setNext(res);
                last = res;
                if (temp.getDown() == null) {
                    flag = false;
                } else {
                    temp = temp.getDown();
                }
            }
        }
        size++;
    }

    @Override
    public T get(T data) {
        if (data == null) { throw new IllegalArgumentException(); }
        Node<T> temp = head;
        boolean flag = true;
        while (flag) {
            if (temp.getNext() != null && temp.getNext()
                    .getData().equals(data)) {
                return temp.getNext().getData();
            } else if (temp.getNext() != null && temp.getNext().getData()
                    .compareTo(data) < 0) {
                temp = temp.getNext();
            } else if (temp.getDown() != null) {
                temp = temp.getDown();
            } else {
                return null;
            }
        }
        return null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        size = 0;
        head = new Node<T>(null, 0);
    }

    @Override
    public Set<T> dataSet() {
        Set<T> res = new HashSet<T>();
        Node<T> temp = getHeadAt(0);
        while (temp.getNext() != null) {
            res.add(temp.getNext().getData());
            temp = temp.getNext();
        }
        return res;
    }

    @Override
    public T remove(T data) {
        if (data == null) { throw new IllegalArgumentException(); }
        if (size == 0) { return null; }
        Node<T> temp = head.getDown();
        boolean flag = true;
        T res = null;
        while (flag) {
            if (temp.getNext() != null && temp.getNext()
                    .getData().equals(data)) {
                res = temp.getNext().getData();
                temp.setNext(temp.getNext().getNext());
            } else if (temp.getNext() != null && data.compareTo(temp.getNext()
                    .getData()) > 0) {
                temp = temp.getNext();
            } else if (temp.getDown() != null) {
                temp = temp.getDown();
            } else {
                flag = false;
            }
        }
        size--;
        return res;
    }
}
