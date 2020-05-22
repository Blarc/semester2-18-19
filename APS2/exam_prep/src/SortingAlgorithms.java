import java.util.Arrays;

@SuppressWarnings("unused")
public class SortingAlgorithms {
    static int[] arr = {4, 3, 6, 7, 8, 1, 9, 2, 10, 5};

    private static int heapify(int i) {
        if (i >= arr.length) {
            return Integer.MAX_VALUE;
        }

        int a = heapify(2*i+1);
        if (a < arr[i]) {
            swap(arr[2+i+1], arr[i]);
        }

        int b = heapify(2*i+2);
        if (b < arr[i]) {
            swap(arr[2+i+2], arr[i]);
        }

        return arr[i];
    }

    private static void insertionSort() {
        for (int i = 1; i < arr.length; i++) {
            for (int j = i; j > 0; j--) {
                if (arr[j-1] > arr[j]) {
                    swap(j-1, j);
                }
            }
        }
    }

    private static void selectionSort() {
        for (int i = 0; i < arr.length; i++) {
            int min = -1;
            int minVal = Integer.MAX_VALUE;
            for (int j = i; j < arr.length; j++) {
                if (arr[j] < minVal) {
                    minVal = arr[j];
                    min = j;
                }
            }
            swap(i, min);
        }
    }

    private static void bubbleSort() {
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length - i - 1; j++) {
                if (arr[j] > arr[j+1]) {
                    swap(j, j+1);
                }
            }
        }
    }

    private static void swap(int a, int b) {
        int tmp = arr[a];
        arr[a] = arr[b];
        arr[b] = tmp;
    }

    public static void main(String[] args) {
//        bubbleSort();
//        selectionSort();
//        insertionSort();
        heapify(0);
        System.out.println(Arrays.toString(arr));
    }
}
