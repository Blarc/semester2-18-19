import java.util.HashSet;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Set;

public class ColoredGraphs {


    public static void twoColors(int outLimit, Scanner sc) {
        System.out.println(outLimit);

        int numV = sc.nextInt();
        int numE = sc.nextInt();
        Queue<Integer> queue;
        Set<Integer> odd, even, tmp;
        LinkedList<Integer>[] vertices = new LinkedList[numV];

        for (int i = 0; i < numE; i++) {
            int v = sc.nextInt();
            int e = sc.nextInt();

            if (vertices[v] == null) {
                vertices[v] = new LinkedList<>();
            }

            vertices[v].add(e);
        }

        odd = new HashSet<>();
        even = new HashSet<>();
        queue = new PriorityQueue<>();

        queue.add(0);
        for (int i = 0; i < vertices.length; i++) {
            int v = 0;
            while (!queue.isEmpty()) {
                v = queue.poll();
                if (i % 2 == 0 && !even.contains(v)) {
                    even.add(v);
                } else if (!odd.contains(v)) {
                    odd.add(v);
                } else {
                    System.out.println("NOK");
                    return;
                }
                System.out.print(v);
            }
            queue.addAll(vertices[v]);
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

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
                TwoColors tc = new TwoColors();
                tc.solve(outputLimit, sc);
                break;
            case "gr":
                break;
            case "ex":
                break;
            case "bt":
                break;
            case "dp":
                break;
            default:
                System.out.println("Wrong arguments!");
                break;

        }
    }
}
