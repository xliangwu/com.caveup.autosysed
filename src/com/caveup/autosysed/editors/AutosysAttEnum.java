package com.caveup.autosysed.editors;

public enum AutosysAttEnum {
	INSERT_JOB("insert_job"), 
	DELETE_JOB("delete_job"), 
	UPDATE_JOB("update_job"),
	JOB_TYPE("job_type"),
	OWNER("owner"),
	COMMAND("command"),
	MACHINE("machine"),
	
	WATCH_FILE("watch_file"),
	DAYS_OF_WEEK("days_of_week"),
	RUN_CALENDAR("run_calendar"),
	EXCLUDE_CALENDAR("exclude_calendar"),
	START_TIMES("start_times"),
	RUN_WINDOW("run_window"),
	START_MINS("start_mins"),
	CONDITION("condition"),
	DESCRIPTION("description"),
	BOX_NAME("box_name"),
	MIN_RUN_ALARM("min_run_alarm"),
	MAX_RUN_ALARM("max_run_alarm"),
	TERM_RUN_TIME("term_run_time"),
	ALARM_IF_FAIL("alarm_if_fail"),
	BOX_TERMINATOR("box_terminator"),
	JOB_TERMINATOR("job_terminator"),
	N_RETRYS("n_retrys"),
	TIMEZONE("timezone"),
	AUTO_DELETE("auto_delete"),
	AUTO_HOLD("auto_hold"),
	PERMISSION("permission"),
	PROFILE("profile"),
	STD_IN_FILE("std_in_file"),
	STD_OUT_FILE("std_out_file"),
	STD_ERR_FILE("std_err_file"),
	JOB_LOAD("job_load"),
	PRIORITY("priority"),
	OVERRIDE_JOB("override_job"),
	MAX_EXIT_SUCCESS("max_exit_success"),
	AVG_RUNTIME("avg_runtime"),
	HEARTBEAT_INTERVAL("heartbeat_interval"),
	WATCH_FILE_MIN_SIZE("watch_file_min_size"),
	WATCH_INTERVAL("watch_interval"),
	CHK_FILES("chk_files"),
	BOX_SUCCESS("box_success"),
	BOX_FAILURE("box_failure"),
	DATE_CONDITIONS("date_conditions");

	private String content;

	/** content **/
	public String getContent() {
		return content;
	}

	private AutosysAttEnum(String content) {
		this.content = content;
	}
}
