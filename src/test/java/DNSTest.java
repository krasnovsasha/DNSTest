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
	Product warranty = new Product("warranty24");

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
		Assert.assertThat("Цена товаров в корзине не соответствует общей цене добавленных товаров",
							productPage.priceInCartActual(), Is.is(ProductTotalPrice.getTotalPriceOfAll()));
		//перейти в корзину
		productPage.goToCart();
		//проверить цену каждого из товаров и сумму
		Assert.assertThat("Цена на товар " + playstation.getName() + " отображается неверно",
								cartPage.getPriceOfProduct(cartPage.getProductPlaystaition()),
								Is.is(playstation.getPrice()));
		Assert.assertThat("Цена на товар " + detroit.getName() + " отображается неверно",
								cartPage.getPriceOfProduct(cartPage.getProductDetroit()),
								Is.is(detroit.getPrice()));
		Assert.assertThat("Общая цена товаров  отображается неверно",
								basePage.getTotalPriceOfProductInMenuBar(),
								Is.is(ProductTotalPrice.getTotalPriceOfAll()));
		//В корзине для playstation Доп.гарантия - выбрать 24 месяца
		cartPage.clickRadioButton();
		//дождаться изменения цены и запомнить цену с гарантией
//		cartPage.waitToRefresh(ProductTotalPrice.getTotalPriceOfAll(), cartPage.getTotalPriceInRightSide());
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		warranty.setPrice(cartPage.getTotalPriceOfProductInMenuBar()-playstation.getPrice()-detroit.getPrice());
		System.out.println("Цена гарантии " + warranty.getPrice());
		ProductTotalPrice.setTotalPriceInCart(cartPage.getTotalPriceOfProductInMenuBar());
		System.out.println("Сохраненная цена до удаления Детроит " + ProductTotalPrice.getTotalPriceInCart());
		//удалить из корзины Detroit
		cartPage.deleteProductFromCart(cartPage.getButtonDeleteFromCart());
		System.out.println("Сохраненная цена после удаления Детроит " + ProductTotalPrice.getTotalPriceInCart());
		//проверить что Detroit нет больше в корзине и что сумма уменьшилась на цену Detroit
//		cartPage.waitToRefresh(ProductTotalPrice.getTotalPriceInCart(), basePage.getCartLinkPrice());
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Assert.assertTrue("Товар отображается в корзине после удаления ",cartPage.checkProduct("1225442"));
		System.out.println("Цена по ссылке в корзине после удаления Детроит " + basePage.getTotalPriceOfProductInMenuBar());
		System.out.println("Сохраненная цена после удаления Детроит " + ProductTotalPrice.getTotalPriceInCart());
		Assert.assertTrue("Цена не уменьшилась на стоимость Детроит",cartPage.wasPriseChangedInCart(detroit,basePage));
		//добавить еще 2 playstation (кнопкой +) и проверить что сумма верна
		//  равна 3*(цена playstation + гарантия))
		cartPage.addProductToCart(cartPage.getButtonAddToCart());
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
//		cartPage.waitToRefresh(30.679,basePage);
		cartPage.addProductToCart(cartPage.getButtonAddToCart());
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
//		cartPage.waitToRefresh(30.679 + playstation.getPrice(),basePage);
		System.out.println("Цена по ссылке в корзине после добавления 2 плейстейшн "
							+ basePage.getTotalPriceOfProductInMenuBar());
		Assert.assertThat("Цена после добавления товаров не верна",
							basePage.getTotalPriceOfProductInMenuBar(),Is.is((playstation.getPrice() + warranty.getPrice()) * 3));
		//удалить (кнопка "удалить") Playstation из корзины
		cartPage.deleteProductFromCartPage();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		//нажать вернуть удаленный товар
		basePage.scroll(-100);
		cartPage.restoreProduct();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		//проверить что 3 playstation снова в корзине и выбрана гарантия 24 месяца
		basePage.scroll(100);
		Assert.assertFalse("Товар не отображается в корзине ",cartPage.checkProduct("1626724"));

	}
}
