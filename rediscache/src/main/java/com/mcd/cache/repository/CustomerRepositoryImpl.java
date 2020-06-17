package com.mcd.cache.repository;

import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.mcd.cache.model.Customer;

@Repository
public class CustomerRepositoryImpl implements CustomerRepository {

	public static final String TABLE_NAME = "CUSTOMER";

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	private HashOperations<String, String, Object> hashOperations;

	public CustomerRepositoryImpl(RedisTemplate<String, Object> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	@PostConstruct
	private void initializeHashOp() {
		hashOperations = redisTemplate.opsForHash();
	}

	@Override
	public void save(Customer user) {
		hashOperations.put(TABLE_NAME, user.getId(), user);
	}

	@Override
	public Map<String, Object> findAll() {
		return hashOperations.entries(TABLE_NAME);
	}

	@Override
	public Customer findById(String id) {
		return (Customer) hashOperations.get(TABLE_NAME, id);
	}

	@Override
	public void update(Customer user) {
		save(user);
	}

	@Override
	public void delete(String id) {

		hashOperations.delete(TABLE_NAME, id);
	}

}
