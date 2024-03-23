package APS.APSKolokvium2.lab11APS.zad3;

import java.util.*;

class AdjacencyMatrixGraph<T> {
    private int numVertices;
    private int[][] matrix;
    private T[] vertices;

    @SuppressWarnings("unchecked")
    public AdjacencyMatrixGraph(int numVertices) {
        this.numVertices = numVertices;
        matrix = new int[numVertices][numVertices];
        for(int i=0;i<numVertices;i++) {
            for(int j=0;j<numVertices;j++) {
                matrix[i][j] = 0;
            }
        }
        vertices = (T[]) new Object[numVertices];
    }
    public void addEdge(int source, int destination, int weight) {
        matrix[source][destination] = weight;
        matrix[destination][source] = weight; // For undirected graph
    }

    public boolean isEdge(int source, int destination) {
        return matrix[source][destination] !=0;
    }

    public List<Edge> prim(int startVertexIndex) {
        List<Edge> mstEdges = new ArrayList<>();
        Queue<Edge> q = new PriorityQueue<>(Comparator.comparingInt(Edge::getWeight));

        boolean[] included = new boolean[numVertices];
        Arrays.fill(included,false);
        included[startVertexIndex] = true;

        for(int i=0;i<numVertices;i++) {
            if(isEdge(startVertexIndex,i)) {
                q.add(new Edge(startVertexIndex, i, matrix[startVertexIndex][i]));
            }
        }

        while(!q.isEmpty()) {
            Edge e = q.poll();

            if(!included[e.getTo()]) {
                included[e.getTo()] = true;
                mstEdges.add(e);
                for(int i=0;i<numVertices;i++) {
                    if(!included[i] && isEdge(e.getTo(),i)) {
                        q.add(new Edge(e.getTo(), i, matrix[e.getTo()][i]));
                    }
                }
            }
        }

        return mstEdges;
    }

    @Override
    public String toString() {
        String ret = "  ";
        for(int i = 0; i < numVertices; i++)
            ret += vertices[i] + " ";
        ret += "\n";
        for(int i = 0; i < numVertices; i++){
            ret += vertices[i] + " ";
            for(int j = 0; j < numVertices; j++)
                ret += matrix[i][j] + " ";
            ret += "\n";
        }
        return ret;
    }

}
class Edge {
    private int fromVertex, toVertex;
    private int weight;
    public Edge(int from, int to, int weight) {
        this.fromVertex = from;
        this.toVertex = to;
        this.weight = weight;
    }

    public int getFrom() {
        return this.fromVertex;
    }
    public int getTo() {
        return this.toVertex;
    }
    public int getWeight() {
        return this.weight;
    }
}

public class Organizacija{
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = Integer.parseInt(scanner.nextLine());
        int nn = Integer.parseInt(scanner.nextLine());
        AdjacencyMatrixGraph<String> graph = new AdjacencyMatrixGraph<>(n);
        HashMap<String,Integer> map = new HashMap<>();
        for (int i = 0; i < nn; i++) {
            String line = scanner.nextLine();
            String[] parts = line.split(" ");
            int prv = Integer.parseInt(parts[0]);
            int vtor = Integer.parseInt(parts[2]);
            map.put(parts[1],prv);
            map.put(parts[3],vtor);
            graph.addEdge(prv,vtor,Integer.parseInt(parts[4]));
        }
        String s = scanner.nextLine();
        System.out.println(graph.prim(map.get(s)).stream().mapToInt(Edge::getWeight).sum());
    }
}