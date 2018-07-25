package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class GetPropertiesValue {
	
	public String getValue(String key) throws IOException,FileNotFoundException
	{
		File file = new File("Env.properties");
		FileInputStream fileInput = new FileInputStream(file);
		Properties properties = new Properties();
		properties.load(fileInput);
		fileInput.close();
		String keyValue = properties.getProperty(key);
		return keyValue;
		
	}

}
