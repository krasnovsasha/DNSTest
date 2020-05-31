import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.Test;

public class DNSTest extends BaseDNSTest {
	BasePage basePage = new BasePage(getDriver());
	MainPage mainPage = new MainPage(getDriver());
	ResultsPage resultsPage = new ResultsPage(getDriver());
	ProductPage productPage = new ProductPage(getDriver());
	Product playstation = new Product("playstation");
	Product detroit = new Product("detroit");

	@Test
	public void dnsTest() {
		//открыть dns-shop
		basePage.goToPage(urlMainPage);
		//в поиске найти playstation
		mainPage.findItem(playstation);
		//кликнуть по playstation 4 slim black
		resultsPage.chooseProduct(resultsPage.getProduct());
		//запомнить цену
		productPage.getAndSavePrice(playstation);
		//Нажать Купить
		productPage.buyProduct();
		//выполнить поиск Detroit
		productPage.findItem(detroit);
		//запомнить цену
		productPage.getAndSavePrice(detroit);
		//нажать купить
		productPage.buyProduct();
		//проверить что цена корзины стала равна сумме покупок
		Assert.assertThat("Цена товаров в корзине не соответствует общей цене добавленных товаров",
							productPage.priceInCartActual(),Is.is(ProductTotalPrice.getTotalPriceOfAll()));
		//перейти в корзину
		productPage.goToCart();
		//проверить цену каждого из товаров и сумму


	}
}
