/*******************************************************************************
 *  Copyright (c) 2006, 2011 IBM Corporation and others.
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v1.0
 *  which accompanies this distribution, and is available at
 *  http://www.eclipse.org/legal/epl-v10.html
 * 
 *  Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package com.caveup.autosysed.outlines;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.ViewerComparator;

public interface IOutlineContentCreator {

	public ViewerComparator createOutlineComparator();

	public ViewerComparator createDefaultOutlineComparator();

	public ILabelProvider createOutlineLabelProvider();

	public ITreeContentProvider createOutlineContentProvider();

	public Object getOutlineInput();

}
