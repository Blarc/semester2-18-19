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
            int start = this.length - 1, index = 0;
            Number result = new Number(this.length + that.length + 2, this.base), tmp;

            for  (int value : that.tab) {
                index = start;
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
                    System.out.println(tmp);
                }

                start -= 1;
            }

            return result;
        }

        Number multiplyBasic(Number that) {
            return multiplyBasic(that, false);
        }

        Number multiplyDivConq(Number b, int aStart, int aEnd, int bStart, int bEnd) {
            
            System.out.print(this + " " + b + "\n");

            // TODO IF

            int aLen = aEnd - aStart;
            int bLen = bEnd - bStart;

            if (aLen == 0 || bLen == 0) {
                System.out.println(0);
                return new Number(this.length + b.length + 2, this.base);
            }

            if (aLen < 2 || bLen < 2) {
                Number tmp = this.multiplyBasic(b);
                System.out.println(tmp);
                return tmp;
            }

            int len = Math.max(aLen, bLen);
            if (len % 2 != 0) len += 1;
            int half = len / 2;

            int aHalf = aStart + half;
            int bHalf = bStart + half;

            Number a1b1 = multiplyDivConq(b, aHalf,   aEnd, bHalf,   bEnd);
            Number a1b0 = multiplyDivConq(b, aHalf,   aEnd, bStart, bHalf).shiftRight(len / 2);
            Number a0b1 = multiplyDivConq(b, aStart, aHalf, bHalf,   bEnd).shiftRight(len / 2);
            Number a0b0 = multiplyDivConq(b, aStart, aHalf, bStart, bHalf).shiftRight(len);

            Number tmp = sum(a1b1, a1b0, a0b1, a0b0);
            System.out.println(tmp);
            return tmp;
        }

        public Number multiplyDivConq(Number b) {
            return multiplyDivConq(b, 0, this.length, 0, b.length);
        }

        Number sum(Number ... numbers) {
            Number tmp = new Number(numbers[0].length, numbers[0].base);
            for (int i = 0; i < numbers[0].length; i++) {
                for (Number a : numbers) {
                   tmp.tab[i] += a.tab[i];
                }
            }
            return tmp;
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

        void align() {
            for (int i = 0; i < this.length && this.tab[0] == 0; i++) {
                this.shiftLeft();
            }
        }

        int numberLength() {
            int res = 0;
            for (int i = 0; i < this.tab.length; i++) {
                if (this.tab[i] > 0) {
                    res = i;
                }
            }
            return res;
        }

        @Override
        public String toString() {
            StringBuilder tmp = new StringBuilder();
            this.align();

            int len = this.numberLength();

            if (len == 0) {
                return "0";
            }

            for (int i = len; i > -1; i--) {
                if (this.tab[i] > 9) {
                    tmp.append((char)(this.tab[i] + 87));
                }
                else {
                    tmp.append(this.tab[i]);
                }
            }

            return tmp.toString();
        }

    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        Number a, b, result;
        String mode;
        int base;

        mode = sc.next();
        base = sc.nextInt();

        NumberMultiplication nm = new NumberMultiplication();

        switch (mode) {
            case "os":
                a = new Number(sc.next(), base);
                b = new Number(sc.next(), base);
                result = a.multiplyBasic(b, true);

                for (int i = 0; i <= result.numberLength(); i++) {
                    System.out.print("-");
                }
                System.out.println();
                System.out.println(result);

                break;
            case "dv":
                a = new Number(sc.next(), base);
                b = new Number(sc.next(), base);


                break;
            default:
                System.out.println("Wrong arguments!");
        }

    }
}
