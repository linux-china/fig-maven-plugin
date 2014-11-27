package org.mvnsearch.docker.fig.orchestration;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.*;

/**
 * fig orchestrator
 *
 * @author linux_china
 */
public class FigOrchestrator {
    private static final Logger LOG = LoggerFactory.getLogger(FigOrchestrator.class);
    private File projDir;
    private Properties properties;

    public FigOrchestrator(File projDir, Properties properties) {
        this.projDir = projDir;
        this.properties = properties;
    }

    /**
     * start container
     */
    public void start() {
        executeFigCommands("up", "-d");
    }

    /**
     * stop and kill all containers
     */
    public void stop() {
        executeFigCommands("stop");
        executeFigCommands("rm", "--force");
    }

    private void executeFigCommands(String command, String... options) {
        List<String> commands = new ArrayList<String>();
        commands.add("fig");
        commands.add(command);
        if (options != null) {
            Collections.addAll(commands, options);
        }
        ProcessBuilder pb = new ProcessBuilder(commands);
        pb.directory(projDir);
        Map<String, String> env = pb.environment();
        if (properties.contains("DOCKER_HOST")) {
            env.put("DOCKER_HOST", properties.getProperty("DOCKER_HOST"));
        }
        try {
            Process p = pb.start();
            if (p.waitFor() != 0) {
                LOG.error("Failed to " + command + " fig");
                System.out.println(IOUtils.toString(p.getErrorStream()));
            } else {
                LOG.info("Fig " + command + ":");
                System.out.println(IOUtils.toString(p.getInputStream()));
            }
        } catch (Exception e) {
            LOG.error("failed to start fig", e);
        }
    }
}
