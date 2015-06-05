

package com.aspose.eclipse.maven.wizard;

import java.util.Collection;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.m2e.core.internal.lifecyclemapping.discovery.LifecycleMappingDiscoveryRequest;
import org.eclipse.m2e.core.ui.internal.wizards.LifecycleMappingDiscoveryHelper;
import org.eclipse.m2e.core.ui.internal.wizards.MavenDiscoveryProposalWizard;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;

public class MappingDiscoveryJob extends WorkspaceJob {

	private Collection<IProject> projects;

	public MappingDiscoveryJob(Collection<IProject> projects) {
		super("Discover lifecycle mappings");
		this.projects = projects;

	}

	public IStatus runInWorkspace(IProgressMonitor monitor)
			throws CoreException {
		// Detect and resolve Lifecycle Mapping issues
		LifecycleMappingDiscoveryRequest discoveryRequest = LifecycleMappingDiscoveryHelper
				.createLifecycleMappingDiscoveryRequest(projects, monitor);
		if (discoveryRequest.isMappingComplete()) {
			return Status.OK_STATUS;
		}
		// Some errors were detected
		discoverProposals(discoveryRequest, monitor);

		openProposalWizard(projects, discoveryRequest);

		return Status.OK_STATUS;
	}

	protected void discoverProposals(
			LifecycleMappingDiscoveryRequest discoveryRequest,
			IProgressMonitor monitor) throws CoreException {
		// LifecycleMappingHelper will discover proposals only if discovery
		// service is available
		LifecycleMappingDiscoveryHelper.discoverProposals(discoveryRequest,
				monitor);
	}

	protected void openProposalWizard(Collection<IProject> projects,
			LifecycleMappingDiscoveryRequest discoveryRequest) {

		final MavenDiscoveryProposalWizard proposalWizard = new MavenDiscoveryProposalWizard(
				projects, discoveryRequest);
		proposalWizard.init(null, null);

		Display.getDefault().asyncExec(new Runnable() {
			@Override
			public void run() {
				final IWorkbench workbench = PlatformUI.getWorkbench();
				WizardDialog dialog = new WizardDialog(workbench
						.getActiveWorkbenchWindow().getShell(), proposalWizard);
				dialog.open();
			}
		});
	}

}
