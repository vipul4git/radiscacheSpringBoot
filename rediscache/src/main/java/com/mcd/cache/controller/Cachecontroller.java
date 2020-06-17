/**
 * 
 */
package com.mcd.cache.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mcd.cache.model.Customer;
import com.mcd.cache.repository.CustomerRepository;

/**
 * @author 
 *
 */
@RestController
@RequestMapping("/cache")
public class Cachecontroller {

	@Autowired
	private CustomerRepository customerRepository;

	@CachePut(key = "#customer.id", value = "customers")
	@RequestMapping(path = "save", method = RequestMethod.POST)
	public Customer save(@RequestBody Customer customer) {
		customerRepository.save(customer);
		return customerRepository.findById(customer.getId());
	}

	@Cacheable(key = "#id", value = "customers",unless="#result != 0")
	@RequestMapping(path = "customer/{id}", method = RequestMethod.GET)
	public Customer getCustomer(@PathVariable String id) {
		return customerRepository.findById(id);
	}

	@CachePut(key = "#customer.id", value = "customers")
	@RequestMapping(path = "update", method = RequestMethod.POST)
	public Customer update(@RequestBody Customer customer) {
		customerRepository.update(customer);
		return customer;
	}

	@CacheEvict(key = "#id", value = "customers")
	@RequestMapping(path = "delete/{id}", method = RequestMethod.DELETE)
	public String deleteCustomer(@PathVariable String id) {
		customerRepository.delete(id);
		return id;
	}
	@Cacheable(value = "customers", key="#")
	@RequestMapping(path = "customers", method = RequestMethod.GET)
	public Map<String, Object> getAllCustomer() {
		return customerRepository.findAll();
	}
}
