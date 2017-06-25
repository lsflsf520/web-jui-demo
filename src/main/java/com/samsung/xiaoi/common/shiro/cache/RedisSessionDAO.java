package com.samsung.xiaoi.common.shiro.cache;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import com.ujigu.secure.common.utils.LogUtils;
import com.ujigu.secure.common.utils.ThreadUtil;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @Author ouyangan
 * @Date 2016/10/8/11:25
 * @Description session redis实现
 */
public class RedisSessionDAO extends AbstractSessionDAO {
    private static final String sessionIdPrefix = "shiro-session-";
    private static final String sessionIdPrefix_keys = "shiro-session-*";
    private long sessionTimeout;  //session 超时时间，单位为秒
    @Autowired
    private transient RedisTemplate<Serializable, Session> redisTemplate;
    
    public RedisSessionDAO() {
		this(86400000);
	}
    
    public RedisSessionDAO(long sessionMilliTimeout) {
		this.sessionTimeout = sessionMilliTimeout / 1000;
	}
    
    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId = sessionIdPrefix + UUID.randomUUID().toString();
        assignSessionId(session, sessionId);
        redisTemplate.opsForValue().set(sessionId, session, sessionTimeout, TimeUnit.SECONDS);
        
        ThreadUtil.put(sessionIdPrefix, session);
        
        LogUtils.info("create shiro session, sessionId is %s", sessionId.toString());
        return sessionId;
    }


    @Override
    protected Session doReadSession(Serializable sessionId) {
    	Session session = ThreadUtil.get(sessionIdPrefix);
    	if(session == null){
    		LogUtils.info("read shiro session, sessionId is %s", sessionId.toString());
    		session = redisTemplate.opsForValue().get(sessionId);
    		ThreadUtil.put(sessionIdPrefix, session);
    	}
    	
    	return session;
    }


    @Override
    public void update(Session session) throws UnknownSessionException {
    	LogUtils.info("update shiro session, sessionId is %s", session.getId().toString());
        redisTemplate.opsForValue().set(session.getId(), session, sessionTimeout, TimeUnit.SECONDS);
        ThreadUtil.put(sessionIdPrefix, session);
    }

    @Override
    public void delete(Session session) {
    	LogUtils.info("delete shiro session, sessionId is %s", session.getId().toString());
        redisTemplate.opsForValue().getOperations().delete(session.getId());
    }

    @Override
    public Collection<Session> getActiveSessions() {
        Set<Serializable> keys = redisTemplate.keys(sessionIdPrefix_keys);
        if (keys.size() == 0) {
            return Collections.emptySet();
        }
        List<Session> sessions = redisTemplate.opsForValue().multiGet(keys);
        return Collections.unmodifiableCollection(sessions);
    }
}
