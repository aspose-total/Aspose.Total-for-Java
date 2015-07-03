
package com.aspose.maven.utils;

import org.netbeans.api.progress.aggregate.AggregateProgressFactory;
import org.netbeans.api.progress.aggregate.ProgressContributor;

/**
 * @author Adeel Ilyas <adeel.ilyas@aspose.com>
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
