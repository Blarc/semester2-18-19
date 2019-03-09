import java.util.Random;
import edu.princeton.cs.introcs.StdDraw;
public class Vaje1 {

    static int[] generateTable(int n) {
        int[] table = new int[n];
        for (int i = 0; i < n; i++) {
            table[i] = i + 1;
        }
        return table;
    }

    static int findLinear(int[] a, int v) {
        int i = 0;
        while (a[i] != v) {
            i++;
        }
        return i;
    }

    static int findBinary(int[] a, int l, int r, int v) {
        if (r >= l) {
            int half = l + (r - l) / 2;

            if (a[half] == v) {
                return half;
            }
            if (a[half] < v) {
                return findBinary(a, half + 1, r, v);
            }

            return findBinary(a, l, half - 1, v);
        }
        return -1;
    }

    static long timeLinear(int n) {
        Random rand = new Random();
        int tab[] = generateTable(n);
        long startTime = System.nanoTime();

        for (int i = 0; i < 1000; i++) {
            int num = rand.nextInt(n) + 1;
            findLinear(tab, num);
        }

        long executionTime = System.nanoTime() - startTime;
        return executionTime / 1000;
    }

    static long timeBinary(int n) {
        Random rand = new Random();
        int tab[] = generateTable(n);
        long startTime = System.nanoTime();

        for (int i = 0; i < 1000; i++) {
            int num = rand.nextInt(n) + 1;
            findBinary(tab, 0, n, num);
        }

        long executionTime = System.nanoTime() - startTime;
        return executionTime / 1000;
    }

    public static void main(String[] args) {
        StdDraw.clear(StdDraw.BLACK);
        StdDraw.setXscale(0, 101000);
        StdDraw.setYscale(0, 20000);
        long linY = 0;
        long binY = 0;

        System.out.printf("%14s |%14s |%14s\n", "n", "linearno", "dvojisko");
        System.out.println("---------------+---------------+---------------");
        for (int i = 1000; i <= 100000; i += 1000) {
            long lin = timeLinear(i);
            long bin = timeBinary(i);
            StdDraw.setPenColor(StdDraw.CYAN);
            StdDraw.line(i-1000, linY, i, lin);
            StdDraw.setPenColor(StdDraw.MAGENTA);
            StdDraw.line(i-1000, binY, i, bin);
            linY = lin;
            binY = bin;
            System.out.printf("%14d |%14d |%14d\n", i, lin, bin);
        }
    }


}
