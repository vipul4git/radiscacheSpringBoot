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

	@CachePut(key = "#customer.id", cacheNames = {"controlledCache"},condition = "#isCacheable",cacheManager = "manager")
	@RequestMapping(path = "save/{isCacheable}", method = RequestMethod.POST)
	public Customer save(@RequestBody Customer customer,@PathVariable boolean isCacheable) {
		customerRepository.save(customer);
		return customerRepository.findById(customer.getId());
	}

	@Cacheable(key = "#id", cacheNames = {"controlledCache"},condition = "#isCacheable",unless="#result == null ",cacheManager = "manager")
	@RequestMapping(path = "customer/{id}/{isCacheable}", method = RequestMethod.GET)
	public Customer getCustomer(@PathVariable String id,@PathVariable boolean isCacheable) {
		Customer customer = customerRepository.findById(id);
		if(null != customer) {
			return customer;
		}
		return null;
	}

	@CachePut(key = "#customer.id", cacheNames = {"controlledCache"},cacheManager = "manager")
	@RequestMapping(path = "update", method = RequestMethod.POST)
	public Customer update(@RequestBody Customer customer) {
		customerRepository.update(customer);
		return customer;
	}

	@CacheEvict(key = "#id",cacheNames = {"controlledCache"},cacheManager = "manager")
	@RequestMapping(path = "delete/{id}", method = RequestMethod.DELETE)
	public String deleteCustomer(@PathVariable String id) {
		customerRepository.delete(id);
		return id;
	}

	@RequestMapping(path = "customers", method = RequestMethod.GET)
	public Map<String, Object> getAllCustomer() {
		return customerRepository.findAll();
	}
}
