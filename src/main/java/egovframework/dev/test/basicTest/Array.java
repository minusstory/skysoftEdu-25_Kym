package egovframework.dev.test.basicTest;

public class Array {

	private static String[][] addArr = { { "기술팀", "차장", "정범훈" }, { "기술팀", "과장", "조홍식" }, { "기술팀", "사원", "김영민" } };

	public static void main(String[] args) {

		for (int i = 0; i < addArr.length; i++) {
			for (int j = 0; j < addArr[i].length; j++) {
				System.out.print(addArr[i][j] + "  ");
				// System.out.print("\t");
			}
			System.out.println();
		}

	}
}
