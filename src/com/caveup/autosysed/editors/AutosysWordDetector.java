package com.caveup.autosysed.editors;

import org.eclipse.jface.text.rules.IWordDetector;

public class AutosysWordDetector implements IWordDetector {

	public static final char LINUX_PATH_SEP_CHAR = '/';

	/**
	 * No white space char
	 */
	@Override
	public boolean isWordStart(char c) {
		return Character.isJavaIdentifierPart(c);
	}

	/**
	 * Allow the path separator valid
	 */
	@Override
	public boolean isWordPart(char c) {
		return Character.isJavaIdentifierPart(c) || c == LINUX_PATH_SEP_CHAR;
	}
}
