

package com.aspose.eclipse.maven.wizard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.maven.model.Model;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.dialogs.DialogSettings;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.m2e.core.internal.MavenPluginActivator;
import org.eclipse.m2e.core.ui.internal.M2EUIPluginActivator;
import org.eclipse.m2e.core.ui.internal.Messages;
import org.eclipse.m2e.core.ui.internal.wizards.ResolverConfigurationComponent;
import org.eclipse.m2e.core.ui.internal.wizards.WidthGroup;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;

/**
 * Wizard page responsible for gathering information about the Maven2 artifact
 * and the directories to create. This wizard page gathers Maven2 specific
 * information. The user must specify the necessary information about the Maven2
 * artifact and she can also decide which directories of the default Maven2
 * directory structure should be created. Input validation is performed in order
 * to make sure that all the provided information is valid before letting the
 * wizard continue.
 */
public class AsposeMavenProjectWizardArtifactPage extends WizardPage {
	/** the history limit */
	protected static final int MAX_HISTORY = 15;

	private boolean enableNext = false;

	/** The resolver configuration panel */
	protected ResolverConfigurationComponent resolverConfigurationComponent;

	/** dialog settings to store input history */
	protected IDialogSettings dialogSettings;

	/** the Map of field ids to List of comboboxes that share the same history */
	private Map<String, List<Combo>> fieldsWithHistory;

	private boolean isHistoryLoaded = false;

	/** @wbp.parser.constructor */
	protected AsposeMavenProjectWizardArtifactPage(String pageName) {
		super(pageName);
		fieldsWithHistory = new HashMap<String, List<Combo>>();

		initDialogSettings();

		setTitle(Messages.wizardProjectPageMaven2Title);
		setDescription(Messages.wizardProjectPageMaven2Description);
		setPageComplete(false);
	}

	/** Loads the advanced settings data when the page is displayed. */
	public void setVisible(boolean visible) {
		if (visible) {
			if (!isHistoryLoaded) {
				// load data before history kicks in
				if (resolverConfigurationComponent != null) {
					resolverConfigurationComponent.loadData();
				}
				loadInputHistory();
				isHistoryLoaded = true;
			} else {
				saveInputHistory();
			}
			if (resolverConfigurationComponent != null) {
				resolverConfigurationComponent.loadData();
			}
		}
		super.setVisible(visible);
		artifactComponent.getGroupIdCombo().setFocus();

	}

	/** Saves the history when the page is disposed. */
	public void dispose() {
		saveInputHistory();
		super.dispose();
	}

	/** Loads the dialog settings using the page name as a section name. */
	private void initDialogSettings() {
		IDialogSettings pluginSettings;

		// This is strictly to get SWT Designer working locally without blowing
		// up.
		if (MavenPluginActivator.getDefault() == null) {
			pluginSettings = new DialogSettings("Workbench");
		} else {
			pluginSettings = M2EUIPluginActivator.getDefault()
					.getDialogSettings();
		}

		dialogSettings = pluginSettings.getSection(getName());
		if (dialogSettings == null) {
			dialogSettings = pluginSettings.addNewSection(getName());
			pluginSettings.addSection(dialogSettings);
		}
	}

	@Override
	public boolean canFlipToNextPage() {
		return enableNext;
	}

	/** Loads the input history from the dialog settings. */
	private void loadInputHistory() {
		for (Map.Entry<String, List<Combo>> e : fieldsWithHistory.entrySet()) {
			String id = e.getKey();
			String[] items = dialogSettings.getArray(id);
			if (items != null) {
				for (Combo combo : e.getValue()) {
					String text = combo.getText();
					combo.setItems(items);
					if (text.length() > 0) {
						// setItems() clears the text input, so we need to
						// restore it
						combo.setText(text);
					}
				}
			}
		}
	}

	/** Saves the input history into the dialog settings. */
	private void saveInputHistory() {
		for (Map.Entry<String, List<Combo>> e : fieldsWithHistory.entrySet()) {
			String id = e.getKey();

			Set<String> history = new LinkedHashSet<String>(MAX_HISTORY);

			for (Combo combo : e.getValue()) {
				String lastValue = combo.getText();
				if (lastValue != null && lastValue.trim().length() > 0) {
					history.add(lastValue);
				}
			}

			Combo combo = e.getValue().iterator().next();
			String[] items = combo.getItems();
			for (int j = 0; j < items.length && history.size() < MAX_HISTORY; j++) {
				history.add(items[j]);
			}

			dialogSettings.put(id, history.toArray(new String[history.size()]));
		}
	}

	/** Adds an input control to the list of fields to save. */
	protected void addFieldWithHistory(String id, Combo combo) {
		if (combo != null) {
			List<Combo> combos = fieldsWithHistory.get(id);
			if (combos == null) {
				combos = new ArrayList<Combo>();
				fieldsWithHistory.put(id, combos);
			}
			combos.add(combo);
		}
	}

