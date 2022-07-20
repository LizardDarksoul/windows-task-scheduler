package com.gamergrotte.core.windows.task.scheduler.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * Command Invoker
 * <p>Executing commands on the command line with the {@link ProcessBuilder}
 *
 * @author Felix Lerch
 */
public class CommandInvoker {

    /**
     * Execute command on the command line using the {@link ProcessBuilder}
     *
     * @param commands commandsto execute
     * @return Output of the command line
     * @throws IOException          Exception while running process
     * @throws InterruptedException Exception while waiting for process
     */
    public static String submitCommands(List<String> commands) throws IOException, InterruptedException {
        ProcessBuilder processBuilder = new ProcessBuilder(commands);
        Process process = processBuilder.start();

        String processOutput;
        try (InputStream is = process.getInputStream()) {
            processOutput = processStream(is);
        }

        String processErrorOutput;
        try (InputStream es = process.getErrorStream()) {
            processErrorOutput = processStream(es);
        }

        process.waitFor();

        if (!processErrorOutput.isEmpty()) {
            System.out.println(processErrorOutput);
        }
        return processOutput;
    }

    private static String processStream(InputStream is) throws IOException {
        final StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
            String line;
            int linenum = 0;
            while ((line = br.readLine()) != null) {
                if (linenum > 0)
                    sb.append(line).append("\n");
                linenum++;
            }
        }
        return sb.toString();
    }

}
