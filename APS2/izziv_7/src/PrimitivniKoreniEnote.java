import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;

public class PrimitivniKoreniEnote {

    private static boolean isPrime(int n) {
        if (n <= 1) {
            return false;
        }

        for (int i = 2; i < n; i++) {
            if (n % i == 0) {
                return false;
            }
        }

        return true;
    }

    private static int findNextPrime(int n) {
        n += 1;
        while(!isPrime(n)) {
            n += 1;
        }
        return n;
    }

    private static LinkedList<Integer> findRoots(int n, int prime) {
        LinkedList<Integer> tmp = new LinkedList<>();
        for (int i = 2; i <= prime; i++) {
            if ((long)Math.pow(i, n) % prime == 1) {
                tmp.add(i);
            }
        }
        return tmp;
    }

    private static LinkedList<Integer> findPRoots(int n, int prime) {
        boolean[] prev = new boolean[prime+1];
        LinkedList<Integer> pRoots;

        for (int i = 2; i < n; i++) {

            pRoots = findRoots(i, prime);

            for (int root : pRoots) {
                prev[root] = true;
            }
        }

        pRoots = findRoots(n, prime);
        LinkedList<Integer> toRemove = new LinkedList<>();
        for (Integer root : pRoots) {
            if (prev[root]) {
                toRemove.add(root);
            }
        }
        pRoots.removeAll(toRemove);
        return pRoots;
    }

    private static void writeMatrix(LinkedList<Integer> list, int prime, int n) {
        int[] tmp = new int[n];
        int first = list.getFirst();

        for (int i = 0; i < n; i++) {
            tmp[i] = (int)(Math.pow(first, i) % prime);
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.printf("%2d ", (int)(Math.pow(tmp[j], i) % prime));
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        LinkedList<Integer> roots = new LinkedList<>();

        int prime = n;
        while (roots.isEmpty()) {
            prime = findNextPrime(prime);
            roots = findPRoots(n, prime);
        }

        System.out.printf("%d: ", prime);

        for (int i = 0; i < roots.size(); i++) {
            System.out.printf("%d ", roots.get(i));
        }
        System.out.println();

        writeMatrix(roots, prime, n);
    }

}
