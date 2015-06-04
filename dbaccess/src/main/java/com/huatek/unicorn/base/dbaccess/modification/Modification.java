package com.huatek.unicorn.base.dbaccess.modification;

import java.sql.SQLException;
import java.util.List;

public interface Modification<E> {

	Integer merge() throws SQLException;

	Integer merge(E data) throws SQLException;

	Integer batch(List<E> dataArray) throws SQLException;

	Integer batch(List<E> dataArray, Integer countOfPerCommit)
			throws SQLException;
}
