import java.util.Arrays;
import java.util.Scanner;

@SuppressWarnings("Duplicates")
public class NumberMultiplication {

    public interface Multiplication {
        void multiply(int[] a, int[] b);
    }

    public class BasicMulti implements Multiplication {
        int base;

        public BasicMulti(int base) {
            this.base = base;
        }

        @Override
        public void multiply(int[] a, int[] b) {
            // or b length
            int start = a.length - 1, index = 0, checksum;
            int[] result = new int[(a.length + b.length + 2)], tmp;

            for (int value : b) {
                checksum = 0;
                index = start;
                tmp = new int[(a.length + b.length + 2)];
                for (int j = a.length - 1; j > -1; j--) {
                    result[index] += a[j] * value;
                    tmp[index] += a[j] * value;
                    checksum += a[j] * value;

                    result[index + 1] += result[index] / base;
                    result[index] %= base;

                    tmp[index+1] += tmp[index] / base;
                    tmp[index] %= base;

                    index += 1;
                }

                result[index + 1] += result[index] / base;
                result[index] %= base;

                tmp[index+1] += tmp[index] / base;
                tmp[index] %= base;

//                System.out.println(Arrays.toString(result));
//                System.out.println(Arrays.toString(tmp));

                if (tmp[index] > 0) {
                    print(index+1, start, tmp);
                    System.out.println();
                }
                else if (checksum == 0) {
                    System.out.println(0);
                }
                else {
                    print(index, start, tmp);
                    System.out.println();
                }
                start -= 1;
            }

            for (int i = 0; i < index + a.length - 1; i++) {
                System.out.print("-");
            }
            System.out.println();
            print(index + a.length - 1, 0, result);
            System.out.println();
        }


    }

    public class DivConqMulti implements Multiplication {
        int base;

        public DivConqMulti(int base) {
            this.base = base;
        }

        @Override
        public void multiply(int[] a, int[] b) {
            int[] product = multiply(a, b, 0, a.length, 0, b.length, 13);
        }

        public int[] multiply(int[] a, int[] b, int aStart, int aEnd, int bStart, int bEnd, int offset) {

            print(aStart, aEnd, a);
            System.out.print(" ");
            print(bStart, bEnd, b);
            System.out.println();

            int aLen = aEnd - aStart;
            int bLen = bEnd - bStart;

            if (aLen == 0 || bLen == 0) {
                System.out.println(0);
                return new int[(a.length + b.length + 2)];
            }

            if (aLen < 2 || bLen < 2) {

                int[] result = new int[(a.length + b.length + 2)];
                int index = 0;

                if (aLen < bLen) {
                    for (int i = bEnd - 1; i >= bStart; i--) {
                        result[index] += a[aStart] * b[i];
                        result[index + 1] += result[index] / base;
                        result[index] %= base;

                        index += 1;
                    }

                    result[index + 1] += result[index] / base;
                    result[index] %= base;
                }
                else {
                    for (int i = aEnd - 1; i >= aStart; i--) {
                        result[index] += a[i] * b[bStart];
                        result[index + 1] += result[index] / base;
                        result[index] %= base;

                        index += 1;
                    }

                    result[index + 1] += result[index] / base;
                    result[index] %= base;
                }

//                System.out.printf("offset: %d, index: %d\n", offset, index);
//                System.out.println(Arrays.toString(result));

                print(numberLength(result) + 1, 0, result);
                System.out.println();
                return result;
            }

            int aHalf = ( aStart + aEnd ) / 2;
            int bHalf = ( bStart + bEnd ) / 2;

            if (aLen > bLen) {
                bHalf -= 1;
            }
            else if (bLen > aLen) {
                aHalf -= 1;
            }

            offset = aLen > bLen ? aLen : bLen;
            if (offset % 2 != 0) {
                offset += 1;
            }

            int[] a1b1 = multiply(a, b, aHalf,  aEnd,  bHalf,  bEnd, 0);
            int[] a1b0 = multiply(a, b, aHalf,  aEnd,  bStart, bHalf, offset/2);
            int[] a0b1 = multiply(a, b, aStart, aHalf, bHalf,  bEnd, offset/2);
            int[] a0b0 = multiply(a, b, aStart, aHalf, bStart, bHalf, offset);

            shift(a1b0, offset / 2);
            shift(a0b1, offset / 2);
            shift(a0b0, offset);

//            System.out.println(Arrays.toString(a1b1));
//            System.out.println(Arrays.toString(a1b0));
//            System.out.println(Arrays.toString(a0b1));
//            System.out.println(Arrays.toString(a0b0));

            for (int i = 0; i < a1b1.length - 1; i++) {
                a1b1[i] += a0b1[i] + a1b0[i] + a0b0[i];
                a1b1[i + 1] += a1b1[i] / base;
                a1b1[i] %= base;

            }

//            System.out.println(Arrays.toString(a1b1));

            print(numberLength(a1b1) + 1, 0, a1b1);
            System.out.println();
            return a1b1;
        }
    }

    private static void shift(int[] arr) {
        int tmp = arr[arr.length - 1];
        System.arraycopy(arr, 0, arr, 1, arr.length - 1);
        arr[0] = tmp;
    }

    private static void shift(int[] arr, int amount) {
        for (int i = 0; i < amount; i++) {
            shift(arr);
        }
    }

    private static int numberLength(int[] arr) {
        int res = 1;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] > 0) {
                res = i;
            }
        }
        return res;
    }

    private static void print(int start, int end, int[] arr) {
        if (start < end) {
            for (int i = start; i < end; i++) {
                if (arr[i]  > 9) {
                    System.out.print((char)(arr[i] + 87));
                }
                else {
                    System.out.print(arr[i]);
                }
            }
        }
        else if (start == end) {
            System.out.print(0);
        }
        else {
            for (int i = start - 1; i > end - 1; i--) {
                if (arr[i]  > 9) {
                    System.out.print((char)(arr[i] + 87));
                }
                else {
                    System.out.print(arr[i]);
                }
            }
        }
    }

    private static int[] read(Scanner sc) {
        char[] a = sc.next().toCharArray();
        int[] aArray = new int[a.length];

        for (int i = 0; i < a.length; i++) {
            if (a[i] > 60) {
                aArray[i] = a[i] - 87;
            }
            else {
                aArray[i] = a[i] - '0';
            }
        }
//        System.out.println(Arrays.toString(aArray));
        return aArray;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        Multiplication multi;
        String mode;
        int[] aArray, bArray;
        int base;

        mode = sc.next();
        base = sc.nextInt();

        aArray = read(sc);
        bArray = read(sc);

//        System.out.println("a: " + Arrays.toString(aArray));
//        System.out.println("b: " + Arrays.toString(bArray));
        NumberMultiplication nm = new NumberMultiplication();

        switch (mode) {
            case "os":
                multi = nm.new BasicMulti(base);
                multi.multiply(aArray, bArray);
                break;
            case "dv":
                multi = nm.new DivConqMulti(base);
                multi.multiply(aArray, bArray);
                break;
            default:
                System.out.println("Wrong arguments!");
        }

    }
}
