package com.tencent.wxcloudrun.dao;

import com.tencent.wxcloudrun.model.UserAccount;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;

// UserAccountMapper.java
@Mapper
public interface UserAccountMapper {
    int insertUserAccount(UserAccount userAccount);
    int updateUserAccount(UserAccount userAccount);
    UserAccount selectUserAccountByUserId(Long userId);
    int updateBalance(@Param("userId") Long userId,
                      @Param("amount") BigDecimal amount,
                      @Param("type") String type); // recharge or consumption
}