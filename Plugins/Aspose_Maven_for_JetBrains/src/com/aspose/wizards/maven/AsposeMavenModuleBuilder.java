
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

import com.aspose.utils.execution.RunnableHelper;
import com.intellij.ide.util.projectWizard.ModuleBuilder;
import com.intellij.ide.util.projectWizard.ModuleWizardStep;
import com.intellij.ide.util.projectWizard.SettingsStep;
import com.intellij.ide.util.projectWizard.WizardContext;
import com.intellij.openapi.Disposable;
import com.intellij.openapi.module.JavaModuleType;
import com.intellij.openapi.module.ModuleType;
import com.intellij.openapi.module.StdModuleTypes;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.projectRoots.JavaSdkType;
import com.intellij.openapi.projectRoots.SdkTypeId;
import com.intellij.openapi.roots.ModifiableRootModel;
import com.intellij.openapi.roots.ui.configuration.ModulesProvider;
import com.intellij.openapi.util.Disposer;
import com.intellij.openapi.util.io.FileUtil;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import icons.AsposeIcons;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.io.File;
import java.util.ResourceBundle;


public class AsposeMavenModuleBuilder extends ModuleBuilder {

    private Project myProject;
    ResourceBundle bundle = ResourceBundle.getBundle("Bundle");

    @Override
    public String getBuilderId() {
        return getClass().getName();
    }

    @Override
    public Icon getBigIcon() {
        return AsposeIcons.AsposeMedium;
    }

    @Override
    public Icon getNodeIcon() {
        return AsposeIcons.AsposeLogo;
    }


    @Override
    public ModuleWizardStep[] createWizardSteps(@NotNull WizardContext wizardContext, @NotNull ModulesProvider modulesProvider) {
        return new ModuleWizardStep[]{
                new AsposeMavenDependenciesWizardStep(this, wizardContext), new AsposeMavenModuleWizardStep(getMyProject(), this, wizardContext, !wizardContext.isNewWizard()),

        };
    }

    private VirtualFile createAndGetContentEntry() {
        String path = FileUtil.toSystemIndependentName(getContentEntryPath());
        new File(path).mkdirs();
        return LocalFileSystem.getInstance().refreshAndFindFileByPath(path);
    }

    @Override
    public void setupRootModel(ModifiableRootModel rootModel) throws com.intellij.openapi.options.ConfigurationException {

        final Project project = rootModel.getProject();
        setMyProject(rootModel.getProject());
        final VirtualFile root = createAndGetContentEntry();
        rootModel.addContentEntry(root);

        rootModel.inheritSdk();

        RunnableHelper.runWhenInitialized(getMyProject(), new Runnable() {
            public void run() {

                AsposeMavenModuleBuilderHelper mavenBuilder = new AsposeMavenModuleBuilderHelper(getMyProjectId(), "Create new Maven module", project, root);
                mavenBuilder.configure();

            }
        });

    }

    @Override
    public String getGroupName() {
        return JavaModuleType.JAVA_GROUP;
    }

    public Project getMyProject() {
        return myProject;
    }

    public void setMyProject(Project myProject) {
        this.myProject = myProject;
    }

    @Nullable
    public ModuleWizardStep getCustomOptionsStep(WizardContext context, Disposable parentDisposable) {
        AsposeIntroWizardStep step = new AsposeIntroWizardStep();
        Disposer.register(parentDisposable, step);
        return step;
    }


    public ModuleType getModuleType() {
        return StdModuleTypes.JAVA;
    }

    @Override
    public boolean isSuitableSdkType(SdkTypeId sdkType) {
        return sdkType instanceof JavaSdkType;
    }

    @Nullable
    @Override
    public ModuleWizardStep modifySettingsStep(@NotNull SettingsStep settingsStep) {
        return StdModuleTypes.JAVA.modifySettingsStep(settingsStep, this);
    }


    @Nullable
    protected static String getPathForOutputPathStep() {
        return null;
    }


    public MavenId getMyProjectId() {
        return myProjectId;
    }

    public void setMyProjectId(MavenId myProjectId) {
        this.myProjectId = myProjectId;
    }

    private MavenId myProjectId;


    @Override
    public String getPresentableName() {
        return "Aspose Maven Project";
    }


    @Override
    public String getDescription() {
        return bundle.getString("AsposeWizardPanel.myMainPanel.description");
    }

}