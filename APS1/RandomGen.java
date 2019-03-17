import java.util.*;

public class RandomGen {
	public static void main(String[] args) {
		Random rnd = new Random();
		for (int i = 0; i < 10; i++) {
			System.out.println((i+1)+") " + (rnd.nextInt(100)+1));
		}

		System.out.println("-----------------");
		
		for (int i = 0; i < 4; i++) {
			System.out.println(rnd.nextInt(10)+1);
		}
	}
}