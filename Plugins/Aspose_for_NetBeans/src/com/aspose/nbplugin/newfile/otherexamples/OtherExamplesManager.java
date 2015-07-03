/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspose.nbplugin.newfile.otherexamples;

import com.aspose.nbplugin.examplesmodel.Data;
import com.aspose.nbplugin.examplesmodel.Example;
import com.aspose.nbplugin.examplesmodel.Examples;
import com.aspose.nbplugin.examplesmodel.Folder;
import com.aspose.nbplugin.examplesmodel.Folders;
import com.aspose.nbplugin.examplesmodel.ObjectFactory;
import com.aspose.nbplugin.progressmonitor.AbstractTask;
import com.aspose.nbplugin.progressmonitor.TasksExecutor;
import com.aspose.nbplugin.utils.AsposeConstants;
import com.aspose.nbplugin.utils.AsposeJavaComponent;
import com.aspose.nbplugin.utils.CustomtMutableTreeNode;
import com.aspose.nbplugin.utils.GitHelper;
import static com.aspose.nbplugin.utils.GitHelper.getLocalRepositoryPath;
import java.io.File;
import java.net.HttpURLConnection;
import java.net.URL;

import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import org.openide.awt.StatusDisplayer;

/**
 *
 * @author Adeel Ilyas
 */
/* Added by adeel.ilyas@aspose.com - Integration of Apache POI Examples / Other FrameWork Examples *
 */
public class OtherExamplesManager {
    // Factories for creating Apache POI Exampels for Aspose API

    private static ExamplesFrameWork poiFrameWork;

    public static ExamplesFrameWork getPOIFrameWork() {
        if (poiFrameWork != null) {
            return poiFrameWork;
        }

        poiFrameWork = new ExamplesFrameWork();

        poiFrameWork.setFrameworkName(AsposeConstants.APACHE_POI);

        //Defining FrameWork Lib Dependencies:
        poiFrameWork.addLibDependency(new LibDependency("dom4j-1.6.1", "https://raw.githubusercontent.com/asposemarketplace/Aspose_for_Apache_POI/master/lib/ApachePOI/dom4j-1.6.1.jar"));
        poiFrameWork.addLibDependency(new LibDependency("xmlbeans-2.6.0", "https://raw.githubusercontent.com/asposemarketplace/Aspose_for_Apache_POI/master/lib/ApachePOI/xmlbeans-2.6.0.jar"));
        poiFrameWork.addLibDependency(new LibDependency("stax-api-1.0.1", "https://raw.githubusercontent.com/asposemarketplace/Aspose_for_Apache_POI/master/lib/ApachePOI/stax-api-1.0.1.jar"));

        poiFrameWork.addLibDependency(new LibDependency("poi-3.11", "https://raw.githubusercontent.com/asposemarketplace/Aspose_for_Apache_POI/master/lib/ApachePOI/poi-3.11-20141221.jar"));
        poiFrameWork.addLibDependency(new LibDependency("poi-ooxml-3.11", "https://raw.githubusercontent.com/asposemarketplace/Aspose_for_Apache_POI/master/lib/ApachePOI/poi-ooxml-3.11-20141221.jar"));
        poiFrameWork.addLibDependency(new LibDependency("poi-ooxml-schemas-3.11", "https://raw.githubusercontent.com/asposemarketplace/Aspose_for_Apache_POI/master/lib/ApachePOI/poi-ooxml-schemas-3.11-20141221.jar"));
        poiFrameWork.addLibDependency(new LibDependency("poi-scratchpad-3.11", "https://raw.githubusercontent.com/asposemarketplace/Aspose_for_Apache_POI/master/lib/ApachePOI/poi-scratchpad-3.11-20141221.jar"));
        poiFrameWork.addLibDependency(new LibDependency("poi-examples-3.11", "https://raw.githubusercontent.com/asposemarketplace/Aspose_for_Apache_POI/master/lib/ApachePOI/poi-examples-3.11-20141221.jar"));
        poiFrameWork.addLibDependency(new LibDependency("poi-excelant-3.11", "https://raw.githubusercontent.com/asposemarketplace/Aspose_for_Apache_POI/master/lib/ApachePOI/poi-excelant-3.11-20141221.jar"));
        return poiFrameWork;
    }

