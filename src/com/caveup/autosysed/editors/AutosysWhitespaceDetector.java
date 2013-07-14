package com.caveup.autosysed.editors;

import org.eclipse.jface.text.rules.IWhitespaceDetector;

public class AutosysWhitespaceDetector implements IWhitespaceDetector {

	public boolean isWhitespace(char c) {
		return (c == ' ' || c == '\t' || c == '\n' || c == '\r');
	}
}
