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
		//1) открыть dns-shop
		basePage.goToPage(urlMainPage);
		//2) в поиске найти playstation
		basePage.findItem(playstation);
		//3) кликнуть по playstation 4 slim black
		resultsPage.chooseProduct(resultsPage.getProduct());
		//4) запомнить цену
		productPage.getAndSavePrice(playstation);
		//5) Нажать Купить
		productPage.buyProduct();
		//6) выполнить поиск Detroit
		productPage.findItem(detroit);
		//7) запомнить цену
		productPage.getAndSavePrice(detroit);
		//8) нажать купить
		productPage.buyProduct();
		//9) проверить что цена корзины стала равна сумме покупок
		cartPage.waitToRefresh();
		Assert.assertThat("Цена товаров в корзине не соответствует общей цене добавленных товаров",
				basePage.getTotalPriceOfProductInMenuBar(), Is.is(ProductTotalPrice.getTotalPriceOfAll()));
		//10) перейти в корзину
		productPage.goToCart();
		//11) проверить цену каждого из товаров и сумму
		Assert.assertThat("Цена на товар " + playstation.getName() + " отображается неверно",
								cartPage.getPriceOfProduct(cartPage.getProductPlaystaition()),
								Is.is(playstation.getPrice()));
		Assert.assertThat("Цена на товар " + detroit.getName() + " отображается неверно",
								cartPage.getPriceOfProduct(cartPage.getProductDetroit()),
								Is.is(detroit.getPrice()));
		Assert.assertThat("Общая цена товаров  отображается неверно",
								basePage.getTotalPriceOfProductInMenuBar(),
								Is.is(ProductTotalPrice.getTotalPriceOfAll()));
		//12) В корзине для playstation Доп.гарантия - выбрать 24 месяца
		cartPage.clickRadioButton();
		//13) дождаться изменения цены и запомнить цену с гарантией
		ProductTotalPrice.setTotalPriceInCart(cartPage.getTotalPriceOfProductInMenuBar());
		//14) удалить из корзины Detroit
		cartPage.deleteProductFromCart(cartPage.getButtonDeleteFromCart());
		// 15) проверить что Detroit нет больше в корзине и что сумма уменьшилась на цену Detroit
		Assert.assertTrue("Товар отображается в корзине после удаления ",cartPage.checkProduct(detroit.getId()));
		Assert.assertTrue("Цена не уменьшилась на стоимость Детроит",cartPage.wasPriseChangedInCart(detroit,basePage));
		// 16) добавить еще 2 playstation (кнопкой +) и проверить что сумма верна (равна 3*(цена playstation + гарантия))
		cartPage.addProductToCart(cartPage.getButtonAddToCart(),2);
		Assert.assertThat("Цена после добавления товаров не верна",
								basePage.getTotalPriceOfProductInMenuBar(),Is.is(92037.0));
		//17) удалить (кнопка "удалить") Playstation из корзины
		cartPage.deleteProductFromCartPage();
		//нажать вернуть удаленный товар
		basePage.scroll(-100);
		cartPage.restoreProduct();
		//проверить что 3 playstation снова в корзине и выбрана гарантия 24 месяца
		basePage.scroll(100);
		Assert.assertFalse("Товар не отображается в корзине ",cartPage.checkProduct(playstation.getId()));
		Assert.assertFalse("Гарантия не выбрана",cartPage.radioButtonIsChecked(cartPage.getRadioButtonXPath()));
	}
}
