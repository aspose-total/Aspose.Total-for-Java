package com.aspose.ecplugin.wizard.file;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.eclipse.core.internal.resources.Project;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.internal.core.PackageFragmentRoot;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.dialogs.ContainerSelectionDialog;

import com.aspose.ecplugin.AsposeComponentsManager;
import com.aspose.ecplugin.AsposeJavaComponent;
import com.aspose.ecplugin.AsposeJavaComponents;
import com.aspose.ecplugin.AsposeConstants;
import com.aspose.ecplugin.examplesmodel.Data;
import com.aspose.ecplugin.examplesmodel.Example;
import com.aspose.ecplugin.examplesmodel.Examples;
import com.aspose.ecplugin.examplesmodel.Folder;
import com.aspose.ecplugin.examplesmodel.Folders;
import com.aspose.ecplugin.examplesmodel.ObjectFactory;
import com.aspose.utils.git.GitHelper;

/**
 * The "New" wizard page allows setting the container for the new file as well
 * as the file name. The page will only accept file name without the extension
 * OR with the extension that matches the expected one (java).
 */

public class NewFileWizardPage extends WizardPage {
	private Text containerText;
	private TreeItem[] selectedExample;

	public TreeItem[] getSelectedExample() {
		return selectedExample;
	}

	// private Text fileText;
	private Tree examplesTree;
	private Combo componentSelection;

	public Combo getComponentSelection() {
		return componentSelection;
	}

	private ISelection selection;
	private boolean examplesNotAvailable = true;

	/**
	 * Constructor for SampleNewWizardPage.
	 * 
	 * @param pageName
	 */
	public NewFileWizardPage(ISelection selection) {
		super("wizardPage");
		setTitle("Aspose New File");
		setDescription("This wizard creates a new file with *.java that contains sample for selected Java component.");
		this.selection = selection;
	}

