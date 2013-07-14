package com.caveup.autosysed.outlines;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.ViewerComparator;

public class DefaultOutlineCreator implements IOutlineContentCreator {

	private AutosysOutlinePage outlinePage;

	public DefaultOutlineCreator(AutosysOutlinePage outlinePage) {
		this.outlinePage = outlinePage;
	}

	@Override
	public ViewerComparator createOutlineComparator() {
		return null;
	}

	@Override
	public ViewerComparator createDefaultOutlineComparator() {
		return outlinePage.createDefaultOutlineComparator();
	}

	@Override
	public ILabelProvider createOutlineLabelProvider() {
		return AutosysOutlineLabelProvider.INSTANCE;
	}

	@Override
	public ITreeContentProvider createOutlineContentProvider() {
		return AutosysOutlineContentProvider.INSTANCE;
	}

	@Override
	public Object getOutlineInput() {
		return outlinePage.getInputs();
	}

}
