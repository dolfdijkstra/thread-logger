package com.fatwire.cs.logging.log4j;

import java.util.regex.Pattern;

public class ThreadNameToFileNameTransformer {
	// escacpe chars that are illegal:
	// windows list is ? [ ] / \ = + < > : ; " ,
	// some more reading at http://www.portfoliofaq.com/pfaq/FAQ00352.htm
	private final Pattern p = Pattern.compile("[\\?\\[\\]/\\\\=+<>:;\",\\$&@|]");

	ThreadNameToFileNameTransformer() {
		super();
		// System.out.println(p.pattern());
	}

	public String getFilename(int postfix) {
		return getFileName(Thread.currentThread().getName() + "." + postfix);
	}

	protected String getFileName(String name) {
		final String s = p.matcher(name).replaceAll("_");
		// System.out.println(s);
		return s;

	}

}
