package com.gamergrotte.core.windows.task.scheduler.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author Felix Lerch
 */
public class CommandInvoker {

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
