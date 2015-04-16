package egovframework.dev.test.basicTest;

import java.io.*;

public class IO {

	public static void main(String[] args) {

		FileInputStream fis = null;
		BufferedInputStream bis = null;

		try {
			fis = new FileInputStream("c:\\sky.txt");
			bis = new BufferedInputStream(fis);

			int data = 0;
			while ((data = bis.read()) != -1) {
				System.out.print((char) data);
			}
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		} finally {
			if (fis != null){
				try {
					fis.close();
				} catch (IOException e) {
				}
			}
			if (bis != null){
				try {
					bis.close();
				} catch (IOException e) {
				}
			}
		}
	}
}
