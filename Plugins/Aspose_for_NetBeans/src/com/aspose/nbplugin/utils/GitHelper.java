/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspose.nbplugin.utils;

import com.aspose.nbplugin.newfile.otherexamples.ExamplesFrameWork;
import com.aspose.nbplugin.newfile.otherexamples.OtherExamplesManager;
import com.aspose.nbplugin.newfile.otherexamples.OtherExamples;
import java.io.File;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.netbeans.api.progress.aggregate.ProgressContributor;
import org.openide.awt.StatusDisplayer;

/**
 *
 * @author Administrator
 */
public class GitHelper {

    public static void updateRepository(String localPath, String remotePath) throws Exception {
        Repository localRepo;
        try {
            localRepo = new FileRepository(localPath + "/.git");

            Git git = new Git(localRepo);
            AsposeConstants.println("Clone Repo [" + remotePath + "]....");

            // First try to clone the repository
            try 
            {
                AsposeConstants.println("Cloning...");
                Git.cloneRepository().setURI(remotePath).setDirectory(new File(localPath)).call();
                
                String checkoutId = extractCheckoutId(remotePath);
                
                if ( checkoutId != null )
                {
                    AsposeConstants.println("Checkout with ID.");
                    git.checkout().setName( checkoutId ).call();
                    git.checkout().setCreateBranch( true ).setName( "new-branch" ).setStartPoint( checkoutId ).call();
                }
            } 
            catch (Exception ex) 
            {
                // If clone fails, try to pull the changes
                try 
                {
                    AsposeConstants.println("Clone Failed. Now PULL");
                    git.pull().call();
                    
                    String checkoutId = extractCheckoutId(remotePath);

                    if ( checkoutId != null )
                    {
                        AsposeConstants.println("Checkout with ID.");
                        git.checkout().setName( checkoutId ).call();
                        git.checkout().setCreateBranch( true ).setName( "new-branch" ).setStartPoint( checkoutId ).call();
                    }
                } 
                catch (Exception exPull) 
                {
                    // Pull also failed. Throw this exception to caller
                    {
                        AsposeConstants.println("Pull also failed.");
                    }
                    throw exPull; // throw it
                }
            }
        } 
        catch (Exception ex) 
        {
            throw new Exception("Could not download Repository from Github. Error: " + ex.getMessage());
        }
    }

    private static String extractCheckoutId(String remotePath)
    {
        String checkoutId;
        
        switch (remotePath)
        {
            case AsposeConstants.GIT_EX_URL_WORDS:
                checkoutId = AsposeConstants.GIT_ID_ASPOSE_WORDS;
                break;
                
            case AsposeConstants.GIT_EX_URL_CELLS :
                checkoutId = AsposeConstants.GIT_ID_ASPOSE_CELLS;
                break;

            case AsposeConstants.GIT_EX_URL_PDF :
                checkoutId = AsposeConstants.GIT_ID_ASPOSE_PDF;
                break;

            case AsposeConstants.GIT_EX_URL_SLIDES :
                checkoutId = AsposeConstants.GIT_ID_ASPOSE_SLIDES;
                break;

            case AsposeConstants.GIT_EX_URL_EMAIL :
                checkoutId = AsposeConstants.GIT_ID_ASPOSE_EMAIL;
                break;

            case AsposeConstants.GIT_EX_URL_BARCODE :
                checkoutId = AsposeConstants.GIT_ID_ASPOSE_BARCODE;
                break;

            case AsposeConstants.GIT_EX_URL_IMAGING :
                checkoutId = AsposeConstants.GIT_ID_ASPOSE_IMAGING;
                break;

            case AsposeConstants.GIT_EX_URL_TASKS :
                checkoutId = AsposeConstants.GIT_ID_ASPOSE_TASKS;
                break;

            case AsposeConstants.GIT_EX_URL_OCR :
                checkoutId = AsposeConstants.GIT_ID_ASPOSE_OCR;
                break;

            case AsposeConstants.GIT_EX_URL_DIAGRAM :
                checkoutId = AsposeConstants.GIT_ID_ASPOSE_DIAGRAM;
                break;
                
            default:
                checkoutId = null;
                break;
        }
        
        return checkoutId;
    }
    
    /**
     *
     * @param folderPath
     */
    public static void checkAndCreateFolder(String folderPath) {
        File folder = new File(folderPath);
        if (!folder.exists()) {
            folder.mkdirs();
        }
    }

    /**
     *
     * @param component
     */
    public static void updateRepository(AsposeJavaComponent component, ProgressContributor p) {
        StatusDisplayer.getDefault().setStatusText("Downloading " + component.get_name() + " examples ...");
        checkAndCreateFolder(getLocalRepositoryPath(component));

        try {

            updateRepository(getLocalRepositoryPath(component), component.get_remoteExamplesRepository());
            p.progress(3);
            // Added by adeel.ilyas@aspose.com - Integration of Apache POI Examples / Other FrameWork Examples
            OtherExamplesManager.updateOtherExamplesRepositories(component);
            // adeel.ilyas@aspose.com
            p.progress(3);
        } catch (Exception e) {
        }
    }

    /**
     *
     * @param component
     * @return
     */
    public static boolean isExamplesDefinitionsPresent(AsposeJavaComponent component) {
        Boolean isExamplesDefinitionsPresent = true;
        File file = new File(getLocalRepositoryPath(component) + File.separator + "Examples.xml");
        isExamplesDefinitionsPresent = file.exists();

        if (isExamplesDefinitionsPresent) {
            if (!component.getOtherFrameworkExamples().isEmpty()) {
                for (OtherExamples _otherExample : component.getOtherFrameworkExamples()) {
                    File otherExamplesDefinitionFile = new File(OtherExamplesManager.getOtherExamplesDefinitionsPath(_otherExample, component));
                    if (!otherExamplesDefinitionFile.exists()) {
                        isExamplesDefinitionsPresent = false;
                        break;
                    }
                }
            }
        }
        return isExamplesDefinitionsPresent;
    }

    /**
     *
     * @param component
     * @return
     */
    public static String getExamplesDefinitionsPath(AsposeJavaComponent component) {
        return getLocalRepositoryPath(component) + File.separator + "Examples.xml";
    }

    /**
     *
     * @param component
     * @return
     */
    public static String getLocalRepositoryPath(AsposeJavaComponent component) {
        return AsposeComponentsManager.getAsposeHomePath() + "GitSampleRepos" + File.separator + component.get_name();
    }
}
