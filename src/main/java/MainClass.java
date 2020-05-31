import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

public class MainClass {
	private static WebDriver driver;

	public static void main(String[] args) {
		System.setProperty("webdriver.chrome.driver", "drivers/driversLin/chromedriver");
		driver = new ChromeDriver();
		driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		MainPage mainPage = new MainPage(driver);
		Product playstation = new Product("playstation");
		driver.get("https://www.dns-shop.ru/");
		mainPage.findItem(playstation);
		ResultsPage resultsPage = new ResultsPage(driver);
		resultsPage.chooseProduct(resultsPage.getProduct());
		ProductPage productPage = new ProductPage(driver);
		productPage.getAndSavePrice(playstation);



		driver.quit();
	}
}
