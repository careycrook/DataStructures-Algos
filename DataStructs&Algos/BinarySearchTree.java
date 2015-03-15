import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.List;

public class BinarySearchTree<T extends Comparable<T>> implements BSTInterface<T> {

    private Node<T> root, tempNode;
    private int size = 0;
    private int counter;
    private ArrayList<T> list;
    private Queue<Node<T>> queue;

    /**
     * Recursive private method that searches a BST and returns a node that
     * contains a given data. Returns null if the Node is not found.
     * @param data the data that is being searched for
     * @param here the current node in the tree
     * @return the node that contains data. Null if the data is not found
     */
    private Node<T> search(T data, Node<T> here) {
        if (here == null) { return null; }
        if (here.getData().equals(data)) {
            return here;
        } else if (here.getData().compareTo(data) > 0) {
            return search(data, here.getLeft());
        } else {
            return search(data, here.getRight());
        }
    }

    /**
     * Recursive add method. This is used as the backing for the public
     * add method.
     * @param here the current node in the tree
     * @param data the data that is being added to the tree
     */
    private void add(Node<T> here, T data) {
        if (data.compareTo(here.getData()) < 0) {
            if (here.getLeft() != null) {
                add(here.getLeft(), data);
            } else {
                here.setLeft(new Node<T>(data));
            }
        } else if (data.compareTo(here.getData()) > 0) {
            if (here.getRight() != null) {
                add(here.getRight(), data);
            } else {
                here.setRight(new Node<T>(data));
            }
        }
    }

    /**
     * Recursive method that is used as the backing for the public
     * remove method. Mainly useful for its Pointer Reassignment
     * which allows for easy access to parent nodes. Returns the
     * new child node for each parent in the tree that is effected
     * by the removal of a node.
     * @param data the data that is contained by the node that is
     *             being removed
     * @param here the current node in the tree
     * @return The updated child nodes for each node in the branch
     * effected by the removal
     */
    private Node<T> remove(T data, Node<T> here) {
        if (here == null) { return null; }
        if (here.getData().equals(data)) {
            if ((here.getLeft() == null) && (here.getRight() == null)) {
                return null;
            } else if (here.getRight() == null) {
                return here.getLeft();
            } else if (here.getLeft() == null) {
                return here.getRight();
            } else {
                tempNode = highestNode(here.getLeft());
                here.setData(tempNode.getData());
                here.setLeft(remove(here.getData(), here.getLeft()));
                return here;
            }
        } else if (here.getData().compareTo(data) > 0) {
            here.setLeft(remove(data, here.getLeft()));
            return here;
        } else {
            here.setRight(remove(data, here.getRight()));
            return here;
        }
    }

    /**
     * Recursive method used simply to find the highest data
     * below a given node. Mainly useful for finding predcessors
     * @param here the current node in the tree
     * @return the node with the highest value starting with the given node
     */
    private Node<T> highestNode(Node<T> here) {
        if (here.getRight() != null) {
            return highestNode(here.getRight());
        } else { return here; }
    }

    /**
     * Recursive method used to append nodes to a list in preorder
     * @param here the current node in the tree
     * @param data the list that is being appended to
     * @return the new list after being added to
     */
    private List<T> preorder(Node<T> here, List<T> data) {
        if (here != null) {
            data.add(here.getData());
            preorder(here.getLeft(), data);
            preorder(here.getRight(), data);
        }
        return data;
    }

    /**
     * Recursive method used to append nodes to a list in postorder
     * @param here the current node in the tree
     * @param data the list that is being appended to
     * @return the new list after being added to
     */
    private List<T> postorder(Node<T> here, List<T> data) {
        if (here != null) {
            postorder(here.getLeft(), data);
            postorder(here.getRight(), data);
            data.add(here.getData());
        }
        return data;
    }

    /**
     * Recursive method used to append nodes to a list in order
     * @param here the current node in the tree
     * @param data the list that is being appended to
     * @return the new list after being added to
     */
    private List<T> inorder(Node<T> here, List<T> data) {
        if (here != null) {
            inorder(here.getLeft(), data);
            data.add(here.getData());
            inorder(here.getRight(), data);
        }
        return data;
    }

    /**
     * Method used to find the height of a given node
     * @param here the current node in the tree
     * @return returns the hight of the node. If the tree has one
     * node, returns 0. If the tree is empty, returns -1
     */
    private int getHeight(Node<T> here) {
        if (here != null) {
            if (here.getLeft() == null && here.getRight() == null) { return 0; }
            counter = 1 + Math.max(getHeight(here.getLeft()),
                    getHeight(here.getRight()));
            return counter;
        } else { return -1; }
    }

    @Override
    public void add(T data) {
        if (size == 0) {
            root = new Node<T>(data);
        } else {
            add(root, data);
        }
        size++;
    }


    @Override
    public T remove(T data) {
        if (!contains(data)) { return null; }
        root = remove(data, root);
        if (root != null) {
            size--;
            return data;
        } else {
            return null;
        }
    }

    @Override
    public T get(T data) {
        tempNode = search(data, root);
        if (tempNode == null) { return null; }
        return tempNode.getData();
    }

    @Override
    public boolean contains(T data) {
        return (get(data) != null);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public List<T> preorder() {
        return preorder(root, new ArrayList<T>(size));
    }

    @Override
    public List<T> postorder() {
        return postorder(root, new ArrayList<T>(size));
    }

    @Override
    public List<T> inorder() {
        return inorder(root, new ArrayList<T>(size));
    }

    @Override
    public List<T> levelorder() {
        list = new ArrayList<T>(size);
        if (size == 0) { return list; }
        counter = 0;
        queue = new LinkedList<Node<T>>();
        queue.add(root);
        Node<T> here;
        while (!queue.isEmpty()) {
            here = queue.remove();
            list.add(counter, (T) here.getData());
            counter++;
            if (here.getLeft() != null) { queue.add(here.getLeft()); }
            if (here.getRight() != null) { queue.add(here.getRight()); }
        }
        return list;
    }

    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    @Override
    public int height() {
        counter = 0;
        return getHeight(root);
    }

    @Override
    public Node<T> getRoot() {
        return root;
    }
}