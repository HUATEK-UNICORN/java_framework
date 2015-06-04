package com.huatek.unicorn.base.dbaccess.dialect;

import static com.huatek.unicorn.base.dbaccess.define.Const.*;

public class OracleDialect implements Dialect {

	private static final String PAGE_STMT_PREFIX = " SELECT * FROM ( SELECT UNICORN_INNER_TAB.*, rownum AS UNICORN_INNER_ROWNUM FROM ( ";

	private static final String PAGE_STMT_SUFFIX = " ) UNICORN_INNER_TAB ) UNICORN_EXTERNAL_TAB WHERE UNICORN_EXTERNAL_TAB.UNICORN_INNER_ROWNUM >= ? AND UNICORN_EXTERNAL_TAB.UNICORN_INNER_ROWNUM < ? ";

	private static final String COUNT_STMT_PREFIX = " SELECT SUM(1) AS COUNTER FROM ( ";

	private static final String COUNT_STMT_SUFFIX = " ) UNICORN_COUNT_TAB ";
	
	private static final int PAGE_PARAMS_LEN = 2;

	@Override
	public String generateCountStatement(String orignal) {
		return COUNT_STMT_PREFIX + orignal + COUNT_STMT_SUFFIX;
	}

	@Override
	public String generatePageStatement(String orignal) {
		return PAGE_STMT_PREFIX + orignal + PAGE_STMT_SUFFIX;
	}

	@Override
	public Object[] transformPageParameters(Object first,
			Object countOfPerPage, Object[] params) {
		if (null == params || EMPTY_ARRAY_LENGTH >= params.length) {
			return new Object[] { first, countOfPerPage };
		}

		Object[] newParams = new Object[params.length + PAGE_PARAMS_LEN];
		System.arraycopy(params, 0, newParams, 0, params.length);
		newParams[params.length] = first;
		newParams[params.length + 1] = countOfPerPage;
		return newParams;
	}

	@Override
	public boolean supportsBatchUpdates() {
		return true;
	}
}
