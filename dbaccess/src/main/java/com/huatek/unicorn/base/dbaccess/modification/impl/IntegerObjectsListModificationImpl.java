package com.huatek.unicorn.base.dbaccess.modification.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.sql.Statement;
import java.util.List;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;

import static com.huatek.unicorn.base.dbaccess.define.Const.*;

import com.huatek.unicorn.base.dbaccess.dialect.Dialect;
import com.huatek.unicorn.base.dbaccess.modification.IntegerObjectsListModification;

public class IntegerObjectsListModificationImpl implements
		IntegerObjectsListModification {

	private QueryRunner queryRunner;

	private Dialect dialect;
	
	private String orignalStatement;

	public IntegerObjectsListModificationImpl(QueryRunner queryRunner,
			Dialect dialect, 
			String orignalStatement) {
		this.queryRunner = queryRunner;
		this.dialect = dialect;
		this.orignalStatement = orignalStatement;
	}

	@Override
	public Integer modify() throws SQLException {
		return queryRunner.update(orignalStatement);
	}

	@Override
	public Integer modify(Object[] data) throws SQLException {
		return queryRunner.update(orignalStatement, data);
	}

	@Override
	public Integer batch(List<Object[]> dataArray) throws SQLException {
		return batch(dataArray, dataArray.size());
	}

	@Override
	public Integer batch(List<Object[]> dataArray, Integer countOfPerCommit)
			throws SQLException {

		return doBatch(dataArray, countOfPerCommit, false);
	}

	private Integer doBatch(List<Object[]> dataArray, int countOfPerCommit,
			boolean rollbackWhenError) throws SQLException {

		if (null == dataArray || BLANK_ARRAY_LENGTH >= dataArray.size()) {
			return 0;
		}

		if (countOfPerCommit < 1) {
			countOfPerCommit = dataArray.size();
		}

		Connection conn = null;
		PreparedStatement pstmt = null;

		try {

			conn = queryRunner.getDataSource().getConnection();

			if (null == conn) {
				throw new SQLException("Could not create connection.");
			}

			// record auto commit label.
			boolean autoCommit = conn.getAutoCommit();
			// set do not auto commit.
			conn.setAutoCommit(false);
			conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);

			pstmt = conn.prepareStatement(this.orignalStatement);
			
			int affectsRowCount = 0;
			if (this.dialect.supportsBatchUpdates()) {
				affectsRowCount = executeBatch(conn, pstmt, dataArray,
						countOfPerCommit, rollbackWhenError);
			} else {
				affectsRowCount = emulateBatch(conn, pstmt, dataArray,
						countOfPerCommit, rollbackWhenError);
			}

			// revert auto commit label.
			conn.setAutoCommit(autoCommit);

			return affectsRowCount;
		} catch (SQLException sqle) {
			throw sqle;
		} finally {
			try {
				DbUtils.close(pstmt);
			} catch (SQLException sqle) {
				pstmt = null;
				throw sqle;
			}

			try {
				DbUtils.close(conn);
			} catch (SQLException sqle) {
				conn = null;
				throw sqle;
			}
		}
	}

	/**
	 * just use for inner.
	 * 
	 * @return
	 */
	private Integer executeBatch(Connection conn, PreparedStatement pstmt,
			List<Object[]> dataArray, Integer countOfPerCommit,
			boolean rollbackWhenError) throws SQLException {

		
		Savepoint txBegin = conn.setSavepoint();

		int commitCount = (dataArray.size() / countOfPerCommit) + 1;
		int affectsRowCount = 0;

		for (int i = 0; i < commitCount; i++) {
			int loopLimit = (i + 1) * countOfPerCommit;
			for (int j = i * countOfPerCommit; j < dataArray.size()
					&& j < loopLimit; j++) {
				queryRunner.fillStatement(pstmt, dataArray.get(j));
				pstmt.addBatch();
			}
			int[] batchResult = pstmt.executeBatch();

			for (int resultCode : batchResult) {
				if (Statement.EXECUTE_FAILED == resultCode) {
					if (rollbackWhenError) {
						pstmt.clearBatch();
						conn.rollback(txBegin);
						return 0;
					}
					continue;
				}

				affectsRowCount += resultCode;
			}
		}

		conn.commit();

		return affectsRowCount;
	}

	/**
	 * just use for inner.
	 * 
	 * @return
	 */
	private Integer emulateBatch(Connection conn, PreparedStatement pstmt,
			List<Object[]> dataArray, Integer countOfPerCommit,
			boolean errorBreak) throws SQLException {

		Savepoint txBegin = conn.setSavepoint();

		int commitCount = (dataArray.size() / countOfPerCommit) + 1;
		int affectsRowCount = 0;

		for (int i = 0; i < commitCount; i++) {
			int loopLimit = (i + 1) * countOfPerCommit;
			for (int j = i * countOfPerCommit; j < dataArray.size()
					&& j < loopLimit; j++) {
				int resultCode = queryRunner.update(conn, orignalStatement,
						dataArray.get(j));
				if (Statement.EXECUTE_FAILED == resultCode) {
					if (errorBreak) {
						conn.rollback(txBegin);
						return 0;
					}
					continue;
				}
				affectsRowCount += resultCode;
			}
		}
		conn.commit();
		return affectsRowCount;
	}
}
