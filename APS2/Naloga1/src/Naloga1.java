import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

@SuppressWarnings("Duplicates")
public class Naloga1 {

    public class BubbleSort {
        int n;
        int[] arr;

        public BubbleSort(int n) {
            this.n = n;
            this.arr = readArr(n);
        }

        public void sort(String mode, String direction) {
            if (mode.equals("trace")) {
                if (direction.equals("up")) {
                    bubbleSortUp(true);
                }
                else if (direction.equals("down")) {
                    bubbleSortDown(true);
                } else {
                    System.out.println("Napacni argumenti!");
                    System.exit(1);
                }
            } else if (mode.equals("count")) {
                if (direction.equals("up")) {
                    bubbleSortUp(false);
                    bubbleSortUp(false);
                    bubbleSortDown(false);
                }
                else if (direction.equals("down")) {
                    bubbleSortDown(false);
                    bubbleSortDown(false);
                    bubbleSortUp(false);
                } else {
                    System.out.println("Napacni argumenti!");
                    System.exit(1);
                }

            } else {
                System.out.println("Napacni argumenti!");
                System.exit(1);
            }
        }

        private void bubbleSortUp(boolean print) {

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
                        swap(i, arr);
                        bool = true;
                    }
                }
                iter += 1;
            }

            if (!print) {
                System.out.printf("%d %d\n", compares, setters);
            }
        }

        private void bubbleSortDown(boolean print) {

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
                        swap(i, arr);
                        bool = true;
                    }
                }
                iter += 1;
            }

            if (!print) {
                System.out.printf("%d %d\n", compares, setters);
            }
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

    private int[] readArr(int n) {
        Scanner sc = new Scanner(System.in);
        int[] arr = new int[n];

        for (int i = 0; i < n; i++) {
            arr[i] = sc.nextInt();
        }

        return arr;
    }

    private void swap (int i, int[] arr) {
        int temp = arr[i];
        arr[i] = arr[i+1];
        arr[i+1] = temp;
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
                bs.sort(mode, direction);
                break;
            case "ss":
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