	protected String validateArtifactIdInput(String text) {
		return validateIdInput(text, true);
	}

	protected String validateGroupIdInput(String text) {
		return validateIdInput(text, false);
	}

	private String validateIdInput(String text, boolean artifact) {
		if (text == null || text.length() == 0) {
			return artifact ? Messages.wizardProjectPageMaven2ValidatorArtifactID
					: Messages.wizardProjectPageMaven2ValidatorGroupID;
		}

		if (text.contains(" ")) { //$NON-NLS-1$
			return artifact ? Messages.wizardProjectPageMaven2ValidatorArtifactIDnospaces
					: Messages.wizardProjectPageMaven2ValidatorGroupIDnospaces;
		}

		IStatus nameStatus = ResourcesPlugin.getWorkspace().validateName(text,
				IResource.PROJECT);
		if (!nameStatus.isOK()) {
			return NLS
					.bind(artifact ? Messages.wizardProjectPageMaven2ValidatorArtifactIDinvalid
							: Messages.wizardProjectPageMaven2ValidatorGroupIDinvalid,
							nameStatus.getMessage());
		}

		if (!text.matches("[A-Za-z0-9_\\-.]+")) { //$NON-NLS-1$
			return NLS
					.bind(artifact ? Messages.wizardProjectPageMaven2ValidatorArtifactIDinvalid
							: Messages.wizardProjectPageMaven2ValidatorGroupIDinvalid,
							text);
		}

		return null;
	}

	private static final ProjectFolder JAVA = new ProjectFolder(
			"src/main/java", "target/classes"); //$NON-NLS-1$ //$NON-NLS-2$

	private static final ProjectFolder JAVA_TEST = new ProjectFolder(
			"src/test/java", "target/test-classes"); //$NON-NLS-1$ //$NON-NLS-2$

	private static final ProjectFolder RESOURCES = new ProjectFolder(
			"src/main/resources", "target/classes"); //$NON-NLS-1$ //$NON-NLS-2$

	private static final ProjectFolder RESOURCES_TEST = new ProjectFolder(
			"src/test/resources", "target/test-classes"); //$NON-NLS-1$ //$NON-NLS-2$

	private static final ProjectFolder WEBAPP = new ProjectFolder(
			"src/main/webapp", null); //$NON-NLS-1$

	private static final ProjectFolder EAR = new ProjectFolder(
			"src/main/application", null); //$NON-NLS-1$

	private static final ProjectFolder SITE = new ProjectFolder(
			"src/site", null); //$NON-NLS-1$

	private static final ProjectFolder[] JAR_DIRS = { JAVA, JAVA_TEST,
			RESOURCES, RESOURCES_TEST };

	private static final ProjectFolder[] WAR_DIRS = { JAVA, JAVA_TEST,
			RESOURCES, RESOURCES_TEST, WEBAPP };

	private static final ProjectFolder[] EAR_DIRS = { EAR }; // MNGECLIPSE-688
																// add EAR
																// Support

	private static final ProjectFolder[] POM_DIRS = { SITE };

	/** special directory sets, default is JAR_DIRS */
	private static final Map<String, ProjectFolder[]> directorySets = new HashMap<String, ProjectFolder[]>();
	static {
		directorySets.put(AsposeMavenArtifactComponent.WAR, WAR_DIRS);
		directorySets.put(AsposeMavenArtifactComponent.POM, POM_DIRS);
		directorySets.put(AsposeMavenArtifactComponent.EAR, EAR_DIRS); // MNGECLIPSE-688
																		// add
																		// EAR
																		// Support
	}

	/**
	 * Returns the Maven2 model containing the artifact information provided by
	 * the user.
	 * 
	 * @return The Maven2 model containing the provided artifact information. Is
	 *         never <code>null</code>.
	 */
	public Model getModel() {
		Model model = artifactComponent.getModel();

		return model;
	}

	/** artifact property panel */
	protected AsposeMavenArtifactComponent artifactComponent;

	private boolean isUsed;

