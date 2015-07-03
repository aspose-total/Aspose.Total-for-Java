/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspose.maven;

import java.awt.Component;
import java.util.HashSet;
import java.util.Set;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.openide.WizardDescriptor;
import org.openide.WizardValidationException;
import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;
/**
 * @author Adeel Ilyas <adeel.ilyas@aspose.com>
 */

public class AsposeMavenBasicWizardPanel implements WizardDescriptor.Panel,
        WizardDescriptor.ValidatingPanel {

    private WizardDescriptor wizardDescriptor;
    private AsposeMavenBasicPanelVisual component;

    public AsposeMavenBasicWizardPanel() {
    }

    @Override
    public Component getComponent() {
        if (component == null) {
            component = new AsposeMavenBasicPanelVisual(this);
            component.setName(NbBundle.getMessage(AsposeMavenBasicWizardPanel.class, "LBL_CreateProjectStep"));
        }
        return component;
    }

    @Override
    public HelpCtx getHelp() {
        // Show no Help button for this panel:
        return HelpCtx.DEFAULT_HELP;

    }

    @Override
    public boolean isValid() {
        getComponent();
        return component.valid(wizardDescriptor);
    }

    private final Set<ChangeListener> listeners = new HashSet<ChangeListener>(1); // or can use ChangeSupport in NB 6.0

    @Override
    public final void addChangeListener(ChangeListener l) {
        synchronized (listeners) {
            listeners.add(l);
        }
    }

    @Override
    public final void removeChangeListener(ChangeListener l) {
        synchronized (listeners) {
            listeners.remove(l);
        }
    }

    protected final void fireChangeEvent() {
        Set<ChangeListener> ls;
        synchronized (listeners) {
            ls = new HashSet<ChangeListener>(listeners);
        }
        ChangeEvent ev = new ChangeEvent(this);
        for (ChangeListener l : ls) {
            l.stateChanged(ev);
        }
    }

    @Override
    public void readSettings(Object settings) {
 
        wizardDescriptor = (WizardDescriptor) settings;
        component.read(wizardDescriptor);
    }

    @Override
    public void storeSettings(Object settings) {

        WizardDescriptor d = (WizardDescriptor) settings;
        component.store(d);
    }

    public boolean isFinishPanel() {
        return true;
    }

    @Override
    public void validate() throws WizardValidationException {
    
        getComponent();
        component.validate(wizardDescriptor);
    }

}
