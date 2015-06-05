/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspose.nbplugin;

import com.aspose.nbplugin.utils.AsposeConstants;
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
 * Panel just asking for basic info.
 */
public class AsposeWizardPanel implements WizardDescriptor.Panel,
    WizardDescriptor.ValidatingPanel
{

    private WizardDescriptor wizardDescriptor;
    private AsposePanelVisual component;

    public AsposeWizardPanel()
    {
    }

    public Component getComponent()
    {
        if (component == null)
        {
            component = new AsposePanelVisual(this);
            component.setName(NbBundle.getMessage(AsposeWizardPanel.class, "LBL_CreateProjectStep"));
        }
        return component;
    }

    public HelpCtx getHelp()
    {
        // Show no Help button for this panel:
        return HelpCtx.DEFAULT_HELP;
        // If you have context help:
        //return new HelpCtx(AsposeWizardPanel.class);
    }

    public boolean isValid()
    {
        getComponent();
        return component.valid(wizardDescriptor);
    }

    private final Set<ChangeListener> listeners = new HashSet<ChangeListener>(1); // or can use ChangeSupport in NB 6.0

    public final void addChangeListener(ChangeListener l)
    {
        synchronized (listeners)
        {
            listeners.add(l);
        }
    }

    public final void removeChangeListener(ChangeListener l)
    {
        synchronized (listeners)
        {
            listeners.remove(l);
        }
    }

    protected final void fireChangeEvent()
    {
        Set<ChangeListener> ls;
        synchronized (listeners)
        {
            ls = new HashSet<ChangeListener>(listeners);
        }
        ChangeEvent ev = new ChangeEvent(this);
        for (ChangeListener l : ls)
        {
            l.stateChanged(ev);
        }
    }

    public void readSettings(Object settings)
    {
        AsposeConstants.println("1. readSettings is called.");
        wizardDescriptor = (WizardDescriptor) settings;
        component.read(wizardDescriptor);
    }

    public void storeSettings(Object settings)
    {
        AsposeConstants.println("1. Store Settings is called.");
        WizardDescriptor d = (WizardDescriptor) settings;
        component.store(d);
    }

    public boolean isFinishPanel()
    {
        return true;
    }

    public void validate() throws WizardValidationException
    {
        AsposeConstants.println("1. Validate is called.");
        getComponent();
        component.validate(wizardDescriptor);
    }

}