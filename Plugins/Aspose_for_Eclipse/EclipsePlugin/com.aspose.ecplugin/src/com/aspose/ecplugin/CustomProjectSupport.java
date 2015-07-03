
/*
 * Copyright (c) 2001-2013 Aspose Pty Ltd. All Rights Reserved.
 * Author: Mohsan.Raza
 */

package com.aspose.ecplugin;



import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

import org.eclipse.core.resources.ICommand;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import com.aspose.ecplugin.AsposeComponentsManager;

public class CustomProjectSupport {
	/**
	 * For this marvelous project we need to:
	 * - create the default Eclipse project
	 * - add the custom project nature
	 * - create the folder structure
	 *
	 * @param projectName
	 * @param location
	 * @param natureId
	 * @return
	 */
	public static IProject createProject(String projectName, URI location) {
		Assert.isNotNull(projectName);
		Assert.isTrue(projectName.trim().length() > 0);

		IProject project = createBaseProject(projectName, location);
		//project.
		try {
			addNature(project,AsposeConstants.NATURE_ID);
			addNature(project,AsposeConstants.NATURE_ID_JDT);
			addBuildCommand(project);
			String[] paths = { AsposeConstants.SRC_FOLDER, AsposeConstants.BIN_FOLDER,AsposeConstants.LIB_FOLDER };
			addToProjectStructure(project, paths);
			addClassPath(project);
			project.refreshLocal(0, null);

		} catch (CoreException e) {
			e.printStackTrace();
			project = null;
		}

		return project;
	}

