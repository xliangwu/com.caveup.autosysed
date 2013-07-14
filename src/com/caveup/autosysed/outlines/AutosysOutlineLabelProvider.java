package com.caveup.autosysed.outlines;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Image;

import com.caveup.autosysed.domain.Job;

public class AutosysOutlineLabelProvider implements ILabelProvider {

	public static final AutosysOutlineLabelProvider INSTANCE = new AutosysOutlineLabelProvider();

	@Override
	public Image getImage(Object element) {
		if (element instanceof Job) {
			Job node = (Job) element;
			return node.getImage();
		}
		return null;
	}

	@Override
	public String getText(Object element) {
		if (element instanceof Job) {
			return ((Job) element).getJobName();
		}
		return null;
	}

	@Override
	public void addListener(ILabelProviderListener listener) {
		// nothing to do
	}

	@Override
	public void dispose() {
	}

	@Override
	public boolean isLabelProperty(Object element, String property) {
		return false;
	}

	@Override
	public void removeListener(ILabelProviderListener listener) {

	}

}
