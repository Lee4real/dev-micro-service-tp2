package org.sid.customer_service.repositories;

import org.sid.customer_service.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.webmvc.RepositoryRestController;


@RepositoryRestController
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
