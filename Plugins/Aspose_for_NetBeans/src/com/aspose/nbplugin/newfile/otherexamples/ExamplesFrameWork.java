/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspose.nbplugin.newfile.otherexamples;

import com.aspose.nbplugin.progressmonitor.TasksExecutor;
import com.aspose.nbplugin.utils.AsposeComponentsManager;
import com.aspose.nbplugin.utils.GitHelper;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.openide.awt.StatusDisplayer;
import com.aspose.nbplugin.progressmonitor.AbstractTask;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import org.netbeans.api.progress.aggregate.ProgressContributor;
import org.openide.util.Exceptions;

/**
 *
 * @author Adeel Ilyas
 */
/* Added by adeel.ilyas@aspose.com - Integration of Apache POI Examples / Other FrameWork Examples *
 */
public class ExamplesFrameWork {

    private String frameworkName;
    private final List<LibDependency> libDependencies = new ArrayList<LibDependency>();
    private final FrameWorkStatus frameWorkStatus = new FrameWorkStatus(this);

    /**
     * @return the framworkName
     */
    public String getFrameworkName() {
        return frameworkName;
    }

    /**
     * @param framworkName the framworkName to set
     */
    public void setFrameworkName(String framworkName) {
        this.frameworkName = framworkName;
    }

    /**
     * @param dependency to add the dependencies
     */
    public void addLibDependency(LibDependency dependency) {
        this.libDependencies.add(dependency);
    }

    /**
     * @return libDependencies
     */
    public List<LibDependency> getLibDependencies() {
        return this.libDependencies;
    }

    /**
     * remove any dependencies
     */
    public void clearLibDependencies() {
        this.libDependencies.clear();
    }

