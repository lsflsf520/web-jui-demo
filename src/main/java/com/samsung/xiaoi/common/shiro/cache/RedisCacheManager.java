package com.samsung.xiaoi.common.shiro.cache;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.util.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.Serializable;

/**
 * @Author ouyangan
 * @Date 2016/10/9/14:13
 * @Description 接口实现
 */
@SuppressWarnings(value={"unchecked", "rawtypes"})
public class RedisCacheManager implements CacheManager, Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

    private transient RedisTemplate<Object, Object> redisTemplate;

    public RedisCacheManager() {
    }

    public RedisCacheManager(RedisTemplate<Object, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public <K, V> Cache<K, V> getCache(String name) throws CacheException {
        if (!StringUtils.hasText(name)) {
            throw new IllegalArgumentException("Cache name cannot be null or empty.");
        }
//        String cachekey = RedisCache.shiro_cache_prefix + name;
//        LogUtils.debug("redis cache manager get cache name is %s", cachekey);
//        Cache cache = (Cache) redisTemplate.opsForValue().get(cachekey);
//        if (cache == null) {
        Cache cache = new RedisCache<>(redisTemplate);
//            redisTemplate.opsForValue().set(cachekey, cache);
//        }
        return cache;
    }

    public void setRedisTemplate(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
}
