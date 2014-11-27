package org.mvnsearch.docker.fig.orchestration;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.nio.file.Files;
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
        if (options != null && options.length > 0) {
            Collections.addAll(commands, options);
        }
        ProcessBuilder pb = new ProcessBuilder(commands);
        pb.directory(projDir);
        pb.redirectErrorStream(true);
        Map<String, String> env = pb.environment();
        if (properties.contains("DOCKER_HOST")) {
            env.put("DOCKER_HOST", properties.getProperty("DOCKER_HOST"));
        }
        try {
            LOG.info(command + "ing the fig...");
            Process p = pb.start();
            LOG.info(IOUtils.toString(p.getInputStream()).trim());
        } catch (Exception e) {
            LOG.error("failed to start fig", e);
        }
    }

}
