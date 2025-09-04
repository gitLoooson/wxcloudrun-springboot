package com.tencent.wxcloudrun.dao;

import com.tencent.wxcloudrun.model.UserCourtPoint;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;

public interface UserCourtPointMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserCourtPoint record);

    int insertSelective(UserCourtPoint record);

    UserCourtPoint selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserCourtPoint record);

    int updateByPrimaryKey(UserCourtPoint record);
    
    int updateBalance(@Param("userId") Long userId,
                      @Param("amount") BigDecimal amount,
                      @Param("type") String type); // recharge or consumption

    UserCourtPoint selectAllByUserId(Long userId);
}