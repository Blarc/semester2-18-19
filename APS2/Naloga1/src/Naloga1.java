
import java.util.Scanner;

@SuppressWarnings("Duplicates")
public class Naloga1 {
    static private Scanner sc = new Scanner(System.in);

    interface NumericSort {
        void sort(boolean print);
    }

    private void sort(NumericSort nsUp, NumericSort nsDown, String mode, String direction) {
        switch (mode) {
            case "trace":
                switch (direction) {
                    case "up":
                        nsUp.sort(true);
                        break;
                    case "down":
                        nsDown.sort(true);
                        break;
                    default:
                        System.out.println("Napacni argumenti!");
                        System.exit(1);
                }
                break;
            case "count":
                switch (direction) {
                    case "up":
                        nsUp.sort(false);
                        nsUp.sort(false);
                        nsDown.sort(false);
                        break;
                    case "down":
                        nsDown.sort(false);
                        nsDown.sort(false);
                        nsUp.sort(false);
                        break;
                    default:
                        System.out.println("Napacni argumenti!");
                        System.exit(1);
                }
                break;
            default:
                System.out.println("Napacni argumenti!");
                System.exit(1);
        }
    }

    public class BubbleSort {
        int n;
        int[] arr;

        private BubbleSort(int n) {
            this.n = n;
            this.arr = readArr(n);
        }

        private NumericSort getNsUp() {
            return (print -> {
                // boolean bool = true;
                int iter = 0;
                int compares = 0;
                int setters = 0;

                for (int j = 0; j < n; j++) {
                    if (print) bubblePrint(iter);
                    // bool = false;
                    for (int i = arr.length - 2; i >= 0; i--) {
                        compares += 1;
                        if (arr[i] > arr[i + 1]) {
                            setters += 3;
                            swap(i);
                            // bool = true;
                        }
                    }
                    iter += 1;
                }
                if (!print) System.out.printf("%d %d\n", compares/2, setters);
            });
        }

        private NumericSort getNsDown() {
            return (print -> {
                // boolean bool = true;
                int iter = 0;
                int compares = 0;
                int setters = 0;

                for (int j = 0; j < n; j++) {
                    if (print) bubblePrint(iter);
                    // bool = false;
                    for (int i = arr.length - 2; i >= 0; i--) {
                        compares += 1;
                        if (arr[i] < arr[i + 1]) {
                            setters += 3;
                            swap(i);
                            // bool = false;
                        }
                    }
                    iter += 1;
                }
                if (!print) System.out.printf("%d %d\n", compares/2, setters);
            });
        }

        private void swap (int i) {
            int temp = arr[i];
            arr[i] = arr[i+1];
            arr[i+1] = temp;
        }

        private void bubblePrint(int iteration) {
            if (iteration == 0) {
                System.out.printf("| %d", arr[0]);
            } else {
                System.out.printf("%d", arr[0]);
            }

            for (int i = 1; i < arr.length; i++) {
                if (iteration == i) {
                    System.out.printf(" | %d", arr[i]);
                } else {
                    System.out.printf(" %d", arr[i]);
                }
            }
            System.out.println();
        }
    }

    public class SelectionSort {
        int n;
        int[] arr;

        private SelectionSort(int n) {
            this.n = n;
            this.arr = readArr(n);
        }

        private NumericSort getNsUp() {
            return (print -> {
                int setters = 0;
                int compares = 0;
                int i;
                for (i = 0; i < arr.length-1; i++) {
                    if (print) selectionPrint(i);
                    int minIndex = findMin(i, arr.length);
                    swap(i, minIndex);
                    setters += 3;
                    compares += n-i-1;
                }
                if (print) selectionPrint(i);

                if (!print) System.out.printf("%d %d\n", compares, setters);
            });
        }

        private NumericSort getNsDown() {
            return (print -> {
                int setters = 0;
                int compares = 0;
                int i;
                for (i = 0; i < arr.length-1; i++) {
                    if (print) selectionPrint(i);
                    int maxIndex = findMax(i, arr.length);
                    swap(i, maxIndex);
                    setters += 3;
                    compares += n-i-1;
                }
                if (print) selectionPrint(i);

                if (!print) System.out.printf("%d %d\n", compares, setters);
            });
        }

