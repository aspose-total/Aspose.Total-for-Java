/*
 * Copyright (c) 2001-2015 Aspose Pty Ltd. All Rights Reserved.
 * Author: Mohsan.Raza
 */

package com.aspose.ecplugin;

import java.util.HashMap;
import java.util.Map;

public class AsposeJavaComponents {
	public static Map<String, AsposeJavaComponent> list = new HashMap<String, AsposeJavaComponent>();

	static {
		// ASPOSE_CELLS
		AsposeJavaComponent asposeCells = new AsposeJavaComponent();
		asposeCells.set_productUrl(AsposeConstants.GIT_URL_CELLS);
		asposeCells.get_downloadFileName();
		asposeCells.set_name(AsposeConstants.ASPOSE_CELLS);
		asposeCells.set_remoteExamplesRepository(AsposeConstants.GIT_EX_URL_CELLS);
		list.put(AsposeConstants.ASPOSE_CELLS, asposeCells);

		// ASPOSE_WORDS
		AsposeJavaComponent asposeWords = new AsposeJavaComponent();
		asposeWords.set_productUrl(AsposeConstants.GIT_URL_WORDS);
		asposeWords.get_downloadFileName();
		asposeWords.set_name(AsposeConstants.ASPOSE_WORDS);
		asposeWords.set_remoteExamplesRepository(AsposeConstants.GIT_EX_URL_WORDS);
		list.put(AsposeConstants.ASPOSE_WORDS, asposeWords);

		// ASPOSE_PDF
		AsposeJavaComponent asposePDF = new AsposeJavaComponent();
		asposePDF.set_productUrl(AsposeConstants.GIT_URL_PDF);
		asposePDF.get_downloadFileName();
		asposePDF.set_name(AsposeConstants.ASPOSE_PDF);
		asposePDF.set_remoteExamplesRepository(AsposeConstants.GIT_EX_URL_PDF);
		list.put(AsposeConstants.ASPOSE_PDF, asposePDF);

		// ASPOSE_Slides
		AsposeJavaComponent asposeSlides = new AsposeJavaComponent();
		asposeSlides.set_productUrl(AsposeConstants.GIT_URL_SLIDES);
		asposeSlides.get_downloadFileName();
		asposeSlides.set_name(AsposeConstants.ASPOSE_SLIDES);
		asposeSlides.set_remoteExamplesRepository(AsposeConstants.GIT_EX_URL_SLIDES);
		list.put(AsposeConstants.ASPOSE_SLIDES, asposeSlides);

		// ASPOSE_BarCode
		AsposeJavaComponent asposeBarcode = new AsposeJavaComponent();
		asposeBarcode.set_productUrl(AsposeConstants.GIT_URL_BARCODE);
		asposeBarcode.get_downloadFileName();
		asposeBarcode.set_name(AsposeConstants.ASPOSE_BARCODE);
		asposeBarcode.set_remoteExamplesRepository(AsposeConstants.GIT_EX_URL_BARCODE);
		list.put(AsposeConstants.ASPOSE_BARCODE, asposeBarcode);

		// ASPOSE_Tasks
		AsposeJavaComponent asposeTasks = new AsposeJavaComponent();
		asposeTasks.set_productUrl(AsposeConstants.GIT_URL_TASKS);
		asposeTasks.get_downloadFileName();
		asposeTasks.set_name(AsposeConstants.ASPOSE_TASKS);
		asposeTasks.set_remoteExamplesRepository(AsposeConstants.GIT_EX_URL_TASKS);
		list.put(AsposeConstants.ASPOSE_TASKS, asposeTasks);

		//ASPOSE_MetaFiles 
		AsposeJavaComponent asposeMetafiles = new AsposeJavaComponent();
		asposeMetafiles.set_productUrl(
				"http://community.aspose.com/community/secureservices/asposedownloads/java/aspose.metafiles.aspx"); 
		asposeMetafiles.get_downloadFileName();
		asposeMetafiles.set_name(AsposeConstants.ASPOSE_METAFILES);
		list.put(AsposeConstants.ASPOSE_METAFILES, asposeMetafiles);
		
		// ASPOSE_Email
		AsposeJavaComponent asposeEmail = new AsposeJavaComponent();
		asposeEmail.set_productUrl(AsposeConstants.GIT_URL_EMAIL);
		asposeEmail.get_downloadFileName();
		asposeEmail.set_name(AsposeConstants.ASPOSE_EMAIL);
		asposeEmail.set_remoteExamplesRepository(AsposeConstants.GIT_EX_URL_EMAIL);
		list.put(AsposeConstants.ASPOSE_EMAIL, asposeEmail);

		// ASPOSE_OCR
		AsposeJavaComponent asposeOCR = new AsposeJavaComponent();
		asposeOCR.set_productUrl(AsposeConstants.GIT_URL_OCR);
		asposeOCR.get_downloadFileName();
		asposeOCR.set_name(AsposeConstants.ASPOSE_OCR);
		asposeOCR.set_remoteExamplesRepository(AsposeConstants.GIT_EX_URL_OCR);
		list.put(AsposeConstants.ASPOSE_OCR, asposeOCR);

		// ASPOSE_Imaging
		AsposeJavaComponent asposeImaging = new AsposeJavaComponent();
		asposeImaging.set_productUrl(AsposeConstants.GIT_URL_IMAGING);
		asposeImaging.get_downloadFileName();
		asposeImaging.set_name(AsposeConstants.ASPOSE_IMAGING);
		asposeImaging.set_remoteExamplesRepository(AsposeConstants.GIT_EX_URL_IMAGING);
		list.put(AsposeConstants.ASPOSE_IMAGING, asposeImaging);

		// ASPOSE_Diagram
		AsposeJavaComponent asposeDiagram = new AsposeJavaComponent();
		asposeDiagram.set_productUrl(AsposeConstants.GIT_URL_DIAGRAM);
		asposeDiagram.get_downloadFileName();
		asposeDiagram.set_name(AsposeConstants.ASPOSE_DIAGRAM);
		asposeDiagram.set_remoteExamplesRepository(AsposeConstants.GIT_EX_URL_DIAGRAM);
		list.put(AsposeConstants.ASPOSE_DIAGRAM, asposeDiagram);
	}

	public static void clearSelection() {
		for (AsposeJavaComponent component : AsposeJavaComponents.list.values()) {
			component.set_selected(false);
		}

	}

}
