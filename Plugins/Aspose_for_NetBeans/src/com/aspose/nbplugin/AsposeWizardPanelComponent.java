package com.aspose.nbplugin;

import com.aspose.nbplugin.utils.AsposeComponentsManager;
import static com.aspose.nbplugin.utils.AsposeComponentsManager.extractFolder;
import static com.aspose.nbplugin.utils.AsposeComponentsManager.getLibaryDownloadPath;
import static com.aspose.nbplugin.utils.AsposeComponentsManager.removeExtention;
import com.aspose.nbplugin.utils.AsposeConstants;
import com.aspose.nbplugin.utils.AsposeJavaComponents;
import java.awt.Component;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import javax.swing.JOptionPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.openide.WizardDescriptor;
import org.openide.WizardValidationException;
import org.openide.awt.StatusDisplayer;
import org.openide.util.HelpCtx;

/**
 * @author Shoaib Khan
 */
public class AsposeWizardPanelComponent implements WizardDescriptor.Panel, WizardDescriptor.ValidatingPanel
{
    /**
     * The visual component that displays this panel. If you need to access the
     * component from this class, just use getComponent().
     */
    private AsposePanelVisualComponent component;
    private WizardDescriptor wizardDescriptor;
    private static boolean storeSettingsCalled = false;

    //=========================================================================
    // Get the visual component for the panel. In this template, the component
    // is kept separate. This can be more efficient: if the wizard is created
    // but never displayed, or not all panels are displayed, it is better to
    // create only those which really need to be visible.
    public Component getComponent()
    {
        if (component == null)
        {
            component = new AsposePanelVisualComponent(this);
        }
        return component;
    }

    //=========================================================================
    public HelpCtx getHelp()
    {
        // Show no Help button for this panel:
        return HelpCtx.DEFAULT_HELP;
        // If you have context help:
        // return new HelpCtx(SampleWizardPanel1.class);
    }

    //=========================================================================
    public boolean isValid()
    {
        getComponent();
        return component.valid(wizardDescriptor);

        // If it depends on some condition (form filled out...), then:
        // return someCondition();
        // and when this condition changes (last form field filled in...) then:
        // fireChangeEvent();
        // and uncomment the complicated stuff below.
    }
    private final Set<ChangeListener> listeners = new HashSet<ChangeListener>(1); // or can use ChangeSupport in NB 6.0

    //=========================================================================
    public final void addChangeListener(ChangeListener l)
    {
        synchronized (listeners)
        {
            listeners.add(l);
        }
    }

    //=========================================================================
    public final void removeChangeListener(ChangeListener l)
    {
        synchronized (listeners)
        {
            listeners.remove(l);
        }
    }

    //=========================================================================
    protected final void fireChangeEvent()
    {
        Iterator<ChangeListener> it;
        synchronized (listeners)
        {
            it = new HashSet<ChangeListener>(listeners).iterator();
        }
        ChangeEvent ev = new ChangeEvent(this);
        while (it.hasNext())
        {
            it.next().stateChanged(ev);
        }
    }

    //=========================================================================
    // You can use a settings object to keep track of state. Normally the
    // settings object will be the WizardDescriptor, so you can use
    // WizardDescriptor.getProperty & putProperty to store information entered
    // by the user.
    public void readSettings(Object settings)
    {
        AsposeConstants.println("2.========= readSettings called..");
    }
    //=========================================================================
    /**
     * This method is called twice
     *
     * @param settings
     */
    public void storeSettings(Object settings)
    {
        WizardDescriptor wizDes;
        AsposeConstants.println("2. ========= storeSettings called..");
        if (settings instanceof WizardDescriptor)
        {
            wizDes = (WizardDescriptor) settings;

            boolean cancelled = wizDes.getValue() != WizardDescriptor.FINISH_OPTION;
            if (!cancelled)
            {
                if (!storeSettingsCalled) // First Time
                {
                    storeSettingsCalled = true;
                    AsposeConstants.println("2. Store Settings is called first time");
                    performFinish();
                }
                else // Second Time
                {
                    storeSettingsCalled = false;
                    AsposeConstants.println("2. Store Settings 2nd time - OK");
                }
            }
        }

        ((WizardDescriptor) settings).putProperty(AsposeConstants.ASPOSE_CELLS, ((AsposePanelVisualComponent) getComponent()).getjCheckBoxAsposeCells().isSelected());
        ((WizardDescriptor) settings).putProperty(AsposeConstants.ASPOSE_WORDS, ((AsposePanelVisualComponent) getComponent()).getjCheckBoxAsposeWords().isSelected());
        ((WizardDescriptor) settings).putProperty(AsposeConstants.ASPOSE_PDF, ((AsposePanelVisualComponent) getComponent()).getjCheckBoxAsposePdf().isSelected());
        ((WizardDescriptor) settings).putProperty(AsposeConstants.ASPOSE_SLIDES, ((AsposePanelVisualComponent) getComponent()).getjCheckBoxAsposeSlides().isSelected());
        ((WizardDescriptor) settings).putProperty(AsposeConstants.ASPOSE_BARCODE, ((AsposePanelVisualComponent) getComponent()).getjCheckBoxAsposeBarCode().isSelected());
        ((WizardDescriptor) settings).putProperty(AsposeConstants.ASPOSE_EMAIL, ((AsposePanelVisualComponent) getComponent()).getjCheckBoxAsposeEmail().isSelected());
        ((WizardDescriptor) settings).putProperty(AsposeConstants.ASPOSE_OCR, ((AsposePanelVisualComponent) getComponent()).getjCheckBoxAsposeOCR().isSelected());
        ((WizardDescriptor) settings).putProperty(AsposeConstants.ASPOSE_IMAGING, ((AsposePanelVisualComponent) getComponent()).getjCheckBoxAsposeImaging().isSelected());
        ((WizardDescriptor) settings).putProperty(AsposeConstants.ASPOSE_TASKS, ((AsposePanelVisualComponent) getComponent()).getjCheckBoxAsposeTasks().isSelected());
        ((WizardDescriptor) settings).putProperty(AsposeConstants.ASPOSE_DIAGRAM, ((AsposePanelVisualComponent) getComponent()).getjCheckBoxAsposeDiagram().isSelected());
    }

