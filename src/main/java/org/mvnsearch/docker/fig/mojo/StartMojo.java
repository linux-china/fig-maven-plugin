package org.mvnsearch.docker.fig.mojo;

import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.mvnsearch.docker.fig.orchestration.FigOrchestrator;

/**
 * start all the fig's containers
 *
 * @author linux_china
 */
@Mojo(name = "start", defaultPhase = LifecyclePhase.PRE_INTEGRATION_TEST)
public class StartMojo extends AbstractDockerMojo {

    @Override
    protected void doExecute(FigOrchestrator orchestrator) {
        orchestrator.start();
    }
}
