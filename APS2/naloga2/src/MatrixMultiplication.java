import java.util.Scanner;

public class MatrixMultiplication {

    class Matrix {
        private int[][] m;
        private int n; //only square matrices
        private int a;
        private int b;

        Matrix(int n) {
            this.n = n;
            m = new int[n][n];
        }

        Matrix(int n, int a, int b) {
            this.n = n;
            this.a = a;
            this.b = b;
            m = new int[n][n];
        }

        //set value at i,j
        void setV(int i, int j, int val) {
            m[i][j] = val;
        }

        // get value at index i,j
        private int getV(int i, int j) {
            return m[i][j];
        }


        // return a square submatrix from this
        private Matrix getSubmatrix(int startRow, int startCol, int dim) {

            Matrix subM = new Matrix(dim, dim, dim);

            for (int i = 0; i < dim; i++) {
                for (int j = 0; j < dim; j++) {
                    subM.setV(i, j, m[startRow + i][startCol + j]);
                }
            }

            return subM;
        }

        // write this matrix as a submatrix from b (useful for the result of multiplication)
        private void putSubmatrix(int startRow, int startCol, Matrix b) {

            for (int i = 0; i < b.n; i++ ) {
                for (int j = 0; j < b.n; j++) {
                    setV(startRow + i, startCol + j, b.getV(i, j));
                }
            }
        }

        // matrix addition
        private Matrix sum(Matrix b) {

            Matrix c = new Matrix(n, this.a, this.b);

            for(int i = 0; i< n;i++) {
                for(int j = 0; j<n;j++) {
                    c.setV(i, j, m[i][j]+b.getV(i, j));
                }
            }

            return c;
        }

        // matrix subtraction
        private Matrix sub(Matrix b) {

            Matrix c = new Matrix(n, this.a, this.b);

            for(int i = 0; i< n;i++) {
                for(int j = 0; j<n;j++) {
                    c.setV(i, j, m[i][j]-b.getV(i, j));
                }
            }

            return c;
        }

        //simple multiplication
        private Matrix mult(Matrix that) {
            int n = this.n > that.n ? this.n : that.n;
            Matrix c = new Matrix(n, this.a, that.b);
            for (int i = 0; i < this.a; i++) {
                for (int j = 0; j < that.b; j++) {
                    c.setV(i, j, 0);
                    for (int k = 0; k < this.b; k++) {
                        int val = this.getV(i, k) * that.getV(k, j);
                        c.setV(i, j, c.getV(i, j) + val);
                    }
                }
            }

            return c;
        }

        private Matrix multBlock(Matrix that, int cutoff) {

            Matrix res = new Matrix(Math.max(this.n, that.n), this.a, that.b);

            for (int i = 0; i < res.a; i += cutoff) {
                for (int j = 0; j < res.b; j += cutoff) {

                    Matrix tmp = new Matrix(cutoff, cutoff, cutoff);

                    for (int k = 0; k < this.n; k += cutoff) {

                        Matrix m1 = this.getSubmatrix(i, k, cutoff);
                        Matrix m2 = that.getSubmatrix(k, j, cutoff);

                        Matrix product = m1.mult(m2);
                        tmp = tmp.sum(product);
                        System.out.println(product.sumAll());
                    }

                    res.putSubmatrix(i, j, tmp);
                }
            }
            return res;
        }

