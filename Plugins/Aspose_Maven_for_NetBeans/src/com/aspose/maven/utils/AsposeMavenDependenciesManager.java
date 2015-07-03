package com.aspose.maven.utils;

import com.aspose.maven.artifacts.Metadata;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import org.netbeans.api.progress.aggregate.ProgressContributor;
import org.openide.awt.StatusDisplayer;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * @author Adeel Ilyas <adeel.ilyas@aspose.com>
 */
public class AsposeMavenDependenciesManager {

    public AsposeMavenDependenciesManager() {

    }

    public String readURLContents(String Url) throws MalformedURLException, IOException {
        URL url = new URL(Url);
        URLConnection con = url.openConnection();
        InputStream in = con.getInputStream();
        String encoding = con.getContentEncoding();
        encoding = encoding == null ? "UTF-8" : encoding;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buf = new byte[8192];
        int len = 0;
        while ((len = in.read(buf)) != -1) {
            baos.write(buf, 0, len);
        }
        String body = new String(baos.toByteArray(), encoding);
        return body;
    }

    public Metadata getProductMavenDependency(String productMavenRepositoryUrl) {

        Metadata data = null;

        try {
            String productMavenInfo;
            productMavenInfo = readURLContents(productMavenRepositoryUrl + AsposeConstants.MAVEN_METADATA_FILE);
            JAXBContext jaxbContext = JAXBContext.newInstance(com.aspose.maven.artifacts.ObjectFactory.class);
            Unmarshaller unmarshaller;
            unmarshaller = jaxbContext.createUnmarshaller();
            data = (Metadata) unmarshaller.unmarshal(new StreamSource(new StringReader(productMavenInfo)));

            String remoteArtifactFile = productMavenRepositoryUrl + data.getVersioning().getLatest() + "/" + data.getArtifactId() + "-" + data.getVersioning().getLatest();

            if (!remoteFileExists(remoteArtifactFile + ".jar")) {
                data.setClassifier(getResolveSupportedJDK(remoteArtifactFile));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return data;
    }

    public static String getResolveSupportedJDK(String ProductURL) {
        String supportedJDKs[] = {"jdk17", "jdk16", "jdk15", "jdk14", "jdk18"};
        String classifier = null;
        for (String jdkCheck : supportedJDKs) {

            if (remoteFileExists(ProductURL + "-" + jdkCheck + ".jar")) {

                classifier = jdkCheck;
                break;
            }
        }
        return classifier;
    }

    public static boolean remoteFileExists(String URLName) {
        try {
            HttpURLConnection.setFollowRedirects(false);
            // note : you may also need
            //        HttpURLConnection.setInstanceFollowRedirects(false)
            HttpURLConnection con
                    = (HttpURLConnection) new URL(URLName).openConnection();
            con.setRequestMethod("HEAD");
            return (con.getResponseCode() == HttpURLConnection.HTTP_OK);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * @return boolean
     */
    public static boolean isInternetConnected() {
        try {
            InetAddress address = InetAddress.getByName(AsposeConstants.INTERNTE_CONNNECTIVITY_PING_URL);
            if (address == null) {
                return false;
            }

        } catch (UnknownHostException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public boolean retrieveAsposeMavenDependencies(ProgressContributor p) {
        try {
            AsposeMavenDependenciesManager.getAsposeProjectMavenDependencies().clear();

            p.progress(1);
            byte i = 2;
            for (AsposeJavaAPI component : AsposeMavenProject.getApiList().values()) {

                if (component.is_selected()) {
                    StatusDisplayer.getDefault().setStatusText("Retrieving " + component.get_name() + " Maven Artifact...");
                    Metadata productMavenDependency = getProductMavenDependency(component.get_mavenRepositoryURL());
                    if (productMavenDependency != null) {
                        AsposeMavenDependenciesManager.getAsposeProjectMavenDependencies().add(productMavenDependency);
                    }
                    p.progress(i++);
                }
            }
        } catch (Exception rex) {
           AsposeMavenDependenciesManager.getAsposeProjectMavenDependencies().clear();
        }
        return !AsposeMavenDependenciesManager.getAsposeProjectMavenDependencies().isEmpty();
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
     * @param zipFile
     * @param newPath
     * @throws ZipException
     * @throws IOException
     */
    static public void extractFolder(String zipFile, String newPath) throws ZipException, IOException {

        int BUFFER = 2048;
        File file = new File(zipFile);

        ZipFile zip = new ZipFile(file);
        new File(newPath).mkdir();
        @SuppressWarnings("rawtypes")
        Enumeration zipFileEntries = zip.entries();
        while (zipFileEntries.hasMoreElements()) {
            ZipEntry entry = (ZipEntry) zipFileEntries.nextElement();
            String currentEntry = entry.getName();
            File destFile = new File(newPath, currentEntry);
            File destinationParent = destFile.getParentFile();
            destinationParent.mkdirs();

            if (!entry.isDirectory()) {
                BufferedInputStream is = new BufferedInputStream(zip
                        .getInputStream(entry));
                int currentByte;
                byte data[] = new byte[BUFFER];

                FileOutputStream fos = new FileOutputStream(destFile);
                BufferedOutputStream dest = new BufferedOutputStream(fos,
                        BUFFER);
                while ((currentByte = is.read(data, 0, BUFFER)) != -1) {
                    dest.write(data, 0, currentByte);
                }
                dest.flush();
                dest.close();
                is.close();
            }
        }
    }

    public static List<Metadata> getAsposeProjectMavenDependencies() {
        return asposeProjectMavenDependencies;
    }

    public static void clearAsposeProjectMavenDependencies() {
        asposeProjectMavenDependencies.clear();
    }

    private static final List<Metadata> asposeProjectMavenDependencies = new ArrayList<Metadata>();

}
