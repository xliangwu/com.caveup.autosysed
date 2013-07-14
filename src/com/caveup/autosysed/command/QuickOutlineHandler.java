package com.caveup.autosysed.command;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.swt.SWT;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.handlers.HandlerUtil;

import com.caveup.autosysed.editors.AutosysEditor;
import com.caveup.autosysed.outlines.DefaultOutlineCreator;
import com.caveup.autosysed.outlines.QuickOutlinePopupDialog;

/**
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class QuickOutlineHandler extends AbstractHandler {
	/**
	 * The constructor.
	 */
	public QuickOutlineHandler() {
	}

	/**
	 * the command has been executed, so extract extract the needed information
	 * from the application context.
	 */
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		IEditorPart editorPart = HandlerUtil.getActiveEditor(event);
		if (editorPart instanceof AutosysEditor) {
			AutosysEditor ad = (AutosysEditor) editorPart;
			int sytle = SWT.RESIZE | SWT.BORDER | SWT.FOCUSED;
			DefaultOutlineCreator defaultOutlineCreator = new DefaultOutlineCreator(ad.getOutlinePage());
			QuickOutlinePopupDialog dialog = new QuickOutlinePopupDialog(window.getShell(), sytle,
					defaultOutlineCreator, ad.getOutlinePage());
			dialog.setSize(400, 300);
			dialog.open();
			dialog.setFocus();
		}
		return null;
	}
}
