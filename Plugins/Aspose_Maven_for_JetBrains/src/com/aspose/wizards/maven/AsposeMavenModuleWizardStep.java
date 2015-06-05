
package com.aspose.wizards.maven;

import com.aspose.utils.AsposeMavenDependenciesManager;
import com.aspose.utils.AsposeConstants;
import com.aspose.utils.execution.ModalTaskImpl;
import com.intellij.ide.util.projectWizard.ModuleWizardStep;
import com.intellij.ide.util.projectWizard.WizardContext;
import com.intellij.ide.wizard.CommitStepException;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.application.ModalityState;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.progress.ProgressManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AsposeMavenModuleWizardStep extends ModuleWizardStep {
    private final Project myProjectOrNull;
    private final AsposeMavenModuleBuilder myBuilder;
    private final WizardContext myContext;


    private JPanel myMainPanel;


    private JTextField myGroupIdField;

    private JTextField myArtifactIdField;
    private JTextField myVersionField;


    private JPanel myArchetypesPanel;


    public AsposeMavenModuleWizardStep(Project project, AsposeMavenModuleBuilder builder, WizardContext context, boolean includeArtifacts) {
        myProjectOrNull = project;
        myBuilder = builder;
        myContext = context;
        initComponents();
        loadSettings();
    }

    private void initComponents() {


        ActionListener updatingListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateComponents();
            }
        };

    }

    @Override
    public JComponent getPreferredFocusedComponent() {
        return myGroupIdField;
    }


    @Override
    public void onStepLeaving() {
        saveSettings();
    }

    private void loadSettings() {


    }

    private void saveSettings() {

    }


    public JComponent getComponent() {
        return myMainPanel;
    }

    @Override
    public boolean validate() throws ConfigurationException {
        if (StringUtil.isEmptyOrSpaces(myGroupIdField.getText())) {
            throw new ConfigurationException("Please, specify groupId");
        }

        if (StringUtil.isEmptyOrSpaces(myArtifactIdField.getText())) {
            throw new ConfigurationException("Please, specify artifactId");
        }

        if (StringUtil.isEmptyOrSpaces(myVersionField.getText())) {
            throw new ConfigurationException("Please, specify version");
        }

        return true;
    }


    private static void setTestIfEmpty(@NotNull JTextField artifactIdField, @Nullable String text) {
        if (StringUtil.isEmpty(artifactIdField.getText())) {
            artifactIdField.setText(StringUtil.notNullize(text));
        }
    }

    @Override
    public void updateStep() {


        MavenId projectId = myBuilder.getMyProjectId();

        if (projectId == null) {
            setTestIfEmpty(myArtifactIdField, myBuilder.getName());
            setTestIfEmpty(myGroupIdField, myBuilder.getName());
            setTestIfEmpty(myVersionField, "1.0-SNAPSHOT");
        } else {
            setTestIfEmpty(myArtifactIdField, projectId.getArtifactId());
            setTestIfEmpty(myGroupIdField, projectId.getGroupId());
            setTestIfEmpty(myVersionField, projectId.getVersion());
        }


        updateComponents();
    }


    private void updateComponents() {


        myGroupIdField.setEnabled(true);
        myVersionField.setEnabled(true);


    }


    @Override
    public void updateDataModel() {
        myContext.setProjectBuilder(myBuilder);


        myBuilder.setMyProjectId(new MavenId(myGroupIdField.getText(),
                myArtifactIdField.getText(),
                myVersionField.getText()));


        if (myContext.getProjectName() == null) {
            myContext.setProjectName(myBuilder.getMyProjectId().getArtifactId());
        }


    }


    @Override
    public String getHelpId() {
        return "reference.dialogs.new.project.fromScratch.maven";
    }

    @Override
    public void disposeUIResources() {

    }

    @Override
    public void onWizardFinished() throws CommitStepException {
        AsposeConstants.println("2. ================ Finish (AsposeMavenModuleWizardStep) ================");


        if (!AsposeMavenDependenciesManager.isInternetConnected()) {

            throw new CommitStepException(AsposeConstants.MAVEN_INTERNET_CONNECTION_REQUIRED_MESSAGE);
        }

        AsposeAPIManagerMavenCallback callback = new AsposeAPIManagerMavenCallback();

        final ModalTaskImpl modalTask = new ModalTaskImpl(myProjectOrNull, callback, "Retrieving Aspose Maven Dependencies");
        ApplicationManager.getApplication().invokeAndWait(new Runnable() {
            @Override
            public void run() {
                ProgressManager.getInstance().run(modalTask);
            }
        }, ModalityState.defaultModalityState());

        if (!modalTask.isDone()) {

            throw new CommitStepException(AsposeConstants.MAVEN_ARTIFACTS_RETRIEVE_FAIL);
        }


    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        myMainPanel = new JPanel();
        myMainPanel.setLayout(new GridLayoutManager(4, 3, new Insets(0, 0, 0, 0), -1, -1));
        final JLabel label1 = new JLabel();
        label1.setText("GroupId");
        myMainPanel.add(label1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("ArtifactId");
        myMainPanel.add(label2, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setText("Version");
        myMainPanel.add(label3, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        myGroupIdField = new JTextField();
        myMainPanel.add(myGroupIdField, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        myArtifactIdField = new JTextField();
        myMainPanel.add(myArtifactIdField, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        myVersionField = new JTextField();
        myMainPanel.add(myVersionField, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        myArchetypesPanel = new JPanel();
        myArchetypesPanel.setLayout(new BorderLayout(0, 0));
        myMainPanel.add(myArchetypesPanel, new GridConstraints(3, 0, 1, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return myMainPanel;
    }
}