    private void downloadFrameWorkLibFromInternet(LibDependency libsDependency, ProgressContributor p) {

        InputStream input;
        int bufferSize = 4096;
        String localPath = getExamplesFrameWorkPath();
        try {
            URL url = new URL(libsDependency.getLibDownloadLink());
            input = url.openStream();
            byte[] buffer = new byte[bufferSize];
            File f = new File(localPath + libsDependency.getLibName());
            OutputStream output = new FileOutputStream(f);
            int bytes = 0;
            long totalLength = AsposeComponentsManager.getFileDownloadLength(libsDependency.getLibDownloadLink());
            long pages = totalLength / bufferSize;
            if (p != null) {
                p.start((int) pages);
            }
            int currentPage = 0;
            try {
                int bytesRead;
                while ((bytesRead = input.read(buffer, 0, buffer.length)) >= 0) {
                    StatusDisplayer.getDefault().setStatusText("Downloading " + libsDependency.getLibName());
                    output.write(buffer, 0, bytesRead);
                    bytes = bytes + buffer.length;
                    currentPage = currentPage + 1;
                    if (p != null) {
                        p.progress(currentPage);
                    }
                }

                output.flush();
                output.close();

            } finally {
                if (p != null) {
                    p.finish();
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    /**
     *
     * @param TasksExecutor
     */
    public void download(TasksExecutor tasksExecutor) {
        if (!getFrameWorkStatus().isFrameWorkSuccessfullyDownloaded()) {
          
            for (final LibDependency libDependency : getLibDependencies()) {
                GitHelper.checkAndCreateFolder(getExamplesFrameWorkPath());
                if (!isFrameworkLibAlreadyDownloaded(libDependency.getLibName())) {
                    // Then download here.
                    if (tasksExecutor == null) {
                        downloadFrameWorkLibFromInternet(libDependency, null);
                    } else {
                        tasksExecutor.addNewTask(new AbstractTask("Downloading " + libDependency.getLibName()) {
                            public void run() {
                                downloadFrameWorkLibFromInternet(libDependency, p);
                            }
                        });
                    }
                }

            }

        }
    }

    /**
     *
     * @return
     */
    public String getExamplesFrameWorkPath() {
        return AsposeComponentsManager.getAsposeHomePath() + "GitSampleRepos" + File.separator + getFrameworkName() + File.separator;
    }

    /**
     *
     * @param libFileName
     * @return
     */
    private boolean isFrameworkLibAlreadyDownloaded(String libFileName) {
        File confirmPath = new File(getExamplesFrameWorkPath() + libFileName);
        if (confirmPath.exists()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     *
     * @param projectPath
     * @return
     */
    public String getExamplesFrameWorkProjectPath(String projectPath) {
        return projectPath + File.separator + "lib" + File.separator +"ExamplesFrameWorks"+ File.separator +getFrameworkName();
    }

 
    /**
     *
     * @param path
     * @return
     */
    public boolean findFrameWorkInPath(String frameworkPath) {

        if (!new File(frameworkPath).exists()) {
            return false;
        }
        boolean isFrameWorkAvailableInPath = true;
        for (LibDependency libDependency : getLibDependencies()) {
            if (!new File(frameworkPath + File.separator + libDependency.getLibName()).exists()) {
                isFrameWorkAvailableInPath = false;
                break;
            }
        }
        return isFrameWorkAvailableInPath;
    }

    public void addFrameWorkInProjectClassPath(String projectPath) {
        String fullFrameworkProjectPath = getExamplesFrameWorkProjectPath(projectPath);
        if (getFrameWorkStatus().isFrameWorkAddedToProjects(projectPath) && !getFrameWorkStatus().isFrameWorkAddedToProjectsClassPath(projectPath)) {
            try {
                String filePath = projectPath + File.separator + "nbproject" + File.separator + "project.properties";
                File in = new File(filePath);
                File out = new File(filePath + "bk");
                BufferedReader reader = new BufferedReader(new FileReader(in));
                PrintWriter writer = new PrintWriter(new FileWriter(out));
                String line = null;
                while ((line = reader.readLine()) != null) {
                    if (line.toString().equals("javac.classpath=")) {
                        line = line + "\\";
                    }
                    writer.println(line);
                    if (line.equals("excludes=")) {

                        for (LibDependency libDependency : getLibDependencies()) {
                            String libraryName = libDependency.getLibName();
                            String libraryPath = fullFrameworkProjectPath + File.separator + libraryName;
                            libraryPath = libraryPath.replace("\\", "\\\\");
                            String libEntry = "file.reference." + libraryName + "=" + libraryPath;
                            writer.println(libEntry);
                        }

                    }
                    if (line.equals("javac.classpath=\\")) {

                        for (LibDependency libDependency : getLibDependencies()) {
                            String libraryName = libDependency.getLibName();
                            String libEntry = "";

                            libEntry = "    ${file.reference." + libraryName + "}:\\";

                            writer.println(libEntry);

                        }
                    }
                }

                reader.close();
                writer.close();
                in.delete();
                out.renameTo(new File(filePath));
                getFrameWorkStatus().getFrameWorkAddedToProjectsClassPath().add(fullFrameworkProjectPath);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    public void addFrameWorkLibrariesToProject(String projectPath) {
        String fullFrameworkProjectPath = getExamplesFrameWorkProjectPath(projectPath);
        if (getFrameWorkStatus().isFrameWorkSuccessfullyDownloaded() && !getFrameWorkStatus().isFrameWorkAddedToProjects(projectPath)) {
            try {
                String sourcePath = getExamplesFrameWorkPath();
                AsposeComponentsManager.copyDirectory(sourcePath, fullFrameworkProjectPath);
            } catch (IOException ex) {
                Exceptions.printStackTrace(ex);
            }
            if (findFrameWorkInPath(fullFrameworkProjectPath)) {
                getFrameWorkStatus().getFrameWorkAddedToProjects().add(fullFrameworkProjectPath);
            }
        }

    }

    /**
     * @return the frameWorkStatus
     */
    public FrameWorkStatus getFrameWorkStatus() {
        return frameWorkStatus;
    }

}
