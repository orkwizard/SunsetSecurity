package mx.com.sunset.heimdall.backend.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import mx.com.sunset.heimdall.app.BeanLocator;
import mx.com.sunset.heimdall.backend.PickupLocationRepository;
import mx.com.sunset.heimdall.backend.data.entity.PickupLocation;

@Service
public class PickupLocationService {

	private PickupLocationRepository pickupLocationRepository;

	public Page<PickupLocation> findAnyMatching(Optional<String> filter, Pageable pageable) {
		if (filter.isPresent()) {
			String repositoryFilter = "%" + filter.get() + "%";
			return getPickupLocationRepository().findByNameLikeIgnoreCase(repositoryFilter, pageable);
		} else {
			return getPickupLocationRepository().findByNameLikeIgnoreCase("%", pageable);
		}
	}

	public long countAnyMatching(Optional<String> filter) {
		if (filter.isPresent()) {
			String repositoryFilter = "%" + filter.get() + "%";
			return getPickupLocationRepository().countByNameLikeIgnoreCase(repositoryFilter);
		} else {
			return getPickupLocationRepository().countByNameLikeIgnoreCase("%");
		}
	}

	public PickupLocation getDefault() {
		return findAnyMatching(Optional.empty(), new PageRequest(0, 1)).iterator().next();
	}

	protected PickupLocationRepository getPickupLocationRepository() {
		if (pickupLocationRepository == null) {
			pickupLocationRepository = BeanLocator.find(PickupLocationRepository.class);
		}
		return pickupLocationRepository;
	}
}
