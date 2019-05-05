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

            Matrix c = new Matrix(n, n, n);

            for(int i = 0; i< n;i++) {
                for(int j = 0; j<n;j++) {
                    c.setV(i, j, m[i][j]+b.getV(i, j));
                }
            }

            return c;
        }

        // matrix subtraction
        private Matrix sub(Matrix b) {

            Matrix c = new Matrix(n);

            for(int i = 0; i< n;i++) {
                for(int j = 0; j<n;j++) {
                    c.setV(i, j, m[i][j]-b.getV(i, j));
                }
            }

            return c;
        }

        //simple multiplication
        private Matrix mult(Matrix b) {
            Matrix c = new Matrix(this.n, this.a, b.b);
            for (int i = 0; i < this.n; i++) {
                for (int j = 0; j < this.n; j++) {
                    c.setV(i, j, 0);
                    for (int k = 0; k < this.n; k++) {
                        int val = this.getV(i, k) * b.getV(k, j);
                        c.setV(i, j, c.getV(i, j) + val);
                    }
                }
            }

            return c;
        }

        private Matrix multBlock(Matrix that, int cutoff) {
            Matrix tmp = new Matrix(this.a, this.a, that.b);

            for (int i = 0; i < this.a; i += cutoff) {
                for (int j = 0; j < this.b; j += cutoff) {
                    Matrix m1 = this.getSubmatrix(i, j, cutoff);
                    Matrix m2 = that.getSubmatrix(j, i, cutoff);

                    Matrix product = m1.mult(m2);
                    System.out.println(product.sumAll());
                    tmp = tmp.sum(product);
                }
            }
            return tmp;
        }

        private Matrix divConqMult(Matrix that, int cutoff) {
            int dim = this.n / 2;
            return null;
        }

        // Strassen multiplication
        private Matrix multStrassen(Matrix b, int cutoff) {
            int dim = this.n / 2;
            // Matrix m1 = ( (1, 1) + (2,2) ) * ( (1,1) + (2,2) );
            Matrix m1 = (this.getSubmatrix(0, 0, dim).sum(this.getSubmatrix(dim, dim, dim))).matrixMulti(b.getSubmatrix(0, 0, dim).sum(b.getSubmatrix(dim, dim, dim)), cutoff);
            // m1.toPrint();
            // Matrix m2 = ( (2,1) + (2,2) ) * (1,1);
            Matrix m2 = this.getSubmatrix(dim, 0, dim).sum(this.getSubmatrix(dim, dim, dim)).matrixMulti(b.getSubmatrix(0, 0, dim), cutoff);
            // m2.toPrint();
            // Matrix m3 = (1,1) * ( (1,2) - (2,2) );
            Matrix m3 = this.getSubmatrix(0, 0, dim).matrixMulti(b.getSubmatrix(0, dim, dim).sub(b.getSubmatrix(dim, dim, dim)), cutoff);
            // m3.toPrint();
            // Matrix m4 = (2,2) * ( (2,1) - (1,1) );
            Matrix m4 = this.getSubmatrix(dim, dim, dim).matrixMulti(b.getSubmatrix(dim, 0, dim).sub(b.getSubmatrix(0, 0, dim)), cutoff);
            // m4.toPrint();
            // Matrix m5 = ( (1,1) + (1,2) ) * (2, 2);
            Matrix m5 = this.getSubmatrix(0, 0, dim).sum(this.getSubmatrix(0, dim, dim)).matrixMulti(b.getSubmatrix(dim, dim, dim), cutoff);
            // m5.toPrint();
            // Matrix m6 = ( (2, 1) - (1, 1) ) * ( (1, 1) + (1, 2) );
            Matrix m6 = this.getSubmatrix(dim, 0, dim).sub(this.getSubmatrix(0, 0, dim)).matrixMulti(b.getSubmatrix(0, 0, dim).sum(b.getSubmatrix(0, dim, dim)), cutoff);
            // m6.toPrint();
            // Matrix m7 = ( (1, 2) - (2, 2) ) * ( (2, 1) + (2, 2) );
            Matrix m7 = this.getSubmatrix(0, dim, dim).sub(this.getSubmatrix(dim, dim, dim)).matrixMulti(b.getSubmatrix(dim, 0, dim).sum(b.getSubmatrix(dim, dim, dim)), cutoff);
            // m7.toPrint();

            System.out.printf("m1: %d\n", m1.sumAll());
            System.out.printf("m2: %d\n", m2.sumAll());
            System.out.printf("m3: %d\n", m3.sumAll());
            System.out.printf("m4: %d\n", m4.sumAll());
            System.out.printf("m5: %d\n", m5.sumAll());
            System.out.printf("m6: %d\n", m6.sumAll());
            System.out.printf("m7: %d\n", m7.sumAll());

            Matrix c11 = ((m1.sum(m4)).sub(m5)).sum(m7);
            Matrix c12 = m3.sum(m5);
            Matrix c21 = m2.sum(m4);
            Matrix c22 = ((m1.sub(m2)).sum(m3)).sum(m6);

            Matrix c = new Matrix(this.n);
            c.putSubmatrix(0, 0, c11);
            c.putSubmatrix(0, dim, c12);
            c.putSubmatrix(dim, 0, c21);
            c.putSubmatrix(dim, dim, c22);

            return c;
        }

        Matrix matrixMulti(Matrix b, int cutoff) {
            // System.out.printf("n: %d <= cutoff: %d\n", this.n, cutoff);
            if (this.n <= cutoff) {
                return this.mult(b);
            }

            return this.multStrassen(b, cutoff);
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
        int cutoff;

        String mode;
        Matrix a, b;

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

                a.toPrint(true);
                b.toPrint(true);

                break;
            default:
                System.out.println("Wrong arguments!");
        }
    }
}
