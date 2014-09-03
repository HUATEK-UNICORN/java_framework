package com.huatek.unicorn.base.dbaccess.dialect;

public interface Dialect {

	String generateCountStatement(String orignal);
	
	String generatePageStatement(String orignal);

	Object[] transformPageParameters(Object first, Object countOfPerPage, Object[] params);
	
	boolean supportsBatchUpdates();
}
