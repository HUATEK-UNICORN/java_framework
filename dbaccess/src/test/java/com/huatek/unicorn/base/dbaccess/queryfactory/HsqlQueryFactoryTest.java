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
import com.huatek.unicorn.base.dbaccess.factory.impl.DefaultDbaccessFactory;
import com.huatek.unicorn.base.dbaccess.modification.IntegerObjectsListModification;
import com.huatek.unicorn.base.dbaccess.query.IntegerMapListQuery;
import com.huatek.unicorn.base.dbaccess.test.BaseTestCase;

public class HsqlQueryFactoryTest extends BaseTestCase {

	private static DefaultDbaccessFactory queryFactory;

	@BeforeClass
	public static void prepare() throws Exception {

		System.out.print("prepare...");

		queryFactory = new DefaultDbaccessFactory();
		queryFactory.setConfigPaths(new String[] { "/dao_config.xml" });
		// queryFactory
		// .setDataSource(setupDataSource("jdbc:oracle:thin:@192.168.128.163:1521:undb"));
		queryFactory.setDataSource(setupPoolDataSource(
				"jdbc:hsqldb:mem:DefaultQueryFactoryTest", "testins",
				"6yhn*IK<"));
		queryFactory.setDialect(new HsqldbDialect());

		try {
			queryFactory.init();
		} catch (Exception e) {
			e.printStackTrace();
		}

		IntegerObjectsListModification integerObjectsListModification = queryFactory
				.getIntegerObjectsListModification("createTableXXX");
		integerObjectsListModification.modify();

		// prepare data
		IntegerObjectsListModification insertXxx = queryFactory
				.getIntegerObjectsListModification("insertXxx");
		int batchUnit = 64;
		String[] strs = new String[30];
		Object[][] strsArr = new String[200][];
		Arrays.fill(strs, "test1test2test3test4");
		Arrays.fill(strsArr, strs);
		insertXxx.batch(Arrays.asList(strsArr), batchUnit);

		System.out.println(" done.");
	}

	@AfterClass
	public static void destory() throws Exception {
		IntegerObjectsListModification integerObjectsListModification = queryFactory
				.getIntegerObjectsListModification("dropTableXXX");
		integerObjectsListModification.modify();
	}

	@Test
	public void testSelectXxx() throws Exception {

		System.out.print("start testing...");

		IntegerMapListQuery selectXxx = queryFactory
				.getIntegerMapListQuery("selectXxx");

		long start = System.currentTimeMillis();

		for (int i = 0; i < 100000; i++) {
			List<Map<String, Object>> m = selectXxx.all();
		}

		long finished = System.currentTimeMillis();
		System.out.println("selectXxx.all() --- Elapse: " + (finished - start)
				+ "(ms)");
		System.out.println(" done.");
	}

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
