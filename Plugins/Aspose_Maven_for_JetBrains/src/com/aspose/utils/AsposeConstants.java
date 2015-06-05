/**
 * Copyright (c) Aspose 2002-2014. All Rights Reserved.
 *
 * LICENSE: This program is free software; you can redistribute it
 * and/or modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 3
 * of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with this program. If not,
 * see http://opensource.org/licenses/gpl-3.0.html
 *
 * @author Adeel Ilyas <adeel.ilyas@aspose.com>
 *
 */
package com.aspose.utils;

public class AsposeConstants {
    /**
     * AsposeConstants
     */

    //Aspose Java components
    public static final String ASPOSE_CELLS = "Aspose.Cells";
    public static final String ASPOSE_WORDS = "Aspose.Words";
    public static final String ASPOSE_PDF = "Aspose.Pdf";
    public static final String ASPOSE_SLIDES = "Aspose.Slides";
    public static final String ASPOSE_BARCODE = "Aspose.BarCode";
    public static final String ASPOSE_EMAIL = "Aspose.Email";
    public static final String ASPOSE_OCR = "Aspose.OCR";
    public static final String ASPOSE_IMAGING = "Aspose.Imaging";
    public static final String ASPOSE_DIAGRAM = "Aspose.Diagram";
    public static final String ASPOSE_TASKS = "Aspose.Tasks";
    public static final String MAVEN_POM_XML = "pom.xml";


    public static final String INTERNTE_CONNNECTIVITY_PING_URL = "java.sun.com";

    //Messages UI text
    public static final String IS_API_SELECTED = "Please select at least one Aspose component to create a new project";
    public static final String INTERNET_CONNECTION_REQUIRED_MESSAGE_TITLE = "Internet connectivity";
    public static final String MAVEN_INTERNET_CONNECTION_REQUIRED_MESSAGE = "Internet connectivity is not available!\nInternet connectivity is required to retrieve latest Aspose Maven Artifacts";
    //New file messages
    public static final String EXAMPLES_NOT_AVAILABLE_MESSAGE = "This component does not have examples yet, We will add examples soon";
    public static final String EXAMPLES_INTERNET_CONNECTION_REQUIRED_MESSAGE = "Internet connectivity is required to download examples";
    public static final String MAVEN_ARTIFACTS_RETRIEVE_FAIL="Unknown Error!\nCould not retrieve latest Aspose Maven Artifacts!";
   //advertise

    public static boolean printing_allowed = false;

    public static final void println(String message) {
        if (printing_allowed) {
            System.out.println(message);
        }
    }
}
