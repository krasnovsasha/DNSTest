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
		productPage.buyProduct();
		Product detroit = new Product("detroit");
		productPage.findItem(detroit);
		productPage.getAndSavePrice(detroit);
		productPage.buyProduct();
		System.out.println(ProductTotalPrice.getTotalPrices() + "\n" + ProductTotalPrice.getTotalCounts());
		System.out.println(ProductTotalPrice.getTotalPriceOfAll());
		System.out.println(productPage.checkPrices());
		productPage.goToCart();
		System.out.println("Цена на playstation была " + playstation.getPrice());
		System.out.println("Цена была " + detroit.getPrice());
		System.out.println("Общая сохраненная цена " + ProductTotalPrice.getTotalPriceOfAll());
		System.out.println("Проверка общей цены : ");
		CartPage cartPage = new CartPage(driver);
		cartPage.clickRadioButton();
		System.out.println(cartPage.checkPrices());
		System.out.println("Цена на playstation в корзине " + cartPage.getPriceOfProduct());
		System.out.println(cartPage.getTotalPriceOfProductInMenuBar());
		ProductTotalPrice.setTotalPriceInCart(cartPage.getTotalPriceOfProductInMenuBar());
		//удалить из корзины Detroit
		cartPage.deleteProductFromCart(cartPage.getButtonDeleteFromCart());
		//проверить что Detroit нет больше в корзине и что сумма уменьшилась на цену Detroit
		
		driver.quit();
	}
}
