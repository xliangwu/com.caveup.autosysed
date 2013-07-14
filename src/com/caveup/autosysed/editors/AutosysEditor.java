package com.caveup.autosysed.editors;

import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.text.IDocument;
import org.eclipse.ui.editors.text.TextEditor;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;

import com.caveup.autosysed.domain.Job;
import com.caveup.autosysed.outlines.AutosysOutlinePage;
import com.caveup.autosysed.utils.JIlFileUtils;

public class AutosysEditor extends TextEditor {

	private static final String VIEW_CONTEXT_ID = "com.caveup.autosysed.editors.context"; //$NON-NLS-1$

	private ColorManager colorManager;
	private AutosysOutlinePage outlinePage;

	public AutosysEditor() {
		super();
		colorManager = new ColorManager();
		setSourceViewerConfiguration(new AutosysConfiguration(colorManager));
		setDocumentProvider(new AutosysDocumentProvider());
	}

	protected void initializeKeyBindingScopes() {
		setKeyBindingScopes(new String[] { VIEW_CONTEXT_ID });
	}

	public void dispose() {
		if (outlinePage != null) {
			outlinePage.setInputs(null);
		}
		colorManager.dispose();
		super.dispose();
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Object getAdapter(Class adapter) {
		if (IContentOutlinePage.class.equals(adapter)) {
			if (outlinePage == null) {
				List<Job> jobs = getAllNodes();
				outlinePage = new AutosysOutlinePage(jobs, this);
			}
			return outlinePage;
		}
		return super.getAdapter(adapter);
	}

	public List<Job> getAllNodes() {
		List<Job> allJobs = null;
		try {
			IDocument doc = this.getDocumentProvider().getDocument(this.getEditorInput());
			allJobs = JIlFileUtils.parse(doc);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return allJobs;
	}

	public void doSave(IProgressMonitor monitor) {
		super.doSave(monitor);
		if (this.outlinePage != null) {
			this.outlinePage.setInputs(getAllNodes());
			this.outlinePage.update();
		}
	}

	public void doRevertToSaved() {
		super.doRevertToSaved();
		if (this.outlinePage != null) {
			this.outlinePage.setInputs(getAllNodes());
			this.outlinePage.update();
		}
	}

	public void doSaveAs() {
		super.doSaveAs();
		if (this.outlinePage != null) {
			this.outlinePage.setInputs(getAllNodes());
			this.outlinePage.update();
		}
	}

	public AutosysOutlinePage getOutlinePage() {
		return outlinePage;
	}
}
