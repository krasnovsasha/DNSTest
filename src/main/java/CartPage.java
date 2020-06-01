import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class CartPage extends BasePage {
	public CartPage(WebDriver driver) {
		super(driver);
	}

	@FindBy(xpath = "//div[@class='total-amount__label']//div[@class='price__block price__block_main']//span[@class='price__current']")
	private WebElement totalPriceInRightSide;
	@FindBy(xpath = "//div[contains(text(),'1626724')]/parent::div/parent::div/parent::div//span[@class='price__current']")
	private WebElement productPlaystaition;
	@FindBy(xpath = "//div[contains(text(),'1225442')]/parent::div/parent::div/parent::div//span[@class='price__current']")
	private WebElement productDetroit;
	@FindBy(xpath = "//div[@class='base-ui-radio-button additional-warranties-row__radio']//span[contains(text(),'+ 24  мес.')]")
	private WebElement radioButton24;
	@FindBy(xpath = "//div[@data-cart-product-id][2]//*[@class='count-buttons__icon-minus']")
	private WebElement buttonDeleteFromCart;
	@FindBy(xpath = "//a[contains(text(),'Detroit')]")
	private WebElement productInPage;

	public WebElement getProductPlaystaition() {
		return productPlaystaition;
	}

	public WebElement getTotalPriceInRightSide() {
		return totalPriceInRightSide;
	}

	public WebElement getProductDetroit() {
		return productDetroit;
	}

	public WebElement getButtonDeleteFromCart() {
		return buttonDeleteFromCart;
	}

	public WebElement getProductInPage() {
		return productInPage;
	}

	public boolean checkPrices() {
		Double priceInCartActual = Double.parseDouble(totalPriceInRightSide.getText().replaceAll(" ", ""));
		Double priceINSafeActual = ProductTotalPrice.getTotalPriceOfAll();
		return priceInCartActual.equals(priceINSafeActual);
	}

	public Double getPriceOfProduct(WebElement product) {
		Double priceOfProductActual = Double.parseDouble(product.getText().replaceAll(" ", ""));
		return priceOfProductActual;
	}

	public void clickRadioButton() {
		wait.until(ExpectedConditions.elementToBeClickable(radioButton24));
		try {
			radioButton24.click();
		} catch (NoSuchElementException e) {
			radioButton24.click();
		}
	}

	public CartPage deleteProductFromCart(WebElement element) {
		return new CartPage(driver);
	}

	public void waitToRefresh(double totalPriceOfAll, WebElement element) {
		wait.until(driver1 -> {
			double priceOfProduct = Double.parseDouble(element.getText().replaceAll(" ", ""));
			return priceOfProduct != ProductTotalPrice.getTotalPriceOfAll();
		});
	}
}

