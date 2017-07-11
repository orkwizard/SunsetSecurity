package mx.com.sunset.heimdall.ui.view;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;

import mx.com.sunset.heimdall.AbstractIT;
import mx.com.sunset.heimdall.ui.view.orderedit.OrderEditViewElement;

public class LoginIT extends AbstractIT {

	@Test
	public void userIsRedirectedToRequestedView() {
		openLoginView(APP_URL + "#!order/1").login("barista@vaadin.com", "barista");
		Assert.assertEquals("#1", $(OrderEditViewElement.class).first().getOrderId().getText());
	}

	@Test
	public void logoutWorks() {
		loginAsBarista();
		$(MenuElement.class).first().logout();
		Assert.assertEquals("Email", findElement(By.id("login-label")).getText());
	}

}
