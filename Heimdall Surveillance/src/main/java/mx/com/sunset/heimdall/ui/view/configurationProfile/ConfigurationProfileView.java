package mx.com.sunset.heimdall.ui.view.configurationProfile;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.data.BeanValidationBinder;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Component.Focusable;
import com.vaadin.ui.Grid;
import com.vaadin.ui.TextField;

import mx.com.sunset.heimdall.backend.data.entity.ConfigurationProfile;
import mx.com.sunset.heimdall.ui.view.admin.AbstractCrudView;

@SpringView
public class ConfigurationProfileView extends AbstractCrudView<ConfigurationProfile> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final ConfigurationProfilePresenter presenter;
	private final ConfigurationProfileViewDesign userAdminViewDesign;
	
	@Autowired
	 public ConfigurationProfileView(ConfigurationProfilePresenter presenter) {
		// TODO Auto-generated constructor stub
		this.presenter= presenter;
		userAdminViewDesign = new ConfigurationProfileViewDesign();
		
	}
	
	
	@Override
	public void init() {
		super.init();
		presenter.init(this);
	}
	
	@Override
	public void bindFormFields(BeanValidationBinder<ConfigurationProfile> binder) {
		// TODO Auto-generated method stub
		binder.bindInstanceFields(getViewComponent());
	}
	
	
	@Override
	public ConfigurationProfileViewDesign getViewComponent() {
		return userAdminViewDesign;
	}
	
	@Override
	protected ConfigurationProfilePresenter getPresenter() {
		return presenter;
	}

	
	/*@Override
	protected AbstractCrudPresenter<ConfigurationProfile, ?, ? extends AbstractCrudView<ConfigurationProfile>> getPresenter() {
		// TODO Auto-generated method stub
		return null;
	}*/
	
	@Override
	protected Grid<ConfigurationProfile> getGrid() {
		// TODO Auto-generated method stub
		return getViewComponent().list;
	}

	@Override
	protected void setGrid(Grid<ConfigurationProfile> grid) {
		getViewComponent().list=grid;		
	}

	@Override
	protected Component getForm() {
		return getViewComponent().form;
	}

	@Override
	protected Button getAdd() {
		// TODO Auto-generated method stub
		return getViewComponent().add;
	}

	@Override
	protected Button getCancel() {
		// TODO Auto-generated method stub
		return getViewComponent().cancel;
	}

	@Override
	protected Button getDelete() {
		// TODO Auto-generated method stub
		return getViewComponent().delete;
	}

	@Override
	protected Button getUpdate() {
		// TODO Auto-generated method stub
		return getViewComponent().update;
	}

	@Override
	protected TextField getSearch() {
		// TODO Auto-generated method stub
		return getViewComponent().search;
	}

	@Override
	protected Focusable getFirstFormField() {
		// TODO Auto-generated method stub
		return getViewComponent().name;
	}

	

}
