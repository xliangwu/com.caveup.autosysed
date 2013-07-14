package com.caveup.autosysed.editors;

import java.awt.Font;

import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.RuleBasedScanner;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.jface.text.rules.WhitespaceRule;
import org.eclipse.jface.text.rules.WordRule;

public class CodeScanner extends RuleBasedScanner {

	public CodeScanner(ColorManager manager) {
		IToken att = new Token(new TextAttribute(
				manager.getColor(IAutosysColorConstants.ATT), null, Font.BOLD));

		IToken comstr = new Token(new TextAttribute(
				manager.getColor(IAutosysColorConstants.STRING)));
		IRule[] rules = new IRule[2];

		WordRule wordRule = new WordRule(new AutosysWordDetector(), comstr,
				true);
		initAutosysAtts(wordRule, att);
		rules[0] = wordRule;

		rules[1] = new WhitespaceRule(new AutosysWhitespaceDetector());

		setRules(rules);
	}

	private void initAutosysAtts(WordRule wordRule, IToken token) {
		for (AutosysAttEnum att : AutosysAttEnum.values()) {
			wordRule.addWord(att.getContent(), token);
		}
	}
}
