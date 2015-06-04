package com.huatek.unicorn.base.dbaccess.factory.impl;

import java.util.Map;

import com.huatek.unicorn.base.dbaccess.define.StatementDefine;

import com.huatek.unicorn.base.dbaccess.modification.Modification;
import com.huatek.unicorn.base.dbaccess.modification.impl.ObjectsModification;
import com.huatek.unicorn.base.dbaccess.query.Query;
import com.huatek.unicorn.base.dbaccess.query.impl.MapQueryImpl;

public class DefaultDbaccessFactory extends
		AbstractDbaccessFactory<Map<String, Object>, Object[]> {

	@Override
	Query<Map<String, Object>> instanceQuery(StatementDefine statementDefine) {
		return new MapQueryImpl(queryRunner, dialect,
				statementDefine.getStatement(),
				dialect.generateCountStatement(statementDefine.getStatement()),
				dialect.generatePageStatement(statementDefine.getStatement()));
	}

	@Override
	Modification<Object[]> instanceModification(StatementDefine statementDefine) {
		return new ObjectsModification(queryRunner, dialect,
				statementDefine.getStatement());
	}

}
