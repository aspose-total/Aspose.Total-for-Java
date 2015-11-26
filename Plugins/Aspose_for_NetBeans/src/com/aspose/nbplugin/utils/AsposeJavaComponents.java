/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspose.nbplugin.utils;

import com.aspose.nbplugin.newfile.otherexamples.OtherExamplesManager;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author Administrator
 */
public class AsposeJavaComponents {

    public static Map<String, AsposeJavaComponent> list = new LinkedHashMap<String, AsposeJavaComponent>();

    public AsposeJavaComponents() {
        list.clear();
        //if(!compInitialized)
        {
            //ASPOSE_WORDS
            AsposeJavaComponent asposeWords = new AsposeJavaComponent();
            asposeWords.set_downloadUrl(AsposeConstants.GIT_URL_WORDS);
            asposeWords.get_downloadFileName();
            asposeWords.set_name(AsposeConstants.ASPOSE_WORDS);
            asposeWords.set_remoteExamplesRepository(AsposeConstants.GIT_EX_URL_WORDS);

            // Added by adeel.ilyas@aspose.com - Integration of Apache POI Examples / Other FrameWork Examples
            asposeWords.addOtherFrameworkExamples(OtherExamplesManager.getPOIExamples(asposeWords));
            // adeel.ilyas@aspose.com
            
            list.put(AsposeConstants.ASPOSE_WORDS, asposeWords);

            //ASPOSE_CELLS
            AsposeJavaComponent asposeCells = new AsposeJavaComponent();
            asposeCells.set_downloadUrl(AsposeConstants.GIT_URL_CELLS);
            asposeCells.get_downloadFileName();
            asposeCells.set_name(AsposeConstants.ASPOSE_CELLS);
            asposeCells.set_remoteExamplesRepository(AsposeConstants.GIT_EX_URL_CELLS);

            // Added by adeel.ilyas@aspose.com - Integration of Apache POI Examples / Other FrameWork Examples
            asposeCells.addOtherFrameworkExamples(OtherExamplesManager.getPOIExamples(asposeCells));
            // adeel.ilyas@aspose.com

            list.put(AsposeConstants.ASPOSE_CELLS, asposeCells);

            //ASPOSE_PDF
            AsposeJavaComponent asposePDF = new AsposeJavaComponent();
            asposePDF.set_downloadUrl(AsposeConstants.GIT_URL_PDF);
            asposePDF.get_downloadFileName();
            asposePDF.set_name(AsposeConstants.ASPOSE_PDF);
            asposePDF.set_remoteExamplesRepository(AsposeConstants.GIT_EX_URL_PDF);
            list.put(AsposeConstants.ASPOSE_PDF, asposePDF);

            //ASPOSE_Slides
            AsposeJavaComponent asposeSlides = new AsposeJavaComponent();
            asposeSlides.set_downloadUrl(AsposeConstants.GIT_URL_SLIDES);
            asposeSlides.get_downloadFileName();
            asposeSlides.set_name(AsposeConstants.ASPOSE_SLIDES);
            asposeSlides.set_remoteExamplesRepository(AsposeConstants.GIT_EX_URL_SLIDES);
            
            // Added by adeel.ilyas@aspose.com - Integration of Adpache POI Examples / Other FrameWork Examples
            asposeSlides.addOtherFrameworkExamples(OtherExamplesManager.getPOIExamples(asposeSlides));
            // adeel.ilyas@aspose.com
            
            list.put(AsposeConstants.ASPOSE_SLIDES, asposeSlides);
            
            //ASPOSE_Email
            AsposeJavaComponent asposeEmail = new AsposeJavaComponent();
            asposeEmail.set_downloadUrl(AsposeConstants.GIT_URL_EMAIL);
            asposeEmail.get_downloadFileName();
            asposeEmail.set_name(AsposeConstants.ASPOSE_EMAIL);
            asposeEmail.set_remoteExamplesRepository(AsposeConstants.GIT_EX_URL_EMAIL);
            
            // Added by adeel.ilyas@aspose.com - Integration of Apache POI Examples / Other FrameWork Examples
            asposeEmail.addOtherFrameworkExamples(OtherExamplesManager.getPOIExamples(asposeEmail));
            // adeel.ilyas@aspose.com
            
            list.put(AsposeConstants.ASPOSE_EMAIL, asposeEmail);

            //ASPOSE_BarCode
            AsposeJavaComponent asposeBarcode = new AsposeJavaComponent();
            asposeBarcode.set_downloadUrl(AsposeConstants.GIT_URL_BARCODE);
            asposeBarcode.get_downloadFileName();
            asposeBarcode.set_name(AsposeConstants.ASPOSE_BARCODE);
            asposeBarcode.set_remoteExamplesRepository(AsposeConstants.GIT_EX_URL_BARCODE);
            list.put(AsposeConstants.ASPOSE_BARCODE, asposeBarcode);
            
            //ASPOSE_Imaging
            AsposeJavaComponent asposeImaging = new AsposeJavaComponent();
            asposeImaging.set_downloadUrl(AsposeConstants.GIT_URL_IMAGING);
            asposeImaging.get_downloadFileName();
            asposeImaging.set_name(AsposeConstants.ASPOSE_IMAGING);
            asposeImaging.set_remoteExamplesRepository(AsposeConstants.GIT_EX_URL_IMAGING);
            list.put(AsposeConstants.ASPOSE_IMAGING, asposeImaging);

            //ASPOSE_TASKS
            AsposeJavaComponent asposeTasks = new AsposeJavaComponent();
            asposeTasks.set_downloadUrl(AsposeConstants.GIT_URL_TASKS);
            asposeTasks.get_downloadFileName();
            asposeTasks.set_name(AsposeConstants.ASPOSE_TASKS);
            asposeTasks.set_remoteExamplesRepository(AsposeConstants.GIT_EX_URL_TASKS);
            list.put(AsposeConstants.ASPOSE_TASKS, asposeTasks);

            //ASPOSE_OCR
            AsposeJavaComponent asposeOCR = new AsposeJavaComponent();
            asposeOCR.set_downloadUrl(AsposeConstants.GIT_URL_OCR);
            asposeOCR.get_downloadFileName();
            asposeOCR.set_name(AsposeConstants.ASPOSE_OCR);
            asposeOCR.set_remoteExamplesRepository(AsposeConstants.GIT_EX_URL_OCR);
            list.put(AsposeConstants.ASPOSE_OCR, asposeOCR);            

            //ASPOSE_DIAGRAM
            AsposeJavaComponent asposeDiagram = new AsposeJavaComponent();
            asposeDiagram.set_downloadUrl(AsposeConstants.GIT_URL_DIAGRAM);
            asposeDiagram.get_downloadFileName();
            asposeDiagram.set_name(AsposeConstants.ASPOSE_DIAGRAM);
            asposeDiagram.set_remoteExamplesRepository(AsposeConstants.GIT_EX_URL_DIAGRAM);
            list.put(AsposeConstants.ASPOSE_DIAGRAM, asposeDiagram);

            //compInitialized = true;
        }
    }
}