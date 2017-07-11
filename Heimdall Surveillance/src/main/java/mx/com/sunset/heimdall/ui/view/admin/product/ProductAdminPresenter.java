package mx.com.sunset.heimdall.ui.view.admin.product;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.ViewScope;
import mx.com.sunset.heimdall.backend.data.entity.Product;
import mx.com.sunset.heimdall.backend.service.ProductService;
import mx.com.sunset.heimdall.ui.navigation.NavigationManager;
import mx.com.sunset.heimdall.ui.view.admin.AbstractCrudPresenter;

@SpringComponent
@ViewScope
public class ProductAdminPresenter extends AbstractCrudPresenter<Product, ProductService, ProductAdminView> {

	@Autowired
	public ProductAdminPresenter(ProductAdminDataProvider productAdminDataProvider, NavigationManager navigationManager,
			ProductService service) {
		super(navigationManager, service, productAdminDataProvider);
	}
}
