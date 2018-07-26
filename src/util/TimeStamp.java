package util;

import util.FrameworkException;
import util.FrameworkParameters;
import util.Settings;
import util.Util;
import java.io.File;
import java.util.Properties;

public class TimeStamp {
	private static volatile String path;

	public static String getInstance() {
		if (path == null) {
			Class arg = TimeStamp.class;
			synchronized (TimeStamp.class) {
				if (path == null) {
					FrameworkParameters frameworkParameters = FrameworkParameters
							.getInstance();
					if (frameworkParameters.getRelativePath() == null) {
						throw new FrameworkException(
								"FrameworkParameters.relativePath is not set!");
					}

					if (frameworkParameters.getRunConfiguration() == null) {
						throw new FrameworkException(
								"FrameworkParameters.runConfiguration is not set!");
					}

					Properties properties = Settings.getInstance();
					path = frameworkParameters.getRunConfiguration()
							+ Util.getFileSeparator()
							+ "Run_"
							+ Util.getCurrentFormattedTime(
									properties.getProperty("DateFormatString"))
									.replace(" ", "_").replace(":", "-");
					String reportPathWithTimeStamp = frameworkParameters
							.getRelativePath()
							+ Util.getFileSeparator()
							+ "Results" + Util.getFileSeparator() + path;
					(new File(reportPathWithTimeStamp)).mkdirs();
				}
			}
		}

		return path;
	}

	public static void setPath(String myPath) {
		if (path == null) {
			Class arg0 = TimeStamp.class;
			synchronized (TimeStamp.class) {
				if (path == null) {
					FrameworkParameters frameworkParameters = FrameworkParameters
							.getInstance();
					if (frameworkParameters.getRelativePath() == null) {
						throw new FrameworkException(
								"FrameworkParameters.relativePath is not set!");
					}

					path = myPath;
					String reportPathWithTimeStamp = frameworkParameters
							.getRelativePath()
							+ Util.getFileSeparator()
							+ "Results" + Util.getFileSeparator() + path;
					(new File(reportPathWithTimeStamp)).mkdirs();
				}

			}
		} else {
			throw new FrameworkException(
					"The timestamp path is already initialized!");
		}
	}

	public Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}
}
