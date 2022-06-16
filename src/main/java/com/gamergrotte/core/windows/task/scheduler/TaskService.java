package com.gamergrotte.core.windows.task.scheduler;

import com.gamergrotte.core.windows.task.scheduler.object.Task;
import com.gamergrotte.core.windows.task.scheduler.service.CommandInvoker;
import com.univocity.parsers.common.record.Record;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;
import lombok.Getter;
import lombok.NonNull;

import java.io.IOException;
import java.io.StringReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class TaskService {

    @Getter
    public String host;
    @Getter
    public String user;
    @Getter
    public String password;

    public boolean remote;

    /**
     * Create a TaskService for managing Tasks.
     * <p>This will only be for managing local Tasks.
     */
    public TaskService() {
        remote = false;
    }

    /**
     * Create a TaskService for managing Tasks.
     * <p>This will be able for managing Tasks of a remote computer.
     *
     * @param host hostname of the remote computer
     * @param user windows username for using the remote computer <p>Example: <code>&#60;DOMAIN&#62;//&#60;username&#62;</code>
     * @param password windows password for using the remote computer
     */
    public TaskService(String host, String user, String password) {
        setRemote(host, user, password);
    }

    /**
     * Setting remote access for managing tasks of a remote computer
     *
     * @param host hostname of the remote computer
     * @param user windows username for using the remote computer <p>Example: <code>&#60;DOMAIN&#62;//&#60;username&#62;</code>
     * @param password windows password for using the remote computer
     */
    public void setRemote(@NonNull String host, @NonNull String user, @NonNull String password) {
        this.host= host;
        this.user = user;
        this.password = password;
        remote = true;
    }

    private String getRemoteCommand() {
        return "/s " + host + " /u " + user + " /p " + password;
    }

    private String getTaskCommand() {
        String command = "schtasks.exe /Query /fo CSV /v /nh";
        if (remote) {
            command += "" + getRemoteCommand();
        }
        return command;
    }

    private String getEncodingCommand() {
        return "chcp 65001";
    }

    /**
     * Getting a Single Tasks from the Windows Scheduler.
     *
     * @param taskPath Path of the task.<p>Only using the full path of the task.
     * @return List of the scheduled Actions of the given Task
     * @throws IOException
     * @throws InterruptedException
     */
    public List<Task> getSingleTasks(String taskPath) throws IOException, InterruptedException {
        List<String> commands = List.of("cmd.exe", "/c", getEncodingCommand() + " && " + getTaskCommand() + " /tn \"" + taskPath + "\"");
        String output = CommandInvoker.submitCommands(commands);

        return parseCSV(output);
    }

    /**
     * Getting all Tasks from the Windows Scheduler.
     *
     * @return List of all scheduled Actions
     * @throws IOException
     * @throws InterruptedException
     */
    public List<Task> getAllTasks() throws IOException, InterruptedException {
        List<String> commands = List.of("cmd.exe", "/c", getEncodingCommand() + " && " + getTaskCommand());
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
