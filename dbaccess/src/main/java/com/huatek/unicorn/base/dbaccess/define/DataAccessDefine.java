package com.huatek.unicorn.base.dbaccess.define;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("dataaccess")
public class DataAccessDefine {

	@XStreamAlias("statements")
	private List<StatementDefine> statements;

	public DataAccessDefine() {
		this.statements = new ArrayList<StatementDefine>();
	}
	
	public List<StatementDefine> getStatements() {
		return statements;
	}

	public void setStatements(List<StatementDefine> statements) {
		this.statements = statements;
	}
	
	public void addStatement(StatementDefine statementDefine) {
		this.statements.add(statementDefine);
	}
	
	public void addAllStatements(Collection<StatementDefine> statements) {
		this.statements.addAll(statements);
	}
}