    public static OtherExamples getPOIExamples(AsposeJavaComponent component) {

        OtherExamples _otherExamples = new OtherExamples();
        _otherExamples.addFrameWorkDependency(OtherExamplesManager.getPOIFrameWork());

        if (component.get_name().equals(AsposeConstants.ASPOSE_WORDS)) {
            _otherExamples.setGitHubExamplesRepositoryLocation("https://github.com/asposemarketplace/Aspose_Words_for_Apache_POI.git");
            _otherExamples.setExampleName(AsposeConstants.ASPOSE_WORDS_APACHE_POI);
        } else if (component.get_name().equals(AsposeConstants.ASPOSE_CELLS)) {
            _otherExamples.setGitHubExamplesRepositoryLocation("https://github.com/asposemarketplace/Aspose_Cells_for_Apache_POI.git");
            _otherExamples.setExampleName(AsposeConstants.ASPOSE_CELLS_APACHE_POI);
        } else if (component.get_name().equals(AsposeConstants.ASPOSE_SLIDES)) {
            _otherExamples.setGitHubExamplesRepositoryLocation("https://github.com/asposemarketplace/Aspose_Slides_for_Apache_POI.git");
            _otherExamples.setExampleName(AsposeConstants.ASPOSE_SLIDES_APACHE_POI);
        } else if (component.get_name().equals(AsposeConstants.ASPOSE_EMAIL)) {
            _otherExamples.setGitHubExamplesRepositoryLocation("https://github.com/asposemarketplace/Aspose_Email_for_Apache_POI.git");
            _otherExamples.setExampleName(AsposeConstants.ASPOSE_EMAIL_APACHE_POI);
        }

        return _otherExamples;

    }
    private static final TasksExecutor tasksExecution = new TasksExecutor("Installing examples dependencies . . .");

    public static void installExamplesDependencies(OtherExamples _otherExamples, final String projectPath) {
        if (_otherExamples.getFrameWorkDependencies().isEmpty()) {
            return;
        }
        tasksExecution.clearTasks();
        for (final ExamplesFrameWork frameWork : _otherExamples.getFrameWorkDependencies()) {
            // checking if framework & its libs is available in aspose work directory
            if (!frameWork.getFrameWorkStatus().isFrameWorkSuccessfullyDownloaded()) {
                // Download Examples FrameWork here i.e Apache POI etc.

                frameWork.download(tasksExecution);
            }

            // checking if framework & its libs is available inside project
            if (!frameWork.getFrameWorkStatus().isFrameWorkAddedToProjects(projectPath)) {

                // copy Framework & Its libs from aspose work directory to projectpath
                tasksExecution.addNewTask(new AbstractTask("Installing framework ...") {
                    @Override
                    public void run() {
                        p.start(5);
                        frameWork.addFrameWorkLibrariesToProject(projectPath);
                        p.progress(5);
                        p.finish();

                    }
                });

            }
            if (!frameWork.getFrameWorkStatus().isFrameWorkAddedToProjectsClassPath(projectPath)) {
                tasksExecution.addNewTask(new AbstractTask("Initializing framework ...") {
                    @Override
                    public void run() {
                        p.start(5);
                        frameWork.addFrameWorkInProjectClassPath(projectPath);
                        p.progress(5);
                        p.finish();

                    }
                });
            }
        }
        if (tasksExecution.areThereTasks()) {
            tasksExecution.processTasks();
        }
    }

    /**
     *
     * @param _otherExamples
     * @param component
     * @return
     */
    public static String getOtherExamplesDefinitionsPath(OtherExamples _otherExamples, AsposeJavaComponent component) {
        return getOtherExamplesLocalRepositoryPath(_otherExamples, component) + File.separator + "Examples.xml";
    }

