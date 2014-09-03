package com.huatek.unicorn.base.dbaccess.factory;

import com.huatek.unicorn.base.dbaccess.modification.IntegerObjectsListModification;
import com.huatek.unicorn.base.dbaccess.query.IntegerMapListQuery;
import com.huatek.unicorn.base.dbaccess.query.LongMapListQuery;

public interface DbaccessFactory {
	
	IntegerMapListQuery getIntegerMapListQuery(String identity);
	
	LongMapListQuery getLongMapListQuery(String identity);
	
	IntegerObjectsListModification getIntegerObjectsListModification(String identity);
}
