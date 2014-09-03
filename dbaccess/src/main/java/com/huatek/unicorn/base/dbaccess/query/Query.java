package com.huatek.unicorn.base.dbaccess.query;

import java.sql.SQLException;

/**
 * 
 * @author Axl Zhao
 *
 * @param <U> Unit for access.
 * @param <Q> Quantity of unit.
 * @param <C> Container for unit.
 */
public interface Query<U, Q extends Number, C> {

	/**
	 * Return the unit count for the specify business logic.
	 * @return
	 */
	Q count(Object... params) throws SQLException;

	/**
	 * Return all unit for the specify business logic.
	 * @return
	 */
	C all(Object... params) throws SQLException;

	/**
	 * Return page of unit for the specify business logic and given page define.
	 * @param first First record index for the result.
	 * @param countOfPerPage Count of per page.
	 * @return
	 */
	C page(Q first, Q countOfPerPage, Object... params) throws SQLException;

	/**
	 * Return first row of unit for the specify business logic.
	 * @return
	 */
	U firstRow(Object... params) throws SQLException;
}
