/**
 * 
 */
package com.mcd.cache.repository;

import java.util.Map;

import com.mcd.cache.model.Customer;

/**
 * @author 
 *
 */

public interface CustomerRepository {

	public void save(Customer user);

	public Customer findById(String id);

	public void delete(String id);
	
	public void update(Customer user);

	Map<String, Object> findAll();

}
