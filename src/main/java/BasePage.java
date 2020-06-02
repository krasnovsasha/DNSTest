import org.openqa.selenium.JavascriptExecutor;
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
	private JavascriptExecutor executor;
	@FindBy(xpath = "//input[@placeholder='Поиск по сайту']")
	protected WebElement inputSearch;
	@FindBy(xpath = "//span[@class='cart-link__icon']/parent::a")
	private WebElement cartLink;
	@FindBy(xpath = "//div[@class=\"buttons\"]//span[@class='cart-link__price']")
	private WebElement cartLinkPrice;

	protected BasePage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		wait = new WebDriverWait(driver, 20);
	}

	protected void goToPage(String url) {
		driver.get(url);
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

	protected CartPage goToCart() {
		cartLink.click();
		return new CartPage(driver);
	}

	public Double getTotalPriceOfProductInMenuBar() {
		try {
			wait.until(ExpectedConditions.elementToBeClickable(cartLinkPrice));
			return Double.parseDouble(cartLinkPrice.getText().replaceAll(" ", ""));
		} catch (org.openqa.selenium.StaleElementReferenceException e) {
			wait.until(ExpectedConditions.elementToBeClickable(cartLinkPrice));
			return Double.parseDouble(cartLinkPrice.getText().replaceAll(" ", ""));
		}
	}

	public void scroll(int x) {
		executor = (JavascriptExecutor) driver;
		executor.executeScript("window.scrollBy(0," + x + ")", "");
	}
}
