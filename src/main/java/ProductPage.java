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
	@FindBy(xpath = "//select[@class=\"form-control select\"]")
	private WebElement selectWarranty;
	@FindBy(xpath = "//div[contains(@class,'buttons-wrapper')]//button[contains(@class,\"buy-btn\")]")
	private WebElement buttonBuy;

	private ProductPage waitElementsVisible(){
		wait.until(ExpectedConditions.elementToBeClickable(selectWarranty));
		return new ProductPage(driver);
	}

	public double getAndSavePrice(Product product){
		waitElementsVisible();
		double productPrice = Double.parseDouble(price.getAttribute("data-price-value"));
		product.setPrice(productPrice);
		ProductTotalPrice.addCountAndPrice(product.getName(),productPrice);
		return productPrice;
	}
}
