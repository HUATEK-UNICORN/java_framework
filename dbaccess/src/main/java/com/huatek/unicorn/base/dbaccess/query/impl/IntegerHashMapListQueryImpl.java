package com.huatek.unicorn.base.dbaccess.query.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import com.huatek.unicorn.base.dbaccess.dialect.Dialect;
import com.huatek.unicorn.base.dbaccess.query.IntegerMapListQuery;

public class IntegerHashMapListQueryImpl implements IntegerMapListQuery {

	private QueryRunner queryRunner;

	private Dialect dialect;

	private String orignalStatement;

	private String countStatement;

	private String pageStatement;

	public IntegerHashMapListQueryImpl(QueryRunner queryRunner,
			Dialect dialect, String orignalStatement, String countStatement,
			String pageStatement) {
		this.queryRunner = queryRunner;
		this.dialect = dialect;
		this.orignalStatement = orignalStatement;
		this.countStatement = countStatement;
		this.pageStatement = pageStatement;
	}

	@Override
	public Integer count(Object... params) throws SQLException {
		return queryRunner.query(countStatement, DefaultHandlers.DEFAULT_NUM_HANDLER, params)
				.intValue();
	}

	@Override
	public List<Map<String, Object>> all(Object... params) throws SQLException {
		return queryRunner.query(orignalStatement, DefaultHandlers.DEFAULT_MAP_LIST_HANDLER,
				params);
	}

	@Override
	public List<Map<String, Object>> page(Integer first,
			Integer countOfPerPage, Object... params) throws SQLException {
		return queryRunner.query(pageStatement, DefaultHandlers.DEFAULT_MAP_LIST_HANDLER,
				this.dialect.transformPageParameters(first, countOfPerPage,
						params));
	}

	@Override
	public Map<String, Object> firstRow(Object... params) throws SQLException {
		return queryRunner.query(orignalStatement, DefaultHandlers.DEFAULT_MAP_HANDLER, params);
	}

}
