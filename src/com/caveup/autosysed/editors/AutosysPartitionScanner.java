package com.caveup.autosysed.editors;

import org.eclipse.jface.text.rules.EndOfLineRule;
import org.eclipse.jface.text.rules.IPredicateRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.MultiLineRule;
import org.eclipse.jface.text.rules.RuleBasedPartitionScanner;
import org.eclipse.jface.text.rules.Token;

public class AutosysPartitionScanner extends RuleBasedPartitionScanner {
	public final static String AUTOSYS_COMMENT = "__autosys_comment";
	public final static String AUTOSYS_ATT = "__autosys_att";

	public AutosysPartitionScanner() {

		IToken autosysComment = new Token(AUTOSYS_COMMENT);

		IPredicateRule[] rules = new IPredicateRule[2];

		rules[0] = new MultiLineRule("/*", "*/", autosysComment);
		rules[1] = new EndOfLineRule("//", autosysComment);

		setPredicateRules(rules);
	}
}
