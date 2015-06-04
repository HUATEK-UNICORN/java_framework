package com.huatek.unicorn.base.dbaccess.query.impl;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;

import com.huatek.unicorn.base.dbaccess.dialect.Dialect;
import com.huatek.unicorn.base.dbaccess.query.Query;

public abstract class AbstractQuery<E> implements Query<E> {

	protected QueryRunner queryRunner;

	protected Dialect dialect;

	protected String orignalStatement;

	protected String countStatement;

	protected String pageStatement;

	protected AbstractQuery(QueryRunner queryRunner, Dialect dialect,
			String orignalStatement, String countStatement, String pageStatement) {
		this.queryRunner = queryRunner;
		this.dialect = dialect;
		this.orignalStatement = orignalStatement;
		this.countStatement = countStatement;
		this.pageStatement = pageStatement;
	}
	
	@Override
	public Integer count(Object... params) throws SQLException {
		return queryRunner.query(countStatement,
				DefaultHandlers.DEFAULT_NUM_HANDLER, params).intValue();
	}

}
