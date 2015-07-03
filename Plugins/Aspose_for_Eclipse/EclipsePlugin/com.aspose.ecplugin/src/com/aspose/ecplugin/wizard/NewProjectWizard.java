/*
 * Copyright (c) 2001-2013 Aspose Pty Ltd. All Rights Reserved.
 * Author: Mohsan.Raza
 */

package com.aspose.ecplugin.wizard;

import java.net.URI;
import java.net.URL;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExecutableExtension;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;

import com.aspose.ecplugin.AsposeComponentsManager;
import com.aspose.ecplugin.AsposeJavaComponent;
import com.aspose.ecplugin.AsposeJavaComponents;
import com.aspose.ecplugin.CustomProjectSupport;
import com.aspose.ecplugin.AsposeConstants;

public class NewProjectWizard extends Wizard implements INewWizard,
IExecutableExtension {

	private WizardNewProjectCreationPageCustom _pageOne;

	@SuppressWarnings("unused")
	private IConfigurationElement _configurationElement;

	/**
	 * 
	 */
	public NewProjectWizard() {
		setWindowTitle(AsposeConstants.WIZARD_NAME);
	}

	/**
	 * 
	 */
	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {

	}

	/**
	 * 
	 */
	@Override
	public boolean performFinish() {

		String name = _pageOne.getProjectName();
		processComponents();
		AsposeComponentsManager comManager = new AsposeComponentsManager(
				_pageOne);
		if (!comManager.downloadComponents())
			return false;

		URI location = null;
		if (!_pageOne.useDefaults()) {
			location = _pageOne.getLocationURI();
		}

		IProject project = CustomProjectSupport.createProject(name, location);
		IFolder libFolder = project.getFolder(AsposeConstants.BIN_FOLDER);

		try {
			libFolder.delete(true, null);
		} catch (CoreException e) {

			e.printStackTrace();
		}

		return true;
	}

	/**
	 * 
	 */
	void processComponents() {
		AsposeJavaComponents.clearSelection();
		if (_pageOne.asposeCellsButton.getSelection()) {
			AsposeJavaComponent component = AsposeJavaComponents.list
					.get(AsposeConstants.ASPOSE_CELLS);
			component.set_selected(true);
		}	

		if (_pageOne.asposeWordsButton.getSelection()) {
			AsposeJavaComponent component = AsposeJavaComponents.list
					.get(AsposeConstants.ASPOSE_WORDS);
			component.set_selected(true);
		}
		if (_pageOne.asposePdfButton.getSelection()) {
			AsposeJavaComponent component = AsposeJavaComponents.list
					.get(AsposeConstants.ASPOSE_PDF);
			component.set_selected(true);
			
		}
		if (_pageOne.asposeSlidesButton.getSelection()) {
			AsposeJavaComponent component = AsposeJavaComponents.list
					.get(AsposeConstants.ASPOSE_SLIDES);
			component.set_selected(true);
		}
	
		if (_pageOne.asposeBarcodeButton.getSelection()) {
			AsposeJavaComponent component = AsposeJavaComponents.list
					.get(AsposeConstants.ASPOSE_BARCODE);
			component.set_selected(true);
		}
		if (_pageOne.asposeTasksButton.getSelection()) {
			AsposeJavaComponent component = AsposeJavaComponents.list
					.get(AsposeConstants.ASPOSE_TASKS);
			component.set_selected(true);
		}
		
		if (_pageOne.asposeEmailButton.getSelection()) {
			AsposeJavaComponent component = AsposeJavaComponents.list
					.get(AsposeConstants.ASPOSE_EMAIL);
			component.set_selected(true);
		}
		if (_pageOne.asposeOCRButton.getSelection()) {
			AsposeJavaComponent component = AsposeJavaComponents.list
					.get(AsposeConstants.ASPOSE_OCR);
			component.set_selected(true);
		}
		if (_pageOne.asposeImagingButton.getSelection()) {
			AsposeJavaComponent component = AsposeJavaComponents.list
					.get(AsposeConstants.ASPOSE_IMAGING);
			component.set_selected(true);
		}
		if (_pageOne.asposeDiagramButton.getSelection()) {
			AsposeJavaComponent component = AsposeJavaComponents.list
					.get(AsposeConstants.ASPOSE_DIAGRAM);
			component.set_selected(true);
		}
	}

	/**
	 * 
	 */
	@Override
	public void addPages() {
		super.addPages();

		_pageOne = new WizardNewProjectCreationPageCustom(
				AsposeConstants.FIRST_PAGE_NAME);

		_pageOne.setTitle(AsposeConstants.FIRST_PAGE_TITLE);
		_pageOne.setDescription(AsposeConstants.FIRST_PAGE_DESCRIPTION);
		URL imageurl = getClass().getResource("/images/long_banner.PNG");
		ImageDescriptor image = ImageDescriptor.createFromURL(imageurl);
		_pageOne.setImageDescriptor(image);

		addPage(_pageOne);

	}

	/**
	 * 
	 */
	@Override
	public void setInitializationData(IConfigurationElement config,
			String propertyName, Object data) throws CoreException {
		_configurationElement = config;
	}

}
