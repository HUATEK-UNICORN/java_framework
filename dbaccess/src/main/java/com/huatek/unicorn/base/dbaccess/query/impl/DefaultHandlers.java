package com.huatek.unicorn.base.dbaccess.query.impl;

import org.apache.commons.dbutils.RowProcessor;
import org.apache.commons.dbutils.handlers.ArrayHandler;
import org.apache.commons.dbutils.handlers.ArrayListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.huatek.unicorn.base.dbaccess.dbutils.FastRowProcessor;

public final class DefaultHandlers {
	
	static final RowProcessor DEFAULT_ROW_PROCESSOR = new FastRowProcessor();

	static final MapListHandler DEFAULT_MAP_LIST_HANDLER = new MapListHandler(
			DEFAULT_ROW_PROCESSOR);

	static final MapHandler DEFAULT_MAP_HANDLER = new MapHandler(
			DEFAULT_ROW_PROCESSOR);

	static final ScalarHandler<Number> DEFAULT_NUM_HANDLER = new ScalarHandler<Number>();
	
	static final ArrayHandler DEFAULT_ARR_HANDLER = new ArrayHandler();
	
	static final ArrayListHandler DEFAULT_ARR_LIST_HANDLER = new ArrayListHandler();
}
