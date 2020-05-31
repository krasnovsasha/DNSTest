import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class CartPage extends BasePage {
	public CartPage(WebDriver driver) {
		super(driver);
	}
	@FindBy(xpath = "//span[@class='price__current']")
	List<WebElement> elements;
}
