package com.fatwire.cs.logging.log4j;

import junit.framework.TestCase;

public class ThreadNameToFileNameTransformerTest extends TestCase {
	ThreadNameToFileNameTransformer transformer = new ThreadNameToFileNameTransformer();

	public void testGetFileNameIllegal() {
		final String s = "?[]/\\=+<>:;\",&$@|";
		final String ret = transformer.getFileName(s);
		char[] ca = ret.toCharArray();
		for (int i = 0; i < ca.length; i++) {

			assertTrue(Character.toString(ca[i]), ca[i] == '_');
		}
	}

	public void testGetFileNameOK() {
		final String s = "main for . dot and ()";
		final String ret = transformer.getFileName(s);

		assertEquals(s, ret);

	}

}
