package com.aspose.maven;

import com.aspose.maven.utils.AbstractTask;
import com.aspose.maven.utils.AsposeConstants;
import com.aspose.maven.utils.AsposeMavenDependenciesManager;
import com.aspose.maven.utils.AsposeMavenProject;
import com.aspose.maven.utils.TasksExecutor;
import java.awt.Component;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import javax.swing.JOptionPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.openide.WizardDescriptor;
import org.openide.WizardValidationException;
import org.openide.util.HelpCtx;

public class AsposeWizardPanelComponent implements WizardDescriptor.Panel, WizardDescriptor.ValidatingPanel {
    private static boolean storeSettingsCalled = false;

    private AsposeMavenPanel component;
    private WizardDescriptor wizardDescriptor;
    private final Set<ChangeListener> listeners = new HashSet<ChangeListener>(1); // or can use ChangeSupport in NB 6.0

    @Override
    public Component getComponent() {
        if (component == null) {
            component = new AsposeMavenPanel(this);
        }
        return component;
    }

    @Override
    public HelpCtx getHelp() {
        // Show no Help button for this panel:
        return HelpCtx.DEFAULT_HELP;
        // If you have context help:
        // return new HelpCtx(SampleWizardPanel1.class);
    }

    //=========================================================================
    @Override
    public boolean isValid() {
        getComponent();
        return component.valid(wizardDescriptor);

        // If it depends on some condition (form filled out...), then:
        // return someCondition();
        // and when this condition changes (last form field filled in...) then:
        // fireChangeEvent();
        // and uncomment the complicated stuff below.
    }

    //=========================================================================
    @Override
    public final void addChangeListener(ChangeListener l) {
        synchronized (listeners) {
            listeners.add(l);
        }
    }

    //=========================================================================
    @Override
    public final void removeChangeListener(ChangeListener l) {
        synchronized (listeners) {
            listeners.remove(l);
        }
    }

