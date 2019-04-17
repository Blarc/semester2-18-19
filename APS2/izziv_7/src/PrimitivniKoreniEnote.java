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

    private static LinkedList<Integer> findPRoots(int n, int prime) {
        LinkedList<Integer> tmp = new LinkedList<>();
        for (int i = 2; i < prime; i++) {
            if ((int)Math.pow(i, n) % prime == 1) {
                tmp.add(i);
            }
        }
        return tmp;
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

        System.out.println(prime);
        System.out.println(roots.toString());
    }

}
