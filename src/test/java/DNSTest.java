import org.junit.Test;

public class DNSTest extends BaseDNSTest {
	@Test
	public void goToPage(){
		getDriver().get(urlMainPage);
	}
}
