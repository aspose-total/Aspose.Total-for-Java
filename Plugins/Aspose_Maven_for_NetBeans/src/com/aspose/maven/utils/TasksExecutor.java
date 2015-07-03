/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.aspose.maven.utils;

import java.util.ArrayList;
import java.util.List;
import org.netbeans.api.progress.aggregate.AggregateProgressFactory;
import org.netbeans.api.progress.aggregate.AggregateProgressHandle;
import org.netbeans.api.progress.aggregate.ProgressContributor;

/**
 * @author Adeel Ilyas <adeel.ilyas@aspose.com>
 */
public class TasksExecutor {
    private final List<AbstractTask> tasks = new ArrayList<AbstractTask>();
    private final String tasksName;
    public TasksExecutor(String tasksName) {
        this.tasksName=tasksName;
        
    }
    public void addNewTask(AbstractTask task) {
        tasks.add(task);
    }
    
    public void clearTasks() {
        tasks.clear();
    }
    public boolean areThereTasks(){
        return !tasks.isEmpty();
    }
    public void processTasks() {
        ProgressContributor cps[] = new ProgressContributor[tasks.size()];
        int i = 0;
        for (AbstractTask task: tasks) {
            cps[i]=task.getProgressContributor();
            i++;
              
        }
        
        AggregateProgressHandle aph= AggregateProgressFactory.createHandle(tasksName, cps, null, null);
        aph.start();
       
        for (AbstractTask task: tasks) {
            task.start();
            try {
            task.join();
            } catch (InterruptedException ie) {
            }
        }
    }
    
}