    //=========================================================================
    public void validate() throws WizardValidationException
    {
        AsposeConstants.println("2. ========= validate of panel is called..");
        getComponent();
        component.validate(wizardDescriptor);
    }

    //=========================================================================
    private void performFinish()
    {
        AsposeConstants.println("2. ================ performFinish ================");
        new AsposeJavaComponents();
        updateComponentsSelectionList();

        if (downloadComponents() == false)
        {
            clearMessage();
            return;
        }
    }

    //=========================================================================
    void updateComponentsSelectionList()
    {
        AsposeConstants.println("2. ================ updateComponentsSelectionList ================");

        if (((AsposePanelVisualComponent) getComponent()).getjCheckBoxAsposeCells().isSelected())
        {
            AsposeJavaComponents.list.get(AsposeConstants.ASPOSE_CELLS).set_selected(true);
        }

        if (((AsposePanelVisualComponent) getComponent()).getjCheckBoxAsposeWords().isSelected())
        {
            AsposeJavaComponents.list.get(AsposeConstants.ASPOSE_WORDS).set_selected(true);
        }

        if (((AsposePanelVisualComponent) getComponent()).getjCheckBoxAsposePdf().isSelected())
        {
            AsposeJavaComponents.list.get(AsposeConstants.ASPOSE_PDF).set_selected(true);
            AsposeJavaComponents.list.get(AsposeConstants.ASPOSE_PDF_KIT).set_selected(true);
        }

        if (((AsposePanelVisualComponent) getComponent()).getjCheckBoxAsposeSlides().isSelected())
        {
            AsposeJavaComponents.list.get(AsposeConstants.ASPOSE_SLIDES).set_selected(true);
        }

        if (((AsposePanelVisualComponent) getComponent()).getjCheckBoxAsposeBarCode().isSelected())
        {
            AsposeJavaComponents.list.get(AsposeConstants.ASPOSE_BARCODE).set_selected(true);
        }

        if (((AsposePanelVisualComponent) getComponent()).getjCheckBoxAsposeEmail().isSelected())
        {
            AsposeJavaComponents.list.get(AsposeConstants.ASPOSE_EMAIL).set_selected(true);
        }

        if (((AsposePanelVisualComponent) getComponent()).getjCheckBoxAsposeOCR().isSelected())
        {
            AsposeJavaComponents.list.get(AsposeConstants.ASPOSE_OCR).set_selected(true);
        }

        if (((AsposePanelVisualComponent) getComponent()).getjCheckBoxAsposeImaging().isSelected())
        {
            AsposeJavaComponents.list.get(AsposeConstants.ASPOSE_IMAGING).set_selected(true);
        }
        if (((AsposePanelVisualComponent) getComponent()).getjCheckBoxAsposeTasks().isSelected())
        {
            AsposeJavaComponents.list.get(AsposeConstants.ASPOSE_TASKS).set_selected(true);
        }
        if (((AsposePanelVisualComponent) getComponent()).getjCheckBoxAsposeDiagram().isSelected())
        {
            AsposeJavaComponents.list.get(AsposeConstants.ASPOSE_DIAGRAM).set_selected(true);
        }

    }

    //=========================================================================
    private boolean downloadComponents()
    {
        diplayMessage("Preparing to download selected components", true);
        StatusDisplayer.getDefault().setStatusText("Preparing to download selected components");

        AsposeComponentsManager comManager = new AsposeComponentsManager(
            this);
        if (!comManager.downloadComponents())
        {
            return false;
        }
        return true;
    }

    //=========================================================================
    public boolean downloadFileFromInternet(String urlStr, String outputFile, String name)
    {
        InputStream input;
        int bufferSize = 4096;
        String localPath = getLibaryDownloadPath();
        try
        {
            diplayMessage("Downloading " + name, true);
            StatusDisplayer.getDefault().setStatusText("Downloading " + name);

            URL url = new URL(urlStr);
            input = url.openStream();
            byte[] buffer = new byte[bufferSize];
            File f = new File(localPath + outputFile);
            OutputStream output = new FileOutputStream(f);
            int bytes = 0;
            long totalLength = AsposeComponentsManager.getFileDownloadLength(urlStr);
            long pages = totalLength / bufferSize;

            int currentPage = 0;
            try
            {
                int bytesRead;
                while ((bytesRead = input.read(buffer, 0, buffer.length)) >= 0)
                {
                    output.write(buffer, 0, bytesRead);
                    bytes = bytes + buffer.length;
                    currentPage = currentPage + 1;
                }

                output.flush();
                output.close();
                extractFolder(localPath + outputFile, localPath + removeExtention(outputFile));
            }
            finally
            {
            }
        }
        catch (Exception ex)
        {
            return false;
        }
        return true;
    }

    //=========================================================================
    private void diplayMessage(String msg, boolean important)
    {
        ((AsposePanelVisualComponent) getComponent()).diplayMessage(msg, important);
    }

    //=========================================================================
    public int showMessage(String title, String message, int buttons, int icon)
    {
        int result = JOptionPane.showConfirmDialog(((AsposePanelVisualComponent) getComponent()), message, title, buttons, icon);
        return result;
    }

    //=========================================================================
    public void clearMessage()
    {
        ((AsposePanelVisualComponent) getComponent()).clearMessage();
    }
}