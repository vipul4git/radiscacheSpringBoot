/**
 * 
 */
package com.mcd.cache.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author 
 *
 */
@Configuration
public class RedisConfiguration {
	
	@Bean
	JedisConnectionFactory jedisConnectionFactory() {
		//RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration("localhost", 6379);
		//redisStandaloneConfiguration.setPassword(RedisPassword.of("yourpassword"));
		/*RedisSentinelConfiguration sentinelConfig = new RedisSentinelConfiguration()
				  .master("mymaster")
				  .sentinel("127.0.0.1", 26379)
				  .sentinel("127.0.0.1", 26380);
				  return new JedisConnectionFactory(sentinelConfig);*/
		return new JedisConnectionFactory();
	}

	@Bean
	public RedisTemplate<String, Object> redisTemplate() {
		RedisTemplate<String, Object> template = new RedisTemplate<>();
		template.setConnectionFactory(jedisConnectionFactory());
		return template;
	}
	
}
