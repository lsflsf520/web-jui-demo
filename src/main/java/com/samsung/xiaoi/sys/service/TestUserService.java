package com.samsung.xiaoi.sys.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.samsung.xiaoi.sys.dao.TestUserDao;
import com.samsung.xiaoi.sys.entity.TestUser;
import com.ujigu.secure.db.dao.IBaseDao;
import com.ujigu.secure.db.service.AbstractBaseService;

@Service
public class TestUserService extends AbstractBaseService<Integer, TestUser> {
    @Resource
    private TestUserDao testUserDao;

    @Override
    protected IBaseDao<Integer, TestUser> getBaseDao() {
        return testUserDao;
    }

    public Integer insertReturnPK(TestUser testUser) {
        return testUserDao.insertReturnPK(testUser);
    }

    public Integer doSave(TestUser testUser) {
        if (testUser.getPK() == null) {
        	testUser.setStatus((byte)0);
            return this.insertReturnPK(testUser);
        }
        this.update(testUser);
        return testUser.getPK();
    }
}