/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspose.nbplugin.utils;

import com.aspose.nbplugin.newfile.otherexamples.OtherExamplesManager;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Administrator
 */
public class AsposeJavaComponents {

    public static Map<String, AsposeJavaComponent> list = new HashMap<String, AsposeJavaComponent>();

    public AsposeJavaComponents() {
        list.clear();
        //if(!compInitialized)
        {
            //ASPOSE_CELLS
            AsposeJavaComponent asposeCells = new AsposeJavaComponent();
            asposeCells.set_downloadUrl("http://community.aspose.com/community/secureservices/asposedownloads/java/aspose.cells.aspx");
            asposeCells.get_downloadFileName();
            asposeCells.set_name(AsposeConstants.ASPOSE_CELLS);
            asposeCells.set_remoteExamplesRepository(AsposeConstants.GIT_EX_URL_CELLS);

            // Added by adeel.ilyas@aspose.com - Integration of Apache POI Examples / Other FrameWork Examples
            asposeCells.addOtherFrameworkExamples(OtherExamplesManager.getPOIExamples(asposeCells));
            // adeel.ilyas@aspose.com

            list.put(AsposeConstants.ASPOSE_CELLS, asposeCells);

            //ASPOSE_WORDS
            AsposeJavaComponent asposeWords = new AsposeJavaComponent();
            asposeWords.set_downloadUrl("http://community.aspose.com/community/secureservices/asposedownloads/java/aspose.words.aspx");
            asposeWords.get_downloadFileName();
            asposeWords.set_name(AsposeConstants.ASPOSE_WORDS);
            asposeWords.set_remoteExamplesRepository(AsposeConstants.GIT_EX_URL_WORDS);

            // Added by adeel.ilyas@aspose.com - Integration of Apache POI Examples / Other FrameWork Examples
            asposeWords.addOtherFrameworkExamples(OtherExamplesManager.getPOIExamples(asposeWords));
            // adeel.ilyas@aspose.com
            
            list.put(AsposeConstants.ASPOSE_WORDS, asposeWords);

            //ASPOSE_PDF
            AsposeJavaComponent asposePDF = new AsposeJavaComponent();
            asposePDF.set_downloadUrl("http://community.aspose.com/community/secureservices/asposedownloads/java/aspose.pdf.aspx");
            asposePDF.get_downloadFileName();
            asposePDF.set_name(AsposeConstants.ASPOSE_PDF);
            asposePDF.set_remoteExamplesRepository(AsposeConstants.GIT_EX_URL_PDF);
            list.put(AsposeConstants.ASPOSE_PDF, asposePDF);

            //ASPOSE_Slides
            AsposeJavaComponent asposeSlides = new AsposeJavaComponent();
            asposeSlides.set_downloadUrl("http://community.aspose.com/community/secureservices/asposedownloads/java/aspose.slides.aspx");
            asposeSlides.get_downloadFileName();
            asposeSlides.set_name(AsposeConstants.ASPOSE_SLIDES);
            asposeSlides.set_remoteExamplesRepository(AsposeConstants.GIT_EX_URL_SLIDES);
            
            // Added by adeel.ilyas@aspose.com - Integration of Apache POI Examples / Other FrameWork Examples
            asposeSlides.addOtherFrameworkExamples(OtherExamplesManager.getPOIExamples(asposeSlides));
            // adeel.ilyas@aspose.com
            
            list.put(AsposeConstants.ASPOSE_SLIDES, asposeSlides);

            //ASPOSE_PDF KIT
            AsposeJavaComponent asposePDFKit = new AsposeJavaComponent();
            asposePDFKit.set_downloadUrl("http://community.aspose.com/community/secureservices/asposedownloads/java/aspose.pdf.kit.aspx");
            asposePDFKit.get_downloadFileName();
            asposePDFKit.set_name(AsposeConstants.ASPOSE_PDF_KIT);
            list.put(AsposeConstants.ASPOSE_PDF_KIT, asposePDFKit);

            //ASPOSE_BarCode
            AsposeJavaComponent asposeBarcode = new AsposeJavaComponent();
            asposeBarcode.set_downloadUrl("http://community.aspose.com/community/secureservices/asposedownloads/java/aspose.barcode.aspx");
            asposeBarcode.get_downloadFileName();
            asposeBarcode.set_name(AsposeConstants.ASPOSE_BARCODE);
            asposeBarcode.set_remoteExamplesRepository(AsposeConstants.GIT_EX_URL_BARCODE);
            list.put(AsposeConstants.ASPOSE_BARCODE, asposeBarcode);

            //ASPOSE_MetaFiles
            AsposeJavaComponent asposeMetafiles = new AsposeJavaComponent();
            asposeMetafiles.set_downloadUrl("http://community.aspose.com/community/secureservices/asposedownloads/java/aspose.metafiles.aspx");
            asposeMetafiles.get_downloadFileName();
            asposeMetafiles.set_name(AsposeConstants.ASPOSE_METAFILES);
            list.put(AsposeConstants.ASPOSE_METAFILES, asposeMetafiles);

            //ASPOSE_Email
            AsposeJavaComponent asposeEmail = new AsposeJavaComponent();
            asposeEmail.set_downloadUrl("http://community.aspose.com/community/secureservices/asposedownloads/java/aspose.email.aspx");
            asposeEmail.get_downloadFileName();
            asposeEmail.set_name(AsposeConstants.ASPOSE_EMAIL);
            asposeEmail.set_remoteExamplesRepository(AsposeConstants.GIT_EX_URL_EMAIL);
            
            // Added by adeel.ilyas@aspose.com - Integration of Apache POI Examples / Other FrameWork Examples
            asposeEmail.addOtherFrameworkExamples(OtherExamplesManager.getPOIExamples(asposeEmail));
            // adeel.ilyas@aspose.com
            
            list.put(AsposeConstants.ASPOSE_EMAIL, asposeEmail);

            //ASPOSE_OCR
            AsposeJavaComponent asposeOCR = new AsposeJavaComponent();
            asposeOCR.set_downloadUrl("http://community.aspose.com/community/secureservices/asposedownloads/java/aspose.ocr.aspx");
            asposeOCR.get_downloadFileName();
            asposeOCR.set_name(AsposeConstants.ASPOSE_OCR);
            list.put(AsposeConstants.ASPOSE_OCR, asposeOCR);

            //ASPOSE_Imaging
            AsposeJavaComponent asposeImaging = new AsposeJavaComponent();
            asposeImaging.set_downloadUrl("http://community.aspose.com/community/secureservices/asposedownloads/java/aspose.imaging.aspx");
            asposeImaging.set_downloadFileName("");
            asposeImaging.set_name(AsposeConstants.ASPOSE_IMAGING);
            asposeImaging.set_remoteExamplesRepository(AsposeConstants.GIT_EX_URL_IMAGING);
            list.put(AsposeConstants.ASPOSE_IMAGING, asposeImaging);

            //ASPOSE_TASKS
            AsposeJavaComponent asposeTasks = new AsposeJavaComponent();
            asposeTasks.set_downloadUrl("http://community.aspose.com/community/secureservices/asposedownloads/java/aspose.tasks.aspx");
            asposeTasks.set_downloadFileName("aspose.tasks.zip");
            asposeTasks.set_name(AsposeConstants.ASPOSE_TASKS);
            asposeTasks.set_remoteExamplesRepository(AsposeConstants.GIT_EX_URL_TASKS);
            list.put(AsposeConstants.ASPOSE_TASKS, asposeTasks);

            //ASPOSE_DIAGRAM
            AsposeJavaComponent asposeDiagram = new AsposeJavaComponent();
            asposeDiagram.set_downloadUrl("http://community.aspose.com/community/secureservices/asposedownloads/java/aspose.diagram.aspx");
            asposeDiagram.get_downloadFileName();
            asposeDiagram.set_name(AsposeConstants.ASPOSE_DIAGRAM);
            asposeDiagram.set_remoteExamplesRepository(AsposeConstants.GIT_EX_URL_DIAGRAM);
            list.put(AsposeConstants.ASPOSE_DIAGRAM, asposeDiagram);

            //compInitialized = true;
        }
    }
}