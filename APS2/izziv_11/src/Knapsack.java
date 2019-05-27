import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

public class Knapsack {

    static class Item {
        int size;
        int val;

        Item(int size, int val) {
            this.size = size;
            this.val = val;
        }

        boolean invalid(Item i, int limit) {
            return this.size <= i.size && this.val >= i.val || i.size > limit;
        }

        Item sum(Item i) {
            return new Item(this.size + i.size, this.val + i.val);
        }

        @Override
        public String toString() {
            return String.format("(%d, %d)", size, val);
        }
    }

    static void toPrint(ArrayList<Item> items) {
        items.forEach(i -> {
            System.out.printf("%s ", i.toString());
        });
        System.out.println();
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int limit, n, i;
        boolean bool;
        ArrayList<Item> add, remove, result = new ArrayList<>();

        Comparator<Item> comp = Comparator.comparingInt(o -> o.size);

        limit = sc.nextInt();
        n = sc.nextInt();

        result.add(new Item(0, 0));
        for (i = 0; i < n; i++) {

            add = new ArrayList<>();
            remove = new ArrayList<>();

            System.out.printf("%d: ", i);
            result.sort(comp);
            toPrint(result);

            Item tmp = new Item(sc.nextInt(), sc.nextInt());

            for (Item item : result) {

                Item sum = item.sum(tmp);

                bool = true;
                for (Item meti : result) {
                    if (meti.invalid(sum, limit)) {
                        bool = false;
                    }
                    // enaka prostornina, manjša cena
                    if ((meti.size == sum.size && meti.val < sum.val) ||
                            (meti.size > sum.size && meti.val <= sum.val)) {
                        remove.add(meti);
                    }

                }
                if (bool) {
                    add.add(sum);
                }
            }
            result.addAll(add);
            result.removeAll(remove);
        }

        System.out.printf("%d: ", i);
        result.sort(comp);
        toPrint(result);




    }

}
