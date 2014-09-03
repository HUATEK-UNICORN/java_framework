package com.huatek.unicorn.base.dbaccess.gen;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;

import static org.objectweb.asm.Opcodes.*;

public class TestGen2 {

	private static byte[] classBuf;

	public static void main(String[] args) {
		ClassWriter cw = new ClassWriter(0);
		cw.visit(V1_7, ACC_PUBLIC, "com/huatek/unicorn/base/dbaccess/gendao/TestDao", null, "java/lang/Object",
				null);
//		cw.visitField(ACC_PUBLIC + ACC_FINAL + ACC_STATIC, "LESS", "I", null,
//				new Integer(-1)).visitEnd();
//		cw.visitField(ACC_PUBLIC + ACC_FINAL + ACC_STATIC, "EQUAL", "I", null,
//				new Integer(0)).visitEnd();
//		cw.visitField(ACC_PUBLIC + ACC_FINAL + ACC_STATIC, "GREATER", "I",
//				null, new Integer(1)).visitEnd();
		
		// gen method start
		MethodVisitor mvTestInvoke = cw.visitMethod(ACC_PUBLIC, "testInvoke",
				"(Ljava/lang/Object;)I", null, null);
		
		mvTestInvoke.visitEnd();
		// gen method end.
		
		cw.visitEnd();
		classBuf = cw.toByteArray();

		MyClassLoader myClassLoader = new MyClassLoader();
		Class clazz = myClassLoader.defineClass("pkg.Comparable", classBuf);

		System.out.println(clazz);
	}

	static class MyClassLoader extends ClassLoader {

		public Class defineClass(String name, byte[] buf) {
			return defineClass(name, buf, 0, buf.length);
		}
	}

}
