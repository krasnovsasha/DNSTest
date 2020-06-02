import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CartPage extends BasePage {
	public CartPage(WebDriver driver) {
		super(driver);
	}

	@FindBy(xpath = "//div[contains(text(),'1626724')]/parent::div/parent::div/parent::div//span[@class='price__current']")
	private WebElement productPlaystaition;
	@FindBy(xpath = "//div[contains(text(),'1225442')]/parent::div/parent::div/parent::div//span[@class='price__current']")
	private WebElement productDetroit;
	@FindBy(xpath = "//div[@class='base-ui-radio-button additional-warranties-row__radio']//span[contains(text(),'+ 24  мес.')]")
	private WebElement radioButton24;
	@FindBy(xpath = "//div[@data-cart-product-id][1]//*[@class='count-buttons__icon-plus']")
	WebElement buttonAddToCart;
	@FindBy(xpath = "//div[@data-cart-product-id][2]//*[@class='count-buttons__icon-minus']")
	private WebElement buttonDeleteFromCart;
	@FindBy(xpath = "//a[contains(text(),'Вернуть удалённый товар')]")
	private WebElement restoreProduct;
	@FindBy(xpath = "//button[@class='menu-control-button' and contains(text(),'Удалить')]")
	private WebElement buttonDelete;
	private String radioButtonCheckedXPath = "//span[contains(@class,'base-ui-radio-button__icon_checked') and contains(text(),'24')]";

	public String getRadioButtonXPath() {
		return radioButtonCheckedXPath;
	}

	public WebElement getProductPlaystaition() {
		return productPlaystaition;
	}

	public WebElement getProductDetroit() {
		return productDetroit;
	}

	public WebElement getButtonDeleteFromCart() {
		return buttonDeleteFromCart;
	}

	public WebElement getButtonAddToCart() {
		return buttonAddToCart;
	}

	public Double getPriceOfProduct(WebElement product) {
		wait.until(ExpectedConditions.elementToBeClickable(product));
		Double priceOfProductActual = Double.parseDouble(product.getText().replaceAll(" ", ""));
		return priceOfProductActual;
	}

	public CartPage clickRadioButton() {
		wait.until(ExpectedConditions.elementToBeClickable(radioButton24));
		try {
			radioButton24.click();
		} catch (NoSuchElementException e) {
			wait.until(ExpectedConditions.elementToBeClickable(radioButton24));
			radioButton24.click();
		}
		return this;
	}

	public CartPage deleteProductFromCart(WebElement element) {
		wait.until(ExpectedConditions.elementToBeClickable(element));
		element.click();
		return this;
	}

	public void waitToRefresh() {
		try {
			WebDriverWait wdw = new WebDriverWait(driver, 5);
			ProductTotalPrice.setTotalPriceInCart(getTotalPriceOfProductInMenuBar());
			wdw.until(driver -> {
				double newPriceInCart = getTotalPriceOfProductInMenuBar();
				return newPriceInCart != ProductTotalPrice.getTotalPriceInCart();
			});
		} catch (org.openqa.selenium.TimeoutException e) {
			e.printStackTrace();
		}
	}

	public boolean checkProduct(int productID) {
		wait.until(ExpectedConditions.visibilityOf(productPlaystaition));
		waitToRefresh();
		return driver.findElements(By.xpath(String.format("//div[contains(text(),'%d')]/parent::div/parent::div/parent::div//span[@class='price__current']", productID))).isEmpty();
	}

	public boolean wasPriseChangedInCart(Product product, BasePage basePage) {
		return (ProductTotalPrice.getTotalPriceInCart() - basePage.getTotalPriceOfProductInMenuBar()) == product.getPrice();
	}

	public CartPage addProductToCart(WebElement element, int count) {
		for (int i = 0; i < count; i++) {
			element.click();
			waitToRefresh();
		}
		return this;
	}

	public CartPage restoreProduct() {
		wait.until(ExpectedConditions.elementToBeClickable(restoreProduct)).click();
		return this;
	}

	public CartPage deleteProductFromCartPage() {
		wait.until(ExpectedConditions.elementToBeClickable(buttonDelete));
		buttonDelete.click();
		return this;
	}

	public boolean radioButtonIsChecked(String XPath) {
		return driver.findElements(By.xpath(XPath)).isEmpty();
	}
}

