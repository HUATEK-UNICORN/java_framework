package com.huatek.unicorn.base.dbaccess.define;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import com.thoughtworks.xstream.converters.extended.ToAttributedValueConverter;

@XStreamAlias("statement")
@XStreamConverter(value=ToAttributedValueConverter.class, strings={"statement"})
public class StatementDefine {

	public static final String STMT_TYPE_QUERY = "query";
	
	public static final String STMT_TYPE_MODIFICATION = "modification";
	
	@XStreamAlias("id")
   	@XStreamAsAttribute
	private String id;
	
//	@XStreamAlias("collectionType")
//   	@XStreamAsAttribute
	private String collectionType;
	
//	@XStreamAlias("unitType")
//   	@XStreamAsAttribute
	private String unitType;
	
//	@XStreamAlias("quantity")
//   	@XStreamAsAttribute
	private String quantity;
	
	@XStreamAlias("type")
   	@XStreamAsAttribute
	private String type;
	
	private String statement;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCollectionType() {
		return collectionType;
	}

	public void setCollectionType(String collectionType) {
		this.collectionType = collectionType;
	}

	public String getUnitType() {
		return unitType;
	}

	public void setUnitType(String unitType) {
		this.unitType = unitType;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStatement() {
		return statement;
	}

	public void setStatement(String statement) {
		this.statement = statement;
	}

}
