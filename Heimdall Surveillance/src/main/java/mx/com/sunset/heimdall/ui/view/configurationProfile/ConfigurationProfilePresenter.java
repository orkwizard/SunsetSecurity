package mx.com.sunset.heimdall.ui.view.configurationProfile;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.ViewScope;

import mx.com.sunset.heimdall.backend.data.entity.ConfigurationProfile;
import mx.com.sunset.heimdall.backend.service.ConfigurationProfileService;
import mx.com.sunset.heimdall.ui.navigation.NavigationManager;
import mx.com.sunset.heimdall.ui.view.admin.AbstractCrudPresenter;


@SpringComponent
@ViewScope
public class ConfigurationProfilePresenter extends AbstractCrudPresenter<ConfigurationProfile,ConfigurationProfileService, ConfigurationProfileView> {
	
	@Autowired
	public ConfigurationProfilePresenter(ConfigurationProfileDataProvider dataProvider,NavigationManager nav,ConfigurationProfileService service) {
		super(nav,service,dataProvider);
		// TODO Auto-generated constructor stub
	}
	
	
	

}
