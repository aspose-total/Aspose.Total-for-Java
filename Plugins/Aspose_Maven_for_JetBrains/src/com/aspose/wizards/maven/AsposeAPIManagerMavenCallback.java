package com.aspose.wizards.maven;

import com.aspose.utils.AsposeMavenDependenciesManager;
import com.aspose.utils.execution.CallBackHandler;
import com.intellij.openapi.progress.ProgressIndicator;
import org.jetbrains.annotations.NotNull;

/**
 * Created by Adeel Ilyas on 11/11/2014.
 */
public class AsposeAPIManagerMavenCallback implements CallBackHandler {

    @Override
    public boolean executeTask(@NotNull ProgressIndicator progressIndicator) {

        progressIndicator.setIndeterminate(true);
        progressIndicator.setText("Creating Aspose Maven Project");
        AsposeMavenDependenciesManager comManager = new AsposeMavenDependenciesManager();

        return comManager.retrieveAsposeMavenDependencies(progressIndicator);
    }
}
