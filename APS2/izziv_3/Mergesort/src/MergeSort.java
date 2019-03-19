import java.util.Scanner;

public class MergeSort {

    private void mergeSort(int[] arr, int l, int r) {
        if (r > l) {
            int half = (l + r) / 2;
            mergeSort(arr, l, half);
            mergeSort(arr, half+1, r);
            merge(arr, l, half, r);
        }
    }

    @SuppressWarnings("Duplicates")
    private void merge(int[] arr, int l, int half, int r) {

        int tempArr[] = new int[r-l+1];

        int i = l, j = half + 1, k = 0;
        while (i <= half && j <= r) {
            if (arr[i] >= arr[j]) {
                tempArr[k] = arr[j];
                j += 1;
            }
            else {
                tempArr[k] = arr[i];
                i += 1;
            }
            k += 1;
        }

        while (i <= half) {
            tempArr[k] = arr[i];
            i += 1;
            k += 1;
        }

        while (j <= r) {
            tempArr[k] = arr[i];
            j += 1;
            k += 1;
        }

        for (int c = l; c <= r; c++) {
            arr[c] = tempArr[c - l];
        }

    }

    @SuppressWarnings("Duplicates")
    private int[] mergeTwo(int[] left, int[] right) {
        int resultArr[] = new int[left.length + right.length];

        int i = 0, j = 0, k = 0;
        while (i < left.length && j < right.length) {
            if (left[i] <= right[j]) {
                System.out.print("a");
                resultArr[k] = left[i];
                i += 1;
            } else {
                System.out.print("b");
                resultArr[k] = right[j];
                j += 1;
            }
            k += 1;
        }

        while (i < left.length) {
            System.out.print("a");
            resultArr[k] = left[i];
            i += 1;
            k += 1;
        }

        while (j < right.length) {
            System.out.print("b");
            resultArr[k] = right[j];
            j += 1;
            k += 1;
        }

        System.out.println();
        return resultArr;

    }

    public static void main(String[] args) {
        MergeSort ob = new MergeSort();
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int m = sc.nextInt();

        int[] a = new int[n];
        int[] b = new int[m];

        for (int i = 0; i < n; i++) {
            a[i] = sc.nextInt();
        }

        for (int i = 0; i < m; i++) {
            b[i] = sc.nextInt();
        }

        int[] res = ob.mergeTwo(a, b);
        for (int i = 0; i < res.length; i++) {
            System.out.printf("%d ", res[i]);
        }
        System.out.println();


    }
}
