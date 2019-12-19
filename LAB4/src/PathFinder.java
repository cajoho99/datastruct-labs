
import java.util.*;

import java.util.stream.Collectors;


public class PathFinder<V> {

    private DirectedGraph<V> graph;
    private long startTimeMillis;


    public PathFinder(DirectedGraph<V> graph) {
        this.graph = graph;
    }


    public class Result<V> {
        public final boolean success;
        public final V start;
        public final V goal;
        public final double cost;
        public final List<V> path;
        public final int visitedNodes;
        public final double elapsedTime;

        public Result(boolean success, V start, V goal, double cost, List<V> path, int visitedNodes) {
            this.success = success;
            this.start = start;
            this.goal = goal;
            this.cost = cost;
            this.path = path;
            this.visitedNodes = visitedNodes;
            this.elapsedTime = (System.currentTimeMillis() - startTimeMillis) / 1000.0;
        }

        public String toString() {
            String s = "";
            s += String.format("Visited nodes: %d\n", visitedNodes);
            s += String.format("Elapsed time: %.1f seconds\n", elapsedTime);
            if (success) {
                s += String.format("Total cost from %s -> %s: %s\n", start, goal, cost);
                s += "Path: " + path.stream().map(x -> x.toString()).collect(Collectors.joining(" -> "));
            } else {
                s += String.format("No path found from %s", start);
            }
            return s;
        }
    }


    public Result<V> search(String algorithm, V start, V goal) {
        startTimeMillis = System.currentTimeMillis();
        switch (algorithm) {
            case "random":
                return searchRandom(start, goal);
            case "dijkstra":
                return searchDijkstra(start, goal);
            case "astar":
                return searchAstar(start, goal);
        }
        throw new IllegalArgumentException("Unknown search algorithm: " + algorithm);
    }


    public Result<V> searchRandom(V start, V goal) {
        int visitedNodes = 0;
        LinkedList<V> path = new LinkedList<>();
        double cost = 0.0;
        Random random = new Random();

        V current = start;
        path.add(current);
        while (current != null) {
            visitedNodes++;
            if (current.equals(goal)) {
                return new Result<>(true, start, current, cost, path, visitedNodes);
            }

            List<DirectedEdge<V>> neighbours = graph.outgoingEdges(start);
            if (neighbours == null || neighbours.size() == 0) {
                break;
            } else {
                DirectedEdge<V> edge = neighbours.get(random.nextInt(neighbours.size()));
                cost += edge.weight();
                current = edge.to();
                path.add(current);
            }
        }
        return new Result<>(false, start, null, -1, null, visitedNodes);
    }


    public Result<V> searchDijkstra(V start, V goal) {
        HashMap<V, DirectedEdge<V>> edgeTo = new HashMap<>();
        HashMap<V, Double> distTo = new HashMap<>();
        Set<V> visited = new HashSet<>();
        int visitedNodes = 0;


        Comparator<V> comparator = Comparator.comparing(distTo::get);

        PriorityQueue<V> pq = new PriorityQueue<>(comparator);

        // Assume all edges are of positive value

        pq.add(start);
        distTo.put(start, 0.0);

        while (!pq.isEmpty()) {
            V v = pq.poll();
            visitedNodes++;


            if (!visited.contains(v)) {
                visited.add(v);

                if (v.equals(goal)) {
                    Stack<V> s = new Stack<>();

                    for (V current = v; edgeTo.get(current) != null; s.push(current)) {
                        current = edgeTo.get(current).from();
                    }

                    List<V> path = new ArrayList<>();

                    for (V current; !s.empty(); path.add(current)) {
                        current = s.pop();
                    }

                    return new Result<>(true, start, v, distTo.get(v), path, visitedNodes);
                }

                List<DirectedEdge<V>> outgoingEdges = graph.outgoingEdges(v);

                for (DirectedEdge<V> e : outgoingEdges) {
                    V w = e.to();

                    if (!distTo.containsKey(w)) {
                        distTo.put(w, Double.MAX_VALUE);
                    }

                    double newDist = distTo.get(v) + e.weight();


                    if (distTo.get(w) > newDist) {
                        distTo.put(w, newDist);
                        edgeTo.put(w, e);
                        pq.add(w);
                    }
                }
            }


        }

        return new Result<>(false, start, null, -1, null, visitedNodes);
    }


    public Result<V> searchAstar(V start, V goal) {
        HashMap<V, DirectedEdge<V>> edgeTo = new HashMap<>();
        HashMap<V, Double> distTo = new HashMap<>();
        HashMap<V, Double> heuristicTo = new HashMap<>();
        Set<V> visited = new HashSet<>();
        int visitedNodes = 0;


        Comparator<V> comparator = Comparator.comparing(heuristicTo::get);

        PriorityQueue<V> pq = new PriorityQueue<>(comparator);

        // Assume all edges are of positive value

        pq.add(start);
        distTo.put(start, 0.0);
        heuristicTo.put(start, graph.guessCost(start, goal));


        while (!pq.isEmpty()) {
            V v = pq.poll();
            visitedNodes++;


            if (v.equals(goal)) {
                Stack<V> s = new Stack<>();

                for (V current = v; edgeTo.get(current) != null; s.push(current)) {
                    current = edgeTo.get(current).from();
                }

                List<V> path = new ArrayList<>();

                for (V current; !s.empty(); path.add(current)) {
                    current = s.pop();
                }

                return new Result<>(true, start, v, distTo.get(v), path, visitedNodes);
            }

            if (!visited.contains(v)) {
                visited.add(v);


                List<DirectedEdge<V>> outgoingEdges = graph.outgoingEdges(v);

                for (DirectedEdge<V> e : outgoingEdges) {
                    V w = e.to();


                    if (!distTo.containsKey(w)) {
                        distTo.put(w, Double.MAX_VALUE);
                    }

                    if(!heuristicTo.containsKey(w)){
                        heuristicTo.put(w, Double.MAX_VALUE);
                    }

                    double newDist = distTo.get(v) + e.weight();
                    if(newDist < distTo.get(w)){
                        edgeTo.put(w, e);
                        distTo.put(w, newDist);
                        heuristicTo.put(w, distTo.get(w) + graph.guessCost(w, goal));
                        if(!pq.contains(w)){
                            pq.add(w);
                        }
                    }
                }
            }


        }

        return new Result<>(false, start, null, -1, null, visitedNodes);
    }

}
