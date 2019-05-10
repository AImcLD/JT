package com.jt.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;

/**
 * 如何标识配置类
 * 需要配置bean注解  
 * @author Administrator
 *
 */
@Configuration
@PropertySource
("classpath:/properties/redis.properties")
public class RedisConfig {
	//2.redis分片机制测试
	
	@Value("${redis.nodes}")
	private String nodes; //ip:端口,ip:端口...
	
	@Bean
	public ShardedJedis getShards() {
		List<JedisShardInfo>shards = new ArrayList<JedisShardInfo>();
		
		//将nodes中的数据进行分组 {IP:端口}
		String[] node = nodes.split(",");
		for (String nodeArgs : node) {
			//{ip,端口}
			String[] args = nodeArgs.split(":");
			String nodeIP = args[0];
			int nodePort = Integer.parseInt(args[1]);
			JedisShardInfo info = new JedisShardInfo(nodeIP, nodePort);
			shards.add(info);
		}
		return new ShardedJedis(shards);
	}

	/*
	 * //1.redis单台测试
	 * 
	 * @Value("${redis.host}") private String host;
	 * 
	 * @Value("${redis.port}") private int port;
	 * 
	 * @Bean //执行方法获取实例化对象 public Jedis getJedis() {
	 * 
	 * return new Jedis(host, port); }
	 */
}







