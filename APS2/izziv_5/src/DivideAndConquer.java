import java.util.Scanner;

public class DivideAndConquer {

    private void divideAndConquer(int[] arr) {
        divide(0, arr.length-1, arr, Integer.MIN_VALUE);
    }

    private int divide(int l, int r, int[] arr, int max) {
        int half = (l + r) / 2;

        if (l < r) {
            int left = divide(l, half, arr, max);
            int right = divide(half + 1, r, arr, max);

            if (left > right) {
                max = left;
            } else {
                max = right;
            }
        }

        print(l, r, arr);

        int sum = 0;
        int atmMax = Integer.MIN_VALUE;
        for (int i = half; i >= l; i--) {
            sum += arr[i];
            if (sum > atmMax) {
                atmMax = sum;
            }
        }

        sum = atmMax;
        for (int i = half+1; i <= r; i++) {
            sum += arr[i];
            if (sum > atmMax) {
                atmMax = sum;
            }
        }

        if (atmMax > max) {
            max = atmMax;
        }

        System.out.println(max);
        return max;

    }

    private void print(int l, int r, int[] arr) {
        System.out.print("[");
        for (int i = l; i < r; i++) {
            System.out.printf("%d, ", arr[i]);
        }
        System.out.printf("%d]: ", arr[r]);
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
