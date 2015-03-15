import java.util.Collection;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.Map;

public class MinimumSpanningTree {

    /**
     * Using disjoint set(s), run Kruskal's algorithm on the given graph and
     * return the MST. Return null if no MST exists for the graph.
     *
     * @param g The graph to be processed. Will never be null.
     * @return The MST of the graph; null if no valid MST exists.
     */
    public static Collection<Edge> kruskals(Graph g) {
        PriorityQueue<Edge> eQ = new PriorityQueue<>();
        Set<Vertex> vertSet = g.getVertices();
        DisjointSets<Vertex> vSet = new DisjointSets<>(vertSet);
        Collection<Edge> eSet = g.getEdgeList();
        eQ.addAll(eSet);
        Collection<Edge> out = new HashSet<>();
        Set<Vertex> vertCount = new HashSet<>();
        while (eQ.size() != 0) {
            Edge current = eQ.remove();
            if (!vSet.sameSet(current.getU(), current.getV())) {
                out.add(current);
                vSet.merge(current.getU(), current.getV());
                if (!vertCount.contains(current.getU())) {
                    vertCount.add(current.getU());
                }
                if (!vertCount.contains(current.getV())) {
                    vertCount.add(current.getV());
                }
            }
        }
        if (out.size() != vertSet.size() - 1) {
            return null;
        } else {
            return out;
        }
    }

    /**
     * Run Prim's algorithm on the given graph and return the minimum spanning
     * tree. If no MST exists, return null.
     *
     * @param g The graph to be processed. Will never be null.
     * @param start The ID of the start node. Will always exist in the graph.
     * @return the MST of the graph; null if no valid MST exists.
     */
    public static Collection<Edge> prims(Graph g, int start) {
        PriorityQueue<Edge> eQ = new PriorityQueue<>();
        Collection<Edge> out = new HashSet<>();
        Set<Vertex> vertSet = g.getVertices();
        DisjointSets<Vertex> vSet = new DisjointSets<>(g.getVertices());
        Vertex u = new Vertex(start);
        Map<Vertex, Integer> adj = g.getAdjacencies(u);
        Set<Vertex> keys = adj.keySet();
        Edge newE;
        for (Vertex v : keys) {
            newE = new Edge(u, v, adj.get(v));
            eQ.add(newE);
        }
        while (!eQ.isEmpty()) {
            newE = eQ.remove();
            if (!vSet.sameSet(newE.getU(), newE.getV())) {
                out.add(newE);
                vSet.merge(newE.getU(), newE.getV());
            }
            adj = g.getAdjacencies(newE.getV());
            keys = adj.keySet();
            u = newE.getV();
            for (Vertex v : keys) {
                if (!vSet.sameSet(u, v)) {
                    eQ.add(new Edge(u, v, adj.get(v)));
                }
            }
        }
        if (out.size() == vertSet.size() - 1) {
            return out;
        } else { return null; }
    }
}
