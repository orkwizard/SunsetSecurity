package com.example.sunsetsecurityweb;

import javax.servlet.annotation.WebServlet;

import com.ejt.vaadin.loginform.DefaultVerticalLoginForm;
import com.ejt.vaadin.loginform.LoginForm.LoginEvent;
import com.google.gwt.layout.client.Layout;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
@Theme("sunsetsecurityweb")
public class SunsetsecuritywebUI extends UI {
	
	
	@WebServlet(value = "/*", asyncSupported = true)
	@VaadinServletConfiguration(productionMode = false, ui = SunsetsecuritywebUI.class)
	public static class Servlet extends VaadinServlet {
	}

	@Override
	protected void init(VaadinRequest request) {
		final VerticalLayout layout = new VerticalLayout();
		DefaultVerticalLoginForm login = new DefaultVerticalLoginForm();
		
		Main main = new Main();
		layout.setMargin(false);
		login.setVisible(true);
		main.content.addComponent(login);
		
		setContent(main);
		/*final VerticalLayout layout = new VerticalLayout();
		layout.setMargin(true);
		setContent(layout);

		Button button = new Button("Click Me");
		button.addClickListener(new Button.ClickListener() {
			public void buttonClick(ClickEvent event) {
				layout.addComponent(new Label("Thank you for clicking"));
			}
		});
		layout.addComponent(button);*/
		
	}

}