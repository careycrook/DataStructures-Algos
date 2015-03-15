import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class DisjointSets<T> implements DisjointSetsInterface<T> {
    
    private Map<T, Node> set;

    /**
     * @param setItems the initial items (sameSet and merge will never be called
     * with items not in this set, this set will never contain null elements).
     */
    public DisjointSets(Set<T> setItems) {
        set = new HashMap<>();
        for (T data : setItems) {
            set.put(data, new Node(1));
        }
    }

    @Override
    public boolean sameSet(T u, T v) {
        if (u == null || v == null) {
            throw new IllegalArgumentException();
        }
        return find(set.get(v)) == find(set.get(u));
    }

    @Override
    public void merge(T u, T v) {
        if (u == null || v == null) {
            throw new IllegalArgumentException();
        }
        if (sameSet(u, v)) { return; }
        Node vRoot = find(set.get(v));
        Node uRoot = find(set.get(u));
        if (set.get(u).getRank() > set.get(v).getRank()) {
            vRoot.setParent(uRoot);
            uRoot.setRank(uRoot.getRank() + vRoot.getRank());
        } else {
            uRoot.setParent(vRoot);
            vRoot.setRank(uRoot.getRank() + vRoot.getRank());
        }

    }

    /**
     * Recursive helper method that finds the root of a node and compresses
     * all paths to it
     * @param current the node that is being searched
     * @return the root node
     */
    private Node find(Node current) {
        if (current.getParent() != current) {
            current.setParent(find(current.getParent()));
        }
        return current.getParent();
    }

    private class Node {
        //Fill in whatever methods or variables you believe are needed by node
        //here.  Should be O(1) space. This means no arrays, data structures,
        //etc.
        private int rank;
        private Node parent = this;

        public Node(int r) {
            rank = r;
        }

        public int getRank() {
            return rank;
        }

        public Node getParent() { return parent; }

        public void setRank(int r) { rank = r; }

        public void setParent(Node p) { parent = p; }
    }
}
