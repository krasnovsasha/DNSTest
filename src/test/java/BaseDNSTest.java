import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class BaseDNSTest {
	private static WebDriver driver;
	private static Properties properties = SingleProperty.getInstance().getProperties();
	protected static String urlMainPage;

	public static WebDriver getDriver() {
		return driver;
	}

	@BeforeClass
	public static void setUp() {
		setUpBrowser(System.getProperty("browser", "chrome"));
		urlMainPage = properties.getProperty("dnsURL");
		driver.manage().window().maximize();
		driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
	}

	@AfterClass
	public static void tearDown() {
		driver.quit();
	}

	private static void setUpBrowser(String browserName) {
		String[] osName = System.getProperty("os.name").toLowerCase().split("\\s");
		switch (osName[0]) {
			case "linux":
				switch (browserName) {
					case "chrome":
						System.setProperty(properties.getProperty("driverChrome"), properties.getProperty("pathToDriverChromeLin"));
						driver = new ChromeDriver();
						break;
					case "firefox":
						System.setProperty(properties.getProperty("driverFirefox"), properties.getProperty("pathToDriverFirefoxLin"));
						driver = new FirefoxDriver();
						break;
				}
				break;
			case "windows":
				switch (browserName) {
					case "chrome":
						System.setProperty(properties.getProperty("driverChrome"), properties.getProperty("pathToDriverChromeWin"));
						driver = new ChromeDriver();
						break;
					case "firefox":
						System.setProperty(properties.getProperty("driverFirefox"), properties.getProperty("pathToDriverFirefoxWin"));
						driver = new FirefoxDriver();
						break;
				}
				break;
			case "mac":
				switch (browserName) {
					case "chrome":
						System.setProperty(properties.getProperty("driverChrome"), properties.getProperty("pathToDriverChromeMac"));
						driver = new ChromeDriver();
						break;
					case "firefox":
						System.setProperty(properties.getProperty("driverFirefox"), properties.getProperty("pathToDriverFirefoxMac"));
						driver = new FirefoxDriver();
						break;
				}
				break;
		}
	}

}
