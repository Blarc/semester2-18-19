import java.util.Arrays;
import java.util.Scanner;

public class DivideAndConquer {

    public void divideAndConquer(int[] arr) {
        divide(0, arr.length-1, arr);
    }

    private void divide(int l, int r, int[] arr) {
        if (r > l) {
            int half = (l + r) / 2;
            divide(l, half, arr);
            divide(half + 1, r, arr);
            print(l, r, arr);
            // conquer(l, half, r, arr);
        }
    }

    private void conquer(int l, int half, int r, int[] arr) {
        print(l, r, arr);
    }

    private void print(int l, int r, int[] arr) {
        for (int i = l; i <= r; i++) {
            System.out.printf("%d ", arr[i]);
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String line = sc.nextLine();
        String[] split = line.split(" ");

        int[] arr = new int[split.length];

        for (int i = 0; i < split.length; i++) {
            arr[i] = Integer.parseInt(split[i]);
        }

        DivideAndConquer o = new DivideAndConquer();
        o.divideAndConquer(arr);

    }
}
