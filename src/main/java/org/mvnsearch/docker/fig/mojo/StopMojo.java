package org.mvnsearch.docker.fig.mojo;

import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.mvnsearch.docker.fig.orchestration.FigOrchestrator;

/**
 * Stop all the fig's containers.
 *
 * @author linux_china
 */
@Mojo(name = "stop", defaultPhase = LifecyclePhase.POST_INTEGRATION_TEST)
public class StopMojo extends AbstractDockerMojo {

    @Override
    protected void doExecute(FigOrchestrator orchestrator) throws Exception {
        orchestrator.stop();
    }
}
