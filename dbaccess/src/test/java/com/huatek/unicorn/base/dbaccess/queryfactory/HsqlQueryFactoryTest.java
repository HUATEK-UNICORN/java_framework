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

import com.huatek.unicorn.base.dbaccess.dialect.HsqldbDialect;
import com.huatek.unicorn.base.dbaccess.dialect.OracleDialect;
import com.huatek.unicorn.base.dbaccess.factory.impl.DefaultDbaccessFactory;
import com.huatek.unicorn.base.dbaccess.modification.IntegerObjectsListModification;
import com.huatek.unicorn.base.dbaccess.query.IntegerMapListQuery;
import com.huatek.unicorn.base.dbaccess.test.BaseTestCase;

public class HsqlQueryFactoryTest extends BaseTestCase {

	private static DefaultDbaccessFactory queryFactory;

	@BeforeClass
	public static void prepare() throws Exception {

		queryFactory = new DefaultDbaccessFactory();
		queryFactory.setConfigPaths(new String[] { "/dao_config.xml" });
//		queryFactory
//				.setDataSource(setupDataSource("jdbc:oracle:thin:@192.168.128.163:1521:undb"));
		queryFactory
		.setDataSource(setupPoolDataSource("jdbc:hsqldb:mem:DefaultQueryFactoryTest", "testins", "6yhn*IK<"));
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

//	public static DataSource setupDataSource(String connectURI) {
//		BasicDataSource ds = new BasicDataSource();
//		ds.setDriverClassName("oracle.jdbc.driver.OracleDriver");
//		ds.setUsername("testins");
//		ds.setPassword("6yhn*IK<");
//		ds.setUrl(connectURI);
//		return ds;
//	}

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
	// int batchUnit = 1000;
	//
	// String[] strs = new String[10];
	// Object[][] strsArr = new String[batchUnit * 100][];
	// Arrays.fill(strs, "testes");
	// Arrays.fill(strsArr, strs);
	//
	// long start = System.currentTimeMillis();
	// integerObjectsListModification.batch(Arrays.asList(strsArr));
	// long finished = System.currentTimeMillis();
	// System.out.println("integerObjectsListModification.batch(Arrays.asList(strsArr)) --- Elapse: "
	// + (finished - start) + "(ms)");
	// System.out
	// .println("--------------------------------------------------------------");
	//
	// // -------------------------------------------
	//
	// start = System.currentTimeMillis();
	// integerObjectsListModification.batch(Arrays.asList(strsArr), batchUnit);
	// finished = System.currentTimeMillis();
	// System.out.println("integerObjectsListModification.batch(Arrays.asList(strsArr), batchUnit); --- Elapse: "
	// + (finished - start) + "(ms)");
	// System.out
	// .println("--------------------------------------------------------------");
	//
	// IntegerMapListQuery integerMapListQuery = queryFactory
	// .getIntegerMapListQuery("selectXxx");
	// System.out.println(integerMapListQuery.count());
	// }

	@Test
	public void testGetIntegerPerformance() throws Exception {

		int loops = 10000 * 1;

		IntegerMapListQuery integerMapListQuery = queryFactory
				.getIntegerMapListQuery("selectSysTables");
		System.out.println(integerMapListQuery);

		long start = System.currentTimeMillis();
		for (int i = 0; i < loops; i++) {
//			 List<Map<String, Object>> result = integerMapListQuery.all();
			int result = integerMapListQuery.count();
		}
		long finished = System.currentTimeMillis();

		System.out.println("Elapse: " + (finished - start) + "(ms)");
		System.out
				.println("--------------------------------------------------------------");
	}

	@Test
	public void testGetIntegerPerformance2() throws Exception {

		int loops = 10000 * 1;

		IntegerMapListQuery integerMapListQuery = queryFactory
				.getIntegerMapListQuery("selectSysTables2");
		System.out.println(integerMapListQuery);

		long start = System.currentTimeMillis();
		for (int i = 0; i < loops; i++) {
//			 List<Map<String, Object>> result = integerMapListQuery.all();
			int result = integerMapListQuery.count();
		}
		long finished = System.currentTimeMillis();

		System.out.println("Elapse: " + (finished - start) + "(ms)");
		System.out
				.println("--------------------------------------------------------------");
	}

	public static DataSource setupPoolDataSource(String connectURI, String username, String password) {
		//
		// First, we'll create a ConnectionFactory that the
		// pool will use to create Connections.
		// We'll use the DriverManagerConnectionFactory,
		// using the connect string passed in the command line
		// arguments.
		//
		ConnectionFactory connectionFactory = new DriverManagerConnectionFactory(
				connectURI, username, password);

		//
		// Next we'll create the PoolableConnectionFactory, which wraps
		// the "real" Connections created by the ConnectionFactory with
		// the classes that implement the pooling functionality.
		//
		PoolableConnectionFactory poolableConnectionFactory = new PoolableConnectionFactory(
				connectionFactory, null);

		//
		// Now we'll need a ObjectPool that serves as the
		// actual pool of connections.
		//
		// We'll use a GenericObjectPool instance, although
		// any ObjectPool implementation will suffice.
		//
		ObjectPool<PoolableConnection> connectionPool = new GenericObjectPool<PoolableConnection>(
				poolableConnectionFactory);

		// Set the factory's pool property to the owning pool
		poolableConnectionFactory.setPool(connectionPool);

		//
		// Finally, we create the PoolingDriver itself,
		// passing in the object pool we created.
		//
		PoolingDataSource<PoolableConnection> dataSource = new PoolingDataSource<>(
				connectionPool);

		return dataSource;
	}
}
