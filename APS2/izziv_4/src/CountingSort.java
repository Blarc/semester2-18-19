import java.util.Scanner;

public class CountingSort {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        int[] tab = new int[n];
        int[] count = new int[32];
        int[] result = new int[n];

        for (int i = 0; i < n; i++) {
            int atm = sc.nextInt();
            tab[i] = atm;
            count[Integer.bitCount(atm)] += 1;
        }

        for (int i = 0; i < count.length - 1; i++) {
            count[i+1] += count[i];
        }


        for (int i = n-1; i >= 0; i--) {
            int atm = tab[i];
            count[Integer.bitCount(atm)] -= 1;
            int index = count[Integer.bitCount(atm)];
            result[index] = atm;
            System.out.printf("(%d,%d)\n", atm, index);
        }

        for (int i = 0; i < result.length; i++) {
            System.out.printf("%d ", result[i]);
        }
        System.out.println();
    }
}
