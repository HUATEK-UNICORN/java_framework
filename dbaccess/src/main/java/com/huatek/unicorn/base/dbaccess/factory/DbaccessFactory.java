package com.huatek.unicorn.base.dbaccess.factory;

import com.huatek.unicorn.base.dbaccess.modification.Modification;
import com.huatek.unicorn.base.dbaccess.query.Query;

public interface DbaccessFactory<E, P> {
	
	Query<E> getQuery(String identity);
	
	Modification<P> getModification(String identity);
}
