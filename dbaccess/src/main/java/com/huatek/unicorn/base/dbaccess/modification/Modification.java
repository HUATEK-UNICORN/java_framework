package com.huatek.unicorn.base.dbaccess.modification;

import java.sql.SQLException;

public interface Modification<U, Q extends Number, C> {

	Q modify() throws SQLException;
	
	Q modify(U data) throws SQLException;
	
	Q batch(C dataArray) throws SQLException;
	
	Q batch(C dataArray, Q countOfPerCommit) throws SQLException;
	
}
