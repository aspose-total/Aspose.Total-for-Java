package com.aspose.ecplugin.wizard.file;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.core.runtime.*;
import org.eclipse.jface.operation.*;
import org.eclipse.jface.resource.ImageDescriptor;

import java.lang.reflect.InvocationTargetException;
import java.net.URL;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.core.resources.*;
import org.eclipse.core.runtime.CoreException;

import java.io.*;

import org.eclipse.ui.*;

import com.aspose.ecplugin.AsposeJavaComponent;
import com.aspose.ecplugin.AsposeJavaComponents;
import com.aspose.ecplugin.examplesmodel.Example;
import com.aspose.utils.git.*;

/**
 * This is a sample new wizard. Its role is to create a new file 
 * resource in the provided container. If the container resource
 * (a folder or a project) is selected in the workspace 
 * when the wizard is opened, it will accept it as the target
 * container. The wizard creates one file with the extension
 * "java". If a sample multi-page editor (also available
 * as a template) is registered for the same extension, it will
 * be able to open it.
 */

public class NewFileWizard extends Wizard implements INewWizard {
	private  NewFileWizardPage page;
	private ISelection selection;

	/**
	 * Constructor for NewFileWizard.
	 */
	public NewFileWizard() {
		super();
		setNeedsProgressMonitor(true);
		setWindowTitle("Aspose new file");
	}

	/**
	 * Adding the page to the wizard.
	 */

	public void addPages() {
		page = new NewFileWizardPage(selection);
		URL imageurl = getClass().getResource("/images/long_banner.PNG");
		ImageDescriptor image = ImageDescriptor.createFromURL(imageurl);
		page.setImageDescriptor(image);

		addPage(page);
	}

	/**
	 * This method is called when 'Finish' button is pressed in
	 * the wizard. We will create an operation and run it
	 * using wizard as execution context.
	 */
	public boolean performFinish() {
		final String containerName = page.getContainerName();
		final String selectedComponent=page.getComponentSelection().getText();
		final Example selectedExample =(Example) page.getSelectedExample()[0].getData("Example");
		final String examplePath = (String) page.getSelectedExample()[0].getData("Path");

		IRunnableWithProgress op = new IRunnableWithProgress() {
			public void run(IProgressMonitor monitor) throws InvocationTargetException {
				try {
					doFinish(containerName,examplePath,selectedComponent, selectedExample, monitor);
				} catch (CoreException e) {
					throw new InvocationTargetException(e);
				} finally {
					monitor.done();
				}
			}
		};
		try {
			getContainer().run(true, false, op);

		} catch (InterruptedException e) {
			return false;
		} catch (InvocationTargetException e) {
			Throwable realException = e.getTargetException();
			MessageDialog.openError(getShell(), "Error", realException.getMessage());
			return false;
		}
		return true;
	}

	/**
	 * The worker method. It will find the container, create the
	 * file if missing or just replace its contents, and open
	 * the editor on the newly created file.
	 */

	private void doFinish(
			String containerName,
			String folderPath,
			String selectedComponent,
			Example selectedExample,
			IProgressMonitor monitor)
					throws CoreException {

		// create a sample file
		monitor.beginTask("Creating " , 2);
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		IResource resource = root.findMember(new Path(containerName));

		String workspacePath = ResourcesPlugin.getWorkspace().getRoot().getLocation().toString();//resource.getRawLocation();//.makeAbsolute().toOSString();
		if (!resource.exists() || !(resource instanceof IContainer)) {
			throwCoreException("Container \"" + containerName + "\" does not exist.");
		}
		IContainer container = (IContainer) resource;
		IProject project = container.getProject();
		AsposeJavaComponent  component= AsposeJavaComponents.list.get(selectedComponent);

		String localRepPath = GitHelper.getLocalRepositoryPath(component);
		copyFilesAsResouces(project,workspacePath,localRepPath + File.separator + folderPath,folderPath);
		monitor.worked(1);
		monitor.worked(1);
	}

	/**
	 * 
	 * @param project
	 * @param workspacePath
	 * @param rootPath
	 * @param path
	 */
	private static void copyFilesAsResouces(IProject project, String workspacePath, String rootPath, String path ) {

		path = path.toLowerCase();
		File root = new File( rootPath );
		File[] list = root.listFiles();
		if(root.isDirectory())
			createResourceFolder(project, workspacePath, path);

		for ( File f : list ) {
			if ( f.isDirectory() ) {
				copyFilesAsResouces( project, workspacePath, f.getAbsolutePath(), path+ File.separator + f.getName() );
			}
			else {
				File file = new File(f.getAbsolutePath());
				copyAsResource(project,file.getAbsolutePath(),  path  + File.separator+ file.getName()/*projectLib*/);
			}
		}
	}
	private static void createResourceFolder(IProject project,String workspacePath, String path)
	{
		IPath projectPath = project.getFullPath();
		File file = new File(workspacePath +File.separator + projectPath.toOSString() + File.separator + path);
		if(!file.exists())
			file.mkdirs();

		IFolder folder = project.getFolder(path);
		IPath ipath = folder.getFullPath();
		String[] pathSeg = ipath.segments();


		for(int i=0;i<pathSeg.length;i++)
		{
			boolean passed = false;
			String path2="";
			for(int j=1;j<i;j++)
			{
				path2= path2+File.separator + pathSeg[j];
				passed = true;
			}
			if(passed)
			{
				IFolder folder2 = project.getFolder(path2);
				if(!folder2.exists())
					try {
						folder2.create(true, true, null);
					} catch (CoreException e) {
						e.printStackTrace();
					}
			}

		}
		if(!folder.exists())
			try {
				folder.create(true, true, null);
			} catch (CoreException e) {
				e.printStackTrace();
			}
	}

	/**
	 * 
	 * @param project
	 * @param srcPath
	 * @param destPath
	 * @return
	 */
	private static boolean copyAsResource(IProject project, String srcPath, String destPath)
	{
		try {
			InputStream is = new BufferedInputStream(new FileInputStream(srcPath));
			IFile file = project.getFile(destPath);
			if(!file.exists())
				file.create(is, false, null);
			return true;
		} catch (CoreException e) {
			e.printStackTrace();
			return false;
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 
	 * @param message
	 * @throws CoreException
	 */
	private void throwCoreException(String message) throws CoreException {
		IStatus status =
				new Status(IStatus.ERROR, "com.aspose.ecplugin", IStatus.OK, message, null);
		throw new CoreException(status);
	}

	/**
	 * We will accept the selection in the workbench to see if
	 * we can initialize from it.
	 * @see IWorkbenchWizard#init(IWorkbench, IStructuredSelection)
	 */
	public void init(IWorkbench workbench, IStructuredSelection selection) {

		this.selection = selection;

	}
}