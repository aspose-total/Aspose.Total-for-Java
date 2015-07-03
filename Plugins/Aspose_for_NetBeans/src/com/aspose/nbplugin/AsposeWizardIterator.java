/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspose.nbplugin;

import com.aspose.nbplugin.utils.AsposeComponentsManager;
import com.aspose.nbplugin.utils.AsposeConstants;
import com.aspose.nbplugin.utils.AsposeJavaComponent;
import com.aspose.nbplugin.utils.AsposeJavaComponents;
import com.aspose.nbplugin.utils.GitHelper;
import java.awt.Component;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import javax.swing.JComponent;
import javax.swing.event.ChangeListener;
import org.netbeans.api.project.ProjectManager;
import org.netbeans.api.templates.TemplateRegistration;
import org.netbeans.spi.project.ui.support.ProjectChooser;
import org.netbeans.spi.project.ui.templates.support.Templates;
import org.openide.WizardDescriptor;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.util.Exceptions;
import org.openide.util.NbBundle;
import org.openide.util.NbBundle.Messages;
import org.openide.xml.XMLUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

/**
 * @author Shoaib Khan
 */

@TemplateRegistration(
    folder = "Project/Standard",
    displayName = "#Aspose_displayName",
    description = "AsposeDescription.html",
    iconBase = "com/aspose/nbplugin/Aspose.png",
    position = 10,
    content = "AsposeProject.zip")
@Messages("Aspose_displayName=Aspose Application")

public class AsposeWizardIterator implements WizardDescriptor./*Progress*/InstantiatingIterator
{

    private int index;
    private WizardDescriptor.Panel[] panels;
    private WizardDescriptor wiz;
    List<String> list = new ArrayList<String>();

    public AsposeWizardIterator()
    {
    }

    //=========================================================================
    public static AsposeWizardIterator createIterator()
    {
        return new AsposeWizardIterator();
    }

    //=========================================================================
    private WizardDescriptor.Panel[] createPanels()
    {
        return new WizardDescriptor.Panel[]
        {
            new AsposeWizardPanel(),
            new AsposeWizardPanelComponent()
        };
    }

    //=========================================================================
    private String[] createSteps()
    {
        return new String[]
        {
            NbBundle.getMessage(AsposeWizardIterator.class, "LBL_CreateProjectStep"),
            NbBundle.getMessage(AsposeWizardIterator.class, "LBL_SelectAsposeComponents")

        };
    }

    //=========================================================================
    public Set/*<FileObject>*/ instantiate(/*ProgressHandle handle*/) throws IOException
    {
        AsposeConstants.println("================ Instantiate is called ================");

        Set<FileObject> resultSet = new LinkedHashSet<FileObject>();

        String projectDirName = (String) wiz.getProperty("projdirName");
        String projLocation = (String) wiz.getProperty("projLocation");
        String projectName = (String) wiz.getProperty("name");

        File dirF = FileUtil.normalizeFile((File) wiz.getProperty("projdir"));
        dirF.mkdirs();

        FileObject template = Templates.getTemplate(wiz);
        FileObject dir = FileUtil.toFileObject(dirF);
        unZipFile(template.getInputStream(), dir);

        extractTemplate(projLocation, projectName);
        setProjectName(projectDirName);
        copyLibraryFiles(projectDirName);
        addLibraryFileToProject(projectDirName);

        // Always open top dir as a project:
        resultSet.add(dir);
        // Look for nested projects to open as well:
        Enumeration<? extends FileObject> e = dir.getFolders(true);
        while (e.hasMoreElements())
        {
            FileObject subfolder = e.nextElement();
            if (ProjectManager.getDefault().isProject(subfolder))
            {
                resultSet.add(subfolder);
            }
        }

        File parent = dirF.getParentFile();
        if (parent != null && parent.exists())
        {
            ProjectChooser.setProjectsFolder(parent);
        }

        return resultSet;
    }

    //=========================================================================    
    private void extractTemplate(String projectPath, String projectName) throws IOException
    {
        URL url = getClass().getResource("/resources/" + AsposeConstants.ASPOSE_COMPONENTS_FILE);

        GitHelper.checkAndCreateFolder(projectPath);
        FileOutputStream output = new FileOutputStream(projectPath + File.separator + AsposeConstants.ASPOSE_COMPONENTS_FILE);
        InputStream input = url.openStream();
        byte[] buffer = new byte[4096];
        int bytesRead = input.read(buffer);
        while (bytesRead != -1)
        {
            output.write(buffer, 0, bytesRead);
            bytesRead = input.read(buffer);
        }
        output.flush();
        output.close();
        input.close();

        AsposeComponentsManager.extractFolder(projectPath + File.separator + AsposeConstants.ASPOSE_COMPONENTS_FILE, projectPath + File.separator + projectName);
        File fl = new File(projectPath + File.separator + AsposeConstants.ASPOSE_COMPONENTS_FILE);
        fl.delete();
    }

