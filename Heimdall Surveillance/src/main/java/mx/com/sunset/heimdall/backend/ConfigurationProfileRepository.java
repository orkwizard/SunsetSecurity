package mx.com.sunset.heimdall.backend;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import mx.com.sunset.heimdall.backend.data.entity.ConfigurationProfile;

public interface ConfigurationProfileRepository extends JpaRepository<ConfigurationProfile, Long> {
	
	Page<ConfigurationProfile> findBy(Pageable page);
	Page<ConfigurationProfile> findByNameLikeIgnoreCase(String name,Pageable page);
	int countByNameLikeIgnoreCase(String name);
	

}
