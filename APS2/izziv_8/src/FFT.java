import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class FFT {
    private static int  n;

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

    private static Complex[] Fft(Complex[] list, int len) {

        if (len < 2) {
            return list;
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

        Complex[] left = Fft(a, len/2);
        Complex[] right = Fft(b, len/2);

        Complex wFactor = new Complex(Math.cos(2 * Math.PI / n), Math.sin(2 * Math.PI / n));
        Complex w = new Complex(1, 0);
        Complex[] tmp = new Complex[len];

        for (int i = 0; i < len/2; i++) {
            Complex tmpLeft = left[i];
            Complex tmpRight = right[i];

            tmp[i] = tmpLeft.plus(w.times(tmpRight));
            tmp[i + (len/2)] = tmpLeft.minus(w.times(tmpRight));

            w = w.times(wFactor);
        }

        System.out.println(Arrays.toString(tmp));
        return tmp;
    }

    private static Complex[] readVector(Scanner sc) {
        Complex[] tmp = new Complex[n];

        for (int i = 0; i < n; i++) {
            tmp[i] = new Complex(sc.nextDouble(), 0);
        }

        return tmp;
    }

//  https://introcs.cs.princeton.edu/java/97data/FFT.java.html
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        n = sc.nextInt();

        Complex[] a = readVector(sc);
        Complex[] b = readVector(sc);

        Fft(a, n);
        Fft(b, n);
    }
}
