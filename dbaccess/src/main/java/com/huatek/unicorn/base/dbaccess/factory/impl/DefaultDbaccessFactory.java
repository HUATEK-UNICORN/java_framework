package com.huatek.unicorn.base.dbaccess.factory.impl;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.dbutils.QueryRunner;

import static com.huatek.unicorn.base.dbaccess.define.Const.*;

import com.huatek.unicorn.base.dbaccess.define.DataAccessDefine;
import com.huatek.unicorn.base.dbaccess.define.StatementDefine;
import com.huatek.unicorn.base.dbaccess.dialect.Dialect;
import com.huatek.unicorn.base.dbaccess.exception.DbaccessException;
import com.huatek.unicorn.base.dbaccess.exception.DbaccessSyntaxException;
import com.huatek.unicorn.base.dbaccess.factory.DbaccessFactory;
import com.huatek.unicorn.base.dbaccess.modification.IntegerObjectsListModification;
import com.huatek.unicorn.base.dbaccess.modification.impl.IntegerObjectsListModificationImpl;
import com.huatek.unicorn.base.dbaccess.query.IntegerMapListQuery;
import com.huatek.unicorn.base.dbaccess.query.LongMapListQuery;
import com.huatek.unicorn.base.dbaccess.query.impl.IntegerHashMapListQueryImpl;
import com.huatek.unicorn.base.dbaccess.query.impl.LongHashMapListQueryImpl;
import com.thoughtworks.xstream.XStream;

public class DefaultDbaccessFactory implements DbaccessFactory {

	private QueryRunner queryRunner;

	private DataSource dataSource;

	private Dialect dialect;

	private Map<String, StatementDefine> statementDefines;

	private Map<String, IntegerMapListQuery> integerMapListQueryInstances;

	private Map<String, LongMapListQuery> longMapListQueryInstances;

	private Map<String, IntegerObjectsListModification> integerObjectsListModificationInstances;

	private String[] configPaths;

	public void init() throws Exception {

		if (null == configPaths || BLANK_ARRAY_LENGTH >= configPaths.length) {
			throw new DbaccessException(
					"Can not find configuration files use for initial.");
		}

		// check data source is available.
		if (null == dataSource) {
			throw new DbaccessException("The \"dataSource\" is unavailable.");
		}

		statementDefines = new HashMap<String, StatementDefine>();

		XStream xstream = new XStream();
		xstream.processAnnotations(DataAccessDefine.class);
		xstream.processAnnotations(StatementDefine.class);

		for (String configPath : configPaths) {
			if (null == configPath) {
				continue;
			}

			DataAccessDefine dataAccessDefine = null;
			try {
				dataAccessDefine = (DataAccessDefine) xstream
						.fromXML(DefaultDbaccessFactory.class
								.getResourceAsStream(configPath));
			} catch (Exception e) {
				// TODO Warn for parse configuration file error.
				// log out this configuration file parse error, level WARN.
				e.printStackTrace();
				continue;
			}

			if (null == dataAccessDefine
					|| null == dataAccessDefine.getStatements()) {
				// TODO Warn for the configuration file could not contain
				// statement defines.
				// log out this error, level WARN.
				continue;
			}

			for (StatementDefine statementDefine : dataAccessDefine
					.getStatements()) {
				statementDefines.put(statementDefine.getId(), statementDefine);
			}
		}

		integerMapListQueryInstances = new HashMap<String, IntegerMapListQuery>();
		longMapListQueryInstances = new HashMap<String, LongMapListQuery>();
		integerObjectsListModificationInstances = new HashMap<String, IntegerObjectsListModification>();

		// initial query runner.
		queryRunner = new QueryRunner(dataSource);
	}

	@Override
	public IntegerMapListQuery getIntegerMapListQuery(String identity) {

		if (null == identity || identity.equals(BLANK_STR)) {
			return null;
		}
		IntegerMapListQuery integerMapListQuery = this.integerMapListQueryInstances
				.get(identity);
		if (null == integerMapListQuery) {
			return initIntegerMapListQuery(identity);
		}
		return integerMapListQuery;
	}

