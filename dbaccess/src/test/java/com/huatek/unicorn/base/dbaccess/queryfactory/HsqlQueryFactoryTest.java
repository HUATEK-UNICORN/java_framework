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

public class HsqlQueryFactoryTest extends BaseTestCase {

	private static DbaccessFactory<Map<String, Object>, Object[]> queryFactory;

	@BeforeClass
	public static void prepare() throws Exception {

		System.out.print("prepare...");

		DbAccessFactoryConfig conf = DbAccessFactoryConfig.configure(); 

		try {
			queryFactory = conf.build();
		} catch (Exception e) {
			e.printStackTrace();
		}

		Modification<Object[]> objectsModification = queryFactory
				.getModification("createTableXXX");
		objectsModification.modify();

		// prepare data
		Modification<Object[]> objectsModification2 = queryFactory
				.getModification("insertXxx");
		int batchUnit = 64;
		String[] strs = new String[30];
		Object[][] strsArr = new String[20][];
		Arrays.fill(strs, "test1test2test3test4");
		Arrays.fill(strsArr, strs);
		objectsModification2.batch(Arrays.asList(strsArr), batchUnit);

		System.out.println(" done.");
	}

	@AfterClass
	public static void destory() throws Exception {
		Modification<Object[]> objectsModification = queryFactory
				.getModification("dropTableXXX");
		objectsModification.modify();
	}

	@Test
	public void testSelectXxx() throws Exception {

		System.out.print("start testing...");

		Query<Map<String, Object>> query = queryFactory
				.getQuery("selectXxx");

		long start = System.currentTimeMillis();

		for (int i = 0; i < 100000; i++) {
			List<Map<String, Object>> m = query.all();
		}

		long finished = System.currentTimeMillis();
		System.out.println("selectXxx.all() --- Elapse: " + (finished - start)
				+ "(ms)");
		System.out.println(" done.");
	}

//	public static DataSource setupPoolDataSource(String connectURI,
//			String username, String password) {
//
//		ConnectionFactory connectionFactory = new DriverManagerConnectionFactory(
//				connectURI, username, password);
//
//		PoolableConnectionFactory poolableConnectionFactory = new PoolableConnectionFactory(
//				connectionFactory, null);
//
//		ObjectPool<PoolableConnection> connectionPool = new GenericObjectPool<PoolableConnection>(
//				poolableConnectionFactory);
//
//		poolableConnectionFactory.setPool(connectionPool);
//
//		PoolingDataSource<PoolableConnection> dataSource = new PoolingDataSource<>(
//				connectionPool);
//
//		return dataSource;
//	}
}