        private void swap(int indexA, int indexB) {
            int temp = arr[indexA];
            arr[indexA] = arr[indexB];
            arr[indexB] = temp;
        }

        private int findMin(int start, int end) {
            int min = Integer.MAX_VALUE;
            int minIndex = 0;
            for (int i = start; i < end; i++) {
                if (arr[i] < min) {
                    min = arr[i];
                    minIndex = i;
                }
            }
            return minIndex;
        }

        private int findMax(int start, int end) {
            int max = Integer.MIN_VALUE;
            int maxIndex = 0;
            for (int i = start; i < end; i++) {
                if (arr[i] > max) {
                    max = arr[i];
                    maxIndex = i;
                }
            }
            return maxIndex;
        }

        private void selectionPrint(int iteration) {
            if (iteration == 0) {
                System.out.printf("| %d", arr[0]);
            } else {
                System.out.printf("%d", arr[0]);
            }

            for (int i = 1; i < arr.length; i++) {
                if (iteration == i) {
                    System.out.printf(" | %d", arr[i]);
                } else {
                    System.out.printf(" %d", arr[i]);
                }
            }
            System.out.println();
        }
    }

    public class InsertionSort {
        int n;
        int[] arr;

        private InsertionSort(int n) {
            this.n = n;
            this.arr = readArr(n);
        }

        private NumericSort getNsUp() {
            return (print -> {
                int setters = 0;
                int compares = 0;
                int i;
                for (i = 1; i < n; i++) {
                    if (print) insertionPrint(i-1);
                    int j = i;
                    while (j > 0) {
                        compares += 1;
                        if (arr[j] < arr[j - 1]) {
                            swap(j);
                            setters += 3;
                            j -= 1;
                        } else {
                            break;
                        }
                    }
                }
                if (print) insertionPrint(i-1);

                if (!print) System.out.printf("%d %d\n", compares, setters);
            });
        }

        private NumericSort getNsDown() {
            return (print -> {
                int setters = 0;
                int compares = 0;
                int i;
                for (i = 1; i < n; i++) {
                    if (print) insertionPrint(i-1);
                    int j = i;
                    while (j > 0) {
                        compares += 1;
                        if (arr[j] > arr[j - 1]) {
                            swap(j);
                            setters += 3;
                            j -= 1;
                        } else {
                            break;
                        }
                    }
                }
                if (print) insertionPrint(i-1);

                if (!print) System.out.printf("%d %d\n", compares, setters);
            });
        }

        private void swap(int i) {
            int temp = arr[i];
            arr[i] = arr[i-1];
            arr[i-1] = temp;
        }

        private void insertionPrint(int iteration) {
            if (iteration == 0) {
                System.out.printf("%d |", arr[0]);
            } else {
                System.out.printf("%d", arr[0]);
            }

            for (int i = 1; i < arr.length; i++) {
                if (iteration == i) {
                    System.out.printf(" %d |", arr[i]);
                } else {
                    System.out.printf(" %d", arr[i]);
                }
            }
            System.out.println();
        }
    }

    public class HeapSort {
        int n;
        int[] arr;
        int compares = 0;
        int setters = 0;

        private HeapSort(int n) {
            this.n = n;
            this.arr = readArr(n);
        }

        private NumericSort getNsUp() {
            return (print -> {
                for (int i = n / 2 - 1; i >= 0; i--) {
                    heapifyUp(n, i);
                }

                if (print) heapPrint(arr, n);
                for (int i = n-1; i > 0; i--) {
                    int temp = arr[0];
                    arr[0] = arr[i];
                    arr[i] = temp;
                    setters += 3;

                    heapifyUp(i, 0);
                    if (print) heapPrint(arr, i);
                }

                if (!print) System.out.printf("%d %d\n", compares, setters);

                reset();
            });
        }

        private NumericSort getNsDown() {
            return (print -> {
                for (int i = n / 2 - 1; i >= 0; i--) {
                    heapifyDown(n, i);
                }

                if (print) heapPrint(arr, n);
                for (int i = n-1; i > 0; i--) {
                    int temp = arr[0];
                    arr[0] = arr[i];
                    arr[i] = temp;
                    setters += 3;

                    heapifyDown(i, 0);
                    if (print) heapPrint(arr, i);
                }

                if (!print) System.out.printf("%d %d\n", compares, setters);

                reset();
            });
        }

