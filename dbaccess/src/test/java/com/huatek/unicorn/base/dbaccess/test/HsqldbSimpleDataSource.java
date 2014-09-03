package com.huatek.unicorn.base.dbaccess.test;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Logger;

import javax.sql.DataSource;

public class HsqldbSimpleDataSource implements DataSource {
	
	private String url;

	/**
	 * Returns 0, indicating the default system timeout is to be used.
	 */
	public int getLoginTimeout() throws SQLException {
		return 0;
	}

	/**
	 * Setting a login timeout is not supported.
	 */
	public void setLoginTimeout(int timeout) throws SQLException {
		throw new UnsupportedOperationException("setLoginTimeout");
	}

	/**
	 * LogWriter methods are not supported.
	 */
	public PrintWriter getLogWriter() {
		throw new UnsupportedOperationException("getLogWriter");
	}

	/**
	 * LogWriter methods are not supported.
	 */
	public void setLogWriter(PrintWriter pw) throws SQLException {
		throw new UnsupportedOperationException("setLogWriter");
	}


	//---------------------------------------------------------------------
	// Implementation of JDBC 4.0's Wrapper interface
	//---------------------------------------------------------------------
	@SuppressWarnings("unchecked")
	public <T> T unwrap(Class<T> iface) throws SQLException {
		if (iface.isInstance(this)) {
			return (T) this;
		}
		throw new SQLException("DataSource of type [" + getClass().getName() +
				"] cannot be unwrapped as [" + iface.getName() + "]");
	}

	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		return iface.isInstance(this);
	}


	//---------------------------------------------------------------------
	// Implementation of JDBC 4.1's getParentLogger method
	//---------------------------------------------------------------------
	public Logger getParentLogger() {
		return Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	}

	/**
	 * This implementation delegates to {@code getConnectionFromDriver},
	 * using the default username and password of this DataSource.
	 * @see #getConnectionFromDriver(String, String)
	 * @see #setUsername
	 * @see #setPassword
	 */
	public Connection getConnection() throws SQLException {
		return DriverManager.getConnection(url);
	}

	/**
	 * This implementation delegates to {@code getConnectionFromDriver},
	 * using the given username and password.
	 * @see #getConnectionFromDriver(String, String)
	 */
	public Connection getConnection(String username, String password) throws SQLException {
		Properties connectionProperties = new Properties();
		if (username != null) {
			connectionProperties.setProperty("user", username);
		}
		if (password != null) {
			connectionProperties.setProperty("password", password);
		}
		return DriverManager.getConnection(url, connectionProperties);
	}

	/**
	 * Create a new DriverManagerDataSource with the given standard Driver parameters.
	 * @param driver the JDBC Driver object
	 * @param url the JDBC URL to use for accessing the DriverManager
	 * @see java.sql.Driver#connect(String, java.util.Properties)
	 */
	public HsqldbSimpleDataSource(String url) {
		this.url = url.trim();
	}

}