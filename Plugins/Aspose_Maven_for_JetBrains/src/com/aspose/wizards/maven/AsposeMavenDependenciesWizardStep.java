
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
package com.aspose.wizards.maven;

import com.aspose.utils.AsposeConstants;
import com.aspose.utils.AsposeMavenProject;
import com.intellij.ide.util.projectWizard.ModuleWizardStep;
import com.intellij.ide.util.projectWizard.WizardContext;
import com.intellij.ide.wizard.CommitStepException;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.util.IconLoader;

import javax.swing.*;


public class AsposeMavenDependenciesWizardStep extends ModuleWizardStep {
    private static final Icon WIZARD_ICON = IconLoader.getIcon("/resources/long_bannerIntelliJ.png");


    private final AsposeMavenModuleBuilder myBuilder;
    private final WizardContext myContext;
    private AsposeMavenWizardPanel myMainPanel;


    public AsposeMavenDependenciesWizardStep(AsposeMavenModuleBuilder builder, WizardContext context) {
        myBuilder = builder;
        myContext = context;
    }


    @Override
    public JComponent getPreferredFocusedComponent() {
        return myMainPanel;
    }


    @Override
    public void onWizardFinished() throws CommitStepException {

        AsposeConstants.println("2. ================ Finish (AsposeMAvenDependenciesWizardStep) ================");
        new AsposeMavenProject();
        updateComponentsSelectionList();

    }

    @Override
    public void disposeUIResources() {
        super.disposeUIResources();
    }


    void updateComponentsSelectionList() {

        AsposeConstants.println("2. ================ updateComponentsSelectionList (Aspose Maven) ================");
        AsposeMavenProject.clearSelection();
        if (getComponent().getjCheckBoxAsposeCells().isSelected()) {
            AsposeMavenProject.getApiList().get(AsposeConstants.ASPOSE_CELLS).set_selected(true);
        }

        if (getComponent().getjCheckBoxAsposeWords().isSelected()) {
            AsposeMavenProject.getApiList().get(AsposeConstants.ASPOSE_WORDS).set_selected(true);
        }

        if (getComponent().getjCheckBoxAsposePdf().isSelected()) {
            AsposeMavenProject.getApiList().get(AsposeConstants.ASPOSE_PDF).set_selected(true);
        }

        if (getComponent().getjCheckBoxAsposeSlides().isSelected()) {
            AsposeMavenProject.getApiList().get(AsposeConstants.ASPOSE_SLIDES).set_selected(true);
        }

        if (getComponent().getjCheckBoxAsposeBarCode().isSelected()) {
            AsposeMavenProject.getApiList().get(AsposeConstants.ASPOSE_BARCODE).set_selected(true);
        }

        if (getComponent().getjCheckBoxAsposeTasks().isSelected()) {
            AsposeMavenProject.getApiList().get(AsposeConstants.ASPOSE_TASKS).set_selected(true);
        }

        if (getComponent().getjCheckBoxAsposeEmail().isSelected()) {
            AsposeMavenProject.getApiList().get(AsposeConstants.ASPOSE_EMAIL).set_selected(true);
        }

        if (getComponent().getjCheckBoxAsposeOCR().isSelected()) {
            AsposeMavenProject.getApiList().get(AsposeConstants.ASPOSE_OCR).set_selected(true);
        }

        if (getComponent().getjCheckBoxAsposeImaging().isSelected()) {
            AsposeMavenProject.getApiList().get(AsposeConstants.ASPOSE_IMAGING).set_selected(true);
        }

        if (getComponent().getjCheckBoxAsposeDiagram().isSelected()) {
            AsposeMavenProject.getApiList().get(AsposeConstants.ASPOSE_DIAGRAM).set_selected(true);
        }

    }


    @Override
    public AsposeMavenWizardPanel getComponent() {

        if (myMainPanel == null) {
            myMainPanel = new AsposeMavenWizardPanel();
        }
        return myMainPanel;
    }

    @Override
    public boolean validate() throws ConfigurationException {
        AsposeMavenWizardPanel _page = getComponent();
        if (!_page.isAPISelected())
            throw new ConfigurationException(AsposeConstants.IS_API_SELECTED);
        return true;
    }

    @Override
    public void updateStep() {
    }

    @Override
    public void updateDataModel() {
        myContext.setProjectBuilder(myBuilder);

    }

    @Override
    public Icon getIcon() {
        return WIZARD_ICON;
    }

}