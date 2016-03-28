package com.huatek.unicorn.base.dbaccess.factory.impl;

import javax.sql.DataSource;

import com.huatek.unicorn.base.dbaccess.define.StatementDefine;
import com.huatek.unicorn.base.dbaccess.dialect.Dialect;
import com.huatek.unicorn.base.dbaccess.exception.DbaccessException;
import com.huatek.unicorn.base.dbaccess.modification.Modification;
import com.huatek.unicorn.base.dbaccess.modification.impl.ObjectsModification;
import com.huatek.unicorn.base.dbaccess.query.Query;
import com.huatek.unicorn.base.dbaccess.query.impl.ArrayQueryImpl;

public class SpringDbaccessFactory extends
		AbstractDbaccessFactory<Object[], Object[]> {

	@Override
	Query<Object[]> instanceQuery(StatementDefine statementDefine) {
		return new ArrayQueryImpl(queryRunner, dialect,
				statementDefine.getStatement(),
				dialect.generateCountStatement(statementDefine.getStatement()),
				dialect.generatePageStatement(statementDefine.getStatement()));
	}

	@Override
	Modification<Object[]> instanceModification(StatementDefine statementDefine) {
		return new ObjectsModification(queryRunner, dialect,
				statementDefine.getStatement());
	}
	
	public void init() throws DbaccessException {
		super.init();
		
	}

	public void setDialect(Dialect dialect) {
		this.dialect = dialect;
	}

	public void setConfigPaths(String[] configPaths) {
		this.configPaths = configPaths;
	}

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
}
