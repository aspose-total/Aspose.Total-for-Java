/*
 * Copyright 2000-2009 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.aspose.wizards.maven;

import com.aspose.maven.apis.artifacts.Metadata;
import com.aspose.utils.AsposeConstants;
import com.aspose.utils.AsposeMavenDependenciesManager;
import com.aspose.utils.AsposeMavenUtil;
import com.intellij.codeInsight.actions.ReformatCodeProcessor;
import com.intellij.ide.util.EditorHelper;
import com.intellij.openapi.application.ModalityState;
import com.intellij.openapi.application.Result;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ex.ProjectManagerEx;
import com.intellij.openapi.util.io.FileUtil;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VfsUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;
import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class AsposeMavenModuleBuilderHelper {
    private final MavenId myProjectId;

    private final String myCommandName;
    private Project project;
    private VirtualFile root;

    public AsposeMavenModuleBuilderHelper(@NotNull MavenId projectId, String commaneName, Project project, VirtualFile root) {
        myProjectId = projectId;
        this.project = project;
        this.root = root;
        myCommandName = commaneName;
    }

    public void configure() {
        PsiFile[] psiFiles = PsiFile.EMPTY_ARRAY;
        final VirtualFile pom = new WriteCommandAction<VirtualFile>(project, myCommandName, psiFiles) {
            @Override
            protected void run(Result<VirtualFile> result) throws Throwable {
                VirtualFile file;
                try {
                    file = root.createChildData(this, AsposeConstants.MAVEN_POM_XML);

                    AsposeMavenUtil.runOrApplyMavenProjectFileTemplate(project, file, myProjectId);
                    result.setResult(file);
                } catch (IOException e) {
                    showError(project, e);
                    return;
                }

                updateProjectPom(project, file);


            }
        }.execute().getResultObject();

        if (pom == null) return;

        try {
            System.out.println("Creating Maven project structure ...");
            VfsUtil.createDirectories(root.getPath() + "/src/main/java");
            VfsUtil.createDirectories(root.getPath() + "/src/main/resources");
            VfsUtil.createDirectories(root.getPath() + "/src/test/java");
        } catch (IOException e) {

        }
        // execute when current dialog is closed (e.g. Project Structure)
        AsposeMavenUtil.invokeLater(project, ModalityState.NON_MODAL, new Runnable() {
            public void run() {
                if (!pom.isValid()) return;
                copyMavenConfigurationFiles(pom);


            }
        });
    }

    private void writeXmlDocumentToVirtualFile(VirtualFile pom, Document pomDocument) throws TransformerConfigurationException, TransformerException, IOException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
        DOMSource source = new DOMSource(pomDocument);

        ByteOutputStream bytes = new ByteOutputStream();

        StreamResult result = new StreamResult(bytes);
        transformer.transform(source, result);
        VfsUtil.saveText(pom, bytes.toString());
    }

    private Document getXmlDocument(String mavenPomXmlfile) throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        Document pomDocument = docBuilder.parse(mavenPomXmlfile);

        return pomDocument;
    }

    private void addAsposeMavenRepositoryConfiguration(Document pomDocument, Node projectNode) {
// Adding Aspose Cloud Maven Repository configuration
        Element repositories = pomDocument.createElement("repositories");
        projectNode.appendChild(repositories);
        Element repository = pomDocument.createElement("repository");
        repositories.appendChild(repository);
        Element id = pomDocument.createElement("id");
        id.appendChild(pomDocument.createTextNode("AsposeJavaAPI"));
        Element name = pomDocument.createElement("name");
        name.appendChild(pomDocument.createTextNode("Aspose Java API"));
        Element url = pomDocument.createElement("url");
        url.appendChild(pomDocument.createTextNode("http://maven.aspose.com/artifactory/simple/ext-release-local/"));
        repository.appendChild(id);
        repository.appendChild(name);
        repository.appendChild(url);
    }

    private void addAsposeMavenDependency(Document doc, Element dependenciesTag, Metadata dependency) {
        Element dependencyTag = doc.createElement("dependency");
        dependenciesTag.appendChild(dependencyTag);

        Element groupIdTag = doc.createElement("groupId");
        groupIdTag.appendChild(doc.createTextNode(dependency.getGroupId()));
        dependencyTag.appendChild(groupIdTag);

        Element artifactId = doc.createElement("artifactId");
        artifactId.appendChild(doc.createTextNode(dependency.getArtifactId()));
        dependencyTag.appendChild(artifactId);
        Element version = doc.createElement("version");
        version.appendChild(doc.createTextNode(dependency.getVersioning().getLatest()));
        dependencyTag.appendChild(version);
        if (dependency.getClassifier() != null) {
            Element classifer = doc.createElement("classifier");
            classifer.appendChild(doc.createTextNode(dependency.getClassifier()));
            dependencyTag.appendChild(classifer);
        }
    }

    private void updateProjectPom(final Project project, final VirtualFile pom) {
        try {
            String mavenPomXmlfile = AsposeMavenUtil.getPOMXmlFile(pom);

            Document pomDocument = getXmlDocument(mavenPomXmlfile);

            // Get the root element
            Node projectNode = pomDocument.getFirstChild();

            //Adding Aspose Cloud Maven Repository configuration setting here
            addAsposeMavenRepositoryConfiguration(pomDocument, projectNode);

            // Adding Dependencies here
            Element dependenciesTag = pomDocument.createElement("dependencies");
            projectNode.appendChild(dependenciesTag);

            for (Metadata dependency : getAsposeProjectMavenDependencies()) {

                addAsposeMavenDependency(pomDocument, dependenciesTag, dependency);

            }

            // write the content into maven pom xml file

            writeXmlDocumentToVirtualFile(pom, pomDocument);


        } catch (IOException io) {
            io.printStackTrace();
        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (TransformerException tfe) {
            tfe.printStackTrace();
        } catch (SAXException sae) {
            sae.printStackTrace();
        }
    }

    private static PsiFile getPsiFile(Project project, VirtualFile pom) {
        return PsiManager.getInstance(project).findFile(pom);
    }


    private void copyMavenConfigurationFiles(VirtualFile pom) {
        try {


            String projectPath = project.getBasePath();

            final File workingDir = new File(AsposeMavenDependenciesManager.getAsposeMavenWorkSpace());

            String projectModulefile = projectPath + File.separator + project.getName() + ".iml";
            String projectIdea_compiler_xml = projectPath + File.separator + ".idea" + File.separator + "compiler.xml";
            String projectIdea_misc_xml = projectPath + File.separator + ".idea" + File.separator + "misc.xml";

            FileUtil.copy(new File(workingDir, AsposeMavenDependenciesManager.intelliJMavenFiles.get(0)), new File(projectModulefile));
            FileUtil.copy(new File(workingDir,  AsposeMavenDependenciesManager.intelliJMavenFiles.get(1)), new File(projectIdea_compiler_xml));
            FileUtil.copy(new File(workingDir,  AsposeMavenDependenciesManager.intelliJMavenFiles.get(2)), new File(projectIdea_misc_xml));

            //VirtualFile vf_projectModulefile = LocalFileSystem.getInstance().findFileByPath(projectModulefile);
            //VirtualFile vf_projectIdea_compiler_xml = LocalFileSystem.getInstance().findFileByPath(projectIdea_compiler_xml);
           // VirtualFile vf_projectIdea_misc_xml = LocalFileSystem.getInstance().findFileByPath(projectIdea_misc_xml);

            //updateFileContents(project, vf_projectModulefile, new File(workingDir, "untitled.iml"));
           // updateFileContents(project, vf_projectIdea_compiler_xml, new File(workingDir, "compiler.xml"));
          //  updateFileContents(project, vf_projectIdea_misc_xml, new File(workingDir, "misc.xml"));
            ProjectManagerEx pm = ProjectManagerEx.getInstanceEx();

            pm.reloadProject(project);

            EditorHelper.openInEditor(getPsiFile(project, pom));

        } catch (IOException e) {
            showError(project, e);
            return;
        } catch (Throwable e) {
        }

    }


    private static void showError(Project project, Throwable e) {
        AsposeMavenUtil.showError(project, "Failed to create a Maven project", e);
    }

    private static void updateFileContents(Project project, final VirtualFile vf, final File f) throws Throwable {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        InputStream in = null;
        try {

            in = new FileInputStream(f);

            write(in, bytes);

        } finally {

            if (in != null) {
                in.close();
            }
        }
        VfsUtil.saveText(vf, bytes.toString());

        PsiFile psiFile = PsiManager.getInstance(project).findFile(vf);
        if (psiFile != null) {
            new ReformatCodeProcessor(project, psiFile, null, false).run();
        }
    }

    private static void write(InputStream inputStream, OutputStream os) throws IOException {
        byte[] buf = new byte[1024];
        int len;
        while ((len = inputStream.read(buf)) > 0) {
            os.write(buf, 0, len);
        }
    }

    public static List<Metadata> getAsposeProjectMavenDependencies() {
        return asposeProjectMavenDependencies;
    }

    public static void clearAsposeProjectMavenDependencies() {
        asposeProjectMavenDependencies.clear();
    }

    private static List<Metadata> asposeProjectMavenDependencies = new ArrayList<Metadata>();
}
