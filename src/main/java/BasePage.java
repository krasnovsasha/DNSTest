import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasePage {
	protected WebDriver driver;
	protected WebDriverWait wait;
	@FindBy(xpath = "//input[@placeholder='Поиск по сайту']")
	protected WebElement inputSearch;
	@FindBy(xpath = "//span[@class='cart-link__icon']/parent::a")
	private WebElement cartLink;
	@FindBy(xpath = "//span[@class='cart-link__icon']/following-sibling::span//span")
	private WebElement cartLinkPrice;

	protected BasePage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		wait = new WebDriverWait(driver, 5);
	}

	protected BasePage clickSearchField() {
		wait.until(ExpectedConditions.visibilityOf(inputSearch));
		wait.until(ExpectedConditions.elementToBeClickable(inputSearch));
		inputSearch.click();
		return new BasePage(driver);
	}

	private BasePage sendTextToSearchField(String productName) {
		inputSearch.sendKeys(productName);
		return new BasePage(driver);
	}

	protected ResultsPage findItem(Product product) {
		clickSearchField();
		sendTextToSearchField(product.getName());
		inputSearch.sendKeys(Keys.ENTER);
		return new ResultsPage(driver);
	}

	protected CartPage goToCart(){
		cartLink.click();
		return new CartPage(driver);
	}

	public Double getTotalPriceOfProductInMenuBar(){
		(new WebDriverWait(driver, 30)).until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOf(cartLinkPrice)));
		Double priceOfProductActual = Double.parseDouble(cartLinkPrice.getText().replaceAll(" ", ""));
		return priceOfProductActual;
	}
}
