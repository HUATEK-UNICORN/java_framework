package com.huatek.unicorn.base.dbaccess.gen;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Type;

import static org.objectweb.asm.Opcodes.*;

public class TestGen {

	private static byte[] classBuf;

	public static void main(String[] args) {

		System.out.println(Type.getType(
				com.huatek.unicorn.base.dbaccess.query.Query.class)
				.getDescriptor());

		ClassWriter cw = new ClassWriter(0);
		cw.visit(V1_7, ACC_PUBLIC + ACC_ABSTRACT + ACC_INTERFACE,
				"pkg/Comparable", null, "java/lang/Object", null);
		// new String[] { "pkg/Mesurable" });
		cw.visitField(ACC_PUBLIC + ACC_FINAL + ACC_STATIC, "LESS", "I", null,
				new Integer(-1)).visitEnd();
		cw.visitField(ACC_PUBLIC + ACC_FINAL + ACC_STATIC, "EQUAL", "I", null,
				new Integer(0)).visitEnd();
		cw.visitField(ACC_PUBLIC + ACC_FINAL + ACC_STATIC, "GREATER", "I",
				null, new Integer(1)).visitEnd();
		cw.visitMethod(ACC_PUBLIC + ACC_ABSTRACT, "compareTo",
				"(Ljava/lang/Object;)I", null, null).visitEnd();
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
