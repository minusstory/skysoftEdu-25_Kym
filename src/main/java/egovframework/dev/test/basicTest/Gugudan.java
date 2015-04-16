package egovframework.dev.test.basicTest;

public class Gugudan {

	public static void main(String[] args) {
		for (int z = 1; z <= 9; z += 3) {
			for (int i = 1; i <= 9; i++) {
				for (int j = z; j <= z + 2; j++) {
					System.out.print(j + " * " + i + " = " + i * j + "\t");
				}
				System.out.println();
			}
			System.out.println();
		}
	}

}