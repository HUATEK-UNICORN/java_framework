package com.huatek.unicorn.base.dbaccess.define;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbcp2.BasicDataSourceFactory;

import com.huatek.unicorn.base.dbaccess.dialect.Dialect;
import com.huatek.unicorn.base.dbaccess.exception.DbaccessException;
import com.huatek.unicorn.base.dbaccess.factory.DbaccessFactory;
import com.huatek.unicorn.base.dbaccess.factory.impl.DefaultDbaccessFactory;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("dbAccessFactory")
public class DbAccessFactoryConfig {

	private final static String CONF_ATTR_ALIAS_PATH = "path";

	private final static String DEFAULT_DAF_CONF_PATH = "/daf_config.xml";

	private String dialect;

	@XStreamAlias("statementConfigs")
	private List<String> statementConfigs;

	@XStreamAlias("dataSourceProps")
	private Properties dataSourceProps;

	private DbAccessFactoryConfig() {
		statementConfigs = new ArrayList<String>();
		dataSourceProps = new Properties();
	}

	public static DbAccessFactoryConfig configure() throws DbaccessException {
		return DbAccessFactoryConfig.configure(DEFAULT_DAF_CONF_PATH);
	}

	public static DbAccessFactoryConfig configure(String path)
			throws DbaccessException {

		if (null == path || Const.BLANK_STR.equals(path)) {
			throw new DbaccessException(
					"DB access factory configure file should not be null.");
		}

		XStream xstream = new XStream();
		xstream.alias(CONF_ATTR_ALIAS_PATH, String.class);
		xstream.processAnnotations(DbAccessFactoryConfig.class);

		DbAccessFactoryConfig dbAccessFactoryConfig = null;

		try {
			dbAccessFactoryConfig = (DbAccessFactoryConfig) xstream
					.fromXML(DefaultDbaccessFactory.class
							.getResourceAsStream(path));
		} catch (Exception ex) {
			throw new DbaccessException(
					"DB access factory load configure file faliure.", ex);
		}

		if (null == dbAccessFactoryConfig) {
			throw new DbaccessException(
					"create DB access factory instance faliure.");
		}

		return dbAccessFactoryConfig;
	}

	public DbaccessFactory<Map<String, Object>, Object[]> build()
			throws DbaccessException {

		DefaultDbaccessFactory defaultDbaccessFactory = new DefaultDbaccessFactory();

		if (null == this.getDialect()
				|| Const.BLANK_STR.equals(this.getDialect())) {
			throw new DbaccessException(
					"DB access factory dialect type should be setup.");
		}
		try {

			Class<?> dialectClass = Class.forName(this.getDialect());

			Object dialectObj = dialectClass.newInstance();

			if (Dialect.class.isInstance(dialectObj)) {

				defaultDbaccessFactory.setDialect((Dialect) dialectObj);

			}

		} catch (Exception ex) {
			throw new DbaccessException(
					"DB access factory initial dialect faliure.", ex);
		}

		// initial data source.
		BasicDataSource basicDataSource = null;
		try {
			basicDataSource = BasicDataSourceFactory.createDataSource(this
					.getDataSourceProps());
		} catch (Exception ex) {
			throw new DbaccessException(
					"DB access factory initial data source faliure.", ex);
		}

		defaultDbaccessFactory.setDataSource(basicDataSource);

		defaultDbaccessFactory.setConfigPaths(this.getStatementConfigs()
				.toArray(new String[this.getStatementConfigs().size()]));

		defaultDbaccessFactory.init();
		
		return defaultDbaccessFactory;
	}

	public String getDialect() {
		return null == dialect ? null : dialect.trim();
	}

	public void setDialect(String dialect) {
		this.dialect = dialect;
	}

	public List<String> getStatementConfigs() {
		return statementConfigs;
	}

	public void setStatementConfigs(List<String> statementConfigs) {
		this.statementConfigs = statementConfigs;
	}

	public Properties getDataSourceProps() {
		return dataSourceProps;
	}

	public void setDataSourceProps(Properties dataSourceProps) {
		this.dataSourceProps = dataSourceProps;
	}
}
