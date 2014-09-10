package com.huatek.unicorn.base.dbaccess.query.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.huatek.unicorn.base.dbaccess.dialect.Dialect;
import com.huatek.unicorn.base.dbaccess.query.LongMapListQuery;

public class LongHashMapListQueryImpl implements LongMapListQuery {

	private QueryRunner queryRunner;

	private Dialect dialect;

	private String orignalStatement;

	private String countStatement;

	private String pageStatement;

	public LongHashMapListQueryImpl(QueryRunner queryRunner, Dialect dialect,
			String orignalStatement, String countStatement, String pageStatement) {
		this.queryRunner = queryRunner;
		this.dialect = dialect;
		this.orignalStatement = orignalStatement;
		this.countStatement = countStatement;
		this.pageStatement = pageStatement;
	}

	@Override
	public Long count(Object... params) throws SQLException {
		return queryRunner.query(countStatement, new ScalarHandler<BigDecimal>(),
				params).longValue();
	}

	@Override
	public List<Map<String, Object>> all(Object... params) throws SQLException {
		return queryRunner
				.query(orignalStatement, new MapListHandler(), params);
	}

	@Override
	public List<Map<String, Object>> page(Long first, Long countOfPerPage,
			Object... params) throws SQLException {
		return queryRunner.query(pageStatement, new MapListHandler(),
				this.dialect.transformPageParameters(first, countOfPerPage,
						params));

	}

	@Override
	public Map<String, Object> firstRow(Object... params) throws SQLException {
		return queryRunner.query(orignalStatement, new MapHandler(), params);
	}

}
