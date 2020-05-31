import org.junit.Test;

public class DNSTest extends BaseDNSTest {
	@Test
	public void goToPage(){
		getDriver().get(urlMainPage);
		MainPage mp = new MainPage(getDriver());
		Product playstation = new Product("playstation");
		mp.findItem(playstation);
	}
}
