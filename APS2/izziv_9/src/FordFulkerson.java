
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

public class FordFulkerson {

    static class Edge {
        Vertice src;
        Vertice end;
        int cap;
        int flow;


        Edge(Vertice src, Vertice end, int cap) {
            this.src = src;
            this.end = end;
            this.cap = cap;
        }

        int evalFlow() {
            return cap - flow;
        }

        boolean full() {
            return flow >= cap;
        }

        boolean empty() {
            return flow <= 0;
        }
    }

    static class Vertice {
        int id;
        boolean visited = false;
        boolean marked = false;
        Edge augEdge = null;
        int incFlow = -1;
        boolean back;

        List<Edge> edgesIn;
        List<Edge> edgesOut;

        Vertice(int id) {
            this.id = id;
            this.edgesIn = new ArrayList<>();
            this.edgesOut = new ArrayList<>();
        }

        void addIn(Edge edge) {
            this.edgesIn.add(edge);
        }

        void addOut(Edge edge) {
            this.edgesOut.add(edge);
        }

        void reset() {
            this.augEdge = null;
            this.marked = false;
            this.visited = false;
            this.incFlow = -1;
        }

        @Override
        public String toString() {
            return Integer.toString(this.id);
        }

    }
    static class Network {

        List<Vertice> vertices;
        List<Vertice> cache;

        Network(int n) {
            vertices = new ArrayList<>(n);
            IntStream.range(0, n).forEachOrdered(i -> this.vertices.add(new Vertice(i)));
            cache = new ArrayList<>(n);
        }

        void addEdge(int src, int end, int cap) {
            Edge e = new Edge(vertices.get(src), vertices.get(end), cap);
            vertices.get(src).addOut(e);
            vertices.get(end).addIn(e);
        }

        void reset() {
            vertices.forEach(Vertice::reset);
        }

        Vertice start() {
            return vertices.get(0);
        }

        Vertice goal() {
            return vertices.get(vertices.size() - 1);
        }

        Vertice next() {
            for (Vertice v : vertices) {
                if (!v.visited && v.marked) {
                    return v;
                }
            }
            return null;
        }

        Vertice fordFulkerson(Vertice v, Vertice goal) {
            v.marked = true;
            v.visited = true;
            v.incFlow = Integer.MAX_VALUE;

            while (!goal.marked && v != null) {
                v.visited = true;

                for (Edge e : v.edgesOut) {
                    if (!e.full() && !e.end.marked) {
                        e.end.marked = true;
                        e.end.augEdge = e;
                        e.end.incFlow = Math.min(e.src.incFlow, e.evalFlow());
                        e.end.back = false;
                    }
                }

                for (Edge e : v.edgesIn) {
                    if (!e.empty() && !e.src.marked) {
                        e.src.marked = true;
                        Edge tmp = new Edge(e.end, e.src, e.cap);
                        tmp.flow = e.flow;
                        e.src.augEdge = tmp;
                        e.src.incFlow = Math.min(e.end.incFlow, e.flow);
                        e.src.back = true;
                    }
                }

                v = this.next();

            }

            if (v == null) {
                return null;
            }

            Edge tmp = goal.augEdge;
            List<Vertice> temp = new ArrayList<>();
            while(tmp != null) {
                temp.add(tmp.src);
                tmp = tmp.src.augEdge;
            }

            if (!cache.isEmpty()) {
                temp.removeAll(cache);
                if (temp.isEmpty()) {
                    return null;
                }
            }

            System.out.printf("%d: %s+  ", goal.incFlow, goal);
            tmp = goal.augEdge;

            cache = new ArrayList<>();
            while (tmp != null) {
                if (tmp.src.back) {
                    System.out.printf("%s- ", tmp.src);
                    tmp.flow -= goal.incFlow;
                }
                else if (tmp.src.id == 0) {
                    System.out.print(0);
                    tmp.flow += goal.incFlow;
                }
                else {
                    System.out.printf("%s+  ", tmp.src);
                    tmp.flow += goal.incFlow;
                }
                cache.add(tmp.src);
                tmp = tmp.src.augEdge;
            }

            System.out.println();

            return v;
        }

        void fordFulkerson() {

            while (fordFulkerson(this.start(), this.goal()) != null) {
                this.reset();
            }
        }
    }


    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();

        Network net = new Network(n);

        while (sc.hasNextInt()) {
            net.addEdge(sc.nextInt(), sc.nextInt(), sc.nextInt());
        }

        net.fordFulkerson();
    }
}
