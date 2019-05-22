import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedList;
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

        boolean equals(Item i) {
            return this.val == i.val && this.size == i.size;
        }

        Item sum(Item i) {
            return new Item(this.size + i.size, this.val + i.val);
        }

        @Override
        public String toString() {
            return String.format("%d %d", size, val);
        }
    }

    static int solve(Item[] items, int limit, int n) {
        if (n == 0 || limit == 0) {
            return 0;
        }

        if (items[n-1].size > limit) {
            return solve(items, limit, n-1);
        }

        else {
            return Math.max(items[n-1].val + solve(items,limit - items[n-1].size, n-1),
                    solve(items, limit, n-1)
            );
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int limit, n;

        limit = sc.nextInt();
        n = sc.nextInt();

        Item[] items = new Item[n];


        ArrayList<Item> result = new ArrayList<>();
        ArrayList<Item> add;
        ArrayList<Item> remove;
        result.add(new Item(0, 0));
        for (int i = 0; i < n; i++) {
            add = new ArrayList<>();
            remove = new ArrayList<>();
            System.out.println(result);
            Item tmp = new Item(sc.nextInt(), sc.nextInt());
            for (Item item : result) {

                System.out.printf("First: %s %s\n", item, tmp);
                if (item.size == tmp.size && item.val < tmp.val) {
                    System.out.println("true");
                    remove.add(item);
                }

                Item sum = item.sum(tmp);

                boolean bool = true;
                for (Item meti : result) {
                    if (meti.invalid(sum, limit)) {
                        bool = false;
                        System.out.println("Hello1");
                        break;
                    }
                }
                if (bool) {
                    System.out.println("Hello2");
                    System.out.printf("%s %s\n", item, sum);
                    if (item.size >= sum.size && item.val < sum.val) {
                    }
                    add.add(sum);
                }
            }
            result.addAll(add);
            result.removeAll(remove);
        }



    }

}
