
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Competition {
    private static long timeStart;
    private static long iterations;

    static class Vertice {
        private int id;
        private int color = -1;
        private LinkedList<Integer> neighbours;

        private Vertice(int id) {
            this.id = id;
            this.neighbours = new LinkedList<>();
        }

        private void add(int n) {
            this.neighbours.add(n);
        }

        @Override
        public String toString() {
            return String.valueOf(id);
        }
    }


    private static boolean checkNeighbours(int color, Vertice v, Vertice[] vertices) {
        for (int i : v.neighbours) {
            if (vertices[i].color == color) {
                return false;
            }
        }
        return true;
    }

    private static boolean descend(int k, Vertice[] vertices, int index) {
        iterations += 1;
        long time = System.currentTimeMillis();
        if (iterations > 500000) {
            return false;
        }

        if (index == vertices.length) {
            return true;
        }

        for (int i = 0; i <= Math.min(k-1, index); i++) {
            if (checkNeighbours(i, vertices[index], vertices)) {
                vertices[index].color = i;
                if(descend(k, vertices, index + 1)) {
                    vertices[index].color = -1;
                    return true;
                }
                vertices[index].color = -1;
            }
        }
        return false;
    }

    public static void main(String[] args) throws IOException {
        timeStart = System.currentTimeMillis();
        iterations = 0;

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        st.nextToken();

        st = new StringTokenizer(br.readLine());

        int numV = Integer.parseInt(st.nextToken());
        int numE = Integer.parseInt(st.nextToken());

        Vertice[] vertices = new Vertice[numV];

        for (int i = 0; i < numE; i++) {

            st = new StringTokenizer(br.readLine());

            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            if (vertices[a] == null) {
                vertices[a] = new Vertice(a);
            }

            if (vertices[b] == null) {
                vertices[b] = new Vertice(b);
            }

            vertices[a].add(b);
            vertices[b].add(a);

        }

        int k = numV;
        while(descend(k, vertices, 0)) {
            iterations = 0;
            k -= 1;
        }
        k += 1;


        System.out.println(k);

//        long timeEnd = System.currentTimeMillis();
//        System.out.println(timeEnd - timeStart);

    }
}
