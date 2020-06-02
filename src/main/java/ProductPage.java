import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class ProductPage extends BasePage {
	public ProductPage(WebDriver driver) {
		super(driver);
	}

	@FindBy(xpath = "//span[@class=\"current-price-value\"]")
	private WebElement price;
	@FindBy(xpath = "//div[contains(@class,'buttons-wrapper')]//button[contains(@class,'buy-btn')]")
	private WebElement buttonBuy;

	public double getAndSavePrice(Product product) {
		wait.until(ExpectedConditions.elementToBeClickable(buttonBuy));
		double productPrice = Double.parseDouble(price.getAttribute("data-price-value"));
		int idProduct = Integer.parseInt(driver.findElement(By.xpath("//div[@class=\"price-item-code\"]/span")).getText());
		product.setPrice(productPrice);
		product.setId(idProduct);
		return productPrice;
	}

	public ProductPage buyProduct(Product product) {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(buttonBuy));
			buttonBuy.click();
		} catch (org.openqa.selenium.NoSuchElementException e) {
			wait.until(ExpectedConditions.elementToBeClickable(buttonBuy));
			buttonBuy.click();
		}
		new CartPage(driver).waitToRefresh();
		ProductTotalPrice.addCountAndPrice(product.getName(), product.getPrice());
		return this;
	}
}
