package com.example.order_management;

import java.util.List;

import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class CustomerService {

	 private static final Logger logger =
	            LoggerFactory.getLogger(CustomerService.class);

	    private final CustomerRepository repository;

	    public CustomerService(CustomerRepository repository) {
	        this.repository = repository;
	    }

	    public CustomerResponseDTO createCustomer(CustomerRequestDTO dto) {

	        logger.info("Creating customer with email: {}", dto.getEmail());

	        Customer customer = new Customer();
	        customer.setName(dto.getName());
	        customer.setEmail(dto.getEmail());

	        Customer savedCustomer = repository.save(customer);

	        logger.info("Customer created successfully with id: {}",
	                savedCustomer.getId());

	        CustomerResponseDTO response = new CustomerResponseDTO();

	        response.setId(savedCustomer.getId());
	        response.setName(savedCustomer.getName());
	        response.setEmail(savedCustomer.getEmail());

	        return response;
	    }
    public List<Customer> getAllCustomers() {
        return repository.findAll();
    }

    public Customer getCustomerById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public Customer updateCustomer(Long id, Customer customer) {

        Customer existingCustomer = repository.findById(id).orElse(null);

        if (existingCustomer == null) {
            return null;
        }

        existingCustomer.setName(customer.getName());
        existingCustomer.setEmail(customer.getEmail());

        return repository.save(existingCustomer);
    }

    public void deleteCustomer(Long id) {
        repository.deleteById(id);
    }
}
