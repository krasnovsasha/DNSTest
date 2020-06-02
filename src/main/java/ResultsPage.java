import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class ResultsPage extends BasePage {
	public ResultsPage(WebDriver driver) {
		super(driver);
	}

	@FindBy(xpath = "//span[text()='Наличие в магазинах']")
	private WebElement presentInShops;
	@FindBy(xpath = "//a[contains(text(),'PlayStation 4 Slim Black')]")
	private WebElement product;

	public WebElement getProduct() {
		return product;
	}

	private ResultsPage waitLoading() {
		wait.until(ExpectedConditions.visibilityOf(presentInShops));
		wait.until(ExpectedConditions.elementToBeClickable(presentInShops));
		return this;
	}

	public ProductPage chooseProduct(WebElement element) {
		waitLoading();
		element.click();
		return new ProductPage(driver);
	}
}