    //=========================================================================
    private void copyLibraryFiles(String projectPath)
    {
        for (AsposeJavaComponent component : AsposeJavaComponents.list.values())
        {
            if (component.is_selected())
            {
                try
                {
                    AsposeComponentsManager.copyDirectory(AsposeComponentsManager.getLibaryDownloadPath() + component.get_name().toLowerCase(), projectPath + File.separator + "lib" + File.separator + component.get_name());
                }
                catch (IOException ex)
                {
                    Exceptions.printStackTrace(ex);
                }
            }
        }
    }

    //=========================================================================
    public void initialize(WizardDescriptor wiz)
    {
        this.wiz = wiz;
        index = 0;
        panels = createPanels();
        // Make sure list of steps is accurate.
        String[] steps = createSteps();
        for (int i = 0; i < panels.length; i++)
        {
            Component c = panels[i].getComponent();
            if (steps[i] == null)
            {
                // Default step name to component name of panel.
                // Mainly useful for getting the name of the target
                // chooser to appear in the list of steps.
                steps[i] = c.getName();
            }
            if (c instanceof JComponent)
            { // assume Swing components
                JComponent jc = (JComponent) c;
                // Step #.
                // TODO if using org.openide.dialogs >= 7.8, can use WizardDescriptor.PROP_*:
                jc.putClientProperty("WizardPanel_contentSelectedIndex", new Integer(i));
                // Step name (actually the whole list for reference).
                jc.putClientProperty("WizardPanel_contentData", steps);
            }
        }
    }

    //=========================================================================
    public void uninitialize(WizardDescriptor wiz)
    {
        this.wiz.putProperty("projdir", null);
        this.wiz.putProperty("name", null);
        this.wiz = null;
        panels = null;
    }

    //=========================================================================
    public String name()
    {
        return MessageFormat.format("{0} of {1}",
            new Object[]
            {
                new Integer(index + 1), new Integer(panels.length)
            });
    }

    //=========================================================================
    public boolean hasNext()
    {
        return index < panels.length - 1;
    }

    //=========================================================================
    public boolean hasPrevious()
    {
        return index > 0;
    }

    //=========================================================================
    public void nextPanel()
    {
        if (!hasNext())
        {
            throw new NoSuchElementException();
        }
        index++;
    }

    //=========================================================================
    public void previousPanel()
    {
        if (!hasPrevious())
        {
            throw new NoSuchElementException();
        }
        index--;
    }

    //=========================================================================
    public WizardDescriptor.Panel current()
    {
        return panels[index];
    }

    //=========================================================================
    // If nothing unusual changes in the middle of the wizard, simply:
    public final void addChangeListener(ChangeListener l)
    {
    }

    //=========================================================================
    public final void removeChangeListener(ChangeListener l)
    {
    }

    //=========================================================================
    private static void unZipFile(InputStream source, FileObject projectRoot) throws IOException
    {
        try
        {
            ZipInputStream str = new ZipInputStream(source);
            ZipEntry entry;
            while ((entry = str.getNextEntry()) != null)
            {
                if (entry.isDirectory())
                {
                    FileUtil.createFolder(projectRoot, entry.getName());
                }
                else
                {
                    FileObject fo = FileUtil.createData(projectRoot, entry.getName());
                    if ("nbproject/project.xml".equals(entry.getName()))
                    {
                        // Special handling for setting name of Ant-based projects; customize as needed:
                        filterProjectXML(fo, str, projectRoot.getName());
                    }
                    else
                    {
                        writeFile(str, fo);
                    }
                }
            }
        }
        finally
        {
            source.close();
        }
    }

    //=========================================================================
    private static void writeFile(ZipInputStream str, FileObject fo) throws IOException
    {
        OutputStream out = fo.getOutputStream();
        try
        {
            FileUtil.copy(str, out);
        }
        finally
        {
            out.close();
        }
    }