	/**
	 * 
	 * @param project
	 * @param libName
	 * @param URL
	 */
	public static void downloadLibrary(IProject project, String libName, String URL)
	{
		URL website;
		try {
			IJavaProject javaProject = JavaCore.create(project);


			IFolder libFolder = project.getFolder(AsposeConstants.LIB_FOLDER);			
			IPackageFragmentRoot libRoot = javaProject.getPackageFragmentRoot(libFolder);
			IPath libFolderPath = libRoot.getPath();
			IPath workspacePath = ResourcesPlugin.getWorkspace().getRoot().getLocation();
			IPath absoluteLibFolderPath=workspacePath.append(libFolderPath);
			@SuppressWarnings("unused")
			String libraryPath = absoluteLibFolderPath.toString();
			String combPath = "lib" + "/" + libName;
			IFile iFile= project.getFile(combPath);
			website = new URL(URL + libName);
			ReadableByteChannel rbc;
			rbc = Channels.newChannel(website.openStream());
			FileOutputStream fos;
			java.io.File file = iFile.getLocation().toFile();
			fos = new FileOutputStream(file);
			fos.getChannel().transferFrom(rbc, 0, 1 << 24);
			fos.close();
			try {
				iFile.refreshLocal(0, null);
			} catch (CoreException e) {
				e.printStackTrace();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 
	 * @param project
	 * @throws JavaModelException
	 */
	public static void addClassPath(IProject project) throws JavaModelException
	{
		IJavaProject javaProject = JavaCore.create(project);
		IClasspathEntry[] newEntries = new IClasspathEntry[2];

		// add a new entry using the path to the container
		Path junitPath = new Path(AsposeConstants.ORG_ECLIPSE_JDT_LAUNCHING_CONTAINER);
		IClasspathEntry junitEntry = JavaCore.newContainerEntry(junitPath);
		newEntries[0] = JavaCore.newContainerEntry(junitEntry.getPath());

		//add src folder
		IFolder sourceFolder = project.getFolder(AsposeConstants.SRC_FOLDER);
		IPackageFragmentRoot srcRoot = javaProject.getPackageFragmentRoot(sourceFolder);
		IClasspathEntry srcPathEntry = JavaCore.newSourceEntry(srcRoot.getPath());
		newEntries[1] = srcPathEntry;
		for(AsposeJavaComponent component:AsposeJavaComponents.list.values())
		{

			if(component.is_selected() && component.is_downloaded())
			{
				IFolder libFolder = project.getFolder(AsposeConstants.LIB_FOLDER + File.separator + component.get_name());
				if(!libFolder.exists())
				{
					try {
						libFolder.create(IResource.NONE,true,null);
					} catch (CoreException e) {
						e.printStackTrace();
					}
				}
				IPackageFragmentRoot libRoot = javaProject.getPackageFragmentRoot(libFolder);				
				File dir = new File(AsposeComponentsManager.getLibaryDownloadPath() + AsposeComponentsManager.removeExtention(component.get_downloadFileName()));
				copyLibsAsResouces(dir.getAbsolutePath(),project, component);	

				try {
					IResource[] libFiles = libFolder.members();
					boolean entriesAdded = false;
					for(int i=0; i< libFiles.length;i++)
					{
						String libName =	libFiles[i].getName();
						IClasspathEntry libPathEntry = JavaCore.newLibraryEntry(libRoot.getPath().append(libName),libRoot.getPath().append(libName),null);
						newEntries = incrementCapacity(newEntries);
						newEntries[newEntries.length -1] = libPathEntry;
						entriesAdded = true;						
					}
					if(entriesAdded)
						javaProject.setRawClasspath(newEntries, null);
				} catch (CoreException e) {
					e.printStackTrace();
				}
			}
		}


	}

	/**
	 * 
	 * @param path
	 * @param project
	 */
	private static void copyLibsAsResouces( String path, IProject project,AsposeJavaComponent component ) {

		File root = new File( path );
		File[] list = root.listFiles();

		for ( File f : list ) {
			if ( f.isDirectory() ) {
				copyLibsAsResouces( f.getAbsolutePath(), project, component );
			}
			else {
				File file = new File(f.getAbsolutePath());
				copyAsResource(project,file.getAbsolutePath(), "lib" + File.separator + component.get_name() + File.separator  + file.getName()/*projectLib*/);
			}
		}
	}
	/**
	 * 
	 * @param list
	 * @return
	 */
	public static IClasspathEntry[] incrementCapacity(IClasspathEntry[] list){    

		IClasspathEntry[] newList = new IClasspathEntry[list.length+1];  
		for (int j = 0; j < list.length; j++ ){  
			newList[j] = list[j];}  
		return newList;  
	}   

	/**
	 * 
	 * @param project
	 * @param srcPath
	 * @param destPath
	 * @return
	 */
	private static boolean copyAsResource(IProject project, String srcPath, String destPath)
	{
		try {
			InputStream is = new BufferedInputStream(new FileInputStream(srcPath));
			IFile file = project.getFile(destPath);
			file.create(is, false, null);
			return true;
		} catch (CoreException e) {
			e.printStackTrace();
			return false;
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		}
	}
	/**
	 * Just do the basics: create a basic project.
	 *
	 * @param location
	 * @param projectName
	 */
	private static IProject createBaseProject(String projectName, URI location) {
		// it is acceptable to use the ResourcesPlugin class
		IProject newProject = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);

		if (!newProject.exists()) {
			URI projectLocation = location;
			IProjectDescription desc = newProject.getWorkspace().newProjectDescription(newProject.getName());
			if (location != null && ResourcesPlugin.getWorkspace().getRoot().getLocationURI().equals(location)) {
				projectLocation = null;
			}

			desc.setLocationURI(projectLocation);
			try {
				newProject.create(desc, null);
				if (!newProject.isOpen()) {
					newProject.open(null);
				}
			} catch (CoreException e) {
				e.printStackTrace();
			}
		}

		return newProject;
	}

	/**
	 * 
	 * @param folder
	 * @throws CoreException
	 */
	private static void createFolder(IFolder folder) throws CoreException {
		IContainer parent = folder.getParent();
		if (parent instanceof IFolder) {
			createFolder((IFolder) parent);
		}
		if (!folder.exists()) {
			folder.create(false, true, null);
		}
	}

	/**
	 * Create a folder structure with a parent root, overlay, and a few child
	 * folders.
	 *
	 * @param newProject
	 * @param paths
	 * @throws CoreException
	 */
	private static void addToProjectStructure(IProject newProject, String[] paths) throws CoreException {
		for (String path : paths) {
			IFolder etcFolders = newProject.getFolder(path);
			createFolder(etcFolders);
		}
	}

	/**
	 * 
	 * @param project
	 * @param natureId
	 * @throws CoreException
	 */
	private static void addNature(IProject project, String natureId) throws CoreException {
		if (!project.hasNature(natureId)) {
			IProjectDescription description = project.getDescription();
			String[] prevNatures = description.getNatureIds();
			String[] newNatures = new String[prevNatures.length + 1];
			System.arraycopy(prevNatures, 0, newNatures, 0, prevNatures.length);
			newNatures[prevNatures.length] = natureId;
			description.setNatureIds(newNatures);

			IProgressMonitor monitor = null;
			project.setDescription(description, monitor);
		}
	}

	/**
	 * 
	 * @param project
	 * @throws CoreException
	 */
	private static void addBuildCommand(IProject project) throws CoreException {
		IProjectDescription description = project.getDescription();
		ICommand[] buildCommands = description.getBuildSpec();
		ICommand command = description.newCommand();
		command.setBuilderName(AsposeConstants.ORG_ECLIPSE_JDT_CORE_JAVA_BUILDER);

		ICommand[] newBuildCommands;
		if ( contains( buildCommands, AsposeConstants.ORG_ECLIPSE_JDT_CORE_JAVA_BUILDER ) ) {
			newBuildCommands = swap( buildCommands, AsposeConstants.ORG_ECLIPSE_JDT_CORE_JAVA_BUILDER, command );
		} else {
			newBuildCommands = insert( buildCommands, command );
		}			

		description.setBuildSpec(newBuildCommands);
		project.setDescription(description, null);
	}

	/**
	 * 
	 * @param sourceCommands
	 * @param command
	 * @return
	 */
	private static ICommand[] insert( ICommand[] sourceCommands, ICommand command ) {
		ICommand[] newCommands = new ICommand[ sourceCommands.length + 1 ];
		newCommands[0] = command;
		for (int i = 0; i < sourceCommands.length; i++ ) {
			newCommands[i+1] = sourceCommands[i];
		}		
		return newCommands;		
	}

	/**
	 * 
	 * @param sourceCommands
	 * @param builderId
	 * @return
	 */
	@SuppressWarnings("unused")
	private static ICommand[] remove( ICommand[] sourceCommands, String builderId ) {
		ICommand[] newCommands = new ICommand[ sourceCommands.length - 1 ];
		int newCommandIndex = 0;
		for (int i = 0; i < sourceCommands.length; i++ ) {
			if ( !sourceCommands[i].getBuilderName( ).equals( builderId ) ) {
				newCommands[newCommandIndex++] = sourceCommands[i];
			}
		}		
		return newCommands;		
	}

	/**
	 * 
	 * @param commands
	 * @param builderId
	 * @return
	 */
	private static boolean contains(ICommand[] commands, String builderId) {
		boolean found = false;
		for (int i = 0; i < commands.length; i++) {
			if (commands[i].getBuilderName().equals(builderId)) {
				found = true;
				break;
			}
		}
		return found;
	}

	/**
	 * 
	 * @param sourceCommands
	 * @param oldBuilderId
	 * @param newCommand
	 * @return
	 */
	private static ICommand[] swap(
			ICommand[] sourceCommands,
			String oldBuilderId,
			ICommand newCommand) 
	{
		ICommand[] newCommands = new ICommand[sourceCommands.length];
		for ( int i = 0; i < sourceCommands.length; i++ ) {
			if ( sourceCommands[i].getBuilderName( ).equals( oldBuilderId ) ) {
				newCommands[i] = newCommand;
			} else {
				newCommands[i] = sourceCommands[i];
			}
		}	
		return newCommands;	
	}

}
