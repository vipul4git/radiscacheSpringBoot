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
@RestController
@RequestMapping("/rediscache")
public class RedisCacheController {
	@Autowired
	private CustomerRepository customerRepository;

	@CachePut(key = "#customer.id", cacheNames = {"rediscontrolledCache"},condition = "#isCacheable==true")
	@RequestMapping(path = "save/{isCacheable}", method = RequestMethod.POST)
	public Customer save(@RequestBody Customer customer,@PathVariable boolean isCacheable) {
		customerRepository.save(customer);
		return customerRepository.findById(customer.getId());
	}

	@Cacheable(key = "#id", cacheNames = {"rediscontrolledCache"},condition = "#isCacheable",unless="#result == null ")
	@RequestMapping(path = "customer/{id}/{isCacheable}", method = RequestMethod.GET)
	public Customer getCustomer(@PathVariable String id,@PathVariable boolean isCacheable) {
		return customerRepository.findById(id);
	}

	@CachePut(key = "#customer.id", cacheNames = {"rediscontrolledCache"})
	@RequestMapping(path = "update", method = RequestMethod.POST)
	public Customer update(@RequestBody Customer customer) {
		customerRepository.update(customer);
		return customer;
	}

	@CacheEvict(key = "#id",cacheNames = {"rediscontrolledCache"})
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