    //=========================================================================
    private static void filterProjectXML(FileObject fo, ZipInputStream str, String name) throws IOException
    {
        try
        {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            FileUtil.copy(str, baos);
            Document doc = XMLUtil.parse(new InputSource(new ByteArrayInputStream(baos.toByteArray())), false, false, null, null);
            NodeList nl = doc.getDocumentElement().getElementsByTagName("name");
            if (nl != null)
            {
                for (int i = 0; i < nl.getLength(); i++)
                {
                    Element el = (Element) nl.item(i);
                    if (el.getParentNode() != null && "data".equals(el.getParentNode().getNodeName()))
                    {
                        NodeList nl2 = el.getChildNodes();
                        if (nl2.getLength() > 0)
                        {
                            nl2.item(0).setNodeValue(name);
                        }
                        break;
                    }
                }
            }
            OutputStream out = fo.getOutputStream();
            try
            {
                XMLUtil.write(doc, out, "UTF-8");
            }
            finally
            {
                out.close();
            }
        }
        catch (Exception ex)
        {
            Exceptions.printStackTrace(ex);
            writeFile(str, fo);
        }

    }

    //=========================================================================
    public void addLibraryFileToProject(String projectDirName)
        throws IOException
    {
        String filePath = projectDirName + File.separator + "nbproject" + File.separator + "project.properties";

        File in = new File(filePath);
        File out = new File(filePath + "bk");
        BufferedReader reader = new BufferedReader(new FileReader(in));
        PrintWriter writer = new PrintWriter(new FileWriter(out));
        String line = null;
        while ((line = reader.readLine()) != null)
        {
            if (line.toString().equals("javac.classpath="))
            {
                line = line + "\\";
            }
            writer.println(line);
            if (line.toString().equals("excludes="))
            {
                list.clear();
                searchJarFiles(new File(projectDirName + File.separator + "lib"));

                Iterator<String> iterator = list.iterator();
                while (iterator.hasNext())
                {

                    String libraryPath = iterator.next();
                    String libraryName = (new File(libraryPath)).getName();
                    libraryPath = libraryPath.replace("\\", "\\\\");

                    String libEntry = "file.reference." + libraryName + "=" + libraryPath;
                    writer.println(libEntry);
                }
            }

            if (line.toString().equals("javac.classpath=\\"))
            {
                Iterator<String> iterator = list.iterator();
                int size = list.size();
                int cur = 1;
                while (iterator.hasNext())
                {
                    String libraryPath = iterator.next();
                    String libraryName = (new File(libraryPath)).getName();
                    String libEntry = "";

                    if (cur != size)
                    {
                        libEntry = "    ${file.reference." + libraryName + "}:\\";
                    }
                    else
                    {
                        libEntry = "    ${file.reference." + libraryName + "}";
                    }
                    writer.println(libEntry);
                    cur++;
                }
            }
        }
        
        reader.close();
        writer.close();
        in.delete();
        out.renameTo(new File(filePath));
    }

    //=========================================================================
    private void searchJarFiles(File aFile)
    {
        if (aFile.isFile())
        {
            if (aFile.getName().toLowerCase().endsWith(".jar"))
            {
                list.add(aFile.getAbsolutePath());
            }
        }
        else if (aFile.isDirectory())
        {
            File[] listOfFiles = aFile.listFiles();
            if (listOfFiles != null)
            {
                for (int i = 0; i < listOfFiles.length; i++)
                {
                    searchJarFiles(listOfFiles[i]);
                }
            }
            else
            {
                //AsposeConstants.println(spcs + " [ACCESS DENIED]");
            }
        }
    }

    //=========================================================================
    private void setProjectName(String projectPath) throws FileNotFoundException
    {
        String myfile = projectPath + File.separator + "nbproject" + File.separator + "project.xml";
        try
        {
            replace("AsposeComponents", (String) wiz.getProperty("name"), myfile);
        }
        catch (IOException ex)
        {
            Exceptions.printStackTrace(ex);
        }
    }

    //=========================================================================
    public static void replace(String oldstring, String newstring, String filePath)
        throws IOException
    {

        File in = new File(filePath);
        File out = new File(filePath + "bk");
        BufferedReader reader = new BufferedReader(new FileReader(in));
        PrintWriter writer = new PrintWriter(new FileWriter(out));
        String line = null;
        while ((line = reader.readLine()) != null)
        {
            writer.println(line.replaceAll(oldstring, newstring));
        }

        // I'm aware of the potential for resource leaks here. Proper resource
        // handling has been omitted in the interest of brevity
        reader.close();
        writer.close();
        in.delete();
        out.renameTo(new File(filePath));
    }
}