import java.util.Arrays;
import java.util.Scanner;

public class NumberMultiplication {

    public static class Number {
        int[] tab;
        int base;
        int length;

        Number(String num, int base) {
            this.base = base;
            char[] charArray = num.toCharArray();
            this.length = charArray.length;
            this.tab = new int[charArray.length];

            for (int i = 0; i < charArray.length; i++) {
                if (charArray[i] > 60) {
                    tab[i] = charArray[i] - 87;
                }
                else {
                    tab[i] = charArray[i] - '0';
                }
            }
        }

        Number(int length, int base) {
            this.base = base;
            this.length = length;
            this.tab = new int[length];
        }

        Number multiplyBasic(Number that, boolean print) {

//            System.out.printf("%s %s\n", Arrays.toString(this.tab), Arrays.toString(that.tab));
//            System.out.printf("%d %d\n", this.length, that.length);

            int start = this.length > that.length ? this.length : that.length;
            this.resize(start);
            that.resize(start);
            int index;
            Number result = new Number(this.length + that.length + 2, this.base), tmp;

            for  (int value : that.tab) {
                index = start - 1;
                tmp = new Number(this.length + that.length + 2, this.base);

                for (int j = this.length - 1; j > -1; j--) {
                    result.tab[index] += this.tab[j] * value;
                    tmp.tab[index] += this.tab[j] * value;

                    result.tab[index + 1] += result.tab[index] / base;
                    result.tab[index] %= base;

                    tmp.tab[index + 1] += tmp.tab[index] / base;
                    tmp.tab[index] %= base;

                    index += 1;
                }

                result.tab[index + 1] += result.tab[index] / base;
                result.tab[index] %= base;

                tmp.tab[index + 1] += tmp.tab[index] / base;
                tmp.tab[index] %= base;

                if (print) {
                    System.out.println(tmp.toStringReverse(true));
                }

                start -= 1;
            }

            return result;
        }

        Number multiplyBasic(Number that) {
            return multiplyBasic(that, false);
        }

        private Number multiplyDivConq(Number b) {

            int aNumLen = this.numberLength();
            int bNumLen = b.numberLength();

//            System.out.printf("%d %d\n", this.length, b.length);
//            System.out.printf("%s %s\n", Arrays.toString(this.tab), Arrays.toString(b.tab));
            System.out.printf("%s %s\n", this, b);

//            if (this.sumAll() == 0 || b.sumAll() == 0) {
//                System.out.println(0);
//                return new Number(1, this.base);
//            }

            if (aNumLen < 2 || bNumLen < 2) {
                Number tmp = this.multiplyBasic(b);
                System.out.println(tmp.toStringReverse());
                return tmp;
            }

            int len = Math.max(aNumLen, bNumLen);
            if (len % 2 != 0) len += 1;
            int half = len / 2;

            this.resize(len);
            b.resize(len);

            Number a0 = this.subnumber(0, half);
            Number a1 = this.subnumber(half, this.length);
            Number b0 = b.subnumber(0, half);
            Number b1 = b.subnumber(half, b.length);

            Number a1b1 = a1.multiplyDivConq(b1);
            Number a1b0 = a1.multiplyDivConq(b0).shiftLeft(len / 2);
            Number a0b1 = a0.multiplyDivConq(b1).shiftLeft(len / 2);
            Number a0b0 = a0.multiplyDivConq(b0).shiftLeft(len);

            int maxLen = max(a1b1.length, a1b0.length, a0b1.length, a0b0.length);
//            System.out.println("maxLen:" + maxLen);

            a1b1.enlarge(maxLen);
            a1b0.enlarge(maxLen);
            a0b1.enlarge(maxLen);
            a0b0.enlarge(maxLen);

//            Number a1b1 = a1.multiplyDivConq(b1);
//            Number a1b0 = a1.multiplyDivConq(b0);
//            Number a0b1 = a0.multiplyDivConq(b1);
//            Number a0b0 = a0.multiplyDivConq(b0);


//            Number a1b1 = multiplyDivConq(b);
//            Number a1b0 = multiplyDivConq(b).shiftRight(len / 2);
//            Number a0b1 = multiplyDivConq(b).shiftRight(len / 2);
//            Number a0b0 = multiplyDivConq(b).shiftRight(len);
//            System.out.printf("%d\n", a0b0.numberLength());
//            System.out.printf("a0b0: %s\n", Arrays.toString(a0b0.tab));
//            System.out.printf("%d\n", a0b1.numberLength());
//            System.out.printf("a0b1: %s\n", Arrays.toString(a0b1.tab));
//            System.out.printf("%d\n", a1b0.numberLength());
//            System.out.printf("a1b0: %s\n", Arrays.toString(a1b0.tab));
//            System.out.printf("%d\n", a1b1.numberLength());
//            System.out.printf("a1b1: %s\n", Arrays.toString(a1b1.tab));

            Number tmp = sum(a1b1, a1b0, a0b1, a0b0);
//            tmp.reduce(tmp.numberLength());
//            System.out.printf("%d\n", tmp.numberLength());
//            System.out.printf("a0b0: %s\n", Arrays.toString(tmp.tab));
            System.out.println(tmp);
            return tmp;
        }

