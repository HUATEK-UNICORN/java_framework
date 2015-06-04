package com.huatek.unicorn.base.dbaccess.query;

import java.sql.SQLException;
import java.util.List;

/**
 * 
 * @author Axl Zhao
 * 
 * @param <EH>
 *            Element handler
 * @param <CH>
 *            Container handler of element.
 */
public interface Query<E> {

	/**
	 * Return the unit count for the specify business logic.
	 * 
	 * @return
	 */
	Integer count(Object... params) throws SQLException;

	/**
	 * Return all unit for the specify business logic.
	 * 
	 * @return
	 */
	List<E> all(Object... params) throws SQLException;

	/**
	 * Return page of unit for the specify business logic and given page define.
	 * 
	 * @param first
	 *            First record index for the result.
	 * @param countOfPerPage
	 *            Count of per page.
	 * @return
	 */
	List<E> page(Integer first, Integer countOfPerPage, Object... params)
			throws SQLException;

	/**
	 * Return first row of unit for the specify business logic.
	 * 
	 * @return
	 */
	E firstRow(Object... params) throws SQLException;
}