        private Matrix multDivConq(Matrix that, int cutoff) {

            if (this.n <= cutoff) {
                return this.mult(that);
            }

            int dim = this.n / 2;

            Matrix m1 = this.getSubmatrix(0,   0,   dim).multDivConq(that.getSubmatrix(0,   0,   dim), cutoff);
            System.out.println(m1.sumAll());
            Matrix m2 = this.getSubmatrix(0,   dim, dim).multDivConq(that.getSubmatrix(dim, 0,   dim), cutoff);
            System.out.println(m2.sumAll());
            Matrix m3 = this.getSubmatrix(0,   0,   dim).multDivConq(that.getSubmatrix(0,   dim, dim), cutoff);
            System.out.println(m3.sumAll());
            Matrix m4 = this.getSubmatrix(0,   dim, dim).multDivConq(that.getSubmatrix(dim, dim, dim), cutoff);
            System.out.println(m4.sumAll());
            Matrix m5 = this.getSubmatrix(dim, 0,   dim).multDivConq(that.getSubmatrix(0,   0,   dim), cutoff);
            System.out.println(m5.sumAll());
            Matrix m6 = this.getSubmatrix(dim, dim, dim).multDivConq(that.getSubmatrix(dim, 0,   dim), cutoff);
            System.out.println(m6.sumAll());
            Matrix m7 = this.getSubmatrix(dim, 0,   dim).multDivConq(that.getSubmatrix(0,   dim, dim), cutoff);
            System.out.println(m7.sumAll());
            Matrix m8 = this.getSubmatrix(dim, dim, dim).multDivConq(that.getSubmatrix(dim, dim, dim), cutoff);
            System.out.println(m8.sumAll());


            Matrix c11 = m1.sum(m2);
            Matrix c12 = m3.sum(m4);
            Matrix c21 = m5.sum(m6);
            Matrix c22 = m7.sum(m8);

            Matrix tmp = new Matrix(this.n, this.n, this.n);
            tmp.putSubmatrix(0, 0, c11);
            tmp.putSubmatrix(0, dim, c12);
            tmp.putSubmatrix(dim, 0, c21);
            tmp.putSubmatrix(dim, dim, c22);

            return tmp;
        }

        // Strassen multiplication
        private Matrix multStrassen(Matrix that, int cutoff) {

            if (this.n <= cutoff) {
                return this.mult(that);
            }

            int dim = this.n / 2;
            Matrix m1 = this.getSubmatrix(0,    0,      dim).multStrassen(that.getSubmatrix   (0,   dim, dim).sub(that.getSubmatrix(dim, dim, dim)), cutoff);
            System.out.printf("%d\n", m1.sumAll());
            Matrix m2 = this.getSubmatrix(0,    0,      dim).sum        (this.getSubmatrix(0,   dim, dim)).multStrassen(that.getSubmatrix(dim, dim, dim), cutoff);
            System.out.printf("%d\n", m2.sumAll());
            Matrix m3 = (this.getSubmatrix(dim,  0,      dim).sum        (this.getSubmatrix(dim, dim, dim))).multStrassen(that.getSubmatrix(0, 0, dim), cutoff);
            System.out.printf("%d\n", m3.sumAll());
            Matrix m4 = this.getSubmatrix(dim,  dim,    dim).multStrassen(that.getSubmatrix   (dim, 0,   dim).sub(that.getSubmatrix(0, 0, dim)), cutoff);
            System.out.printf("%d\n", m4.sumAll());
            Matrix m5 = (this.getSubmatrix(0,    0,      dim).sum        (this.getSubmatrix(dim, dim, dim))).multStrassen(that.getSubmatrix(0, 0, dim).sum(that.getSubmatrix(dim, dim, dim)), cutoff);
            System.out.printf("%d\n", m5.sumAll());
            Matrix m6 = (this.getSubmatrix(0,    dim,    dim).sub        (this.getSubmatrix(dim, dim, dim))).multStrassen(that.getSubmatrix(dim, 0, dim).sum(that.getSubmatrix(dim, dim, dim)), cutoff);
            System.out.printf("%d\n", m6.sumAll());
            Matrix m7 = (this.getSubmatrix(0,    0,      dim).sub        (this.getSubmatrix(dim, 0,   dim))).multStrassen(that.getSubmatrix(0, 0, dim).sum(that.getSubmatrix(0, dim, dim)), cutoff);
            System.out.printf("%d\n", m7.sumAll());


            Matrix c11 = m5.sum(m4).sub(m2).sum(m6);
            Matrix c12 = m1.sum(m2);
            Matrix c21 = m3.sum(m4);
            Matrix c22 = m1.sum(m5).sub(m3).sub(m7);

            Matrix c = new Matrix(this.n, this.n, this.n);
            c.putSubmatrix(0, 0, c11);
            c.putSubmatrix(0, dim, c12);
            c.putSubmatrix(dim, 0, c21);
            c.putSubmatrix(dim, dim, c22);

            return c;
        }