        private void heapifyUp(int n, int i) {
            int biggest = i;
            int leftIndex = 2 * i + 1;
            int rightIndex =  2 * i + 2;

            if (leftIndex < n) {
                compares += 1;
                if (arr[leftIndex] > arr[biggest]) {
                    biggest = leftIndex;
                }
            }

            if (rightIndex < n) {
                compares += 1;
                if (arr[rightIndex] > arr[biggest]) {
                    biggest = rightIndex;
                }
            }

            if (biggest != i) {
                int temp = arr[i];
                arr[i] = arr[biggest];
                arr[biggest] = temp;
                setters += 3;
                heapifyUp(n, biggest);
            }
        }

        private void heapifyDown(int n, int i) {
            int biggest = i;
            int leftIndex = 2 * i + 1;
            int rightIndex =  2 * i + 2;

            if (leftIndex < n) {
                compares += 1;
                if (arr[leftIndex] < arr[biggest]) {
                    biggest = leftIndex;
                }
            }

            if (rightIndex < n) {
                compares += 1;
                if (arr[rightIndex] < arr[biggest]) {
                    biggest = rightIndex;
                }
            }

            if (biggest != i) {
                int temp = arr[i];
                arr[i] = arr[biggest];
                arr[biggest] = temp;
                setters += 3;
                heapifyDown(n, biggest);
            }
        }

        private void heapPrint(int[] arr, int n) {
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

        private void reset() {
            this.setters = 0;
            this.compares = 0;
        }
    }

    public class QuickSort {
        int n;
        int[] arr;
        int setters = 0;
        int compares = 0;
        boolean print = true;

        private QuickSort(int n) {
            this.n = n;
            this.arr = readArr(n);
        }

        private NumericSort getNsUp() {
            return (print -> {
                this.print = print;
                quickSortUp(0, n-1);

                if (!print) System.out.printf("%d %d\n", compares, setters);

                reset();
            });
        }

        private NumericSort getNsDown() {
            return (print -> {
                this.print = print;
                quickSortDown(0, n-1);

                if (!print) System.out.printf("%d %d\n", compares, setters);
                reset();
            });
        }

        private void quickSortUp(int i, int j) {
            if (i < j) {
                int[] tmp = partitionUp(i, j);

                quickSortUp(i, tmp[1]);
                quickSortUp(tmp[0], j);
            }
        }

        private void quickSortDown(int i, int j) {
            if (i < j) {
                int[] tmp = partitionDown(i, j);

                quickSortDown(i, tmp[1]);
                quickSortDown(tmp[0], j);
            }
        }

        private int[] partitionUp(int i, int j) {
            int pivot = arr[((i+j) / 2)];
            setters += 1;
            int startI = i;
            int startJ = j;

            while (i <= j) {
                compares += 1;
                while (arr[i] < pivot) {
                    compares += 1;
                    i += 1;
                }
                compares += 1;
                while (arr[j] > pivot) {
                    compares += 1;
                    j -= 1;
                }
                if (i <= j) {
                    swap(i, j);
                    i += 1;
                    j -= 1;
                }
            }

            if (print) quickPrint(startI, startJ, i, j);
            // System.out.println((i-startI) + " " + (j-startI));
            return new int[]{i, j};
        }

        private int[] partitionDown(int i, int j) {
            int pivot = arr[((i+j) / 2)];
            setters += 1;
            int startI = i;
            int startJ = j;

            while (i <= j) {
                compares += 1;
                while (arr[i] > pivot) {
                    compares += 1;
                    i += 1;
                }
                compares += 1;
                while (arr[j] < pivot) {
                    compares += 1;
                    j -= 1;
                }
                if (i <= j) {
                    swap(i, j);
                    i += 1;
                    j -= 1;
                }
            }

            if (print) quickPrint(startI, startJ, i, j);
            return new int[]{i, j};
        }

        private void swap(int i, int j) {
            int temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
            setters += 3;
        }

