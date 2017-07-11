package mx.com.sunset.heimdall.backend;

import org.springframework.data.jpa.repository.JpaRepository;

import mx.com.sunset.heimdall.backend.data.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