    /**
     *
     * @param _otherExamples
     * @param component
     * @return
     */
    public static String getOtherExamplesLocalRepositoryPath(OtherExamples _otherExamples, AsposeJavaComponent component) {
        return getLocalRepositoryPath(component) + File.separator + _otherExamples.getExampleName();
    }

    /**
     *
     * @param component
     * @return
     */
    public static void updateOtherExamplesRepositories(AsposeJavaComponent component) throws Exception {
        if (!component.getOtherFrameworkExamples().isEmpty()) {
            for (OtherExamples _otherExample : component.getOtherFrameworkExamples()) {
                if (repositoryExists(_otherExample.getGitHubExamplesRepositoryLocation().replace(".git", ""))) {
                    StatusDisplayer.getDefault().setStatusText("Downloading " + _otherExample.getExampleName() + " examples ...");
                    GitHelper.checkAndCreateFolder(OtherExamplesManager.getOtherExamplesLocalRepositoryPath(_otherExample, component));
                    GitHelper.updateRepository(getOtherExamplesLocalRepositoryPath(_otherExample, component), _otherExample.getGitHubExamplesRepositoryLocation());
                }
            }
        }

    }

    public static boolean repositoryExists(String URLName) {
        try {
            HttpURLConnection.setFollowRedirects(false);

            HttpURLConnection con
                    = (HttpURLConnection) new URL(URLName).openConnection();
            con.setRequestMethod("HEAD");
            return (con.getResponseCode() == HttpURLConnection.HTTP_OK);
        } catch (Exception e) {
            return false;
        }
    }

    public static void addOtherExamples(CustomtMutableTreeNode top, AsposeJavaComponent asposeComponent) throws JAXBException {
        if (!asposeComponent.getOtherFrameworkExamples().isEmpty()) {
            for (OtherExamples _otherExample : asposeComponent.getOtherFrameworkExamples()) {
                File otherExamplesDefinitionFile = new File(OtherExamplesManager.getOtherExamplesDefinitionsPath(_otherExample, asposeComponent));
                if (otherExamplesDefinitionFile.exists()) {
                    String examplesName = _otherExample.getExampleName();
                    StatusDisplayer.getDefault().setStatusText("Populating " + examplesName + " examples ...");
                    JAXBContext jaxbContextOther = JAXBContext.newInstance(ObjectFactory.class);
                    Unmarshaller unmarshallerOther;
                    unmarshallerOther = jaxbContextOther.createUnmarshaller();
                    Data dataOther = (Data) unmarshallerOther.unmarshal(otherExamplesDefinitionFile);
                    List<Folders> rootFoldersListOther = dataOther.getFolders();
                    CustomtMutableTreeNode child = new CustomtMutableTreeNode(examplesName);
                    child.setExPath(examplesName + File.separator + "src");
                    parseFoldersTree(rootFoldersListOther, child);
                    top.add(child);
                }
            }
        }
    }

    public static void parseFoldersTree(List<Folders> rootFoldersList, CustomtMutableTreeNode parentItem) {
        for (Folders folders : rootFoldersList) {
            // Get list of folder
            List<Folder> folderList = folders.getFolder();
            for (Folder folder : folderList) {
                CustomtMutableTreeNode child = new CustomtMutableTreeNode(folder.getTitle());
                child.setExPath(parentItem.getExPath() + File.separator + folder.getFolderName());
                parseExamples(folder.getExamples(), child);
                parseFoldersTree(folder.getFolders(), child);
                parentItem.add(child);
            }
        }
    }

    public static void parseExamples(List<Examples> examplesList, CustomtMutableTreeNode parentItem) {
        for (Examples examples : examplesList) {
            List<Example> exampleList = examples.getExample();
            for (Example example : exampleList) {
                parseExample(example, parentItem);
            }
        }
    }

    //=========================================================================
    public static void parseExample(Example example, CustomtMutableTreeNode parentItem) {
        CustomtMutableTreeNode child = new CustomtMutableTreeNode(example.getTitle());
        child.setExample(example);
        child.setExPath(parentItem.getExPath() + File.separator + example.getFolderName());
        parentItem.add(child);
    }
}