        private void quickPrint(int startI, int startJ, int i, int j) {
            for (int k = startI; k < i; k++) {
                if (k == j+1) {
                    System.out.print("| ");
                }
                System.out.printf("%d ", arr[k]);
            }
            System.out.print("| ");

            for (int k = i; k <= startJ; k++) {
                if (k == j+1) {
                    System.out.print("| ");
                }
                System.out.printf("%d ", arr[k]);
            }

            System.out.println();
        }

        private void reset() {
            this.setters = 0;
            this.compares = 0;
        }
    }

    public class MergeSort {
        int n;
        int[] arr;
        int setters;
        int compares;
        boolean print;

        private MergeSort(int n) {
            this.n = n;
            this.setters = n;
            this.arr = readArr(n);
        }

        private NumericSort getNsUp() {
            return (print -> {
                this.print = print;
                mergeSortUp(0, n-1);

                if (!print) System.out.printf("%d %d\n", compares, setters);

                reset();
            });
        }

        private NumericSort getNsDown() {
            return (print -> {
                this.print = print;
                mergeSortDown(0, n-1);

                if (!print) System.out.printf("%d %d\n", compares, setters);

                reset();
            });
        }

        private void mergeSortUp(int l, int r) {
            if (r > l) {
                int half = (l + r) / 2;
                if (print) dividePrint(l, r, half);
                mergeSortUp(l, half);
                mergeSortUp(half+1, r);
                mergeUp(l, half, r);
            }
        }

        private void mergeSortDown(int l, int r) {
            if (r > l) {
                int half = (l + r) / 2;
                if (print) dividePrint(l, r, half);
                mergeSortDown(l, half);
                mergeSortDown(half+1, r);
                mergeDown(l, half, r);
            }
        }