        void toPrint(boolean bol) {
            System.out.printf("DIMS: %dx%d\n", this.a, this.b);

            int a = bol ? this.n : this.a;
            int b = bol ? this.n : this.b;

            for (int i = 0; i < a; i++) {
                for (int j = 0; j < b; j++) {
                    System.out.printf("%d ", this.getV(i, j));
                }
                System.out.println();
            }
        }

        void toPrint() {
            toPrint(false);
        }

        private int sumAll() {
            int sum = 0;
            for (int i = 0; i < this.n; i++) {
                for (int j = 0; j < this.n; j++) {
                    sum += this.getV(i, j);
                }
            }
            return sum;
        }
    }

    private Matrix readMatrix(Scanner sc, int factor, boolean pow2) {
        int a, b, size;

        a = sc.nextInt();
        b = sc.nextInt();

        size = a > b ? a : b;

        if (!pow2) {
            while (size % factor != 0) {
                size += 1;
            }
        }
        else {
            size = nextPow2(size);
        }

        Matrix tmp = new Matrix(size, a, b);
        for (int i = 0; i < a; i++) {
            for (int j = 0; j < b; j++) {
                tmp.setV(i, j, sc.nextInt());
            }
        }

        return tmp;
    }

    private Matrix readMatrix(Scanner sc) {
        return readMatrix(sc, 1, false);
    }

    private Matrix readMatrix(Scanner sc, int factor) {
        return readMatrix(sc, factor, false);
    }

    private Matrix readMatrix(Scanner sc, boolean pow2) {
        return readMatrix(sc, 1, pow2);
    }


    private static int nextPow2(int a) {
        int c = 2;
        while (a > c) {
            c = c << 1;
        }
        return c;
    }

    public static void main(String[] args) {
        MatrixMultiplication mm = new MatrixMultiplication();
        Scanner sc = new Scanner(System.in);
        int cutoff, n;

        String mode;
        Matrix a, b, c, d;

        mode = sc.next();

        switch (mode) {
            case "os":
                a = mm.readMatrix(sc);
                b = mm.readMatrix(sc);

                a.mult(b).toPrint();

                break;
            case "bl":
                cutoff = sc.nextInt();

                // matrika mora biti velika večkratnik cutoff-a
                a = mm.readMatrix(sc, cutoff);
                b = mm.readMatrix(sc, cutoff);

                a.multBlock(b, cutoff).toPrint();

                break;
            case "dv":
                cutoff = sc.nextInt();

                // matrika mora biti velika potenca na 2
                a = mm.readMatrix(sc, true);
                b = mm.readMatrix(sc, true);

                n = Math.max(a.n, b.n);
                c = mm.new Matrix(n, n, n);
                d = mm.new Matrix(n, n, n);

                c.putSubmatrix(0, 0, a);
                d.putSubmatrix(0, 0, b);

                c.multDivConq(d, cutoff).toPrint(true);

                break;

            case "st":
                cutoff = sc.nextInt();

                // matrika mora biti velika potenca na 2
                a = mm.readMatrix(sc, true);
                b = mm.readMatrix(sc, true);

                n = Math.max(a.n, b.n);
                c = mm.new Matrix(n, n, n);
                d = mm.new Matrix(n, n, n);

                c.putSubmatrix(0, 0, a);
                d.putSubmatrix(0, 0, b);

                c.multStrassen(d, cutoff).toPrint(true);

                break;

            default:
                System.out.println("Wrong arguments!");

                break;
        }
    }
}
