package com.caveup.autosysed.outlines;

import java.util.List;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

import com.caveup.autosysed.domain.Job;

public class AutosysOutlineContentProvider implements ITreeContentProvider {

	public static final AutosysOutlineContentProvider INSTANCE = new AutosysOutlineContentProvider();

	@Override
	public void dispose() {
		// nothing to do
	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		// nothing to do
	}

	@Override
	public Object[] getElements(Object inputElement) {
		if (inputElement instanceof List) {
			List<?> list = (List<?>) inputElement;
			return list.toArray();
		}
		return null;
	}

	@Override
	public Object[] getChildren(Object parentElement) {
		if (parentElement instanceof Job) {
			Job job = (Job) parentElement;
			if (job.getSubJobs() != null && job.getSubJobs().size() > 0) {
				return job.getSubJobs().toArray();
			}
		}
		return null;
	}

	@Override
	public Object getParent(Object element) {
		if (element instanceof Job) {
			Job job = (Job) element;
			return job.getParentJob();
		}
		return null;
	}

	@Override
	public boolean hasChildren(Object element) {
		if (element instanceof Job) {
			Job job = (Job) element;
			if (job.getSubJobs() != null && job.getSubJobs().size() > 0) {
				return true;
			}
		}
		return false;
	}
}
