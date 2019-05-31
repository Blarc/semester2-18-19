
import java.util.*;

public class ColoredGraphs {
    private static Queue<String> printQueue;

    static class Vertice {
        private int id;
        private int color = -1;
        private boolean visited;
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

    private static boolean checkAll(Vertice[] vertices) {
        for (Vertice v: vertices) {
            for (int i : v.neighbours) {
                if (v.color == vertices[i].color) {
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean checkNeighbours(int color, Vertice v, Vertice[] vertices) {
        for (int i : v.neighbours) {
            if (vertices[i].color == color) {
                return false;
            }
        }
        return true;
    }

    private static boolean descend(int outLimit, int k, Vertice[] vertices, StringBuilder stringBuilder, int index) {
        while (printQueue.size() > outLimit && outLimit > 0) {
            printQueue.poll();
        }

        if (index == vertices.length) {
            return true;
        }

        int i;
        for (i = 0; i <= Math.min(k-1, index); i++) {
            if (!checkNeighbours(i, vertices[index], vertices)) {
                printQueue.add(stringBuilder.toString() + i + " NOK");
            }
            else {
                vertices[index].color = i;
                stringBuilder.append(i).append(" ");
                printQueue.add(stringBuilder.toString() + "OK");
                if(descend(outLimit, k, vertices, stringBuilder, index + 1)) {
                    return true;
                }
                vertices[index].color = -1;
                stringBuilder.setLength(Math.max(stringBuilder.length() - 2, 0));
            }
        }
        return false;
    }

    private static void descend(int outLimit, Scanner sc) {

        int numV = sc.nextInt();
        int numE = sc.nextInt();

        Vertice[] vertices = readVertices(numV, numE, sc);
        StringBuilder stringBuilder = new StringBuilder(vertices.length * 2 + 3);

        int k = 2;
        printQueue.add("k = " + k);
        while (!descend(outLimit, k, vertices, stringBuilder, 0)) {
            k += 1;
            printQueue.add("k = " + k);
            stringBuilder = new StringBuilder();
        }

        printQueue.forEach(System.out::println);
    }

    private static boolean thorough(int outLimit, int k, Vertice[] vertices, int index) {
        if (index == vertices.length) {
            StringBuilder stringBuilder = new StringBuilder(vertices.length * 2 + 3);
            Arrays.stream(vertices).forEach(v -> stringBuilder.append(v.color).append(" "));

            while (printQueue.size() >= outLimit && outLimit > 0) {
                printQueue.poll();
            }

            if (checkAll(vertices)) {
                stringBuilder.append("OK");
                printQueue.add(stringBuilder.toString());
                return true;
            }
            stringBuilder.append("NOK");
            printQueue.add(stringBuilder.toString());
            return false;
        }

        for (int i = 0; i < k; i++) {
            vertices[index].color = i;
            if (thorough(outLimit, k, vertices, index + 1)) {
                return true;
            }
        }

        return false;
    }

    private static void thorough(int outLimit, Scanner sc) {

        int numV = sc.nextInt();
        int numE = sc.nextInt();

        Vertice[] vertices = readVertices(numV, numE, sc);

        int k = 2;
        printQueue.add("k = " + k);
        while (!thorough(outLimit, k, vertices, 0)) {
            k += 1;
            printQueue.add("k = " + k);
        }

        printQueue.forEach(System.out::println);
    }

    private static void hungry(int outLimit, Scanner sc) {

        int numV = sc.nextInt();
        int numE = sc.nextInt();

        Vertice[] vertices = readVertices(numV, numE, sc);
        boolean[] free = new boolean[numV];

        for (Vertice v : vertices) {
            Arrays.fill(free, true);
            for (int i : v.neighbours) {
                if (vertices[i].color != -1) {
                    free[vertices[i].color] = false;
                }
            }
            int j;
            for (j = 0; j < free.length; j++) {
                if (free[j]) {
                    break;
                }
            }
            v.color = j;
            System.out.printf("%d : %d\n", v.id, v.color);
        }
    }

    private static void twoColors(int outLimit, Scanner sc) {

        int numV = sc.nextInt();
        int numE = sc.nextInt();

        Vertice[] vertices = readVertices(numV, numE, sc);

        Queue<Vertice> queue = new LinkedList<>();
        vertices[0].color = 0;
        vertices[0].visited = true;
        queue.add(vertices[0]);

        int index = 0;
        while (!queue.isEmpty()) {
            System.out.printf("%d : ", index);
            queue.stream().sorted(Comparator.comparingInt(v -> v.id)).forEach(v -> System.out.print(v + " "));
            System.out.println();
            index += 1;
            int len = queue.size();
            for (int j = 0; j < len; j++) {
                Vertice v = queue.poll();
                for (int i : v.neighbours) {
                    if (!vertices[i].visited) {
                        vertices[i].visited = true;
                        vertices[i].color = (v.color + 1) % 2;
                        queue.add(vertices[i]);
                    } else if (vertices[i].color != (v.color + 1) % 2) {
                        System.out.println("NOK");
                        return;
                    }
                }
            }
        }

        System.out.println("OK");
    }

    private static Vertice[] readVertices(int numV, int numE, Scanner sc) {
        Vertice[] vertices = new Vertice[numV];
        for (int i = 0; i < numV; i++) {
            vertices[i] = new Vertice(i);
        }

        for (int i = 0; i < numE; i++) {
            int a = sc.nextInt();
            int b = sc.nextInt();

            vertices[a].add(b);
            vertices[b].add(a);
        }

        return vertices;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        printQueue = new LinkedList<>();

        int outputLimit;
        String algorithm, line;
        String[] split;

        line = sc.nextLine();
        split = line.split(" ");

        algorithm = split[0];
        if (split.length < 2) {
            outputLimit = -1;
        }
        else {
            outputLimit = Integer.parseInt(split[1]);
        }

        switch (algorithm) {
            case "2c":
                twoColors(outputLimit, sc);
                break;
            case "gr":
                hungry(outputLimit, sc);
                break;
            case "ex":
                thorough(outputLimit, sc);
                break;
            case "bt":
                descend(outputLimit, sc);
                break;
            case "dp":
                break;
            default:
                System.out.println("Wrong arguments!");
                break;
        }
    }
}
