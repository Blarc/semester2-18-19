import java.util.Arrays;
import java.util.Scanner;

public class DivideAndConquer {

    pu

    public void divide() {

    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String line = sc.nextLine();
        String[] split = line.split(" ");

        int[] arr = new int[split.length];

        for (int i = 0; i < split.length; i++) {
            arr[i] = Integer.parseInt(split[i]);
        }

    }
}
