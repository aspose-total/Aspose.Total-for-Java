/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspose.nbplugin.newfile;

import com.aspose.nbplugin.examplesmodel.Example;
import com.aspose.nbplugin.newfile.otherexamples.OtherExamples;
import com.aspose.nbplugin.newfile.otherexamples.OtherExamplesManager;
import com.aspose.nbplugin.utils.AsposeComponentsManager;
import com.aspose.nbplugin.utils.AsposeConstants;
import com.aspose.nbplugin.utils.AsposeJavaComponent;
import com.aspose.nbplugin.utils.AsposeJavaComponents;
import com.aspose.nbplugin.utils.CustomtMutableTreeNode;
import com.aspose.nbplugin.utils.GitHelper;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.openide.WizardDescriptor;

import org.openide.util.Exceptions;
import org.openide.util.HelpCtx;

/**
 * @author Shoaib Khan
 */
public class AsposeNewFileWizardPanel1 implements WizardDescriptor.Panel<WizardDescriptor> {

    /**
     * The visual component that displays this panel. If you need to access the
     * component from this class, just use getComponent().
     */
    private AsposeNewFileVisualPanel1 component;
    private static boolean storeSettingsCalled = false;

    //=========================================================================
    // Get the visual component for the panel. In this template, the component
    // is kept separate. This can be more efficient: if the wizard is created
    // but never displayed, or not all panels are displayed, it is better to
    // create only those which really need to be visible.
    @Override
    public AsposeNewFileVisualPanel1 getComponent() {
        AsposeConstants.println("AsposeNewFileVisualPanel1 getComponent(): is called ...");
        if (component == null) {
            component = new AsposeNewFileVisualPanel1(this);
        }
        return component;
    }

    //=========================================================================
    @Override
    public HelpCtx getHelp() {
        // Show no Help button for this panel:
        return HelpCtx.DEFAULT_HELP;
        // If you have context help:
        // return new HelpCtx("help.key.here");
    }

    //=========================================================================
    @Override
    public boolean isValid() {
        // If it is always OK to press Next or Finish, then:
        AsposeConstants.println("AsposeNewFileWizardPanel1 isValid called..");
        return component.validateDialog();
        // If it depends on some condition (form filled out...) and
        // this condition changes (last form field filled in...) then
        // use ChangeSupport to implement add/removeChangeListener below.
        // WizardDescriptor.ERROR/WARNING/INFORMATION_MESSAGE will also be useful.
    }

    //=========================================================================
    private final Set<ChangeListener> listeners = new HashSet<ChangeListener>(1); // or can use ChangeSupport in NB 6.0

    @Override
    public void addChangeListener(ChangeListener l) {
        synchronized (listeners) {
            listeners.add(l);
        }
    }

    //=========================================================================
    @Override
    public void removeChangeListener(ChangeListener l) {
        synchronized (listeners) {
            listeners.remove(l);
        }
    }

    //=========================================================================
    protected final void fireChangeEvent() {
        AsposeConstants.println("fireChangeEvent called..");
        Iterator<ChangeListener> it;
        synchronized (listeners) {
            it = new HashSet<ChangeListener>(listeners).iterator();
        }
        ChangeEvent ev = new ChangeEvent(this);
        while (it.hasNext()) {
            it.next().stateChanged(ev);
        }
    }

    //=========================================================================
    @Override
    public void readSettings(WizardDescriptor wiz) {
        // use wiz.getProperty to retrieve previous panel state
        component.read(wiz);
    }

    //=========================================================================
    @Override
    public void storeSettings(WizardDescriptor wiz) {
        // use wiz.putProperty to remember current panel state
        boolean cancelled = wiz.getValue() != WizardDescriptor.FINISH_OPTION;
        if (!cancelled) {
            if (!storeSettingsCalled) // First Time
            {
                storeSettingsCalled = true;
                AsposeConstants.println("2. Store Settings is called first time");
                createExample();
            } else // Second Time
            {
                storeSettingsCalled = false;
                AsposeConstants.println("2. Store Settings called 2nd time - OK");
            }
        }
    }

    //=========================================================================
    private boolean createExample() {
        String projectPath = component.getSelectedProjectRootPath();
        CustomtMutableTreeNode comp = getSelectedNode();
        if (comp == null) {
            return false;
        }
        try {
            String path = comp.getExPath();
            Example ex = comp.getExample();
            AsposeJavaComponent asposeComponent = AsposeJavaComponents.list.get(component.getComponentSelection().getSelectedItem());

            // Added by adeel.ilyas@aspose.com - Integration of Apache POI Examples / Other FrameWork Examples 
            String sourceRepositoryPath = GitHelper.getLocalRepositoryPath(asposeComponent) + File.separator + path;
            String destinationPath = projectPath + File.separator + path;
            
            if (!asposeComponent.getOtherFrameworkExamples().isEmpty()) {
                for (OtherExamples _otherExample : asposeComponent.getOtherFrameworkExamples()) {
                    String examplesName = _otherExample.getExampleName();
                    if (destinationPath.contains(examplesName)) 
                    {
                        //updated by shoaib.khan@aspose.com - fixed for Mac
                        destinationPath = destinationPath.replace(examplesName + File.separator, "");
                        
                        OtherExamplesManager.installExamplesDependencies(_otherExample, projectPath);
                        break;
                    }
                }
            }
            AsposeConstants.println("=============================================");
            AsposeConstants.println("sourceRepositoryPath: " + sourceRepositoryPath);
            AsposeConstants.println("destinationPath: " + destinationPath);
            AsposeConstants.println("=============================================");

            copyExample(sourceRepositoryPath, destinationPath);
            // adeel.ilyas@aspose.com

            if (ex == null) {
                return false;
            }

        } catch (Exception ex) {
            return false;
        }
        return true;
    }

    //=========================================================================
    private CustomtMutableTreeNode getSelectedNode() {
        return (CustomtMutableTreeNode) component.getExamplesTree().getLastSelectedPathComponent();
    }

    //=========================================================================
    private void copyExample(String sourcePath, String destinationPath) {
        try {
            AsposeComponentsManager.copyDirectory(sourcePath, destinationPath);
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }
    }
}
