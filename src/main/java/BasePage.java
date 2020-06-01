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
	@FindBy(xpath = "//span[@class='cart-link__icon']/following-sibling::span//span")
	private WebElement cartLinkPrice;

	public WebElement getCartLinkPrice() {
		return cartLinkPrice;
	}

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
		(new WebDriverWait(driver, 10)).until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOf(cartLinkPrice)));
		return Double.parseDouble(cartLinkPrice.getText().replaceAll(" ", ""));
	}

	private void clickByJS(WebElement element) {
		executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].click();", element);
	}
	public void scroll(int x){
		executor = (JavascriptExecutor)driver;
		executor.executeScript("window.scrollBy(0," + x +")", "");
	}
}
