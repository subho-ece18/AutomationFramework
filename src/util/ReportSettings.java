package util;

import java.io.File;

public class ReportSettings {
	private final String reportPath;
	private final String reportName;
	private String projectName = "";
	private int logLevel = 4;
	public boolean generateExcelReports = true;
	public boolean generateHtmlReports = true;
	public boolean takeScreenshotFailedStep = true;
	public boolean takeScreenshotPassedStep = false;
	public boolean linkScreenshotsToTestLog = true;
	public boolean linkTestLogsToSummary = true;
	private String dateFormatString = "dd-MMM-yyyy hh:mm:ss a";

	public String getReportPath() {
		return this.reportPath;
	}

	public String getReportName() {
		return this.reportName;
	}

	public String getProjectName() {
		return this.projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public int getLogLevel() {
		return this.logLevel;
	}

	public void setLogLevel(int logLevel) {
		if (logLevel < 0) {
			logLevel = 0;
		}

		if (logLevel > 5) {
			logLevel = 5;
		}

		this.logLevel = logLevel;
	}

	public String getDateFormatString() {
		return this.dateFormatString;
	}

	public void setDateFormatString(String dateFormatString) {
		this.dateFormatString = dateFormatString;
	}

	public ReportSettings(String reportPath, String reportName) {
		boolean reportPathExists = (new File(reportPath)).isDirectory();
		if (!reportPathExists) {
			throw new FrameworkException(
					"The given report path does not exist!");
		} else {
			this.reportPath = reportPath;
			this.reportName = reportName;
		}
	}
}
