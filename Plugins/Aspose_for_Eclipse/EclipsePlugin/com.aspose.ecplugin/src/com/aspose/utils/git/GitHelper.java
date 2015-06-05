/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspose.utils.git;

import java.io.File;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.internal.storage.file.FileRepository;

import com.aspose.ecplugin.AsposeComponentsManager;
import com.aspose.ecplugin.AsposeConstants;
import com.aspose.ecplugin.AsposeJavaComponent;

/**
 *
 * @author Administrator
 */
public class GitHelper {
    private static void updateRepository(String localPath, String remotePath,IProgressMonitor monitor) throws Exception
	{        
		Repository localRepo;
		try
		{
			localRepo = new FileRepository(localPath + "/.git");

			Git git = new Git(localRepo);
			{AsposeConstants.println("Cloning Repository [" + remotePath + "]....");}

			// First try to clone the repository
			try
			{
				Git.cloneRepository().setURI(remotePath).setDirectory(new File(localPath)).call();
				monitor.worked(1);
			}
			catch(Exception ex)
			{
				monitor.worked(1);
				// If clone fails, try to pull the changes
				try
				{
				   
					git.pull().call();
				}
				catch(Exception exPull)
				{
					// Pull also failed. Throw this exception to caller
					{AsposeConstants.println("Pull also failed.");}
					throw exPull; // throw it
				}
			} finally {
				monitor.worked(1);
			}
		}
		catch(Exception ex)
		{
			throw new Exception("Could not download Repository from Github. Error: " + ex.getMessage());
		}
	}

   public static void updateRepository(AsposeJavaComponent component,IProgressMonitor monitor)
   {
       checkAndCreateFolder(getLocalRepositoryPath(component));
       monitor.worked(1);
       try {
           updateRepository(getLocalRepositoryPath(component), component.get_remoteExamplesRepository(),monitor);
       } catch (Exception e) {
    	   e.printStackTrace();
       } finally {
       
       }
   }




	/**
	 * 
	 * @param folderPath
	 */
	public static void checkAndCreateFolder(String folderPath) 
	{
		File folder = new File(folderPath);
		if (!folder.exists()) {
			folder.mkdirs();
		}
	}


	
	/**
	 * 
	 * @param component
	 * @return
	 */
	public static boolean isExamplesDefinitionsPresent(AsposeJavaComponent component)
	{
		File file = new File(getLocalRepositoryPath(component) + File.separator + "Examples.xml");
		return file.exists();
	}
	
	/**
	 * 
	 * @param component
	 * @return
	 */
	public static String getExamplesDefinitionsPath(AsposeJavaComponent component)
	{
		return getLocalRepositoryPath(component) + File.separator + "Examples.xml";
	}
	
	/**
	 * 
	 * @param component
	 * @return
	 */
	public static String  getLocalRepositoryPath(AsposeJavaComponent component)
	{
		return AsposeComponentsManager.getAsposeHomePath() +  "GitSampleRepos" + File.separator + component.get_name();
	}

}
