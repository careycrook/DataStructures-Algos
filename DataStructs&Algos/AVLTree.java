import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * My AVL implementation.
 *
 * @author Carey Crook
 */
public class AVLTree<T extends Comparable<T>> implements AVLInterface<T>,
       Gradable<T> {

    private Node<T> root;
    private int size;

    /**
     * Finds the height of a Node
     * @param aNode to find the height of
     * @return the height of aNode
     */
    private int calcHeight(Node<T> aNode) {
        if (aNode.getLeft() == null && aNode.getRight() == null) {
            return 0;
        } else if (aNode.getLeft() == null) {
            return aNode.getRight().getHeight();
        } else if (aNode.getRight() == null) {
            return aNode.getLeft().getHeight();
        } else if (aNode.getLeft().getHeight() > aNode.getRight().getHeight()) {
            return aNode.getLeft().getHeight();
        } else { return aNode.getRight().getHeight(); }
    }

    /**
     * Calculates the balance factor of a node
     * @param aNode to find the balance factor of
     * @return the balance factor of aNode
     */
    private int calcBalFactor(Node<T> aNode) {
        if (aNode.getLeft() == null && aNode.getRight() == null) {
            return 0;
        } else if (aNode.getLeft() == null) {
            return -1 - aNode.getRight().getHeight();
        } else if (aNode.getRight() == null) {
            return aNode.getLeft().getHeight() + 1;
        } else {
            return aNode.getLeft().getHeight() - aNode.getRight().getHeight();
        }
    }

    /**
     * Traverses down the tree and updates the height and balance factor of all
     * nodes that are found when searching for the passed data value
     * @param aNode the current node
     * @param data the data that will direct the traversal
     */
    private void updateHeight(Node<T> aNode, T data) {
        if (aNode.getRight() == null && aNode.getLeft() == null) {
            aNode.setHeight(0);
            aNode.setBalanceFactor(0);
            return;
        }
        if (aNode.getData().compareTo(data) > 0) {
            if (aNode.getLeft() != null) {
                updateHeight(aNode.getLeft(), data);
            } else { return; }
        } else if (aNode.getRight() != null) {
            updateHeight(aNode.getRight(), data);
        } else { return; }
        if (aNode.getLeft() == null) {
            aNode.setHeight(aNode.getRight().getHeight() + 1);
            aNode.setBalanceFactor(-1 - aNode.getRight().getHeight());
        } else if (aNode.getRight() == null) {
            aNode.setHeight(aNode.getLeft().getHeight() + 1);
            aNode.setBalanceFactor(aNode.getLeft().getHeight() + 1);
        } else {
            if (aNode.getLeft().getHeight() > aNode.getRight().getHeight()) {
                aNode.setHeight(aNode.getLeft().getHeight() + 1);
            } else {
                aNode.setHeight(aNode.getRight().getHeight() + 1);
            }
            aNode.setBalanceFactor(aNode.getLeft().
                    getHeight() - aNode.getRight().getHeight());
        }
    }

    /**
     * Balances the tree, starting from aNode and traversing the tree downwards
     * towards the passed data value.
     * @param aNode the node where the balancing will begin
     * @param data the data that will direct the traversal
     * @return the parent of the rebalanced subtree. Useful for
     * pointer reinforcement
     */
    private Node<T> balanceTree(Node<T> aNode, T data) {
        if (aNode.getBalanceFactor() == 2) {
            aNode = rotateRight(aNode);
            return aNode;
        } else if (aNode.getBalanceFactor() == -2) {
            aNode = rotateLeft(aNode);
            return aNode;
        } else if (aNode.getLeft() == null && aNode.getRight() == null) {
            return aNode;
        } else if (aNode.getData().compareTo(data) > 0) {
            if (aNode.getLeft() != null) {
                aNode.setLeft(balanceTree(aNode.getLeft(), data));
            } else { return aNode; }
        } else if (aNode.getRight() != null) {
            aNode.setRight(balanceTree(aNode.getRight(), data));
        } else {
            return aNode;
        }
        return aNode;
    }

    /**
     * Performs a left-left or left-right rotation, depending on which
     * is necessary
     * @param child1 the parent node of the subtree that will be rotated
     * @return the new parent of the rotated subtree
     */
    private Node<T> rotateLeft(Node<T> child1) {
        Node<T> child2 = child1.getRight();
        Node<T> child3;
        if (child2.getBalanceFactor() == 1) {
            child3 = child2.getLeft();
            child2.setLeft(child3.getRight());
            child1.setRight(child3.getLeft());
            child3.setRight(child2);
            child3.setLeft(child1);
            child1 = child3;
        } else {
            child1.setRight(child2.getLeft());
            child2.setLeft(child1);
            child1 = child2;
        }
        child2 = child1.getLeft();
        child3 = child1.getRight();
        child2.setHeight(calcHeight(child2));
        child2.setBalanceFactor(calcBalFactor(child2));
        child3.setHeight(calcHeight(child3));
        child3.setBalanceFactor(calcBalFactor(child3));
        child1.setHeight(calcHeight(child1));
        child1.setBalanceFactor(calcBalFactor(child1));
        return child1;
    }

    /**
     * Performs a right-right or right-left rotation, depending on which
     * is necessary
     * @param child1 the parent node of the subtree that will be rotated
     * @return the new parent of the rotated subtree
     */
    private Node<T> rotateRight(Node<T> child1) {
        Node<T> child2 = child1.getLeft();
        Node<T> child3;
        if (child2.getBalanceFactor() == -1) {
            child3 = child2.getRight();
            child2.setRight(child3.getLeft());
            child1.setLeft(child3.getRight());
            child3.setLeft(child2);
            child3.setRight(child1);
            child1 = child3;
        } else {
            child1.setLeft(child2.getRight());
            child2.setRight(child1);
            child1 = child2;
        }
        child2 = child1.getLeft();
        child3 = child1.getRight();
        child2.setHeight(calcHeight(child2));
        child2.setBalanceFactor(calcBalFactor(child2));
        child3.setHeight(calcHeight(child3));
        child3.setBalanceFactor(calcBalFactor(child3));
        child1.setHeight(calcHeight(child1));
        child1.setBalanceFactor(calcBalFactor(child1));
        return child1;
    }

    /**
     * Recursive method used in removal from the AVL
     * @param here the current node
     * @param data the data that will direct the traversal
     * @param res a holder for the removed node
     * @return the next node in the traversal, null if the data is not found
     */

    private Node<T> remove(Node<T> here, T data, Node<T> res) {
        if (here.getData().equals(data)) {
            if (res.getData() == null) { res.setData(here.getData()); }
            if (here.getLeft() == null && here.getRight() == null) {
                return null;
            } else if (here.getLeft() == null) {
                return here.getRight();
            } else if (here.getRight() == null) {
                return here.getLeft();
            } else {
                if (here.getRight().getLeft() != null) {
                    Node<T> temp = here;
                    temp = here.getRight();
                    while (temp.getLeft().getLeft() != null) {
                        temp = temp.getLeft();
                    }
                    here.setData(temp.getLeft().getData());
                    temp.setLeft(null);
                } else {
                    here.setData(here.getRight().getData());
                    here.setRight(here.getRight().getRight());
                }
                return here;
            }
        } else if (data.compareTo(here.getData()) > 0) {
            if (here.getRight() != null) {
                here.setRight(remove(here.getRight(), data, res));
            } else { return null; }
        } else {
            if (here.getLeft() != null) {
                here.setLeft(remove(here.getLeft(), data, res));
            } else { return null; }
        }
        return here;
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

    @Override
    public void add(T data) {
        if (data == null) { throw new IllegalArgumentException(); }
        if (size == 0) {
            root = new Node<T>(data);
            root.setBalanceFactor(0);
            root.setHeight(0);
            size++;
            return;
        }
        if (contains(data)) { return; }
        Node<T> temp = root;
        boolean flag = true;
        while (flag) {
            if (data.compareTo(temp.getData()) > 0) {
                if (temp.getRight() != null) {
                    temp = temp.getRight();
                } else {
                    temp.setRight(new Node<T>(data));
                    flag = false;
                }
            } else {
                if (temp.getLeft() != null) {
                    temp = temp.getLeft();
                } else {
                    temp.setLeft(new Node<T>(data));
                    flag = false;
                }
            }
        }
        size++;
        updateHeight(root, data);
        root = balanceTree(root, data);
    }

    @Override
    public T remove(T data) {
        if (data == null) { throw new IllegalArgumentException(); }
        if (size == 0) { return null; }
        if (!contains(data)) { return null; }
        Node<T> res = new Node<T>(null);
        root = remove(root, data, res);
        size--;
        if (size != 0) {
            updateHeight(root, data);
            root = balanceTree(root, data);
        }
        return res.getData();
    }

    @Override
    public T get(T data) {
        if (data == null) { throw new IllegalArgumentException(); }
        if (root == null) { return null; }
        Node<T> temp = root;
        while (true) {
            if (temp.getData().equals(data)) {
                return temp.getData();
            } else if (temp.getLeft() == null && temp.getRight() == null) {
                return null;
            } else if (temp.getLeft() == null) {
                temp = temp.getRight();
            } else if (temp.getRight() == null) {
                temp = temp.getLeft();
            } else {
                if (data.compareTo(temp.getData()) > 0) {
                    temp = temp.getRight();
                } else {
                    temp = temp.getLeft();
                }
            }
        }
    }

    @Override
    public boolean contains(T data) {
        if (data == null) { throw new IllegalArgumentException(); }
        return (get(data) != null && get(data).equals(data));
    }

    @Override
    public boolean isEmpty() { return size == 0; }

    @Override
    public int size() { return size; }

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
        ArrayList<T> list = new ArrayList<T>(size);
        if (size == 0) { return list; }
        int counter = 0;
        Queue<Node<T>> queue = new LinkedList<Node<T>>();
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
        size = 0;
        root = null;
    }

    @Override
    public int height() {
        return root.getHeight();
    }

    @Override
    public Node<T> getRoot() {
        return root;
    }
}