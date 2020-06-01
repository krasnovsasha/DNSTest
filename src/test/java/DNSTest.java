import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.Test;

public class DNSTest extends BaseDNSTest {
	BasePage basePage = new BasePage(getDriver());
	ResultsPage resultsPage = new ResultsPage(getDriver());
	ProductPage productPage = new ProductPage(getDriver());
	CartPage cartPage = new CartPage(getDriver());
	Product playstation = new Product("playstation");
	Product detroit = new Product("detroit");

	@Test
	public void dnsTest() {
		//открыть dns-shop
		basePage.goToPage(urlMainPage);
		//в поиске найти playstation
		basePage.findItem(playstation);
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
		Assert.assertThat("Цена товаров в корзине не соответствует общей цене добавленных товаров", productPage.priceInCartActual(), Is.is(ProductTotalPrice.getTotalPriceOfAll()));
		//перейти в корзину
		productPage.goToCart();
		//проверить цену каждого из товаров и сумму
		Assert.assertThat("Цена на товар " + playstation.getName() + " отображается неверно", cartPage.getPriceOfProduct(cartPage.getProductPlaystaition()), Is.is(playstation.getPrice()));
		Assert.assertThat("Цена на товар " + detroit.getName() + " отображается неверно", cartPage.getPriceOfProduct(cartPage.getProductDetroit()), Is.is(detroit.getPrice()));
		Assert.assertThat("Общая цена товаров  отображается неверно", basePage.getTotalPriceOfProductInMenuBar(), Is.is(ProductTotalPrice.getTotalPriceOfAll()));
		//В корзине для playstation Доп.гарантия - выбрать 24 месяца
		cartPage.clickRadioButton();
		//дождаться изменения цены и запомнить цену с гарантией
		cartPage.waitToRefresh(ProductTotalPrice.getTotalPriceOfAll(), cartPage.getTotalPriceInRightSide());
		ProductTotalPrice.setTotalPriceInCart(cartPage.getTotalPriceOfProductInMenuBar());
		System.out.println(ProductTotalPrice.getTotalPriceInCart());
		//удалить из корзины Detroit
		cartPage.deleteProductFromCart(cartPage.getButtonDeleteFromCart());
		System.out.println(ProductTotalPrice.getTotalPriceInCart());
		//проверить что Detroit нет больше в корзине и что сумма уменьшилась на цену Detroit
		cartPage.waitToRefresh(ProductTotalPrice.getTotalPriceInCart(), basePage.getCartLinkPrice());
		Assert.assertTrue("Товар " + detroit.getName() + " отображается в корзине", cartPage.getProductDetroit().isDisplayed());
		System.out.println(basePage.getTotalPriceOfProductInMenuBar());
		System.out.println(ProductTotalPrice.getTotalPriceInCart());
	}
}
