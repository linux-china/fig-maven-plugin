package org.mvnsearch.docker.fig.mojo;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import org.mvnsearch.docker.fig.orchestration.FigOrchestrator;
import org.mvnsearch.docker.fig.util.MavenLogAppender;

import java.io.File;
import java.util.Properties;

public abstract class AbstractDockerMojo extends AbstractMojo {

    /**
     * The docker, e.g. -Dfig.docker.host=tcp://192.168.59.103:2375
     */
    @Parameter(property = "fig.docker.host")
    private String dockerHost;


    @Component
    private MavenProject project;

    @Override
    public final void execute() throws MojoExecutionException, MojoFailureException {
        MavenLogAppender.setLog(getLog());
        // not great eh
        final Properties properties = properties();
        if (dockerHost != null && !dockerHost.isEmpty()) {
            properties.put("DOCKER_HOST", dockerHost);
        }
        try {
            doExecute(new FigOrchestrator(project.getBasedir(), properties));
        } catch (Exception e) {
            throw new MojoExecutionException(e.getMessage(), e);
        }
    }


    private Properties properties() {
        final Properties p = new Properties();
        p.putAll(System.getenv());
        p.putAll(System.getProperties());
        final String[] x = new String[]{
                "project.groupId", project.getGroupId(),
                "project.artifactId", project.getArtifactId(),
                "project.version", project.getVersion(),
                "project.name", project.getName(),
                "project.description", project.getDescription(),
                "project.build.finalName", project.getBuild().getFinalName()
        };

        for (int i = 0; i < x.length; i += 2) {
            if (x[i + 1] != null) {
                p.setProperty(x[i], x[i + 1]);
            }
        }
        p.putAll(project.getProperties());
        return p;
    }

    protected abstract void doExecute(FigOrchestrator orchestrator) throws Exception;
}