        int sumAll() {
            int sum = 0;
            for (int value : this.tab) {
                sum += value;
            }
            return sum;
        }

        Number sum(Number ... numbers) {

            int[] tmp = new int[numbers[0].length * 2 + 2];
//            System.out.println("TMP LENGTH: " + tmp.length);
            for (int i = numbers[0].length - 1; i >= 0; i--) {
                int index = tmp.length - 1 - (numbers[0].length - i - 1);
//                System.out.println(index);
                for (Number number : numbers) {
                    tmp[index] += number.tab[i];
                }

                tmp[index - 1] = tmp[index] / base;
                tmp[index] = tmp[index] % base;
            }

//            System.out.println(Arrays.toString(tmp));

            Number res = new Number(tmp.length, this.base);
            res.tab = tmp;
            return res;
        }


        Number subnumber(int start, int end) {
            int[] tmp = new int[end - start];

            System.arraycopy(this.tab, start, tmp, 0, tmp.length);

            Number res = new Number(tmp.length, this.base);
            res.tab = tmp;
            return res;
        }

        void resize(int n) {
            if (n < this.length) {
                this.reduce(n);
            }
            else if (n > this.length) {
                this.enlarge(n);
            }
        }

        void reduce(int n) {
            int[] tmp = new int[n];
            int start = this.length - this.numberLength();
//            System.arraycopy(this.tab, start, tmp, 0, tmp.length - start + 1);

            for (int i = 0; i < n && start + i <= this.tab.length; i++) {
                tmp[i] = this.tab[start + i];
            }

            this.tab = tmp;
            this.length = n;

        }

//        void reduce(int n) {
//            int[] tmp = new int[n];
//            int start = this.length - this.numberLength();
//            if (tmp.length - start >= 0) System.arraycopy(this.tab, start, tmp, 0, tmp.length - start);
//            this.tab = tmp;
//            this.length = n;
//
//        }

        void enlarge(int n) {
            int[] tmp = new int[n];
            int start = n - this.length;
            System.arraycopy(this.tab, 0, tmp, start, this.tab.length);
            this.tab = tmp;
            this.length = n;

        }

        void shiftRight() {
            int tmp = this.tab[this.length - 1];
            System.arraycopy(this.tab, 0, this.tab, 1, this.length - 1);
            this.tab[0] = tmp;
        }

        Number shiftRight(int amount) {
            for (int i = 0; i < amount; i++) {
                this.shiftRight();
            }
            return this;
        }

        void shiftLeft() {
            int tmp = this.tab[0];
            System.arraycopy(this.tab, 1, this.tab, 0, this.length - 1);
            this.tab[this.length - 1] = tmp;
        }

        Number shiftLeft(int amount) {
            for (int i = 0; i < amount; i++) {
                this.shiftLeft();
            }
            return this;
        }

        void align() {
            for (int i = 0; i < this.length && this.tab[this.tab.length - 1] == 0; i++) {
                this.shiftRight();
            }
        }

        int numberLength() {
            int res = 0;
            for (int i = this.tab.length - 1; i >= 0; i--) {
                if (this.tab[i] > 0) {
                    res = this.tab.length - 1 - i;
                }
            }

            return res + 1;
        }

        Number reverseTab() {
            int tmp;
            for (int i = 0; i < this.tab.length / 2; i++) {
                tmp = this.tab[this.tab.length - 1 - i];
                this.tab[this.tab.length - 1 - i] = this.tab[i];
                this.tab[i] = tmp;
            }

            return this;
        }

        String toStringReverse(boolean align) {
            this.reverseTab();
            if (align) this.align();
            return this.toString();
        }

        String toStringReverse() {
            return this.toStringReverse(false);
        }

        String toString(boolean align) {
            StringBuilder tmp = new StringBuilder();
            if (align) this.align();
            int len = this.numberLength();

            if (len == 0) {
                return "0";
            }

            for (int i = this.tab.length - 1; i >= this.tab.length - len; i--) {
                if (this.tab[i] > 9) {
                    tmp.insert(0, (char)(this.tab[i] + 87));
                }
                else {
                    tmp.insert(0, this.tab[i]);
                }
            }

            return tmp.toString();
        }

        @Override
        public String toString() {
            return this.toString(false);
        }

    }

    public static int max(int ... integers) {
        int max = Integer.MIN_VALUE;
        for (int integer : integers) {
            if (integer > max) {
                max = integer;
            }
        }
        return max;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        Number a, b, result;
        String mode;
        int base;

        mode = sc.next();
        base = sc.nextInt();

        switch (mode) {
            case "os":
                a = new Number(sc.next(), base);
                b = new Number(sc.next(), base);
                result = a.multiplyBasic(b, true).reverseTab();

                for (int i = 0; i < result.numberLength(); i++) {
                    System.out.print("-");
                }
                System.out.println();
                System.out.println(result.toString());

                break;
            case "dv":
                a = new Number(sc.next(), base);
                b = new Number(sc.next(), base);

                a.multiplyDivConq(b);

                break;
            default:
                System.out.println("Wrong arguments!");
        }

    }
}
