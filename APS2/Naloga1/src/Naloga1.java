
import java.io.IOException;
import java.util.Scanner;

@SuppressWarnings("Duplicates")
public class Naloga1 {

    interface NumericSort {
        void sort(boolean print);
    }

    private void sort(NumericSort nsUp, NumericSort nsDown, String mode, String direction) {
        if (mode.equals("trace")) {
            if (direction.equals("up")) {
                nsUp.sort(true);
            }
            else if (direction.equals("down")) {
                nsDown.sort(true);
            } else {
                System.out.println("Napacni argumenti!");
                System.exit(1);
            }
        } else if (mode.equals("count")) {
            if (direction.equals("up")) {
                nsUp.sort(false);
                nsUp.sort(false);
                nsDown.sort(false);
            }
            else if (direction.equals("down")) {
                nsDown.sort(false);
                nsDown.sort(false);
                nsUp.sort(false);
            } else {
                System.out.println("Napacni argumenti!");
                System.exit(1);
            }

        } else {
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
            return (print) -> {
                boolean bool = true;
                int iter = 0;
                int compares = 0;
                int setters = 0;

                while (bool) {
                    if (print) bubblePrint(iter);
                    bool = false;
                    for (int i = arr.length - 2; i >= 0; i--) {
                        compares += 1;
                        if (arr[i] < arr[i + 1]) {
                            setters += 3;
                            swap(i);
                            bool = true;
                        }
                    }
                    iter += 1;
                }
                if (!print) System.out.printf("%d %d\n", compares, setters);
            };
        }

        private NumericSort getNsDown() {
            return (print) -> {
                boolean bool = true;
                int iter = 0;
                int compares = 0;
                int setters = 0;

                while (bool) {
                    if (print) bubblePrint(iter);
                    bool = false;
                    for (int i = arr.length - 2; i >= 0; i--) {
                        compares += 1;
                        if (arr[i] > arr[i + 1]) {
                            setters += 3;
                            swap(i);
                            bool = true;
                        }
                    }
                    iter += 1;
                }
                if (!print) System.out.printf("%d %d\n", compares, setters);
            };
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
            return (print) -> {
                int setters = 0;
                int compares = 0;
                for (int i = 0; i < arr.length-1; i++) {
                    if (print) selectionPrint(i);
                    int minIndex = findMin(i, arr.length);
                    swap(i, minIndex);
                    setters += 3;
                    compares += n-i-1;
                }

                if (!print) System.out.printf("%d %d\n", compares, setters);
            };
        }

        private NumericSort getNsDown() {
            return (print) -> {
                int setters = 0;
                int compares = 0;
                for (int i = 0; i < arr.length-1; i++) {
                    if (print) selectionPrint(i);
                    int maxIndex = findMax(i, arr.length);
                    swap(i, maxIndex);
                    setters += 3;
                    compares += n-i-1;
                }

                if (!print) System.out.printf("%d %d\n", compares, setters);
            };
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

    private int[] readArr(int n) {
        Scanner sc = new Scanner(System.in);
        int[] arr = new int[n];

        for (int i = 0; i < n; i++) {
            arr[i] = sc.nextInt();
        }

        return arr;
    }

    public static void main(String[] args) throws IOException {

        if (args.length < 1) {
            System.out.println("Podaj argumente prijatelj.");
            System.exit(1);
        }

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
                break;
            case "hs":
                break;
            case "qs":
                break;
            case "ms":
                break;
            case "cs":
                break;
            case "rs":
                break;
            default:
                System.out.println("Neveljavni argumenti.");
        }
    }
}
