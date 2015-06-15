package com.huatek.unicorn.base.dbaccess.queryfactory;

import java.util.Arrays;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.huatek.unicorn.base.dbaccess.define.DbAccessFactoryConfig;
import com.huatek.unicorn.base.dbaccess.factory.DbaccessFactory;
import com.huatek.unicorn.base.dbaccess.modification.Modification;
import com.huatek.unicorn.base.dbaccess.query.Query;
import com.huatek.unicorn.base.dbaccess.test.BaseTestCase;

public class OracleQueryFactoryTest extends BaseTestCase {

	private static DbaccessFactory<Object[], Object[]> queryFactory;

	@BeforeClass
	public static void prepare() throws Exception {

		System.out.print("prepare...");

		DbAccessFactoryConfig conf = DbAccessFactoryConfig.configure("/ora_daf_config.xml"); 

		try {
			queryFactory = conf.build();
		} catch (Exception e) {
			e.printStackTrace();
		}

		Modification<Object[]> createTableXXX = queryFactory
				.getModification("createTableXXX");
		createTableXXX.modify();

		// prepare data
		Modification<Object[]> insertXxx = queryFactory
				.getModification("insertXxx");
		int batchUnit = 64;
		String[] strs = new String[10];
		Object[][] strsArr = new String[10000][];
		Arrays.fill(strs, "test1test2test3test4");
		Arrays.fill(strsArr, strs);
		insertXxx.batch(Arrays.asList(strsArr), batchUnit);

		System.out.println(" done.");
	}

	@AfterClass
	public static void destory() throws Exception {
		Modification<Object[]> dropTableXXX = queryFactory
				.getModification("dropTableXXX");
		dropTableXXX.modify();
	}

	@Test
	public void testSelectXxx() throws Exception {

		System.out.print("start testing...");

		Query<Object[]> query = queryFactory
				.getQuery("selectXxx");

		long start = System.currentTimeMillis();

		for (int i = 0; i < 100; i++) {
			List<Object[]> m = query.all();
		}

		long finished = System.currentTimeMillis();
		System.out.println("selectXxx.all() --- Elapse: " + (finished - start)
				+ "(ms)");
		System.out.println(" done.");
	}

	// @Test
	// public void testInsert() throws Exception {
	// IntegerObjectsListModification integerObjectsListModification =
	// queryFactory
	// .getIntegerObjectsListModification("insertXxx");
	// String[] strs = new String[10];
	// Arrays.fill(strs, "testes");
	// integerObjectsListModification.modify(strs);
	//
	// IntegerMapListQuery integerMapListQuery = queryFactory
	// .getIntegerMapListQuery("selectXxx");
	// System.out.println(integerMapListQuery.count());
	// }
	//

	// @Test
	// public void testBatchInsert() throws Exception {
	// IntegerObjectsListModification integerObjectsListModification =
	// queryFactory
	// .getIntegerObjectsListModification("insertXxx");
	//
	// int batchUnit = 64;
	//
	// String[] strs = new String[10];
	// Object[][] strsArr = new String[100000][];
	// Arrays.fill(strs, "test1test2test3test4");
	// Arrays.fill(strsArr, strs);
	//
	// // long start = System.currentTimeMillis();
	// // integerObjectsListModification.batch(Arrays.asList(strsArr));
	// // long finished = System.currentTimeMillis();
	// //
	// System.out.println("integerObjectsListModification.batch(Arrays.asList(strsArr)) --- Elapse: "
	// // + (finished - start) + "(ms)");
	// // System.out
	// //
	// .println("--------------------------------------------------------------");
	//
	// // -------------------------------------------
	//
	// long startx = System.currentTimeMillis();
	// integerObjectsListModification.batch(Arrays.asList(strsArr), batchUnit);
	// long finishedx = System.currentTimeMillis();
	// System.out
	// .println("integerObjectsListModification.batch(Arrays.asList(strsArr), batchUnit); --- Elapse: "
	// + (finishedx - startx) + "(ms)");
	// System.out
	// .println("--------------------------------------------------------------");
	//
	// IntegerMapListQuery integerMapListQuery = queryFactory
	// .getIntegerMapListQuery("selectXxx");
	// System.out.println(integerMapListQuery.count());
	// }

	// @Test
	// public void testGetIntegerPerformance() throws Exception {
	//
	// int loops = 1000 * 1;
	//
	// IntegerMapListQuery integerMapListQuery = queryFactory
	// .getIntegerMapListQuery("selectSysTables");
	// System.out.println(integerMapListQuery);
	//
	// long start = System.currentTimeMillis();
	// for (int i = 0; i < loops; i++) {
	// List<Map<String, Object>> result = integerMapListQuery.all();
	// // int result = integerMapListQuery.count();
	// }
	// long finished = System.currentTimeMillis();
	//
	// System.out.println("Elapse: " + (finished - start) + "(ms)");
	// System.out
	// .println("--------------------------------------------------------------");
	// }
	//
	// @Test
	// public void testGetIntegerPerformance2() throws Exception {
	//
	// int loops = 1000 * 1;
	//
	// IntegerMapListQuery integerMapListQuery = queryFactory
	// .getIntegerMapListQuery("selectSysTables2");
	// System.out.println(integerMapListQuery);
	//
	// long start = System.currentTimeMillis();
	// for (int i = 0; i < loops; i++) {
	// List<Map<String, Object>> result = integerMapListQuery.all();
	// // int result = integerMapListQuery.count();
	// }
	// long finished = System.currentTimeMillis();
	//
	// System.out.println("Elapse: " + (finished - start) + "(ms)");
	// System.out
	// .println("--------------------------------------------------------------");
	// }
}
