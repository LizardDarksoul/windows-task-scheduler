package com.gamergrotte.core.windows.task.scheduler;

import com.gamergrotte.core.windows.task.scheduler.exception.TaskServiceException;
import com.gamergrotte.core.windows.task.scheduler.object.Task;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.util.List;

public class TEST_TaskService {

    TaskService taskService;

    @BeforeEach
    public void Start() {
        taskService = new TaskService();
    }

    @AfterEach
    public void End() {

    }

    @Test
    public void GetTask() throws IOException, InterruptedException, TaskServiceException {
        List<Task> tasks = taskService.getAllTasks();

        Assertions.assertNotNull(tasks);
        Assertions.assertTrue(tasks.size() > 0);
    }

    @ParameterizedTest
    @ValueSource(strings = {"\\Microsoft\\Windows\\Device Information\\Device", "\\Microsoft\\Windows\\Device Information\\Device User", "\\Microsoft\\Windows\\Diagnosis\\RecommendedTroubleshootingScanner"})
    public void GetTask(String taskpath) throws IOException, InterruptedException, TaskServiceException {
        List<Task> taskList = taskService.getSingleTasks(taskpath);

        Assertions.assertNotNull(taskList);
        Assertions.assertTrue(taskList.size() > 0);
    }

}
