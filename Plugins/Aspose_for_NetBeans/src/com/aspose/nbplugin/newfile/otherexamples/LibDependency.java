/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspose.nbplugin.newfile.otherexamples;

/**
 *
 * @author Adeel Ilyas
 */
/* Added by adeel.ilyas@aspose.com - Integration of Apache POI Examples / Other FrameWork Examples *
 */
public class LibDependency {
    private String libName;
    private String libDownloadLink;

    public LibDependency(String libName,String libDownloadLink) {
        this.libName=libName;
        this.libDownloadLink=libDownloadLink;      
    }
    /**
     * @return the libName
     */
    public String getLibName() {
        return libName;
    }

    /**
     * @param libName the libName to set
     */
    public void setLibName(String libName) {
        this.libName = libName;
    }

    /**
     * @return the libDownloadLink
     */
    public String getLibDownloadLink() {
        return libDownloadLink;
    }

    /**
     * @param libDownloadLink the libDownloadLink to set
     */
    public void setLibDownloadLink(String libDownloadLink) {
        this.libDownloadLink = libDownloadLink;
    }
}
