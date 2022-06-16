package com.ic.core.modules.windows.task.scheduler.service;

import org.jetbrains.annotations.NotNull;

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
		ProcessBuilder pb = new ProcessBuilder(commands);
		Process p = pb.start();
		
		// capture standard output
		String stdOutput;
		try (InputStream is = p.getInputStream()) {
			stdOutput = consumeStream(is);
		}
		
		// capture standard error
		String stdError;
		try (InputStream es = p.getErrorStream()) {
			stdError = consumeStream(es);
		}
		
		// wait for the external process to end
		p.waitFor();
		
		if (!stdError.isEmpty()) {
			System.out.println(stdError);
		}
		return stdOutput;
	}
	
	private static String consumeStream(InputStream is) throws IOException {
		final StringBuilder sb = new StringBuilder();
		try (InputStreamReader isr = new InputStreamReader(is, StandardCharsets.UTF_8);
			 BufferedReader br = new BufferedReader(isr)
		) {
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
