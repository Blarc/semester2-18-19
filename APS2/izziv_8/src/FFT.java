import java.util.Scanner;

@SuppressWarnings("Duplicates")
public class FFT {
    static private int size;

    static class Complex{
        double re;
        double im;

        public Complex(double real, double imag) {
            re = real;
            im = imag;
        }

        public String toString() {
            double tRe = (double)Math.round(re * 100000) / 100000;
            double tIm = (double)Math.round(im * 100000) / 100000;
            if (tIm == 0) return tRe + "";
            if (tRe == 0) return tIm + "i";
            if (tIm <  0) return tRe + "-" + (-tIm) + "i";
            return tRe + "+" + tIm + "i";
        }

        public Complex conj() {
            return new Complex(re, -im);
        }

        // sestevanje
        public Complex plus(Complex b) {
            Complex a = this;
            double real = a.re + b.re;
            double imag = a.im + b.im;
            return new Complex(real, imag);
        }

        // odstevanje
        public Complex minus(Complex b) {
            Complex a = this;
            double real = a.re - b.re;
            double imag = a.im - b.im;
            return new Complex(real, imag);
        }

        // mnozenje z drugim kompleksnim stevilo
        public Complex times(Complex b) {
            Complex a = this;
            double real = a.re * b.re - a.im * b.im;
            double imag = a.re * b.im + a.im * b.re;
            return new Complex(real, imag);
        }

        // mnozenje z realnim stevilom
        public Complex times(double alpha) {
            return new Complex(alpha * re, alpha * im);
        }

        // reciprocna vrednost kompleksnega stevila
        public Complex reciprocal() {
            double scale = re*re + im*im;
            return new Complex(re / scale, -im / scale);
        }

        // deljenje
        public Complex divides(Complex b) {
            Complex a = this;
            return a.times(b.reciprocal());
        }

        public Complex scale(double a) {
            return new Complex(a * re, a * im);
        }

        // e^this
        public Complex exp() {
            return new Complex(Math.exp(re) * Math.cos(im), Math.exp(re) * Math.sin(im));
        }


        //potenca komplesnega stevila
        public Complex pow(int k) {

            Complex c = new Complex(1,0);
            for (int i = 0; i <k ; i++) {
                c = c.times(this);
            }
            return c;
        }
    }

    private static Complex[] Fft(Complex[] list) {

        int len = list.length;

        if (len < 2) {
            return new Complex[] { list[0] };
        }

        Complex[] a = new Complex[len/2];
        Complex[] b = new Complex[len/2];

        int aIter = 0, bIter = 0;
        for (int i = 0; i < len; i++) {
            if (i % 2 == 0) {
                a[aIter] = list[i];
                aIter += 1;
            }
            else {
                b[bIter] = list[i];
                bIter += 1;
            }
        }

        Complex[] left = Fft(a);
        Complex[] right = Fft(b);

        Complex wFactor = new Complex(Math.cos(2 * Math.PI / len), Math.sin(2 * Math.PI / len));
        Complex w = new Complex(1, 0);
        Complex[] tmp = new Complex[len];

        for (int i = 0; i < len/2; i++) {

            tmp[i]           = left[i].plus(w.times(right[i]));
            tmp[i + (len/2)] = left[i].minus(w.times(right[i]));

            w = w.times(wFactor);
        }

        printArr(tmp);
        return tmp;
    }

    private static Complex[] iFft(Complex[] list) {

        int len = list.length;

        if (len < 2) {
            return new Complex[] { list[0] };
        }

        Complex[] a = new Complex[len/2];
        Complex[] b = new Complex[len/2];

        int aIter = 0, bIter = 0;
        for (int i = 0; i < len; i++) {
            if (i % 2 == 0) {
                a[aIter] = list[i];
                aIter += 1;
            }
            else {
                b[bIter] = list[i];
                bIter += 1;
            }
        }

        Complex[] left = iFft(a);
        Complex[] right = iFft(b);

        Complex wFactor = new Complex(Math.cos(2 * Math.PI / len), Math.sin(2 * Math.PI / len));
        Complex w = new Complex(1, 0);
        Complex[] tmp = new Complex[len];

        for (int i = 0; i < len/2; i++) {

            tmp[i]           = left[i].plus(w.conj().times(right[i]));
            tmp[i + (len/2)] = left[i].minus(w.conj().times(right[i]));

            w = w.times(wFactor);
        }

        printArr(tmp);
        return tmp;
    }

    private static void printArr(Complex[] arr) {
        for (Complex i : arr) {
            System.out.printf("%s ", i);
        }
        System.out.println();
    }

    private static int nextPow2(int a) {
        int c = 2;
        while (a > c) {
            c = c << 1;
        }
        return c;
    }

    private static Complex[] readVector(int n, Scanner sc) {
        size = nextPow2(n*2);
        Complex[] tmp = new Complex[size];

        for (int i = 0; i < n; i++) {
            tmp[i] = new Complex(sc.nextDouble(), 0);
        }

        for (int i = n; i < tmp.length; i++) {
            tmp[i] = new Complex(0, 0);
        }

        return tmp;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();

        Complex[] a = readVector(n, sc);
        Complex[] b = readVector(n, sc);

        a = Fft(a);
        b = Fft(b);

        for (int i = 0; i < a.length; i++) {
            a[i] = a[i].times(b[i]);
        }

        a = iFft(a);

        for (int i = 0; i < a.length; i++) {
            a[i] = a[i].scale(1.0 / size);
        }

        printArr(a);
    }
}
