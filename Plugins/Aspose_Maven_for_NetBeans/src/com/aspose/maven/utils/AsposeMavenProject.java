
package com.aspose.maven.utils;

import java.util.HashMap;
import java.util.Map;
/**
 * @author Adeel Ilyas <adeel.ilyas@aspose.com>
 */
public class AsposeMavenProject {

    private static Map<String, AsposeJavaAPI> apiList = new HashMap<String, AsposeJavaAPI>();

    static {
        //ASPOSE_CELLS
        AsposeJavaAPI asposeCells = new AsposeJavaAPI();
        asposeCells.set_name(AsposeConstants.ASPOSE_CELLS);
        asposeCells.set_mavenRepositoryURL("http://maven.aspose.com/artifactory/ext-release-local/com/aspose/aspose-cells/");
        getApiList().put(AsposeConstants.ASPOSE_CELLS, asposeCells);

        //ASPOSE_WORDS
        AsposeJavaAPI asposeWords = new AsposeJavaAPI();
        asposeWords.set_name(AsposeConstants.ASPOSE_WORDS);
        asposeWords.set_mavenRepositoryURL("http://maven.aspose.com/artifactory/ext-release-local/com/aspose/aspose-words/");
        getApiList().put(AsposeConstants.ASPOSE_WORDS, asposeWords);

        //ASPOSE_PDF
        AsposeJavaAPI asposePDF = new AsposeJavaAPI();
        asposePDF.set_name(AsposeConstants.ASPOSE_PDF);
        asposePDF.set_mavenRepositoryURL("http://maven.aspose.com/artifactory/ext-release-local/com/aspose/aspose-pdf/");
        getApiList().put(AsposeConstants.ASPOSE_PDF, asposePDF);
        //ASPOSE_Slides
        AsposeJavaAPI asposeSlides = new AsposeJavaAPI();
        asposeSlides.set_name(AsposeConstants.ASPOSE_SLIDES);
        asposeSlides.set_mavenRepositoryURL("http://maven.aspose.com/artifactory/ext-release-local/com/aspose/aspose-slides/");
        getApiList().put(AsposeConstants.ASPOSE_SLIDES, asposeSlides);

        //ASPOSE_BarCode
        AsposeJavaAPI asposeBarcode = new AsposeJavaAPI();
        asposeBarcode.set_name(AsposeConstants.ASPOSE_BARCODE);
        asposeBarcode.set_mavenRepositoryURL("http://maven.aspose.com/artifactory/ext-release-local/com/aspose/aspose-barcode/");
        getApiList().put(AsposeConstants.ASPOSE_BARCODE, asposeBarcode);

        //ASPOSE_Tasks
        AsposeJavaAPI asposeTasks = new AsposeJavaAPI();
        asposeTasks.set_name(AsposeConstants.ASPOSE_TASKS);
        asposeTasks.set_mavenRepositoryURL("http://maven.aspose.com/artifactory/ext-release-local/com/aspose/aspose-tasks/");
        getApiList().put(AsposeConstants.ASPOSE_TASKS, asposeTasks);

        //ASPOSE_Email
        AsposeJavaAPI asposeEmail = new AsposeJavaAPI();
        asposeEmail.set_name(AsposeConstants.ASPOSE_EMAIL);
        asposeEmail.set_mavenRepositoryURL("http://maven.aspose.com/artifactory/ext-release-local/com/aspose/aspose-email/");
        getApiList().put(AsposeConstants.ASPOSE_EMAIL, asposeEmail);

        //ASPOSE_OCR
        AsposeJavaAPI asposeOCR = new AsposeJavaAPI();
        asposeOCR.set_name(AsposeConstants.ASPOSE_OCR);
        asposeOCR.set_mavenRepositoryURL("http://maven.aspose.com/artifactory/ext-release-local/com/aspose/aspose-ocr/");
        getApiList().put(AsposeConstants.ASPOSE_OCR, asposeOCR);

        //ASPOSE_Imaging
        AsposeJavaAPI asposeImaging = new AsposeJavaAPI();
        asposeImaging.set_name(AsposeConstants.ASPOSE_IMAGING);
        asposeImaging.set_mavenRepositoryURL("http://maven.aspose.com/artifactory/ext-release-local/com/aspose/aspose-imaging/");
        getApiList().put(AsposeConstants.ASPOSE_IMAGING, asposeImaging);

        //ASPOSE_Diagram
        AsposeJavaAPI asposeDiagram = new AsposeJavaAPI();
        asposeDiagram.set_name(AsposeConstants.ASPOSE_DIAGRAM);
        asposeDiagram.set_mavenRepositoryURL("http://maven.aspose.com/artifactory/ext-release-local/com/aspose/aspose-diagram/");
        getApiList().put(AsposeConstants.ASPOSE_DIAGRAM, asposeDiagram);
    }


    public static Map<String, AsposeJavaAPI> getApiList() {
        return apiList;
    }

    public static void clearSelection() {
        for (AsposeJavaAPI component : getApiList().values()) {
            component.set_selected(false);
        }

    }
}