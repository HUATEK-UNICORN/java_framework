package com.huatek.unicorn.base.dbaccess.queryfactory;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.huatek.unicorn.base.dbaccess.define.DbAccessFactoryConfig;
import com.huatek.unicorn.base.dbaccess.factory.DbaccessFactory;
import com.huatek.unicorn.base.dbaccess.modification.Modification;
import com.huatek.unicorn.base.dbaccess.query.Query;
import com.huatek.unicorn.base.dbaccess.test.BaseTestCase;

public class DefaultQueryFactoryTest extends BaseTestCase {

	private static DbaccessFactory<Map<String, Object>, Object[]> queryFactory;

	@BeforeClass
	public static void prepare() throws Exception {

		// Properties props = new Properties();
		// props.setProperty("url", "jdbc:hsqldb:mem:DefaultQueryFactoryTest");
		// props.setProperty("user", null);
		// props.setProperty("password", null);
		// DataSource dataSource =
		// JDBCDataSourceFactory.createDataSource(props);

		DbAccessFactoryConfig conf = DbAccessFactoryConfig.configure(); 

		try {
			queryFactory = conf.build();
		} catch (Exception e) {
			e.printStackTrace();
		}

		Modification<Object[]> objectsModification = queryFactory
				.getModification("createTableXXX");
		objectsModification.merge();
	}

	@AfterClass
	public static void destory() throws Exception {
		Modification<Object[]> objectsModification = queryFactory
				.getModification("dropTableXXX");
		objectsModification.merge();
	}

	// @Test
	// public void testGetInteger() throws Exception {
	// IntegerMapListQuery integerMapListQuery =
	// queryFactory.getIntegerMapListQuery("selectSysTables");
	// System.out.println(integerMapListQuery);
	//
	// List<Map<String, Object>> result = integerMapListQuery.all();
	// System.out.println(result);
	// System.out.println(result.size());
	//
	// System.out.println("--------------------------------------------------------------");
	//
	// List<Map<String, Object>> pageResult = integerMapListQuery.page(60, 50);
	// System.out.println(pageResult);
	// System.out.println(pageResult.size());
	// }
	//
	// @Test
	// public void testGetInteger2() throws Exception {
	// IntegerMapListQuery integerMapListQuery =
	// queryFactory.getIntegerMapListQuery("selectSysTables");
	// System.out.println(integerMapListQuery);
	//
	// List<Map<String, Object>> result = integerMapListQuery.all();
	// System.out.println(result);
	// System.out.println(result.size());
	//
	// System.out.println("--------------------------------------------------------------");
	//
	// List<Map<String, Object>> pageResult = integerMapListQuery.page(60, 50);
	// System.out.println(pageResult);
	// System.out.println(pageResult.size());
	// }

	// @Test
	// public void testGetIntegerParam() throws Exception {
	// IntegerMapListQuery integerMapListQuery =
	// queryFactory.getIntegerMapListQuery("selectSysTablesByParam");
	// System.out.println(integerMapListQuery);
	//
	// List<Map<String, Object>> result =
	// integerMapListQuery.all("COLUMN_DOMAIN%");
	// System.out.println(result);
	// System.out.println(result.size());
	//
	// System.out.println("--------------------------------------------------------------");
	//
	// List<Map<String, Object>> pageResult = integerMapListQuery.page(0, 1,
	// "COLUMN_DOMAIN%");
	// System.out.println(pageResult);
	// System.out.println(pageResult.size());
	//
	// }
	//
	// @Test
	// public void testGetIntegerCount() throws Exception {
	// IntegerMapListQuery integerMapListQuery =
	// queryFactory.getIntegerMapListQuery("selectSysTablesByParam");
	// System.out.println(integerMapListQuery);
	//
	// int count = integerMapListQuery.count("%%");
	// System.out.println(count);
	//
	// }
	//
	// @Test
	// public void testGetLongCount() throws Exception {
	// LongMapListQuery longMapListQuery =
	// queryFactory.getLongMapListQuery("selectSysTablesByParam");
	// System.out.println(longMapListQuery);
	//
	// long count = longMapListQuery.count("%%");
	// System.out.println(count);
	//
	// }

	@Test
	public void testInsert() throws Exception {
		Modification<Object[]> objectsModification = queryFactory
				.getModification("insertXxx");
		String[] strs = new String[10];
		Arrays.fill(strs, "testes");
		objectsModification.merge(strs);

		Query<Map<String, Object>> query = queryFactory.getQuery("selectXxx");
		System.out.println(query.count());
	}

	@Test
	public void testBatchInsert() throws Exception {
		Modification<Object[]> objectsModification = queryFactory
				.getModification("insertXxx");
		String[] strs = new String[10];
		Object[][] strsArr = new String[100000][];
		Arrays.fill(strs, "testes");
		Arrays.fill(strsArr, strs);

		objectsModification.batch(Arrays.asList(strsArr));

		Query<Map<String, Object>> query = queryFactory.getQuery("selectXxx");
		System.out.println(query.count());
	}

	@Test
	public void testGetIntegerPerformance() throws Exception {

		int loops = 1000 * 1;

		Query<Map<String, Object>> query = queryFactory
				.getQuery("selectSysTables");
		System.out.println(query);

		long start = System.currentTimeMillis();
		for (int i = 0; i < loops; i++) {
			List<Map<String, Object>> result = query.all();
		}
		long finished = System.currentTimeMillis();

		System.out.println("Elapse: " + (finished - start) + "(ms)");
		System.out
				.println("--------------------------------------------------------------");
	}
}
