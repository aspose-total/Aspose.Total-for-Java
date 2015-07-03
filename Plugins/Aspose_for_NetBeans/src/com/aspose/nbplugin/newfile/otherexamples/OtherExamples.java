/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspose.nbplugin.newfile.otherexamples;

import com.aspose.nbplugin.utils.AsposeConstants;
import com.aspose.nbplugin.utils.AsposeJavaComponent;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Adeel Ilyas
 */
/* Added by adeel.ilyas@aspose.com - Integration of Apache POI Examples / Other FrameWork Examples *
 */
public class OtherExamples {
   
    private final List<ExamplesFrameWork> frameWorkDependencies=new ArrayList<ExamplesFrameWork>();
    private String gitHubExamplesRepositoryLocation;
    private String exampleName;
   

    /**
     * @return the gitHubExamplesRepositoryLocation
     */
    public String getGitHubExamplesRepositoryLocation() {
        return gitHubExamplesRepositoryLocation;
    }

    /**
     * @param gitHubExamplesRepositoryLocation the gitHubExamplesRepositoryLocation to set
     */
    public void setGitHubExamplesRepositoryLocation(String gitHubExamplesRepositoryLocation) {
        this.gitHubExamplesRepositoryLocation = gitHubExamplesRepositoryLocation;
    }
    
        /**
     *
     * @param component
     * @return
     */
    public static String getApachePOIExample(AsposeJavaComponent component) {
        
        String componentName=component.get_name();
        String apachePOIExample=null;
        
        
        if (componentName.equals(AsposeConstants.ASPOSE_WORDS)) {
         apachePOIExample=AsposeConstants.ASPOSE_WORDS_APACHE_POI;
        } else if (componentName.equals(AsposeConstants.ASPOSE_CELLS)) {
         apachePOIExample=AsposeConstants.ASPOSE_CELLS_APACHE_POI;
        } else if (componentName.equals(AsposeConstants.ASPOSE_SLIDES)) {
         apachePOIExample=AsposeConstants.ASPOSE_SLIDES_APACHE_POI;
        } else if (componentName.equals(AsposeConstants.ASPOSE_EMAIL)) {
         apachePOIExample=AsposeConstants.ASPOSE_EMAIL_APACHE_POI;
        }
            return apachePOIExample; 
    }

    /**
     * clear framework dependencies if any
     */
    public void  clearFrameWorkDependency() {
        this.frameWorkDependencies.clear();
    }

    /**
     * @param frameWork the frameWork to add
     */
    public void addFrameWorkDependency(ExamplesFrameWork frameWork) {
        this.frameWorkDependencies.add(frameWork);
    }
    
     /**
     * @return frameWorkDependencies
     */
    public List<ExamplesFrameWork> getFrameWorkDependencies() {
        return this.frameWorkDependencies;
    }

    /**
     * @return the exampleName
     */
    public String getExampleName() {
        return exampleName;
    }

    /**
     * @param exampleName the exampleName to set
     */
    public void setExampleName(String exampleName) {
        this.exampleName = exampleName;
    }
}
