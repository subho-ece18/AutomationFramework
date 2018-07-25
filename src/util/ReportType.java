package util;

public interface ReportType {
	void initializeTestLog();

	void addTestLogHeading(String arg0);

	void addTestLogSubHeading(String arg0, String arg1, String arg2, String arg3);

	void addTestLogTableHeadings();

	void addTestLogSection(String arg0);

	void addTestLogSubSection(String arg0);

	void updateTestLog(String arg0, String arg1, String arg2, Status arg3,
			String arg4);

	void addTestLogFooter(String arg0, int arg1, int arg2);

	void initializeResultSummary();

	void addResultSummaryHeading(String arg0);

	void addResultSummarySubHeading(String arg0, String arg1, String arg2,
			String arg3);

	void addResultSummaryTableHeadings();

	void updateResultSummary(String arg0, String arg1, String arg2,
			String arg3, String arg4);

	void addResultSummaryFooter(String arg0, int arg1, int arg2);
}
