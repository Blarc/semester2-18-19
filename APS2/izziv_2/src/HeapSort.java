import java.util.Scanner;

public class HeapSort {

    static void heapify(int[] arr, int n, int i) {
        int biggest = i;
        int leftIndex = 2 * i + 1;
        int rightIndex =  2 * i + 2;

        if (leftIndex < n && arr[leftIndex] > arr[biggest]) {
            biggest = leftIndex;
        }

        if (rightIndex < n && arr[rightIndex] > arr[biggest]) {
            biggest = rightIndex;
        }

        if (biggest != i) {
            int temp = arr[i];
            arr[i] = arr[biggest];
            arr[biggest] = temp;
            heapify(arr, n, biggest);
        }

    }

    static void heapsort(int[] arr) {
        int n = arr.length;

        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(arr, n, i);
        }

        print(arr, n);
        for (int i = n-1; i >= 0; i--) {
            int temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;

            heapify(arr, i, 0);
            print(arr, i);
        }
    }


    static void print(int[] arr, int n) {
        int b = 0;
        for (int i = 0; i < n; i++) {
            if ((i == 0 || i % (b*2) == 0) && i < n-1) {
                System.out.printf("%d | ", arr[i]);
                b += b + 1;
            }
            else {
                System.out.printf("%d ", arr[i]);
            }
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        int[] arr = new int[n];

        for (int i = 0; i < n; i++) {
            arr[i] = sc.nextInt();
        }

        heapsort(arr);

    }
}
