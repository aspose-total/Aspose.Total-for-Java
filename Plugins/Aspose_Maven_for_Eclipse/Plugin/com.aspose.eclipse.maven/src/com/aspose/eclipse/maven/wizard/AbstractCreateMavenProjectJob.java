

package com.aspose.eclipse.maven.wizard;

import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.m2e.core.ui.internal.actions.OpenMavenConsoleAction;
import org.eclipse.m2e.core.ui.internal.wizards.AbstractCreateMavenProjectsOperation;
import org.eclipse.ui.IWorkingSet;
import org.eclipse.ui.progress.IProgressConstants;

public abstract class AbstractCreateMavenProjectJob extends WorkspaceJob {

	private final List<IWorkingSet> workingSets;

	private List<IProject> createdProjects;

	public AbstractCreateMavenProjectJob(String name,
			List<IWorkingSet> workingSets) {
		super(name);
		this.workingSets = workingSets;
	}

	@Override
	public final IStatus runInWorkspace(IProgressMonitor monitor)
			throws CoreException {
		setProperty(IProgressConstants.ACTION_PROPERTY,
				new OpenMavenConsoleAction());
		createdProjects = null;
		AbstractCreateMavenProjectsOperation op = new AbstractCreateMavenProjectsOperation(
				workingSets) {
			@Override
			protected List<IProject> doCreateMavenProjects(
					IProgressMonitor monitor) throws CoreException {
				return AbstractCreateMavenProjectJob.this
						.doCreateMavenProjects(monitor);
			}
		};
		try {
			op.run(monitor);
			List<IProject> projects = op.getCreatedProjects();
			if (projects != null) {
				createdProjects = Collections.unmodifiableList(projects);
			}
		} catch (InvocationTargetException e) {
			return AbstractCreateMavenProjectsOperation.toStatus(e);
		} catch (InterruptedException e) {
			return Status.CANCEL_STATUS;
		}
		return Status.OK_STATUS;
	}

	protected abstract List<IProject> doCreateMavenProjects(
			IProgressMonitor monitor) throws CoreException;

	/**
	 * @return an unmodifiable list of created projects, or <code>null</code>
	 * @since 1.6
	 */
	public List<IProject> getCreatedProjects() {
		return createdProjects;
	}
}
