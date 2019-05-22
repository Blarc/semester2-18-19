import java.util.Arrays;
import java.util.Scanner;

public class DropTheEgg {

    static int[][] memo;
    static int egg(int n, int k) {

        int res;
        int i, j, x;

        for (i = 1; i <= n; i++) {
            memo[i][1] = 1;
            memo[i][0] = 0;
        }
        System.out.println(Arrays.toString(memo[0]));


        for (j = 1; j <= k; j++) {
            memo[1][j] = j;
        }
        System.out.println(Arrays.toString(memo[1]));


        for (i = 2; i <= n; i++) {
            for (j = 2; j <= k; j++) {
                memo[i][j] = Integer.MAX_VALUE;
                for (x = 1; x <= j; x++) {
                    res = 1 + Math.max(memo[i-1][x-1], memo[i][j-x]);
                    if (res < memo[i][j]) {
                        memo[i][j] = res;
                    }
                }
                System.out.println(Arrays.toString(memo[i]));
            }
        }

        return memo[n][k];
    }

    static int egg2(int n, int k)
    {
        int res, min = Integer.MAX_VALUE;


        if (n == 1 || n == 0) {
            return n;
        }

        if (k == 1) {
            return n;
        }

        if (memo[n][k] > -1) {
            return memo[n][k];
        }

        for (int x = 1; x <= n; x++) {
            res = Math.max(egg2(x-1, k-1), egg2(n-x, k));
            if (res < min) {
                min = res;
            }
        }

        min += 1;
        memo[n][k] = min;
        return min;
    }


    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        // eggs
        int n = sc.nextInt();
        // floors
        int k = sc.nextInt();

        memo = new int[n+1][k+1];
        for (int[] iter : memo) {
            Arrays.fill(iter, -1);
        }

        for (int j = 1; j <= k; j++) {
            memo[0][j] = 0;
            memo[1][j] = 1;
        }

        for (int i = 1; i <= n; i++) {
            memo[i][1] = i;
        }

        System.out.print("    ");
        for (int i = 1; i <= k; i++) {
            System.out.printf("%4d", i);
        }
        System.out.println();

        for (int i = 0; i <= n; i++) {
            System.out.printf("%4d", i);
            for (int j = 1; j <= k; j++) {
                System.out.printf("%4d", egg2(i, j));
            }
            System.out.println();
        }

    }
}
