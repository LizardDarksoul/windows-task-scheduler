package com.ic.core.modules.windows.task.scheduler.object;

import lombok.AllArgsConstructor;
import lombok.Data;

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
}
