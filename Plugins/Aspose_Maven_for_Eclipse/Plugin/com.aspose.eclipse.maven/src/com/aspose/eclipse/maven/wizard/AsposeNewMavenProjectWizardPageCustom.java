/**
 * Copyright (c) Aspose 2002-2014. All Rights Reserved.
 *
 * LICENSE: This program is free software; you can redistribute it
 * and/or modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 3
 * of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with this program. If not,
 * see http://opensource.org/licenses/gpl-3.0.html
 *
 * @author Adeel Ilyas <adeel.ilyas@aspose.com>
 *
 */

package com.aspose.eclipse.maven.wizard;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;

import com.aspose.eclipse.maven.AsposeConstants;
import com.aspose.eclipse.maven.AsposeMavenProject;

public class AsposeNewMavenProjectWizardPageCustom extends
		AsposeNewMavenProjectWizardPageBase {
	public Button asposeWordsButton;
	public Button asposeCellsButton;
	public Button asposePdfButton;
	public Button asposeSlidesButton;
	public Button asposeBarcodeButton;
	public Button asposeTasksButton;
	public Button asposeEmailButton;
	public Button asposeOCRButton;
	public Button asposeImagingButton;
	public Button asposeDiagramButton;

	public Button checkUncheckAll;

	/**
	 * 
	 * @param pageName
	 */
	public AsposeNewMavenProjectWizardPageCustom(String pageName) {
		super(pageName);
	}

	@Override
	public void createControl(Composite parent) {
		super.createControl(parent);
		createECComponents(parent);

	}

	@Override
	public boolean validatePage() {
		boolean result = super.validatePage();
		if (!result)
			return result;

		if (!isComponentSelected()) {
			setErrorMessage(AsposeConstants.IS_COMPONENT_SELECTED);
			return false;
		}

		setErrorMessage(null);
		setMessage(null);
		new AsposeMavenProject();
		updateComponentsSelectionList();
		return true;
	}

	void updateComponentsSelectionList() {

		AsposeConstants
				.println("2. ================ updateComponentsSelectionList (Aspose Maven) ================");
		AsposeMavenProject.clearSelection();
		if (asposeCellsButton.getSelection()) {
			AsposeMavenProject.getApiList().get(AsposeConstants.ASPOSE_CELLS)
					.set_selected(true);
		}

		if (asposeWordsButton.getSelection()) {
			AsposeMavenProject.getApiList().get(AsposeConstants.ASPOSE_WORDS)
					.set_selected(true);
		}

		if (asposePdfButton.getSelection()) {
			AsposeMavenProject.getApiList().get(AsposeConstants.ASPOSE_PDF)
					.set_selected(true);
		}

		if (asposeSlidesButton.getSelection()) {
			AsposeMavenProject.getApiList().get(AsposeConstants.ASPOSE_SLIDES)
					.set_selected(true);
		}

		if (asposeBarcodeButton.getSelection()) {
			AsposeMavenProject.getApiList().get(AsposeConstants.ASPOSE_BARCODE)
					.set_selected(true);
		}

		if (asposeTasksButton.getSelection()) {
			AsposeMavenProject.getApiList().get(AsposeConstants.ASPOSE_TASKS)
					.set_selected(true);
		}

		if (asposeEmailButton.getSelection()) {
			AsposeMavenProject.getApiList().get(AsposeConstants.ASPOSE_EMAIL)
					.set_selected(true);
		}

		if (asposeOCRButton.getSelection()) {
			AsposeMavenProject.getApiList().get(AsposeConstants.ASPOSE_OCR)
					.set_selected(true);
		}

		if (asposeImagingButton.getSelection()) {
			AsposeMavenProject.getApiList().get(AsposeConstants.ASPOSE_IMAGING)
					.set_selected(true);
		}

		if (asposeDiagramButton.getSelection()) {
			AsposeMavenProject.getApiList().get(AsposeConstants.ASPOSE_DIAGRAM)
					.set_selected(true);
		}

	}

	/**
	 * 
	 * @return
	 */
	public boolean isComponentSelected() {
		if (checkUncheckAll.getSelection())
			return true;
		if (asposeCellsButton.getSelection())
			return true;
		if (asposeWordsButton.getSelection())
			return true;
		if (asposePdfButton.getSelection())
			return true;
		if (asposeSlidesButton.getSelection())
			return true;
		if (asposeBarcodeButton.getSelection())
			return true;
		if (asposeTasksButton.getSelection())
			return true;
		if (asposeEmailButton.getSelection())
			return true;
		if (asposeOCRButton.getSelection())
			return true;
		if (asposeImagingButton.getSelection())
			return true;
		if (asposeDiagramButton.getSelection())
			return true;

		return false;
	}

	/**
	 * 
	 * @param title
	 * @param message
	 * @param style
	 * @return
	 */
	public int showMessage(String title, String message, int style) {
		MessageBox msgBox = new MessageBox(getShell(), style);
		msgBox.setMessage(message);
		msgBox.setText(title);
		return msgBox.open();
	}

	/**
	 * 
	 * @param parent
	 */
	private void createECComponents(Composite parent) {
		Composite composite = (Composite) getControl();
		Composite dataArea = new Composite(composite, SWT.FILL);
		dataArea.setLayout(new GridLayout(2, false));
		dataArea.setLayoutData(new GridData(GridData.FILL_BOTH));

		Group parentStyleGroup;
		parentStyleGroup = new Group(dataArea, SWT.FILL);
		parentStyleGroup.setLayout(new GridLayout());
		GridData gridData = new GridData(GridData.FILL_BOTH);
		parentStyleGroup.setLayoutData(gridData);

		Group parentStyleGroupSel;
		parentStyleGroupSel = new Group(dataArea, SWT.BOTTOM);
		parentStyleGroupSel.setLayout(new GridLayout());
		GridData gridDataSel = new GridData(GridData.FILL_BOTH);
		parentStyleGroupSel.setLayoutData(gridDataSel);
		parentStyleGroupSel.setText("Common Uses:");

		parentStyleGroup.setText("Select Components:");

		checkUncheckAll = new Button(parentStyleGroup, SWT.CHECK | SWT.BOLD);
		checkUncheckAll.setText("Select All");
		checkUncheckAll
				.setToolTipText(AsposeConstants.ASPOSE_ALL_COMPONENTS_TEXT);
		checkUncheckAll.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (checkUncheckAll.getSelection()) {
					setButtonsValue(true);
				} else {
					setButtonsValue(false);
				}
				setPageComplete(validatePage());
			}
		});

		asposeCellsButton = new Button(parentStyleGroup, SWT.CHECK);
		asposeCellsButton.setText(AsposeConstants.ASPOSE_CELLS);
		asposeCellsButton
				.setToolTipText(AsposeConstants.ASPOSE_CELLS_FEATURE_TEXT);
		asposeCellsButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				setPageComplete(validatePage());
			}
		});

		asposeWordsButton = new Button(parentStyleGroup, SWT.CHECK);
		asposeWordsButton.setText(AsposeConstants.ASPOSE_WORDS);
		asposeWordsButton
				.setToolTipText(AsposeConstants.ASPOSE_WORDS_FEATURE_TEXT);
		asposeWordsButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				setPageComplete(validatePage());
			}
		});

		asposePdfButton = new Button(parentStyleGroup, SWT.CHECK);
		asposePdfButton.setText(AsposeConstants.ASPOSE_PDF);
		asposePdfButton.setToolTipText(AsposeConstants.ASPOSE_PDF_FEATURE_TEXT);
		asposePdfButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				setPageComplete(validatePage());
			}
		});

		asposeSlidesButton = new Button(parentStyleGroup, SWT.CHECK);
		asposeSlidesButton.setText(AsposeConstants.ASPOSE_SLIDES);
		asposeSlidesButton
				.setToolTipText(AsposeConstants.ASPOSE_SLIDES_FEATURE_TEXT);
		asposeSlidesButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				setPageComplete(validatePage());
			}
		});

		asposeBarcodeButton = new Button(parentStyleGroup, SWT.CHECK);
		asposeBarcodeButton.setText(AsposeConstants.ASPOSE_BARCODE);
		asposeBarcodeButton
				.setToolTipText(AsposeConstants.ASPOSE_BARCODE_FEATURE_TEXT);
		asposeBarcodeButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				setPageComplete(validatePage());
			}
		});

		asposeTasksButton = new Button(parentStyleGroup, SWT.CHECK);
		asposeTasksButton.setText(AsposeConstants.ASPOSE_TASKS);
		asposeTasksButton
				.setToolTipText(AsposeConstants.ASPOSE_TASKS_FEATURE_TEXT);
		asposeTasksButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				setPageComplete(validatePage());
			}
		});

		asposeEmailButton = new Button(parentStyleGroup, SWT.CHECK);
		asposeEmailButton.setText(AsposeConstants.ASPOSE_EMAIL);
		asposeEmailButton
				.setToolTipText(AsposeConstants.ASPOSE_EMAIL_FEATURE_TEXT);
		asposeEmailButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				setPageComplete(validatePage());
			}
		});

		asposeOCRButton = new Button(parentStyleGroup, SWT.CHECK);
		asposeOCRButton.setText(AsposeConstants.ASPOSE_OCR);
		asposeOCRButton.setToolTipText(AsposeConstants.ASPOSE_OCR_FEATURE_TEXT);
		asposeOCRButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				setPageComplete(validatePage());
			}
		});

		asposeImagingButton = new Button(parentStyleGroup, SWT.CHECK);
		asposeImagingButton.setText(AsposeConstants.ASPOSE_IMAGING);
		asposeImagingButton
				.setToolTipText(AsposeConstants.ASPOSE_IMAGING_FEATURE_TEXT);
		asposeImagingButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				setPageComplete(validatePage());
			}
		});
		asposeDiagramButton = new Button(parentStyleGroup, SWT.CHECK);
		asposeDiagramButton.setText(AsposeConstants.ASPOSE_DIAGRAM);
		asposeDiagramButton
				.setToolTipText(AsposeConstants.ASPOSE_DIAGRAM_FEATURE_TEXT);
		asposeDiagramButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				setPageComplete(validatePage());
			}
		});

		Label info = new Label(parentStyleGroupSel, SWT.FILL);
		info.setText(AsposeConstants.ASPOSE_ALL_COMPONENTS_TEXT);

	}

	/**
	 * 
	 * @param value
	 */
	void setButtonsValue(boolean value) {
		asposeWordsButton.setSelection(value);
		asposeCellsButton.setSelection(value);
		asposePdfButton.setSelection(value);
		asposeSlidesButton.setSelection(value);
		asposeBarcodeButton.setSelection(value);
		asposeTasksButton.setSelection(value);
		asposeEmailButton.setSelection(value);
		asposeOCRButton.setSelection(value);
		asposeImagingButton.setSelection(value);
		asposeDiagramButton.setSelection(value);

	}
}
