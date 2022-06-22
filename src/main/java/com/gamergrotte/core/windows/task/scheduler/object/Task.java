package com.gamergrotte.core.windows.task.scheduler.object;

import com.gamergrotte.core.windows.task.scheduler.TaskService;
import com.gamergrotte.core.windows.task.scheduler.exception.TaskServiceException;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * @author Felix Lerch
 */
@Data
@AllArgsConstructor
public class Task {
	
	private String hostName;
	private String taskName;
	
	private LocalDateTime nextExecutionTime;
	
	private String status;
	private String loginMode;
	
	private LocalDateTime lastExecutionTime;
	private String lastExecutionResult;
	
	private String author;
	
	private String execution;
	private String startIn;
	
	private String comment;
	private String executionStatus;
	
	private String idleTime;
	private String powerManagement;
	
	private String executionUser;
	
	private String deleteTaskMoved;
	private String killTaskAfterXTime;
	
	private String scheduleType;
	private String scheduleStartTime;
	private String scheduleStartDate;
	private String scheduleEndDate;
	private String scheduleDays;
	private String scheduleMonths;
	private String scheduleRepeatEvery;
	private String scheduleRepeatEndTime;
	private String scheduleRepeatEndDuration;
	private String scheduleRepeatKill;

	private TaskService taskService;

	/**
	 * Running the Task with the Windows Task Scheduler.
	 *
	 * @return Task executed
	 */
	public boolean execute() throws IOException, InterruptedException, TaskServiceException {
		return taskService.executeTask(taskName);
	}

	/**
	 * Ending the Task with the Windows Task Scheduler.
	 *
	 * @return Task ended
	 */
	public boolean end() throws IOException, InterruptedException, TaskServiceException {
		return taskService.endTask(taskName);
	}

	/**
	 * Delete the Task with the Windows Task Scheduler.
	 *
	 * @return Task deleted
	 */
	public boolean delete() throws TaskServiceException, IOException, InterruptedException {
		return taskService.deleteTask(taskName);
	}
}
