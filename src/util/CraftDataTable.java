package util;
import util.ExcelDataAccess;
import util.FrameworkException;

public class CraftDataTable {
	private final String datatablePath;
	private final String datatableName;
	private String dataReferenceIdentifier = "#";
	private String currentTestcase;
	private int currentIteration = 0;
	private int currentSubIteration = 0;

	public void setDataReferenceIdentifier(String dataReferenceIdentifier) {
		if (dataReferenceIdentifier.length() != 1) {
			throw new FrameworkException(
					"The data reference identifier must be a single character!");
		} else {
			this.dataReferenceIdentifier = dataReferenceIdentifier;
		}
	}

	public CraftDataTable(String datatablePath, String datatableName) {
		this.datatablePath = datatablePath;
		this.datatableName = datatableName;
	}

	public void setCurrentRow(String currentTestcase, int currentIteration,
			int currentSubIteration) {
		this.currentTestcase = currentTestcase;
		this.currentIteration = currentIteration;
		this.currentSubIteration = currentSubIteration;
	}

	private void checkPreRequisites() {
		if (this.currentTestcase == null) {
			throw new FrameworkException(
					"CDataTable.currentTestCase is not set!");
		} else if (this.currentIteration == 0) {
			throw new FrameworkException(
					"CDataTable.currentIteration is not set!");
		} else if (this.currentSubIteration == 0) {
			throw new FrameworkException(
					"CDataTable.currentSubIteration is not set!");
		}
	}

	public String getData(String datasheetName, String fieldName) {
		this.checkPreRequisites();
		ExcelDataAccess testDataAccess = new ExcelDataAccess(
				this.datatablePath, this.datatableName);
		testDataAccess.setDatasheetName(datasheetName);
		int rowNum = testDataAccess.getRowNum(this.currentTestcase, 0, 1);
		if (rowNum == -1) {
			throw new FrameworkException("The test case \""
					+ this.currentTestcase + "\""
					+ "is not found in the test data sheet \"" + datasheetName
					+ "\"!");
		} else {
			rowNum = testDataAccess.getRowNum(
					Integer.toString(this.currentIteration), 1, rowNum);
			if (rowNum == -1) {
				throw new FrameworkException("The iteration number \""
						+ this.currentIteration + "\"" + "of the test case \""
						+ this.currentTestcase + "\""
						+ "is not found in the test data sheet \""
						+ datasheetName + "\"!");
			} else {
				rowNum = testDataAccess.getRowNum(
						Integer.toString(this.currentSubIteration), 2, rowNum);
				if (rowNum == -1) {
					throw new FrameworkException("The sub iteration number \""
							+ this.currentSubIteration + "\""
							+ "under iteration number \""
							+ this.currentIteration + "\""
							+ "of the test case \"" + this.currentTestcase
							+ "\"" + "is not found in the test data sheet \""
							+ datasheetName + "\"!");
				} else {
					String dataValue = testDataAccess.getValue(rowNum,
							fieldName);
					if (dataValue.startsWith(this.dataReferenceIdentifier)) {
						dataValue = this.getCommonData(fieldName, dataValue);
					}

					return dataValue;
				}
			}
		}
	}

	private String getCommonData(String fieldName, String dataValue) {
		ExcelDataAccess commonDataAccess = new ExcelDataAccess(
				this.datatablePath, "Common Testdata");
		commonDataAccess.setDatasheetName("Common_Testdata");
		String dataReferenceId = dataValue.split(this.dataReferenceIdentifier)[1];
		int rowNum = commonDataAccess.getRowNum(dataReferenceId, 0, 1);
		if (rowNum == -1) {
			throw new FrameworkException(
					"The common test data row identified by \""
							+ dataReferenceId + "\""
							+ "is not found in the common test data sheet!");
		} else {
			dataValue = commonDataAccess.getValue(rowNum, fieldName);
			return dataValue;
		}
	}

	public void putData(String datasheetName, String fieldName, String dataValue) {
		this.checkPreRequisites();
		ExcelDataAccess testDataAccess = new ExcelDataAccess(
				this.datatablePath, this.datatableName);
		testDataAccess.setDatasheetName(datasheetName);
		int rowNum = testDataAccess.getRowNum(this.currentTestcase, 0, 1);
		if (rowNum == -1) {
			throw new FrameworkException("The test case \""
					+ this.currentTestcase + "\""
					+ "is not found in the test data sheet \"" + datasheetName
					+ "\"!");
		} else {
			rowNum = testDataAccess.getRowNum(
					Integer.toString(this.currentIteration), 1, rowNum);
			if (rowNum == -1) {
				throw new FrameworkException("The iteration number \""
						+ this.currentIteration + "\"" + "of the test case \""
						+ this.currentTestcase + "\""
						+ "is not found in the test data sheet \""
						+ datasheetName + "\"!");
			} else {
				rowNum = testDataAccess.getRowNum(
						Integer.toString(this.currentSubIteration), 2, rowNum);
				if (rowNum == -1) {
					throw new FrameworkException("The sub iteration number \""
							+ this.currentSubIteration + "\""
							+ "under iteration number \""
							+ this.currentIteration + "\""
							+ "of the test case \"" + this.currentTestcase
							+ "\"" + "is not found in the test data sheet \""
							+ datasheetName + "\"!");
				} else {
					Class arg5 = CraftDataTable.class;
					synchronized (CraftDataTable.class) {
						testDataAccess.setValue(rowNum, fieldName, dataValue);
					}
				}
			}
		}
	}

	public String getExpectedResult(String fieldName) {
		this.checkPreRequisites();
		ExcelDataAccess expectedResultsAccess = new ExcelDataAccess(
				this.datatablePath, this.datatableName);
		expectedResultsAccess.setDatasheetName("Parametrized_Checkpoints");
		int rowNum = expectedResultsAccess
				.getRowNum(this.currentTestcase, 0, 1);
		if (rowNum == -1) {
			throw new FrameworkException("The test case \""
					+ this.currentTestcase + "\""
					+ "is not found in the parametrized checkpoints sheet!");
		} else {
			rowNum = expectedResultsAccess.getRowNum(
					Integer.toString(this.currentIteration), 1, rowNum);
			if (rowNum == -1) {
				throw new FrameworkException("The iteration number \""
						+ this.currentIteration + "\"" + "of the test case \""
						+ this.currentTestcase + "\""
						+ "is not found in the parametrized checkpoints sheet!");
			} else {
				rowNum = expectedResultsAccess.getRowNum(
						Integer.toString(this.currentSubIteration), 2, rowNum);
				if (rowNum == -1) {
					throw new FrameworkException(
							"The sub iteration number \""
									+ this.currentSubIteration
									+ "\""
									+ "under iteration number \""
									+ this.currentIteration
									+ "\""
									+ "of the test case \""
									+ this.currentTestcase
									+ "\""
									+ "is not found in the parametrized checkpoints sheet!");
				} else {
					String dataValue = expectedResultsAccess.getValue(rowNum,
							fieldName);
					return dataValue;
				}
			}
		}
	}
}