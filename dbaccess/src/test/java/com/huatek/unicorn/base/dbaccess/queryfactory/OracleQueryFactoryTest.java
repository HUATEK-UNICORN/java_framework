package com.huatek.unicorn.base.dbaccess.queryfactory;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbcp2.ConnectionFactory;
import org.apache.commons.dbcp2.DriverManagerConnectionFactory;
import org.apache.commons.dbcp2.PoolableConnection;
import org.apache.commons.dbcp2.PoolableConnectionFactory;
import org.apache.commons.dbcp2.PoolingDataSource;
import org.apache.commons.pool2.ObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.huatek.unicorn.base.dbaccess.dialect.OracleDialect;
import com.huatek.unicorn.base.dbaccess.factory.impl.DefaultDbaccessFactory;
import com.huatek.unicorn.base.dbaccess.modification.Modification;
import com.huatek.unicorn.base.dbaccess.query.Query;
import com.huatek.unicorn.base.dbaccess.test.BaseTestCase;

public class OracleQueryFactoryTest extends BaseTestCase {

	private static DefaultDbaccessFactory queryFactory;

	@BeforeClass
	public static void prepare() throws Exception {

		System.out.print("prepare...");

		queryFactory = new DefaultDbaccessFactory();
		queryFactory.setConfigPaths(new String[] { "/oracle_config.xml" });
		// queryFactory
		// .setDataSource(setupDataSource("jdbc:oracle:thin:@192.168.128.163:1521:undb"));
		queryFactory.setDataSource(setupPoolDataSource(
				"jdbc:oracle:thin:@192.168.128.163:1521:undb", "testins",
				"6yhn*IK<"));
		queryFactory.setDialect(new OracleDialect());

		try {
			queryFactory.init();
		} catch (Exception e) {
			e.printStackTrace();
		}

		Modification<Object[]> createTableXXX = queryFactory
				.getModification("createTableXXX");
		createTableXXX.merge();

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
		dropTableXXX.merge();
	}

	public static DataSource setupDataSource(String connectURI) {
		BasicDataSource ds = new BasicDataSource();
		ds.setDriverClassName("oracle.jdbc.driver.OracleDriver");
		ds.setUsername("testins");
		ds.setPassword("6yhn*IK<");
		ds.setUrl(connectURI);
		return ds;
	}

	@Test
	public void testSelectXxx() throws Exception {

		System.out.print("start testing...");

		Query<Map<String, Object>> query = queryFactory
				.getQuery("selectXxx");

		long start = System.currentTimeMillis();

		for (int i = 0; i < 100; i++) {
			List<Map<String, Object>> m = query.all();
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

	public static DataSource setupPoolDataSource(String connectURI,
			String username, String password) {

		ConnectionFactory connectionFactory = new DriverManagerConnectionFactory(
				connectURI, username, password);

		PoolableConnectionFactory poolableConnectionFactory = new PoolableConnectionFactory(
				connectionFactory, null);

		ObjectPool<PoolableConnection> connectionPool = new GenericObjectPool<PoolableConnection>(
				poolableConnectionFactory);

		poolableConnectionFactory.setPool(connectionPool);

		PoolingDataSource<PoolableConnection> dataSource = new PoolingDataSource<>(
				connectionPool);

		return dataSource;
	}
}
