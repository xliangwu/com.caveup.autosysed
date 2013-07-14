package com.caveup.autosysed.editors;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextDoubleClickStrategy;
import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.presentation.IPresentationReconciler;
import org.eclipse.jface.text.presentation.PresentationReconciler;
import org.eclipse.jface.text.rules.DefaultDamagerRepairer;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.SourceViewerConfiguration;

public class AutosysConfiguration extends SourceViewerConfiguration {
	private AutosysDoubleClickStrategy doubleClickStrategy;
	private CodeScanner codeScanner;
	private ColorManager colorManager;

	public AutosysConfiguration(ColorManager colorManager) {
		this.colorManager = colorManager;
	}

	public String[] getConfiguredContentTypes(ISourceViewer sourceViewer) {
		return new String[] { IDocument.DEFAULT_CONTENT_TYPE,
				AutosysPartitionScanner.AUTOSYS_COMMENT,
				AutosysPartitionScanner.AUTOSYS_ATT };
	}

	public ITextDoubleClickStrategy getDoubleClickStrategy(
			ISourceViewer sourceViewer, String contentType) {
		if (doubleClickStrategy == null)
			doubleClickStrategy = new AutosysDoubleClickStrategy();
		return doubleClickStrategy;
	}

	protected CodeScanner getAutosysCodeScanner() {
		if (codeScanner == null) {
			codeScanner = new CodeScanner(colorManager);
			codeScanner.setDefaultReturnToken(new Token(new TextAttribute(
					colorManager.getColor(IAutosysColorConstants.STRING))));
		}
		return codeScanner;
	}

	public IPresentationReconciler getPresentationReconciler(
			ISourceViewer sourceViewer) {
		PresentationReconciler reconciler = new PresentationReconciler();

		DefaultDamagerRepairer dr = new DefaultDamagerRepairer(
				getAutosysCodeScanner());
		reconciler.setDamager(dr, IDocument.DEFAULT_CONTENT_TYPE);
		reconciler.setRepairer(dr, IDocument.DEFAULT_CONTENT_TYPE);

		NonRuleBasedDamagerRepairer ndr = new NonRuleBasedDamagerRepairer(
				new TextAttribute(colorManager
						.getColor(IAutosysColorConstants.AUTOSYS_COMMENT)));
		reconciler.setDamager(ndr, AutosysPartitionScanner.AUTOSYS_COMMENT);
		reconciler.setRepairer(ndr, AutosysPartitionScanner.AUTOSYS_COMMENT);

		return reconciler;
	}

}