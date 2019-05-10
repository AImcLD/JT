package com.jt.aop;

import java.util.ArrayList;
import java.util.List;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.jt.util.ObjectMapperUtil;

import redis.clients.jedis.ShardedJedis;
@Aspect
@Service
public class RedisAspect {
	@Autowired
	ShardedJedis jedis;
	@Around("@annotation(com.jt.ann.Cache)")
	public Object redisAspect(ProceedingJoinPoint pj)throws Throwable{
		
		MethodSignature signature = (MethodSignature) pj.getSignature();
		//Object targetClass = pj.getTarget();
		Object[] paramObjects = pj.getArgs();
		
		//String methodname = signature.getMethod().getName();
		//Class<?>[] parameterTypes = signature.getParameterTypes();
		
		//Method method = targetClass.getClass().getMethod(methodname, parameterTypes);
		String pid = paramObjects[0].toString();
		String key = "ITEM_CAT_"+pid;
		List<Object> treeList = new ArrayList<>();
		String jsonResult = jedis.get(key);
		if(StringUtils.isEmpty(jsonResult)) {
			treeList =  (List<Object>) pj.proceed();
			//将list集合转化为json串    对象转化为json必然调用get方法
			String json = ObjectMapperUtil.toJSON(treeList);
			//赋值操作并且添加超时时间
			jedis.setex(key, 3600*24*7, json);
			System.out.println("查询数据库~!!!!!!!!");
		}else {
			treeList = ObjectMapperUtil.toObject(jsonResult, treeList.getClass());
			System.out.println("查询redis缓存!!!!!!");
		}
		return treeList;
		
	}
}







