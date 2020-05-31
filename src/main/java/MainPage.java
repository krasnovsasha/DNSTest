import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class MainPage extends BasePage {

	public MainPage(WebDriver driver) {
		super(driver);
	}

	@FindBy(xpath = "//input[@placeholder='Поиск по сайту']")
	private WebElement inputSearch;

	private MainPage clickSearchField(){
		wait.until(ExpectedConditions.visibilityOf(inputSearch));
		wait.until(ExpectedConditions.elementToBeClickable(inputSearch));
		inputSearch.click();
		return new MainPage(driver);
	}
	private MainPage sendTextToSearchField(String productName){
		inputSearch.sendKeys(productName);
		return new MainPage(driver);
	}
	public ResultsPage findItem(Product product){
		clickSearchField();
		sendTextToSearchField(product.getName());
		inputSearch.sendKeys(Keys.ENTER);
		return new ResultsPage(driver);
	}

}
