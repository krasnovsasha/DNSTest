import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class SingleProperty {
	private Properties properties = new Properties();
	private static SingleProperty INSTANCE = null;

	private SingleProperty() {
		try {
			properties.load(new FileInputStream("environment/.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static SingleProperty getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new SingleProperty();
		}
		return INSTANCE;
	}

	public Properties getProperties() {
		return properties;
	}
}
