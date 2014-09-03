package com.huatek.unicorn.base.dbaccess.queryfactory;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import org.hsqldb.jdbc.JDBCDataSourceFactory;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.huatek.unicorn.base.dbaccess.dialect.HsqldbDialect;
import com.huatek.unicorn.base.dbaccess.factory.impl.DefaultDbaccessFactory;
import com.huatek.unicorn.base.dbaccess.modification.IntegerObjectsListModification;
import com.huatek.unicorn.base.dbaccess.query.IntegerMapListQuery;
import com.huatek.unicorn.base.dbaccess.query.LongMapListQuery;
import com.huatek.unicorn.base.dbaccess.test.BaseTestCase;
import com.huatek.unicorn.base.dbaccess.test.HsqldbSimpleDataSource;

public class DefaultQueryFactoryTest extends BaseTestCase {

	private static DefaultDbaccessFactory queryFactory;

	@BeforeClass
	public static void prepare() throws Exception {
		
//		Properties props = new Properties();
//		props.setProperty("url", "jdbc:hsqldb:mem:DefaultQueryFactoryTest");
//		props.setProperty("user", null);
//		props.setProperty("password", null);
//		DataSource dataSource = JDBCDataSourceFactory.createDataSource(props);
		
		queryFactory = new DefaultDbaccessFactory();
		queryFactory.setConfigPaths(new String[] { "/dao_config.xml" });
		// DriverManager.get
//		queryFactory.setDataSource(dataSource);
		queryFactory.setDataSource(new HsqldbSimpleDataSource(
				"jdbc:hsqldb:mem:DefaultQueryFactoryTest"));

		queryFactory.setDialect(new HsqldbDialect());

		try {
			queryFactory.init();
		} catch (Exception e) {
			e.printStackTrace();
		}

		IntegerObjectsListModification integerObjectsListModification = queryFactory
				.getIntegerObjectsListModification("createTableXXX");
		integerObjectsListModification.modify();
	}

	@AfterClass
	public static void destory() throws Exception {
		IntegerObjectsListModification integerObjectsListModification = queryFactory
				.getIntegerObjectsListModification("dropTableXXX");
		integerObjectsListModification.modify();
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
		IntegerObjectsListModification integerObjectsListModification = queryFactory
				.getIntegerObjectsListModification("insertXxx");
		String[] strs = new String[10];
		Arrays.fill(strs, "testes");
		integerObjectsListModification.modify(strs);
		
		IntegerMapListQuery integerMapListQuery = queryFactory
				.getIntegerMapListQuery("selectXxx");
		System.out.println(integerMapListQuery.count());
	}
	
	@Test
	public void testBatchInsert() throws Exception {
		IntegerObjectsListModification integerObjectsListModification = queryFactory
				.getIntegerObjectsListModification("insertXxx");
		String[] strs = new String[10];
		Object[][] strsArr = new String[100000][];
		Arrays.fill(strs, "testes");
		Arrays.fill(strsArr, strs);
		
		integerObjectsListModification.batch(Arrays.asList(strsArr));
		
		IntegerMapListQuery integerMapListQuery = queryFactory
				.getIntegerMapListQuery("selectXxx");
		System.out.println(integerMapListQuery.count());
	}

	@Test
	public void testGetIntegerPerformance() throws Exception {

		int loops = 1000 * 1;

		IntegerMapListQuery integerMapListQuery = queryFactory
				.getIntegerMapListQuery("selectSysTables");
		System.out.println(integerMapListQuery);

		long start = System.currentTimeMillis();
		for (int i = 0; i < loops; i++) {
			List<Map<String, Object>> result = integerMapListQuery.all();
		}
		long finished = System.currentTimeMillis();

		System.out.println("Elapse: " + (finished - start) + "(ms)");
		System.out
				.println("--------------------------------------------------------------");
	}
}
