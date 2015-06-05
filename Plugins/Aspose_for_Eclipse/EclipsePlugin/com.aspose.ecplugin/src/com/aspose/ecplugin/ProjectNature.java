/*
 * Copyright (c) 2001-2013 Aspose Pty Ltd. All Rights Reserved.
 * Author: Mohsan.Raza
 */

package com.aspose.ecplugin;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectNature;
import org.eclipse.core.runtime.CoreException;


public class ProjectNature implements IProjectNature {


	private IProject project;

	/**
	 * 
	 */
	@Override
	public void configure() throws CoreException {


	}

	/**
	 * 
	 */
	@Override
	public void deconfigure() throws CoreException {


	}
	/**
	 * 
	 */
	@Override
	public IProject getProject() {

		return project;
	}
	/**
	 * 
	 */
	@Override
	public void setProject(IProject prj) {

		project = prj;
	}



}
