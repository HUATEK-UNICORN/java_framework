package com.huatek.unicorn.base.dbaccess.query.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;

import com.huatek.unicorn.base.dbaccess.dialect.Dialect;

public class ArrayQueryImpl extends AbstractQuery<Object[]> {

	public ArrayQueryImpl(QueryRunner queryRunner, Dialect dialect,
			String orignalStatement, String countStatement, String pageStatement) {
		super(queryRunner, dialect, orignalStatement, countStatement,
				pageStatement);
	}

	@Override
	public List<Object[]> all(Object... params) throws SQLException {
		return queryRunner.query(orignalStatement,
				DefaultHandlers.DEFAULT_ARR_LIST_HANDLER, params);
	}

	@Override
	public List<Object[]> page(Integer first, Integer countOfPerPage,
			Object... params) throws SQLException {
		return queryRunner
				.query(pageStatement, DefaultHandlers.DEFAULT_ARR_LIST_HANDLER,
						this.dialect.transformPageParameters(first,
								countOfPerPage, params));
	}

	@Override
	public Object[] firstRow(Object... params) throws SQLException {
		return queryRunner.query(orignalStatement,
				DefaultHandlers.DEFAULT_ARR_HANDLER, params);
	}

}
