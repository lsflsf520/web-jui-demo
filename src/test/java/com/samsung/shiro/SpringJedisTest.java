package com.samsung.shiro;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.samsung.xiaoi.sys.entity.TestUser;
import com.samsung.xiaoi.sys.service.TestUserService;
import com.ujigu.secure.cache.constant.DefaultJedisKeyNS;
import com.ujigu.secure.cache.redis.ShardJedisTool;
import com.ujigu.secure.common.utils.JsonUtil;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;

//@ContextConfiguration(locations={"classpath:context/spring-dataSource.xml", "classpath:context/spring-persist.xml", "classpath:context/spring-redis.xml", "classpath:context/spring-servlet.xml"})  
@ContextConfiguration(locations="classpath:context/spring-*.xml")
public class SpringJedisTest extends AbstractJUnit4SpringContextTests{
	
	@Autowired  
    private TestUserService testUserService;  
      
    @org.junit.Test  
    public void  add(){  
        TestUser user=new TestUser();  
        user.setNick("wen");  
        user.setAge((byte)22);  
        
        testUserService.insert(user);
    }  
      
      
    @org.junit.Test  
    public void  query(){  
    	TestUser user= testUserService.findById(341);  
        System.out.println(JsonUtil.create().toJson(user));  
    }  
    
    @org.junit.Test  
    public void setVal(){
    	ShardJedisTool.set(DefaultJedisKeyNS.testKey, 1233, "232");
    	String val = ShardJedisTool.get(DefaultJedisKeyNS.testKey, 1233);
    	System.out.println(val);
    }
    
    @Test
    public void testJedisPool(){
    	JedisPool pool = new JedisPool(new JedisPoolConfig(), "121.42.204.239", 6379,
				2000, null);
    	Jedis jedis = pool.getResource();
    	jedis.set("test", "eee");
    	String val = jedis.get("test");
    	System.out.println(val);
    	pool.returnResourceObject(jedis);
    }
    
    @Test
    public void testJedis(){
    	JedisShardInfo shardInfo = new JedisShardInfo("121.42.204.239", 6379);
    	
    	Jedis jedis = new Jedis(shardInfo);
    	jedis.connect();
    	jedis.set("test", "eee");
    	String val = jedis.get("test");
    	System.out.println(val);
    	
    }

}
