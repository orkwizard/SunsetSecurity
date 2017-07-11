package mx.com.sunset.heimdall.ui.view.orderedit;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.vaadin.artur.spring.dataprovider.PageableDataProvider;

import com.vaadin.data.provider.Query;
import com.vaadin.data.provider.QuerySortOrder;
import com.vaadin.shared.data.sort.SortDirection;
import com.vaadin.spring.annotation.SpringComponent;
import mx.com.sunset.heimdall.app.BeanLocator;
import mx.com.sunset.heimdall.backend.data.entity.PickupLocation;
import mx.com.sunset.heimdall.backend.service.PickupLocationService;

/**
 * A singleton data provider which knows which products are available.
 */
@SpringComponent
public class PickupLocationComboBoxDataProvider extends PageableDataProvider<PickupLocation, String> {

	private transient PickupLocationService pickupLocationService;

	@Override
	protected Page<PickupLocation> fetchFromBackEnd(Query<PickupLocation, String> query, Pageable pageable) {
		return getPickupLocationService().findAnyMatching(query.getFilter(), pageable);
	}

	@Override
	protected int sizeInBackEnd(Query<PickupLocation, String> query) {
		return (int) getPickupLocationService().countAnyMatching(query.getFilter());
	}

	protected PickupLocationService getPickupLocationService() {
		if (pickupLocationService == null) {
			pickupLocationService = BeanLocator.find(PickupLocationService.class);
		}
		return pickupLocationService;
	}

	@Override
	protected List<QuerySortOrder> getDefaultSortOrders() {
		List<QuerySortOrder> sortOrders = new ArrayList<>();
		sortOrders.add(new QuerySortOrder("name", SortDirection.ASCENDING));
		return sortOrders;
	}

}
