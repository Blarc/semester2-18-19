public class vaje1 {

    public int[] generateTable(int n) {
        int[] table = new int[n];
        for (int i = 0; i < n; i++) {
            table[i] = i;
        }
        return table;
    }

    int findLinear(int[] a, int v) {
        int i = 0;
        while (a[i] != v) {
            i++;
        }
        return i;
    }

    int findBinary(int[] a, int l, int r, int v) {
        if (v == a[l]) {
            return l;
        }
        if (v == a[r]) {
            return r;
        }
        if (v < a[r/2]) {
            return findBinary(a, l, r/2, v);
        }
        return findBinary(a, r/2, r, v);

    }

}
