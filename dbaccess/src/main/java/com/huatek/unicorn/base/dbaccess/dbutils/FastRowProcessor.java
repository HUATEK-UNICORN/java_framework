package com.huatek.unicorn.base.dbaccess.dbutils;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;


public class FastRowProcessor extends AbstractRowProcessor {

    @Override
    public Map<String, Object> toMap(ResultSet rs) throws SQLException {
    	
    	ResultSetMetaData rsmd = rs.getMetaData();
		int cols = rsmd.getColumnCount();
		
		Map<String, Object> result = new HashMap<String, Object>(cols);

		for (int i = 1; i <= cols; i++) {
			result.put(rsmd.getColumnName(i), rs.getObject(i));
		}

		return result;
    }
}
