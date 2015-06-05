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

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;

import org.apache.maven.model.Model;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExecutableExtension;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.m2e.core.MavenPlugin;
import org.eclipse.m2e.core.internal.IMavenConstants;
import org.eclipse.m2e.core.ui.internal.MavenImages;
import org.eclipse.m2e.core.ui.internal.Messages;
import org.eclipse.m2e.core.ui.internal.wizards.AbstractMavenProjectWizard;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import com.aspose.eclipse.maven.AsposeConstants;
import com.aspose.eclipse.maven.AsposeMavenManager;
import com.aspose.eclipse.maven.AsposeMavenProject;
import com.aspose.eclipse.maven.apis.artifacts.Metadata;

public class AsposeMavenNewProjectWizard extends AbstractMavenProjectWizard
		implements INewWizard, IExecutableExtension {
	private AsposeNewMavenProjectWizardPageCustom _pageTwo;

	private AsposeMavenProjectWizardArtifactPage _pageOne;

	@SuppressWarnings("unused")
	private IConfigurationElement _configurationElement;

	/**
	 * 
	 */
	public AsposeMavenNewProjectWizard() {
		setWindowTitle(AsposeConstants.WIZARD_NAME);
		setNeedsProgressMonitor(true);
		setDefaultPageImageDescriptor(MavenImages.WIZ_NEW_PROJECT);
	}

	/**
	 * 
	 */
	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {

	}

	/** Returns the model. */
	public Model getModel() {

		Model _model = _pageOne.getModel();

		return _model;
	}

	/**
	 * 
	 */
	@Override
	public boolean performFinish() {

		final Model model = getModel();
		// final String projectName = importConfiguration.getProjectName(model);
		final String projectName = model.getName();
		IStatus nameStatus = importConfiguration.validateProjectName(model);
		if (!nameStatus.isOK()) {
			MessageDialog.openError(getShell(),
					NLS.bind(Messages.wizardProjectJobFailed, projectName),
					nameStatus.getMessage());
			return false;
		}

		if (!AsposeMavenManager.isInternetConnected()) {
			MessageDialog.openError(getShell(), NLS.bind(
					AsposeConstants.INTERNET_CONNECTION_REQUIRED_MESSAGE_TITLE,
					projectName),
					AsposeConstants.MAVEN_INTERNET_CONNECTION_REQUIRED_MESSAGE);

			return false;
		}

		IWorkspace workspace = ResourcesPlugin.getWorkspace();

		final IPath location;
		if (!_pageTwo.useDefaults()) {

			location = _pageTwo.getLocationPath();
		} else
			location = null;

		final IWorkspaceRoot root = workspace.getRoot();
		final IProject project = importConfiguration.getProject(root, model);

		final IPath pomFile;

		if (location == null || root.getLocation().equals(location)) {
			pomFile = root.getLocation().append(project.getName())
					.append(IMavenConstants.POM_FILE_NAME);

		} else {
			pomFile = location.append(IMavenConstants.POM_FILE_NAME);

		}

		if (pomFile.toFile().exists()) {
			MessageDialog.openError(getShell(),
					NLS.bind(Messages.wizardProjectJobFailed, projectName),
					Messages.wizardProjectErrorPomAlreadyExists);
			return false;
		}

		final AbstractCreateMavenProjectJob job;
		final String[] folders = _pageOne.getFolders();

		ProgressMonitorDialog dialog = new ProgressMonitorDialog(null);

		try {
			dialog.run(true, true, new IRunnableWithProgress() {
				public void run(IProgressMonitor monitor) {
					monitor.beginTask(
							"Retrieving Aspose Maven Dependencies ...",
							AsposeMavenProject.getApiList().size());

					AsposeMavenManager.retrieveAsposeMavenDependencies(monitor);

				}
			});
		} catch (InvocationTargetException | InterruptedException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		job = new AbstractCreateMavenProjectJob(NLS.bind(
				Messages.wizardProjectJobCreatingProject, projectName),
				workingSets) {
			@Override
			protected List<IProject> doCreateMavenProjects(
					IProgressMonitor monitor) throws CoreException {
				MavenPlugin.getProjectConfigurationManager()
						.createSimpleProject(project, location, model, folders, //
								importConfiguration, monitor);

				try {
					Document pomDocument = AsposeMavenManager
							.getXmlDocument(pomFile.toString());

					// Get the root element
					Node projectNode = pomDocument.getFirstChild();
					// Adding Aspose Cloud Maven Repository configuration
					// setting here
					AsposeMavenManager.addAsposeMavenRepositoryConfiguration(
							pomDocument, projectNode);

					// Adding Dependencies here
					Element dependenciesTag = pomDocument
							.createElement("dependencies");
					projectNode.appendChild(dependenciesTag);

					for (Metadata dependency : AsposeMavenManager
							.getAsposeProjectMavenDependencies()) {

						AsposeMavenManager.addAsposeMavenDependency(
								pomDocument, dependenciesTag, dependency);

					}

					AsposeMavenManager.updatePOM(pomDocument, monitor, project);

				} catch (ParserConfigurationException | SAXException
						| IOException | TransformerConfigurationException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (TransformerException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				return Arrays.asList(project);
			}
		};
		job.addJobChangeListener(new JobChangeAdapter() {
			public void done(IJobChangeEvent event) {
				final IStatus result = event.getResult();
				if (!result.isOK()) {
					Display.getDefault().asyncExec(new Runnable() {
						public void run() {
							MessageDialog.openError(getShell(), //
									NLS.bind(Messages.wizardProjectJobFailed,
											projectName), result.getMessage());
						}
					});
				}

				MappingDiscoveryJob discoveryJob = new MappingDiscoveryJob(job
						.getCreatedProjects());
				discoveryJob.schedule();

			}
		});

		job.setRule(MavenPlugin.getProjectConfigurationManager().getRule());
		job.schedule();

		return true;
	}

	/**
	 * 
	 */
	@Override
	public void addPages() {
		super.addPages();

		_pageTwo = new AsposeNewMavenProjectWizardPageCustom(
				AsposeConstants.FIRST_PAGE_NAME);

		_pageTwo.setTitle(AsposeConstants.FIRST_PAGE_TITLE);
		_pageTwo.setDescription(AsposeConstants.FIRST_PAGE_DESCRIPTION);
		URL imageurl = getClass().getResource("/images/long_banner.PNG");
		ImageDescriptor image = ImageDescriptor.createFromURL(imageurl);
		_pageTwo.setImageDescriptor(image);

		_pageOne = new AsposeMavenProjectWizardArtifactPage(
				AsposeConstants.FIRST_PAGE_NAME);

		addPage(_pageOne);
		addPage(_pageTwo);

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
