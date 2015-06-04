package com.huatek.unicorn.base.dbaccess.modification.impl;

import org.apache.commons.dbutils.QueryRunner;

import com.huatek.unicorn.base.dbaccess.dialect.Dialect;

public class ObjectsModification extends AbstractModification<Object[]> {

	public ObjectsModification(QueryRunner queryRunner, Dialect dialect,
			String orignalStatement) {
		super(queryRunner, dialect, orignalStatement);
	}

	@Override
	protected Object[] paramsConvert(Object[] data) {
		return data;
	}

}