	/**
	 * @see IDialogPage#createControl(Composite)
	 */
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);
		GridLayout layout = new GridLayout();
		container.setLayout(layout);
		layout.numColumns = 3;
		layout.verticalSpacing = 9;
		Label label = new Label(container, SWT.NULL);
		label.setText("&Project:");
		// row 1
		containerText = new Text(container, SWT.BORDER | SWT.SINGLE);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		containerText.setLayoutData(gd);
		containerText.setEditable(false);
		containerText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				dialogChanged();
			}
		});

		Button button = new Button(container, SWT.PUSH);
		button.setText("Browse...");
		button.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				handleBrowse();
			}
		});
		// row 2

		Label selectComponentLabel = new Label(container, SWT.NULL);
		selectComponentLabel.setText("&Java component:");

		componentSelection = new Combo(container, SWT.BORDER);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		componentSelection.setLayoutData(gd);
		componentSelection.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				AsposeJavaComponent component = AsposeJavaComponents.list
						.get(componentSelection.getText());
				checkAndUpdateRepo(component);
				dialogChanged();
			}
		});

		new Label(container, SWT.SINGLE);// skip cell
		// row 4

		new Label(container, SWT.SINGLE);// skip cell
		examplesTree = new Tree(container, SWT.BORDER);
		gd = new GridData(GridData.FILL_BOTH | GridData.GRAB_HORIZONTAL);
		examplesTree.setLayoutData(gd);
		examplesTree.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				selectedExample = examplesTree.getSelection();
				dialogChanged();
			}
		});

		initialize();
		dialogChanged();
		setControl(container);
	}

	/**
	 * 
	 * @param component
	 */
	private void checkAndUpdateRepo(final AsposeJavaComponent component) {
		if (null == component)
			return;
		if (null == component.get_remoteExamplesRepository()) {
			showMessage("Examples not available", component.get_name() + " - "
					+ AsposeConstants.EXAMPLES_NOT_AVAILABLE_MESSAGE,
					SWT.ICON_INFORMATION | SWT.OK);
			examplesNotAvailable = true;
			dialogChanged();
			return;
		} else {
			examplesNotAvailable = false;
			dialogChanged();
		}
		String repoPath;
		if (GitHelper.isExamplesDefinitionsPresent(component)) {
			ProgressMonitorDialog dialog = new ProgressMonitorDialog(null);
			try {
				dialog.run(true, true, new IRunnableWithProgress() {

					@Override
					public void run(IProgressMonitor monitor) {
						monitor.beginTask("Retrieving Examples...", 4);
						monitor.subTask("Downloading...");
						GitHelper.updateRepository(component, monitor);
						monitor.subTask("Populating...");
						Display.getDefault().syncExec(new Runnable() {
							@Override
							public void run() {
								try {

									populateExamplesTree(
											GitHelper
													.getExamplesDefinitionsPath(component),
											component.get_name());
								} catch (JAXBException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} 

							}

						});
						monitor.worked(1);
						monitor.done();
					}
				});
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		} else {
			int result = showMessage("Examples download required",
					component.get_name() + " - "
							+ AsposeConstants.EXAMPLES_DOWNLOAD_REQUIRED,
					SWT.ICON_INFORMATION | SWT.YES | SWT.NO);
			if (result == SWT.YES) {
				if (AsposeComponentsManager.isIneternetConnected()) {

					ProgressMonitorDialog dialog = new ProgressMonitorDialog(
							null);
					try {
						dialog.run(true, true, new IRunnableWithProgress() {

							@Override
							public void run(IProgressMonitor monitor) {
								monitor.beginTask("Retrieving Examples...", 4);
								monitor.subTask("Downloading...");
								GitHelper.updateRepository(component, monitor);
								monitor.subTask("Populating...");
								Display.getDefault().syncExec(new Runnable() {
									@Override
									public void run() {
										try {
											if (GitHelper
													.isExamplesDefinitionsPresent(component)) {
												populateExamplesTree(
														GitHelper
																.getExamplesDefinitionsPath(component),
														component.get_name());
											}
										} catch (JAXBException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										} 

									}

								});
								monitor.worked(1);
								monitor.done();
							}
						});
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

				} else
					showMessage(
							AsposeConstants.INTERNET_CONNECTION_REQUIRED_MESSAGE_TITLE,
							component.get_name()
									+ " - "
									+ AsposeConstants.EXAMPLES_INTERNET_CONNECTION_REQUIRED_MESSAGE,
							SWT.OK);
			}
		}
	}

	/**
	 * 
	 * @param examplesDefinitionFile
	 * @param componentName
	 * @throws JAXBException
	 */
	private void populateExamplesTree(String examplesDefinitionFile,
			String componentName) throws JAXBException {
		JAXBContext jaxbContext = JAXBContext.newInstance(ObjectFactory.class);
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
		Data data = (Data) unmarshaller.unmarshal(new File(
				examplesDefinitionFile));
		examplesTree.removeAll();
		TreeItem treeItem = new TreeItem(examplesTree, 0);
		treeItem.setText(componentName);
		treeItem.setData("Path", "src");
		List<Folders> rootFoldersList = (List<Folders>) data.getFolders();
		parseFoldersTree(rootFoldersList, treeItem);
		parseExamples(data.getExamples(), treeItem);
		treeItem.setExpanded(true);
	}

	/**
	 * 
	 * @param rootFoldersList
	 * @param parentItem
	 */
	void parseFoldersTree(List<Folders> rootFoldersList, TreeItem parentItem) {
		for (Folders folders : rootFoldersList) {
			// Get list of folder
			List<Folder> folderList = folders.getFolder();
			for (Folder folder : folderList) {
				TreeItem subTreeItem = new TreeItem(parentItem, SWT.NONE);
				subTreeItem.setText(folder.getTitle());
				subTreeItem.setData("Path", parentItem.getData("Path")
						+ File.separator + folder.getFolderName());
				URL imageurl = getClass()
						.getResource("/images/folder-icon.png");
				ImageDescriptor image = ImageDescriptor.createFromURL(imageurl);
				subTreeItem.setImage(image.createImage());
				parseExamples(folder.getExamples(), subTreeItem);
				parseFoldersTree(folder.getFolders(), subTreeItem);
				subTreeItem.setExpanded(true);
			}
		}
	}

	/**
	 * 
	 * @param examples
	 * @param parentItem
	 */
	void parseExamples(List<Examples> examplesList, TreeItem parentItem) {
		//
		for (Examples examples : examplesList) {
			List<Example> exampleList = ((Examples) examples).getExample();
			for (Example example : exampleList) {
				// false: do not run
				parseExample(example, parentItem);
			}
		}
	}

	/**
	 * 
	 * @param example
	 * @param parentItem
	 */
	void parseExample(Example example, TreeItem parentItem) {
		TreeItem subTreeItem = new TreeItem(parentItem, SWT.NONE);
		subTreeItem.setText(example.getTitle());

		subTreeItem.setData("Example", example);
		subTreeItem.setData("Path", parentItem.getData("Path") + File.separator
				+ example.getFolderName());

		URL imageurl = getClass().getResource("/images/java-icon.png");
		ImageDescriptor image = ImageDescriptor.createFromURL(imageurl);
		subTreeItem.setImage(image.createImage());
	}

	/**
	 * 
	 * @param message
	 */
	void printConsole(String message) {
		System.out.print(message);
	}

	/**
	 * Tests if the current workbench selection is a suitable container to use.
	 */
	private void initialize() {
		Project project = null;
		if (selection != null && selection.isEmpty() == false
				&& selection instanceof IStructuredSelection) {
			IStructuredSelection ssel = (IStructuredSelection) selection;
			if (ssel.size() > 1)
				return;
			Object obj = ssel.getFirstElement();
			String type = obj.getClass().getName();
			if (obj instanceof Project) {
				project = (Project) obj;
				IPath path = project.getFullPath();// project.getLocation();
				String pathstr = path.toOSString();
				containerText.setText(path.toOSString());
			} else if (obj instanceof PackageFragmentRoot) {
				PackageFragmentRoot frag = (PackageFragmentRoot) obj;
				IPath path = frag.getPath();
				containerText.setText(path.toOSString());
				Object element = ((IStructuredSelection) selection)
						.getFirstElement();
				IJavaProject jProject = ((PackageFragmentRoot) element)
						.getJavaProject();
				project = (Project) jProject.getProject();

			} else if (obj instanceof IJavaElement) {
				IJavaProject jProject = ((IJavaElement) obj).getJavaProject();
				project = (Project) jProject.getProject();
				IPath path = project.getFullPath();// project.getLocation();
				String pathstr = path.toOSString();
				containerText.setText(path.toOSString());
			}
		}
		fillComboWithComponents(project);
	}

	/**
	 * 
	 * @param project
	 */
	private void fillComboWithComponents(Project project) {
		if (null != project) {
			if (AsposeJavaComponents.list.size() == 0) {
				AsposeJavaComponents components = new AsposeJavaComponents();
			}
			componentSelection.removeAll();
			for (AsposeJavaComponent component : AsposeJavaComponents.list
					.values()) {
				if (null != project.findMember("/lib/" + component.get_name())) {
					componentSelection.add(component.get_name());
				}
			}
		}
	}

	/**
	 * 
	 * @param title
	 * @param message
	 * @param style
	 * @return
	 */
	public int showMessage(String title, String message, int style) {
		MessageBox msgBox = new MessageBox(getShell(), style/*
															 * SWT.ICON_WARNING
															 * | SWT.YES |
															 * SWT.NO |
															 * SWT.CANCEL
															 */);
		msgBox.setMessage(message);
		msgBox.setText(title);
		return msgBox.open();
	}

	/**
	 * Uses the standard container selection dialog to choose the new value for
	 * the container field.
	 */

	private void handleBrowse() {
		ContainerSelectionDialog dialog = new ContainerSelectionDialog(
				getShell(), ResourcesPlugin.getWorkspace().getRoot(), false,
				"Select project");
		if (dialog.open() == ContainerSelectionDialog.OK) {
			Object[] result = dialog.getResult();
			if (result.length == 1) {
				containerText.setText(((Path) result[0]).toString());
				updateProjectPathDep(containerText.getText());
			}
		}
	}

	/**
	 * 
	 * @param selecteProjectPath
	 */
	private void updateProjectPathDep(String selecteProjectPath) {
		try {
			String[] tokens;
			if (selecteProjectPath.indexOf("/") == -1)
				tokens = selecteProjectPath.split("\\");
			else
				tokens = selecteProjectPath.split("/");

			IProject newProject = ResourcesPlugin.getWorkspace().getRoot()
					.getProject(tokens[1]);
			fillComboWithComponents((Project) newProject);
		} catch (Exception ex) {

		}
	}

	/**
	 * Ensures that both text fields are set.
	 */

	private void dialogChanged() {
		IResource container = ResourcesPlugin.getWorkspace().getRoot()
				.findMember(new Path(getContainerName()));

		if (getContainerName().length() == 0) {
			updateStatus("File container must be specified");
			return;
		}
		if (container == null
				|| (container.getType() & (IResource.PROJECT | IResource.FOLDER)) == 0) {
			updateStatus("File container must exist");
			return;
		}

		if (!container.isAccessible()) {
			updateStatus("Project must be writable");
			return;
		}

		if (!exampleSelected()) {
			updateStatus("Example must be selected");
			return;
		}
		if (componentSelection.getSelectionIndex() == -1) {
			updateStatus("Aspose component must be selected");
			return;
		}

		if (examplesNotAvailable) {
			updateStatus(AsposeConstants.EXAMPLES_NOT_AVAILABLE_MESSAGE);
			return;
		}

		updateStatus(null);
	}

	/**
	 * 
	 * @param message
	 */
	private void updateStatus(String message) {
		setErrorMessage(message);
		setPageComplete(message == null);
	}

	/**
	 * 
	 * @return
	 */
	public String getContainerName() {
		return containerText.getText();
	}

	private boolean exampleSelected() {
		if (null != selectedExample && null != selectedExample[0]
				&& null != selectedExample[0].getData("Example"))
			return true;
		return false;
	}

	public String getComponentName() {

		System.out.println(componentSelection.getText());
		return componentSelection.getText();
	}
}