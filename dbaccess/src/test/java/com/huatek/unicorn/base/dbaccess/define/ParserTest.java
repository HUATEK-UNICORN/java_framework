package com.huatek.unicorn.base.dbaccess.define;

import com.thoughtworks.xstream.XStream;

public class ParserTest {

	public static void main(String[] args) {
		XStream x = new XStream();
		x.processAnnotations(DataAccessDefine.class);
		x.processAnnotations(StatementDefine.class);
//		x.autodetectAnnotations(true);
		
//		DataAccessDefine dad = new DataAccessDefine();
//		
//		StatementDefine sd = new StatementDefine();
//		sd.setId("1111");
//		sd.setQuantity("long");
//		sd.setStatement("select * from xx");
//		
//		StatementDefine sd2 = new StatementDefine();
//		sd2.setId("sd1111");
//		sd2.setQuantity("int");
//		sd2.setStatement("select * from sdxx");
//		
//		dad.addStatement(sd);
//		dad.addStatement(sd2);
//		
//		System.out.println(x.toXML(dad));
		
//		System.out.println(ParserTest.class.getResourceAsStream("/dao_config.xml"));
		
		Object obj = x.fromXML(ParserTest.class.getResourceAsStream("/dao_config.xml"));
		
		DataAccessDefine dad2 = (DataAccessDefine)obj;
		
		System.out.println(dad2.getStatements());
		
//		System.out.println(dad2);
		
//		System.out.println(x.toXML(obj));
	} 

}
