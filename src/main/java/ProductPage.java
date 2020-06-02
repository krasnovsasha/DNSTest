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
	@FindBy(xpath = "//div[contains(@class,'buttons-wrapper')]//button[contains(@class,\"buy-btn\")]")
	private WebElement buttonBuy;
	@FindBy(xpath = "//span[@class='cart-link__price']")
	private WebElement lintToCart;

	public double getAndSavePrice(Product product) {
		wait.until(ExpectedConditions.elementToBeClickable(buttonBuy));
		double productPrice = Double.parseDouble(price.getAttribute("data-price-value"));
		int idProduct = Integer.parseInt(driver.findElement(By.xpath("//div[@class=\"price-item-code\"]/span")).getText());
		product.setPrice(productPrice);
		product.setId(idProduct);
		ProductTotalPrice.addCountAndPrice(product.getName(), productPrice);
		return productPrice;
	}

	public ProductPage buyProduct() {
		wait.until(ExpectedConditions.elementToBeClickable(buttonBuy));
		buttonBuy.click();
		return new ProductPage(driver);
	}
}
