package com.huatek.unicorn.base.dbaccess.query.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;

import com.huatek.unicorn.base.dbaccess.dialect.Dialect;

public class MapQueryImpl extends AbstractQuery<Map<String, Object>> {

	public MapQueryImpl(QueryRunner queryRunner, Dialect dialect,
			String orignalStatement, String countStatement, String pageStatement) {
		super(queryRunner, dialect, orignalStatement, countStatement,
				pageStatement);
	}

	@Override
	public List<Map<String, Object>> all(Object... params) throws SQLException {
		return queryRunner.query(orignalStatement,
				DefaultHandlers.DEFAULT_MAP_LIST_HANDLER, params);
	}

	@Override
	public List<Map<String, Object>> page(Integer first,
			Integer countOfPerPage, Object... params) throws SQLException {
		return queryRunner
				.query(pageStatement, DefaultHandlers.DEFAULT_MAP_LIST_HANDLER,
						this.dialect.transformPageParameters(first,
								countOfPerPage, params));
	}

	@Override
	public Map<String, Object> firstRow(Object... params) throws SQLException {
		return queryRunner.query(orignalStatement,
				DefaultHandlers.DEFAULT_MAP_HANDLER, params);
	}

}