	/**
	 * {@inheritDoc} This wizard page contains a
	 * <code>MavenArtifactComponent</code> to gather information about the Maven
	 * artifact and a <code>Maven2DirectoriesComponent</code> which allows to
	 * choose which directories of the default Maven directory structure to
	 * create.
	 */
	public void createControl(Composite parent) {
		GridLayout layout = new GridLayout();
		layout.marginWidth = 0;
		layout.marginHeight = 0;

		Composite container = new Composite(parent, SWT.NULL);
		container.setLayout(layout);

		WidthGroup widthGroup = new WidthGroup();
		container.addControlListener(widthGroup);

		ModifyListener modifyingListener = new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				validate();
			}
		};

		artifactComponent = new AsposeMavenArtifactComponent(container,
				SWT.NONE);
		artifactComponent.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,
				true));
		artifactComponent.setWidthGroup(widthGroup);
		artifactComponent.setModifyingListener(modifyingListener);

		addFieldWithHistory("groupId", artifactComponent.getGroupIdCombo()); //$NON-NLS-1$
		addFieldWithHistory(
				"artifactId", artifactComponent.getArtifactIdCombo()); //$NON-NLS-1$
		addFieldWithHistory("version", artifactComponent.getVersionCombo()); //$NON-NLS-1$
		addFieldWithHistory("name", artifactComponent.getNameCombo()); //$NON-NLS-1$

		container.layout();

		validate();

		setControl(container);
	}

	/** Returns the list of directories for the currently selected packaging. */
	private ProjectFolder[] getProjectFolders() {
		ProjectFolder[] folders = directorySets.get(artifactComponent
				.getPackaging());
		return folders == null ? JAR_DIRS : folders;
	}

	/**
	 * Returns the directories of the default Maven2 directory structure
	 * selected by the user. These directories should be created along with the
	 * new project.
	 * 
	 * @return The Maven2 directories selected by the user. Neither the array
	 *         nor any of its elements is <code>null</code> .
	 */
	public String[] getFolders() {
		ProjectFolder[] mavenDirectories = getProjectFolders();

		String[] directories = new String[mavenDirectories.length];
		for (int i = 0; i < directories.length; i++) {
			directories[i] = mavenDirectories[i].getPath();
		}

		return directories;
	}

	/**
	 * Validates the contents of this wizard page.
	 * <p>
	 * Feedback about the validation is given to the user by displaying error
	 * messages or informative messages on the wizard page. Depending on the
	 * provided user input, the wizard page is marked as being complete or not.
	 * <p>
	 * If some error or missing input is detected in the user input, an error
	 * message or informative message, respectively, is displayed to the user.
	 * If the user input is complete and correct, the wizard page is marked as
	 * begin complete to allow the wizard to proceed. To that end, the following
	 * conditions must be met:
	 * <ul>
	 * <li>The user must have provided a group ID.</li>
	 * <li>The user must have provided an artifact ID.</li>
	 * <li>The user must have provided a version for the artifact.</li>
	 * <li>The user must have provided the packaging type for the artifact.</li>
	 * </ul>
	 * </p>
	 * 
	 * @see org.eclipse.jface.dialogs.DialogPage#setMessage(java.lang.String)
	 * @see org.eclipse.jface.wizard.WizardPage#setErrorMessage(java.lang.String)
	 * @see org.eclipse.jface.wizard.WizardPage#setPageComplete(boolean)
	 */
	void validate() {
		String error = validateInput();
		setErrorMessage(error);
		setPageComplete(error == null);
		enableNext = error == null;
		canFlipToNextPage();
		getWizard().getContainer().updateButtons();
	}

	private String validateInput() {
		String error = validateGroupIdInput(artifactComponent.getGroupId()
				.trim());
		if (error != null) {
			return error;
		}

		error = validateArtifactIdInput(artifactComponent.getArtifactId()
				.trim());
		if (error != null) {
			return error;
		}

		if (artifactComponent.getVersion().trim().length() == 0) {
			return Messages.wizardProjectPageMaven2ValidatorVersion;
		}

		if (artifactComponent.getPackaging().trim().length() == 0) {
			return Messages.wizardProjectPageMaven2ValidatorPackaging;
		}

		return null;
	}

	/**
	 * Updates the properties when a project name is set on the first page of
	 * the wizard.
	 */
	public void setProjectName(String projectName) {
		if (artifactComponent.getArtifactId().equals(
				artifactComponent.getGroupId())) {
			artifactComponent.setGroupId(projectName);
		}
		artifactComponent.setArtifactId(projectName);
		validate();
	}

	public void setUsed(boolean isUsed) {
		this.isUsed = isUsed;
	}

	public boolean isPageComplete() {
		return !isUsed || super.isPageComplete();
	}

	/**
	 * A placeholder representing a directory in the project structure.
	 */
	final static class ProjectFolder {

		/** Folder path */
		private String path = null;

		/** Output path */
		private String outputPath = null;

		ProjectFolder(String path, String outputPath) {
			this.path = path;
			this.outputPath = outputPath;
		}

		/**
		 * Returns folder path
		 */
		String getPath() {
			return path;
		}

		/**
		 * Returns folder output path
		 */
		String getOutputPath() {
			return outputPath;
		}

		/**
		 * Returns true for source folder
		 */
		boolean isSourceEntry() {
			return this.getOutputPath() != null;
		}

	}

}
