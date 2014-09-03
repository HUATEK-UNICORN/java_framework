package com.huatek.unicorn.base.dbaccess.define;

public class CopyOfParserTest {

	public static void main(String[] args) {
		
		System.out.println(29 / 10);

//		System.out.println(13213 % 8);
//		
//		System.out.println(13213 - ((13213 >> 3) * 8));
		
		int count = 10000 * 100000;
		int result = 0;

		long start = System.currentTimeMillis();

		for (int i = 0; i < count; i++) {
			result = i % 8;
		}

		long finished = System.currentTimeMillis();

		System.out.println("Elapse: " + (finished - start) + "(ms)");

		//-------------------------------------------------------------
		result = 0;
		start = System.currentTimeMillis();

		for (int i = 0; i < count; i++) {
			result = i - ((i >> 3) * 8);
		}
		finished = System.currentTimeMillis();
		
		System.out.println("Elapse: " + (finished - start) + "(ms)");
	}

}