        private void mergeUp(int l, int half, int r) {

            int[] tempArr = new int[r - l + 1];

            int i = l, j = half + 1, k = 0;
            while (i <= half && j <= r) {
                compares += 1;
                setters += 1;
                if (arr[i] > arr[j]) {
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
                setters += 1;
                i += 1;
                k += 1;
            }

            while (j <= r) {
                tempArr[k] = arr[j];
                setters += 1;
                j += 1;
                k += 1;
            }

            if (print) mergePrint(tempArr);

            if (r + 1 - l >= 0) {
                System.arraycopy(tempArr, 0, arr, l, r + 1 - l);
            }

//            for (int c = l; c <= r; c++) {
//                arr[c] = tempArr[c - l];
//                setters += 1;
//            }
        }

        private void mergeDown(int l, int half, int r) {

            int[] tempArr = new int[r - l + 1];

            int i = l, j = half + 1, k = 0;
            while (i <= half && j <= r) {
                compares += 1;
                setters += 1;
                if (arr[i] < arr[j]) {
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
                setters += 1;
                i += 1;
                k += 1;
            }

            while (j <= r) {
                tempArr[k] = arr[j];
                setters += 1;
                j += 1;
                k += 1;
            }

            if (print) mergePrint(tempArr);

            if (r + 1 - l >= 0) {
                System.arraycopy(tempArr, 0, arr, l, r + 1 - l);
            }

//            for (int c = l; c <= r; c++) {
//                arr[c] = tempArr[c - l];
//                setters += 1;
//            }

        }

        private void mergePrint(int[] arr) {
            for (int anArr : arr) {
                System.out.printf("%d ", anArr);
            }
            System.out.println();
        }

        private void dividePrint(int l, int r, int half) {
            for (int i = l; i <= r; i++) {
                if (i == half) {
                    System.out.printf("%d | ", arr[i]);
                } else {
                    System.out.printf("%d ", arr[i]);
                }
            }
            System.out.println();
        }

        private void reset() {
            this.setters = n;
            this.compares = 0;
        }
    }

    public class CountingSort {
        int n;
        int[] countArr = new int[256];
        int[] arr;
        int[] result;

        private CountingSort(int n) {
            this.n = n;
            this.arr = new int[n];
            this.result = new int[n];
            this.readArr(n);
        }

        private NumericSort getNsUp() {
            return (print -> {

                for (int i = 0; i < countArr.length - 1; i++) {
                    countArr[i+1] += countArr[i];
                    System.out.printf("%d ", countArr[i]);
                }
                System.out.printf("%d\n", countArr[countArr.length - 1]);

                for (int i = n-1; i >= 0; i--) {
                    int atm = arr[i];
                    countArr[atm] -= 1;
                    int index = countArr[atm];
                    result[index] = atm;
                    System.out.printf("%d ", index);
                }
                System.out.println();

                for (int aResult : result) {
                    System.out.printf("%d ", aResult);
                }
                System.out.println();

            });
        }

        private void readArr(int n) {
            for (int i = 0; i < n; i++) {
                int atm = sc.nextInt();
                arr[i] = atm;
                countArr[atm] += 1;
            }
        }
    }

    public class RadixSort {
        int n;
        int[] arr;
        int[] countArr;
        int[] result;

        private RadixSort(int n) {
            this.n = n;
            this.arr = readArr(n);
            this.result = new int[n];
        }

        private NumericSort getNsUp() {
            return (print -> {
                countingSort(1);
                countingSort(2);
                countingSort(3);
                countingSort(4);
            });
        }

        private void countingSort(int byteNum) {
            int mod = 256;
            int quotient = 1 << ((byteNum-1) * 8);
            countArr = new int[256];

            for (int i = 0; i < n; i++) {
                // System.out.println(arr[i]%mod);
                int tmp = arr[i] / quotient;
                // System.out.printf("%d ", tmp%mod);
                countArr[(tmp%mod)] += 1;
            }
            // System.out.println();

            for (int i = 0; i < countArr.length - 1; i++) {
                countArr[i+1] += countArr[i];
                System.out.printf("%d ", countArr[i]);
            }
            System.out.printf("%d\n", countArr[countArr.length - 1]);

            for (int i = n-1; i >= 0; i--) {
                int tmp = arr[i] / quotient;

                countArr[tmp%mod] -= 1;
                int index = countArr[tmp%mod];

                result[index] = arr[i];
                System.out.printf("%d ", index);
            }
            System.out.println();

            for (int i = 0; i < result.length; i++) {
                arr[i] = result[i];
                System.out.printf("%d ", result[i]);
            }
            System.out.println();

        }
    }

    private int[] readArr(int n) {
        int[] arr = new int[n];

        for (int i = 0; i < n; i++) {
            arr[i] = sc.nextInt();
        }

        return arr;
    }

    public static void main(String[] arg) {

        String line = sc.nextLine();
        String[] args = line.split(" ");

        String mode = args[0];
        String algorithm = args[1];
        String direction = args[2];
        int size = Integer.parseInt(args[3]);
        Naloga1 o = new Naloga1();

        switch (algorithm) {
            case "bs":
                BubbleSort bs = o.new BubbleSort(size);
                o.sort(bs.getNsUp(), bs.getNsDown(), mode, direction);
                break;
            case "ss":
                SelectionSort ss = o.new SelectionSort(size);
                o.sort(ss.getNsUp(), ss.getNsDown(), mode, direction);
                break;
            case "is":
                InsertionSort is = o.new InsertionSort(size);
                o.sort(is.getNsUp(), is.getNsDown(), mode, direction);
                break;
            case "hs":
                HeapSort hs = o.new HeapSort(size);
                o.sort(hs.getNsUp(), hs.getNsDown(), mode, direction);
                break;
            case "qs":
                QuickSort qs = o.new QuickSort(size);
                o.sort(qs.getNsUp(), qs.getNsDown(), mode, direction);
                break;
            case "ms":
                MergeSort ms = o.new MergeSort(size);
                o.sort(ms.getNsUp(), ms.getNsDown(), mode, direction);
                break;
            case "cs":
                // veljavni argumenti samo trace up
                CountingSort cs = o.new CountingSort(size);
                o.sort(cs.getNsUp(), cs.getNsUp(), mode, direction);
                break;
            case "rs":
                RadixSort rs = o.new RadixSort(size);
                o.sort(rs.getNsUp(), rs.getNsUp(), mode, direction);
                break;
            default:
                System.out.println("Neveljavni argumenti.");
        }
    }
}
