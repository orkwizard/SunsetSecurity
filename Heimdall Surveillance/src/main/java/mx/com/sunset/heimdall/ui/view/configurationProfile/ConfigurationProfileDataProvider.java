package mx.com.sunset.heimdall.ui.view.configurationProfile;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.vaadin.artur.spring.dataprovider.FilterablePageableDataProvider;
import org.vaadin.spring.annotation.PrototypeScope;

import com.vaadin.data.provider.Query;
import com.vaadin.data.provider.QuerySortOrder;
import com.vaadin.shared.data.sort.SortDirection;
import com.vaadin.spring.annotation.SpringComponent;

import mx.com.sunset.heimdall.app.BeanLocator;
import mx.com.sunset.heimdall.backend.data.entity.ConfigurationProfile;
import mx.com.sunset.heimdall.backend.service.ConfigurationProfileService;
import mx.com.sunset.heimdall.backend.service.CrudService;

@SpringComponent
@PrototypeScope
public class ConfigurationProfileDataProvider extends FilterablePageableDataProvider<ConfigurationProfile, Object> {
	
	private transient CrudService<ConfigurationProfile> configurationProfileService;
	
	

	@Override
	protected Page<ConfigurationProfile> fetchFromBackEnd(Query<ConfigurationProfile, Object> query, Pageable pageable) {
		// TODO Auto-generated method stub
		return getConfigurationProfileService().findAnyMatching(getOptionalFilter(),pageable);
	}

	
	@Override
	protected int sizeInBackEnd(Query<ConfigurationProfile, Object> query) {
		return (int) getConfigurationProfileService().countAnyMatching(getOptionalFilter());
	}
	
	
	@Override
	protected List<QuerySortOrder> getDefaultSortOrders() {
		List<QuerySortOrder> sortOrders = new ArrayList<>();
		sortOrders.add(new QuerySortOrder("name", SortDirection.ASCENDING));
		return sortOrders;
	}

	
	protected CrudService<ConfigurationProfile> getConfigurationProfileService(){
		if(configurationProfileService==null)
			BeanLocator.find(ConfigurationProfileService.class);
		return configurationProfileService;
	}

}
