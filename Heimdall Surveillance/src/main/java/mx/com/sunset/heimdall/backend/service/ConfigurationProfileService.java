package mx.com.sunset.heimdall.backend.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.google.gwt.logging.client.DefaultLevel.Config;

import mx.com.sunset.heimdall.app.BeanLocator;
import mx.com.sunset.heimdall.backend.ConfigurationProfileRepository;
import mx.com.sunset.heimdall.backend.data.entity.ConfigurationProfile;

public class ConfigurationProfileService implements CrudService<ConfigurationProfile> {

	@Override
	public ConfigurationProfileRepository getRepository() {
		// TODO Auto-generated method stub
		return BeanLocator.find(ConfigurationProfileRepository.class);
		
	}
	
	@Override
	public long countAnyMatching(Optional<String> filter) {
		if (filter.isPresent()) {
			String repositoryFilter = "%" + filter.get() + "%";
			return getRepository().countByNameLikeIgnoreCase(repositoryFilter);
		} else {
			return count();
		}
	}

	@Override
	public Page<ConfigurationProfile> findAnyMatching(Optional<String> filter, Pageable pageable) {
		// TODO Auto-generated method stub
		if (filter.isPresent()) {
			String repositoryFilter = "%" + filter.get() + "%";
			return getRepository().findByNameLikeIgnoreCase(repositoryFilter, pageable);
		} else {
			return find(pageable);
		}
	}

	@Override
	public Page<ConfigurationProfile> find(Pageable pageable) {
		// TODO Auto-generated method stub
		return getRepository().findBy(pageable);
	}

}
