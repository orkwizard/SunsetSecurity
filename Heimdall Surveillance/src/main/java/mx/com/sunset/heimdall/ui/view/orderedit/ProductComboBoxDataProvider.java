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
import mx.com.sunset.heimdall.backend.data.entity.Product;
import mx.com.sunset.heimdall.backend.service.CrudService;
import mx.com.sunset.heimdall.backend.service.ProductService;

@SpringComponent
public class ProductComboBoxDataProvider extends PageableDataProvider<Product, String> {

	private transient CrudService<Product> productService;

	@Override
	protected Page<Product> fetchFromBackEnd(Query<Product, String> query, Pageable pageable) {
		return getProductService().findAnyMatching(query.getFilter(), pageable);
	}

	@Override
	protected int sizeInBackEnd(Query<Product, String> query) {
		return (int) getProductService().countAnyMatching(query.getFilter());
	}

	protected CrudService<Product> getProductService() {
		if (productService == null) {
			productService = BeanLocator.find(ProductService.class);
		}
		return productService;
	}

	@Override
	protected List<QuerySortOrder> getDefaultSortOrders() {
		List<QuerySortOrder> sortOrders = new ArrayList<>();
		sortOrders.add(new QuerySortOrder("name", SortDirection.ASCENDING));
		return sortOrders;
	}

}
