package supportlibraries;

import util.FrameworkException;
import util.FrameworkParameters;
import util.Util;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class Settings {
	private static Properties properties = loadFromPropertiesFile();

	public static Properties getInstance() {
		return properties;
	}

	private static Properties loadFromPropertiesFile() {
		FrameworkParameters frameworkParameters = FrameworkParameters
				.getInstance();
		if (frameworkParameters.getRelativePath() == null) {
			throw new FrameworkException(
					"FrameworkParameters.relativePath is not set!");
		} else {
			Properties properties = new Properties();

			try {
				properties.load(new FileInputStream(frameworkParameters
						.getRelativePath()
						+ Util.getFileSeparator()
						+ "Global Settings.properties"));
				return properties;
			} catch (FileNotFoundException arg2) {
				arg2.printStackTrace();
				throw new FrameworkException(
						"FileNotFoundException while loading the Global Settings file");
			} catch (IOException arg3) {
				arg3.printStackTrace();
				throw new FrameworkException(
						"IOException while loading the Global Settings file");
			}
		}
	}

	public Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}
}
