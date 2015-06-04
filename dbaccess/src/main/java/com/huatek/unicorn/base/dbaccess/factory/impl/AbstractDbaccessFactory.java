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
import com.huatek.unicorn.base.dbaccess.modification.Modification;
import com.huatek.unicorn.base.dbaccess.query.Query;
import com.thoughtworks.xstream.XStream;

public abstract class AbstractDbaccessFactory<E, P> implements
		DbaccessFactory<E, P> {

	protected QueryRunner queryRunner;

	protected DataSource dataSource;

	protected Dialect dialect;

	protected Map<String, StatementDefine> statementDefines;

	protected Map<String, Query<E>> queryInstances;

	protected Map<String, Modification<P>> modificationInstances;

	protected String[] configPaths;

	public void init() throws Exception {

		if (null == configPaths || EMPTY_ARRAY_LENGTH >= configPaths.length) {
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
						.fromXML(AbstractDbaccessFactory.class
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

		queryInstances = new HashMap<>();
		modificationInstances = new HashMap<>();

		// initial query runner.
		queryRunner = new QueryRunner(dataSource);
	}

	@Override
	public Query<E> getQuery(String identity) {

		if (null == identity || identity.equals(BLANK_STR)) {
			return null;
		}
		Query<E> query = this.queryInstances.get(identity);
		if (null == query) {
			return initQuery(identity);
		}
		return query;
	}

	@Override
	public Modification<P> getModification(String identity) {
		if (null == identity || identity.equals(BLANK_STR)) {
			return null;
		}
		Modification<P> modification = this.modificationInstances.get(identity);
		if (null == modification) {
			return initModification(identity);
		}
		return modification;
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
				|| EMPTY_ARRAY_LENGTH >= statementDefine.getStatement()
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

	private Query<E> initQuery(String identity) {

		StatementDefine statementDefine = checkQueryStatementDefine(identity);

		Query<E> query = instanceQuery(statementDefine);

		this.queryInstances.put(identity, query);

		return query;
	}

	abstract Query<E> instanceQuery(StatementDefine statementDefine);

	private Modification<P> initModification(String identity) {
		StatementDefine statementDefine = checkModificationStatementDefine(identity);
		Modification<P> modification = instanceModification(statementDefine);
		this.modificationInstances.put(identity, modification);
		return modification;
	}

	abstract Modification<P> instanceModification(
			StatementDefine statementDefine);

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
