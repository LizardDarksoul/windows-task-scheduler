package com.ic.core.modules.windows.task.scheduler.query;

import com.ic.core.modules.windows.task.scheduler.object.Task;
import com.ic.core.modules.windows.task.scheduler.service.CommandInvoker;
import com.univocity.parsers.common.record.Record;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;

import java.io.IOException;
import java.io.StringReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Felix Lerch
 */
public class Query {
	
	public List<Task> getSingleTasks(String server, String user, String password, String taskPath) throws IOException, InterruptedException {
		List<String> commands = List.of("cmd.exe", "/c", "chcp 65001 && schtasks.exe /Query /fo CSV /v /nh /s " + server + " /u " + user + " /p " + password + " /tn \"" + taskPath + "\"");
		String output = CommandInvoker.submitCommands(commands);
		
		return parseCSV(output);
	}
	
	public List<Task> getSingleTasks(String taskPath) throws IOException, InterruptedException {
		List<String> commands = List.of("cmd.exe", "/c", "chcp 65001 && schtasks.exe /Query /fo CSV /v /nh /tn \"" + taskPath + "\"");
		String output = CommandInvoker.submitCommands(commands);
		
		return parseCSV(output);
	}
	
	public List<Task> getAllTasks(String server, String user, String password) throws IOException, InterruptedException {
		List<String> commands = List.of("cmd.exe", "/c", "chcp 65001 && schtasks.exe /Query /fo CSV /v /nh /s " + server + " /u " + user + " /p " + password);
		String output = CommandInvoker.submitCommands(commands);
		
		return parseCSV(output);
	}
	
	public List<Task> getAllTasks() throws IOException, InterruptedException {
		List<String> commands = List.of("cmd.exe", "/c", "chcp 65001 && schtasks.exe /Query /fo CSV /v /nh");
		String output = CommandInvoker.submitCommands(commands);
		
		return parseCSV(output);
	}
	
	private List<Task> parseCSV(String csv) {
		CsvParser parser = new CsvParser(new CsvParserSettings());
		List<Record> recordList = parser.parseAllRecords(new StringReader(csv));
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
		List<Task> list = new ArrayList<>();
		for (Record record : recordList) {
			try {
				Task recordTask = new Task(
						record.getString(0)
						, record.getString(1)
						, record.getString(2).equals("Nicht zutreffend") ? null : LocalDateTime.parse(record.getString(2), formatter)
						, record.getString(3)
						, record.getString(4)
						, record.getString(5).equals("Nicht zutreffend") ? null : LocalDateTime.parse(record.getString(5), formatter)
						, record.getString(6)
						, record.getString(7)
						, record.getString(8)
						, record.getString(9)
						, record.getString(10)
						, record.getString(11)
						, record.getString(12)
						, record.getString(13)
						, record.getString(14)
						, record.getString(15)
						, record.getString(16)
						, record.getString(18)
						, record.getString(19)
						, record.getString(20)
						, record.getString(21)
						, record.getString(22)
						, record.getString(23)
						, record.getString(24)
						, record.getString(25)
						, record.getString(26)
						, record.getString(27)
				);
				list.add(recordTask);
			} catch (Exception ex) {
			
			}
		}
		return list;
	}
	
}
