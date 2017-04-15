package com.samsung.xiaoi.sys.dao;

import com.samsung.xiaoi.sys.entity.TestUser;
import com.ujigu.secure.db.dao.IBaseDao;
import org.springframework.stereotype.Repository;

@Repository
public interface TestUserDao extends IBaseDao<Integer, TestUser> {
    Integer insertReturnPK(TestUser testUser);
}