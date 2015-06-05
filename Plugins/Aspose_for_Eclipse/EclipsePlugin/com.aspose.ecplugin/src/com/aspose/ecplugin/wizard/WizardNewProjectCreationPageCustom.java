/*
 * Copyright (c) 2001-2013 Aspose Pty Ltd. All Rights Reserved.
 * Author: Mohsan.Raza
 */

package com.aspose.ecplugin.wizard;

import java.io.IOException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.ui.dialogs.WizardNewProjectCreationPage;

import com.aspose.ecplugin.AsposeConstants;

public class WizardNewProjectCreationPageCustom extends WizardNewProjectCreationPage {
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
	public WizardNewProjectCreationPageCustom(String pageName) {
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
		if(!result)
			return result;

		if(!isComponentSelected())
		{
			setErrorMessage(AsposeConstants.IS_COMPONENT_SELECTED);
			return false;
		}

		setErrorMessage(null);
		setMessage(null);
		return true;
	}

	/**
	 * 
	 * @return
	 */
	public boolean isComponentSelected()
	{
		if(asposeCellsButton.getSelection())
			return true;
		if(asposeWordsButton.getSelection())
			return true;
		if(asposePdfButton.getSelection())
			return true;
		if(asposeSlidesButton.getSelection())
			return true;
	    if(asposeBarcodeButton.getSelection())
			return true;
	    if(asposeTasksButton.getSelection())
			return true;
		if(asposeEmailButton.getSelection())
			return true;
		if(asposeOCRButton.getSelection())
			return true;
		if(asposeImagingButton.getSelection())
			return true;
		if(asposeDiagramButton.getSelection())
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
	public int showMessage(String title, String message,int style)
	{
		MessageBox msgBox = new MessageBox(getShell(),style/*SWT.ICON_WARNING | SWT.YES | SWT.NO | SWT.CANCEL*/);
		msgBox.setMessage(message);
		msgBox.setText(title);
		return msgBox.open();
	}

	/**
	 * 
	 * @param parent
	 */
	private void createECComponents(Composite parent){
		Composite composite = (Composite) getControl ();
		Composite dataArea = new Composite (composite, SWT.FILL);
		dataArea.setLayout (new GridLayout (2,false));
		dataArea.setLayoutData (new GridData (GridData.FILL_BOTH));

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

		checkUncheckAll = new Button(parentStyleGroup, SWT.CHECK|SWT.BOLD);
		checkUncheckAll.setText("Select All");
		checkUncheckAll.setToolTipText(AsposeConstants.ASPOSE_ALL_COMPONENTS_TEXT);
		checkUncheckAll.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (checkUncheckAll.getSelection()) {
					setButtonsValue(true);
				} else {
					setButtonsValue(false);
				}
			}
		});


		asposeCellsButton = new Button(parentStyleGroup, SWT.CHECK);
		asposeCellsButton.setText(AsposeConstants.ASPOSE_CELLS);
		asposeCellsButton.setToolTipText(AsposeConstants.ASPOSE_CELLS_FEATURE_TEXT);
		asposeCellsButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {setPageComplete(validatePage());}});


		asposeWordsButton = new Button(parentStyleGroup, SWT.CHECK);
		asposeWordsButton.setText(AsposeConstants.ASPOSE_WORDS);
		asposeWordsButton.setToolTipText(AsposeConstants.ASPOSE_WORDS_FEATURE_TEXT);
		asposeWordsButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {setPageComplete(validatePage());}});


		asposePdfButton = new Button(parentStyleGroup, SWT.CHECK);
		asposePdfButton.setText(AsposeConstants.ASPOSE_PDF);
		asposePdfButton.setToolTipText(AsposeConstants.ASPOSE_PDF_FEATURE_TEXT);
		asposePdfButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {setPageComplete(validatePage());}});

		asposeSlidesButton = new Button(parentStyleGroup, SWT.CHECK);
		asposeSlidesButton.setText(AsposeConstants.ASPOSE_SLIDES);
		asposeSlidesButton.setToolTipText(AsposeConstants.ASPOSE_SLIDES_FEATURE_TEXT);
		asposeSlidesButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {setPageComplete(validatePage());}});

		
		asposeBarcodeButton = new Button(parentStyleGroup, SWT.CHECK);
		asposeBarcodeButton.setText(AsposeConstants.ASPOSE_BARCODE);
		asposeBarcodeButton.setToolTipText(AsposeConstants.ASPOSE_BARCODE_FEATURE_TEXT);
		asposeBarcodeButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {setPageComplete(validatePage());}});
		
		asposeTasksButton = new Button(parentStyleGroup, SWT.CHECK);
		asposeTasksButton.setText(AsposeConstants.ASPOSE_TASKS);
		asposeTasksButton.setToolTipText(AsposeConstants.ASPOSE_TASKS_FEATURE_TEXT);
		asposeTasksButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {setPageComplete(validatePage());}});
		
		asposeEmailButton = new Button(parentStyleGroup, SWT.CHECK);
		asposeEmailButton.setText(AsposeConstants.ASPOSE_EMAIL);
		asposeEmailButton.setToolTipText(AsposeConstants.ASPOSE_EMAIL_FEATURE_TEXT);
		asposeEmailButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {setPageComplete(validatePage());}});

		asposeOCRButton = new Button(parentStyleGroup, SWT.CHECK);
		asposeOCRButton.setText(AsposeConstants.ASPOSE_OCR);
		asposeOCRButton.setToolTipText(AsposeConstants.ASPOSE_OCR_FEATURE_TEXT);
		asposeOCRButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {setPageComplete(validatePage());}});

		asposeImagingButton = new Button(parentStyleGroup, SWT.CHECK);
		asposeImagingButton.setText(AsposeConstants.ASPOSE_IMAGING);
		asposeImagingButton.setToolTipText(AsposeConstants.ASPOSE_IMAGING_FEATURE_TEXT);
		asposeImagingButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {setPageComplete(validatePage());}});
		asposeDiagramButton = new Button(parentStyleGroup, SWT.CHECK);
		asposeDiagramButton.setText(AsposeConstants.ASPOSE_DIAGRAM);
		asposeDiagramButton.setToolTipText(AsposeConstants.ASPOSE_DIAGRAM_FEATURE_TEXT);
		asposeDiagramButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {setPageComplete(validatePage());}});

		Label info = new Label(parentStyleGroupSel,SWT.FILL);
		info.setText(AsposeConstants.ASPOSE_ALL_COMPONENTS_TEXT);




	}
	/**
	 * 
	 * @param value
	 */
	void setButtonsValue(boolean value){
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
