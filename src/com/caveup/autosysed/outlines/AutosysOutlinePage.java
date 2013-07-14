package com.caveup.autosysed.outlines;

import java.util.Comparator;
import java.util.List;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreePath;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IPropertyListener;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.editors.text.TextEditor;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.ui.views.contentoutline.ContentOutlinePage;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;

import com.caveup.autosysed.domain.Job;

public class AutosysOutlinePage extends ContentOutlinePage implements IPropertyListener, ISelectionListener,
		IOutlineSelectionHandler {
	private static final ViewerComparator lexComparator = new ViewerComparator(new LexComparator());
	private List<Job> inputs;
	private TextEditor textEditor;
	private IActionBars bar;

	public AutosysOutlinePage(List<Job> inputs, TextEditor textEditor) {
		this.inputs = inputs;
		this.textEditor = textEditor;
	}

	@Override
	public void createControl(Composite parent) {
		super.createControl(parent);
		this.getTreeViewer().setLabelProvider(AutosysOutlineLabelProvider.INSTANCE);
		this.getTreeViewer().setContentProvider(AutosysOutlineContentProvider.INSTANCE);
		getSite().getPage().addPostSelectionListener(this);

		if (inputs != null)
			this.getTreeViewer().setInput(inputs);
		bar = this.getSite().getActionBars();
		bar.getToolBarManager().add(new SortAction());
		bar.updateActionBars();
	}

	public void dispose() {
		getSite().getPage().removeSelectionListener(this);
		super.dispose();
	}

	public void update() {
		TreeViewer viewer = getTreeViewer();
		if (viewer != null) {
			Control control = viewer.getControl();
			if ((control != null) && (!control.isDisposed())) {
				control.setRedraw(false);
				viewer.setInput(this.inputs);
				control.setRedraw(true);
			}
		}
	}

	private class SortAction extends Action {
		public SortAction() {
			super();
			this.setId("com.caveup.autosysed.outline.page.sortAction");
			this.setImageDescriptor(AbstractUIPlugin.imageDescriptorFromPlugin("com.caveup.autosysed",
					"icons/alphab_sort_co.gif"));
			this.setDisabledImageDescriptor(AbstractUIPlugin.imageDescriptorFromPlugin("com.caveup.autosysed",
					"icons/alphab_sort_co_dl.gif"));
			this.setEnabled(true);
			this.valueChange(false);
		}

		@Override
		public void run() {
			this.valueChange(isChecked());
		}

		private void valueChange(final boolean on) {
			this.setChecked(on);
			final TreeViewer viewer = getTreeViewer();
			BusyIndicator.showWhile(viewer.getControl().getDisplay(), new Runnable() {
				public void run() {
					if (on) {
						viewer.setComparator(lexComparator);
					} else {
						viewer.setComparator(null);
					}
				}
			});
		}
	}

	private static class LexComparator implements Comparator<String> {
		public int compare(String object1, String object2) {
			return object1.compareTo(object2);
		}
	}

	public List<Job> getInputs() {
		return inputs;
	}

	public void setInputs(List<Job> inputs) {
		this.inputs = inputs;
	}

	@Override
	public void selectionChanged(SelectionChangedEvent event) {
		super.selectionChanged(event);
		ISelection selection = event.getSelection();
		if (selection.isEmpty()) {
			textEditor.resetHighlightRange();
		} else {
			Job job = (Job) ((IStructuredSelection) selection).getFirstElement();
			if (job.isTextSelection()) {
				job.setTextSelection(false);
				return;
			}
			int start = job.getOffset();
			int length = job.getLength();
			try {
				textEditor.selectAndReveal(start, length);
			} catch (IllegalArgumentException x) {
				textEditor.resetHighlightRange();
			}
		}
	}

	@Override
	public void selectionChanged(IWorkbenchPart part, ISelection selection) {
		// nothing to do to improve the performance
	}

	public void selectionChangedAction(IWorkbenchPart part, ISelection selection) {
		if ((selection instanceof ITextSelection)) {
			ITextSelection textSelection = (ITextSelection) selection;
			int start = textSelection.getOffset();
			int length = textSelection.getLength();

			Job element = findNearestElement(start, length);
			if (element != null) {
				element.setTextSelection(true);
				getTreeViewer().reveal(element);
				TreeSelection treeSelection = new TreeSelection(new TreePath(new Object[] { element }));
				getTreeViewer().setSelection(treeSelection);
			}
		}
	}

	private Job findNearestElement(int start, int length) {
		if (inputs == null || inputs.size() == 0)
			return null;
		Job searched = null;
		for (Job treeNode : inputs) {
			searched = findNearestElement(treeNode, start, length);
			if (searched != null)
				break;
		}

		return searched;
	}

	private Job findNearestElement(Job job, int start, int length) {
		if (job == null)
			return null;
		if (start >= job.getStartOffset() && (start + length) <= job.getEndOffset()) {
			return job;
		}
		Job searched = null;
		if (job.getSubJobs() != null && !job.getSubJobs().isEmpty()) {
			for (Job subtreeNode : job.getSubJobs()) {
				searched = findNearestElement(subtreeNode, start, length);
				if (searched != null)
					break;
			}
		}
		return searched;
	}

	public void setTextEditor(TextEditor textEditor) {
		this.textEditor = textEditor;
	}

	@Override
	public void propertyChanged(Object source, int propId) {
		// nothing to do
	}

	@Override
	public void updateSelection(SelectionChangedEvent event) {
		selectionChanged(event);
	}

	@Override
	public IContentOutlinePage getContentOutline() {
		return this;
	}

	public ViewerComparator createDefaultOutlineComparator() {
		return lexComparator;
	}

}
