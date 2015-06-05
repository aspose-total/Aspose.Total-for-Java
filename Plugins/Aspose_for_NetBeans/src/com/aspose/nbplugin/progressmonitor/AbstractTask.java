/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspose.nbplugin.progressmonitor;

import org.netbeans.api.progress.aggregate.AggregateProgressFactory;
import org.netbeans.api.progress.aggregate.ProgressContributor;

/**
 *
 * @author Adeel
 */
/* Added by adeel.ilyas@aspose.com - Integration of Apache POI Examples / Other FrameWork Examples *
 */
public abstract class AbstractTask extends Thread{
    protected ProgressContributor p = null;
    public AbstractTask(String id) {
        p=AggregateProgressFactory.createProgressContributor(id);
    }
    
    public ProgressContributor getProgressContributor() {
        return p;
    }
}