	@Override
	public LongMapListQuery getLongMapListQuery(String identity) {

		if (null == identity || identity.equals(BLANK_STR)) {
			return null;
		}
		LongMapListQuery longMapListQuery = this.longMapListQueryInstances
				.get(identity);
		if (null == longMapListQuery) {
			return initLongMapListQuery(identity);
		}
		return longMapListQuery;
	}

	@Override
	public IntegerObjectsListModification getIntegerObjectsListModification(
			String identity) {
		if (null == identity || identity.equals(BLANK_STR)) {
			return null;
		}
		IntegerObjectsListModification integerObjectsListModification = this.integerObjectsListModificationInstances
				.get(identity);
		if (null == integerObjectsListModification) {
			return initIntegerObjectsListModification(identity);
		}
		return integerObjectsListModification;
	}

	private StatementDefine checkStatementDefine(String identity, String type) {

		StatementDefine statementDefine = statementDefines.get(identity);

		if (null == statementDefine) {
			throw new DbaccessSyntaxException(
					"Can not load a valid statement define object.");
		}

		if (!type.equals(statementDefine.getType())) {
			throw new DbaccessSyntaxException(
					"Not a expect statement define type, except: "
							+ StatementDefine.STMT_TYPE_QUERY + ", there is: "
							+ statementDefine.getType());
		}

		if (null == statementDefine.getStatement()
				|| BLANK_ARRAY_LENGTH >= statementDefine.getStatement()
						.length()) {
			// TODO handle statement error, need check detail syntax.
			throw new DbaccessSyntaxException(
					"The SQL statement is invalid. SQL statement: "
							+ statementDefine.getStatement());
		}

		return statementDefine;
	}

	private StatementDefine checkQueryStatementDefine(String identity) {
		return checkStatementDefine(identity, StatementDefine.STMT_TYPE_QUERY);
	}

	private StatementDefine checkModificationStatementDefine(String identity) {
		return checkStatementDefine(identity,
				StatementDefine.STMT_TYPE_MODIFICATION);
	}

	private IntegerMapListQuery initIntegerMapListQuery(String identity) {

		StatementDefine statementDefine = checkQueryStatementDefine(identity);

		IntegerHashMapListQueryImpl intHashMapListQueryImpl = new IntegerHashMapListQueryImpl(
				queryRunner, dialect, statementDefine.getStatement(),
				dialect.generateCountStatement(statementDefine.getStatement()),
				dialect.generatePageStatement(statementDefine.getStatement()));

		this.integerMapListQueryInstances
				.put(identity, intHashMapListQueryImpl);

		return intHashMapListQueryImpl;
	}

	private LongMapListQuery initLongMapListQuery(String identity) {

		StatementDefine statementDefine = checkQueryStatementDefine(identity);

		LongHashMapListQueryImpl longHashMapListQueryImpl = new LongHashMapListQueryImpl(
				queryRunner, dialect, statementDefine.getStatement(),
				dialect.generateCountStatement(statementDefine.getStatement()),
				dialect.generatePageStatement(statementDefine.getStatement()));

		this.longMapListQueryInstances.put(identity, longHashMapListQueryImpl);

		return longHashMapListQueryImpl;
	}

	private IntegerObjectsListModification initIntegerObjectsListModification(
			String identity) {
		StatementDefine statementDefine = checkModificationStatementDefine(identity);
		IntegerObjectsListModificationImpl integerObjectsListModificationImpl = new IntegerObjectsListModificationImpl(
				queryRunner, dialect, statementDefine.getStatement());
		this.integerObjectsListModificationInstances.put(identity,
				integerObjectsListModificationImpl);
		return integerObjectsListModificationImpl;
	}

	public Dialect getDialect() {
		return dialect;
	}

	public void setDialect(Dialect dialect) {
		this.dialect = dialect;
	}

	public String[] getConfigPaths() {
		return configPaths;
	}

	public void setConfigPaths(String[] configPaths) {
		this.configPaths = configPaths;
	}

	public QueryRunner getQueryRunner() {
		return queryRunner;
	}

	public void setQueryRunner(QueryRunner queryRunner) {
		this.queryRunner = queryRunner;
	}

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
}