    //=========================================================================
    protected final void fireChangeEvent() {
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
    // You can use a settings object to keep track of state. Normally the
    // settings object will be the WizardDescriptor, so you can use
    // WizardDescriptor.getProperty & putProperty to store information entered
    // by the user.
    @Override
    public void readSettings(Object settings) {

    }
    //=========================================================================
    /**
     * This method is called twice
     *
     * @param settings
     */
    @Override
    public void storeSettings(Object settings) {
        WizardDescriptor wizDes;

        if (settings instanceof WizardDescriptor) {
            wizDes = (WizardDescriptor) settings;

            boolean cancelled = wizDes.getValue() != WizardDescriptor.FINISH_OPTION;
            if (!cancelled) {
                if (!storeSettingsCalled) // First Time
                {
                    storeSettingsCalled = true;

                } else // Second Time
                {
                    storeSettingsCalled = false;

                }
            }
        }

        ((WizardDescriptor) settings).putProperty(AsposeConstants.ASPOSE_CELLS, ((AsposeMavenPanel) getComponent()).getjCheckBoxAsposeCells().isSelected());
        ((WizardDescriptor) settings).putProperty(AsposeConstants.ASPOSE_WORDS, ((AsposeMavenPanel) getComponent()).getjCheckBoxAsposeWords().isSelected());
        ((WizardDescriptor) settings).putProperty(AsposeConstants.ASPOSE_PDF, ((AsposeMavenPanel) getComponent()).getjCheckBoxAsposePdf().isSelected());
        ((WizardDescriptor) settings).putProperty(AsposeConstants.ASPOSE_SLIDES, ((AsposeMavenPanel) getComponent()).getjCheckBoxAsposeSlides().isSelected());
        ((WizardDescriptor) settings).putProperty(AsposeConstants.ASPOSE_BARCODE, ((AsposeMavenPanel) getComponent()).getjCheckBoxAsposeBarCode().isSelected());
        ((WizardDescriptor) settings).putProperty(AsposeConstants.ASPOSE_EMAIL, ((AsposeMavenPanel) getComponent()).getjCheckBoxAsposeEmail().isSelected());
        ((WizardDescriptor) settings).putProperty(AsposeConstants.ASPOSE_OCR, ((AsposeMavenPanel) getComponent()).getjCheckBoxAsposeOCR().isSelected());
        ((WizardDescriptor) settings).putProperty(AsposeConstants.ASPOSE_IMAGING, ((AsposeMavenPanel) getComponent()).getjCheckBoxAsposeImaging().isSelected());
        ((WizardDescriptor) settings).putProperty(AsposeConstants.ASPOSE_TASKS, ((AsposeMavenPanel) getComponent()).getjCheckBoxAsposeTasks().isSelected());
        ((WizardDescriptor) settings).putProperty(AsposeConstants.ASPOSE_DIAGRAM, ((AsposeMavenPanel) getComponent()).getjCheckBoxAsposeDiagram().isSelected());
    }

    @Override
    public void validate() throws WizardValidationException {

        getComponent();

        if (!AsposeMavenDependenciesManager.isInternetConnected()) {

            throw new WizardValidationException(null, AsposeConstants.MAVEN_INTERNET_CONNECTION_REQUIRED_MESSAGE, AsposeConstants.MAVEN_INTERNET_CONNECTION_REQUIRED_MESSAGE);
        }

    }

    public void performFinish() {

        new AsposeMavenProject();
        updateComponentsSelectionList();
        TasksExecutor tasksExecution = new TasksExecutor("Retrieving Aspose Maven Dependencies...");
        diplayMessage("Preparing to create Aspose Maven based project ...", true);

        tasksExecution.addNewTask(new AbstractTask("Retrieving Aspose Maven Dependencies...") {
            public void run() {
                int totalDependenciestoFetch= AsposeMavenProject.getApiList().size();
                p.start(totalDependenciestoFetch+1);
                AsposeMavenDependenciesManager comManager = new AsposeMavenDependenciesManager();
                comManager.retrieveAsposeMavenDependencies(p);
                p.finish();
            }
        });
        tasksExecution.processTasks();
    }

    void updateComponentsSelectionList() {

        if (((AsposeMavenPanel) getComponent()).getjCheckBoxAsposeCells().isSelected()) {
            AsposeMavenProject.getApiList().get(AsposeConstants.ASPOSE_CELLS).set_selected(true);
        }

        if (((AsposeMavenPanel) getComponent()).getjCheckBoxAsposeWords().isSelected()) {
            AsposeMavenProject.getApiList().get(AsposeConstants.ASPOSE_WORDS).set_selected(true);
        }

        if (((AsposeMavenPanel) getComponent()).getjCheckBoxAsposePdf().isSelected()) {
            AsposeMavenProject.getApiList().get(AsposeConstants.ASPOSE_PDF).set_selected(true);

        }

        if (((AsposeMavenPanel) getComponent()).getjCheckBoxAsposeSlides().isSelected()) {
            AsposeMavenProject.getApiList().get(AsposeConstants.ASPOSE_SLIDES).set_selected(true);
        }

        if (((AsposeMavenPanel) getComponent()).getjCheckBoxAsposeBarCode().isSelected()) {
            AsposeMavenProject.getApiList().get(AsposeConstants.ASPOSE_BARCODE).set_selected(true);
        }

        if (((AsposeMavenPanel) getComponent()).getjCheckBoxAsposeEmail().isSelected()) {
            AsposeMavenProject.getApiList().get(AsposeConstants.ASPOSE_EMAIL).set_selected(true);
        }

        if (((AsposeMavenPanel) getComponent()).getjCheckBoxAsposeOCR().isSelected()) {
            AsposeMavenProject.getApiList().get(AsposeConstants.ASPOSE_OCR).set_selected(true);
        }

        if (((AsposeMavenPanel) getComponent()).getjCheckBoxAsposeImaging().isSelected()) {
            AsposeMavenProject.getApiList().get(AsposeConstants.ASPOSE_IMAGING).set_selected(true);
        }
        if (((AsposeMavenPanel) getComponent()).getjCheckBoxAsposeTasks().isSelected()) {
            AsposeMavenProject.getApiList().get(AsposeConstants.ASPOSE_TASKS).set_selected(true);
        }
        if (((AsposeMavenPanel) getComponent()).getjCheckBoxAsposeDiagram().isSelected()) {
            AsposeMavenProject.getApiList().get(AsposeConstants.ASPOSE_DIAGRAM).set_selected(true);
        }

    }

    //=========================================================================
    private void diplayMessage(String msg, boolean important) {
        ((AsposeMavenPanel) getComponent()).diplayMessage(msg, important);
    }

    //=========================================================================
    public int showMessage(String title, String message, int buttons, int icon) {
        int result = JOptionPane.showConfirmDialog(((AsposeMavenPanel) getComponent()), message, title, buttons, icon);
        return result;
    }

    //=========================================================================
    public void clearMessage() {
        ((AsposeMavenPanel) getComponent()).clearMessage();
    }

    public boolean isStoreSettingCalled() {
        return storeSettingsCalled;
    }
}
