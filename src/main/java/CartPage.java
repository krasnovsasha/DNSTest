import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CartPage extends BasePage {
	public CartPage(WebDriver driver) {
		super(driver);
	}

	@FindBy(xpath = "//div[@class='total-amount__label']//div[@class='price__block price__block_main']//span[@class='price__current']")
	private WebElement totalPriceInRightSide;
	@FindBy(xpath = "//div[contains(text(),'1626724')]/parent::div/parent::div/parent::div//span[@class='price__current']")
	private WebElement product1;
	@FindBy(xpath = "//div[contains(text(),'1225442')]/parent::div/parent::div/parent::div//span[@class='price__current']")
	private WebElement product2;
	@FindBy(xpath = "//div[@class='base-ui-radio-button additional-warranties-row__radio']//span[contains(text(),'+ 24  мес.')]")
	private WebElement radioButton24;
	@FindBy(xpath = "//a[contains(text(),'Detroit')]/parent::div/parent::div//button[contains(text(),'Удалить')]")
	private WebElement buttonDeleteFromCart;
	@FindBy(xpath = "//a[contains(text(),'Detroit')]")
	private WebElement productInPage;

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

	public Double getPriceOfProduct() {
		Double priceOfProductActual = Double.parseDouble(product1.getText().replaceAll(" ", ""));
		return priceOfProductActual;
	}

	public void clickRadioButton() {
		radioButton24.click();
	}

	public CartPage deleteProductFromCart(WebElement element) {
		element.click();
		return new CartPage(driver);
	}
}
