package com.tencent.wxcloudrun.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tencent.wxcloudrun.model.TransactionRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

// TransactionRecordMapper.java
@Mapper
public interface TransactionRecordMapper {
    int insertTransactionRecord(TransactionRecord record);
    List<TransactionRecord> selectTransactionsByUser(Long userId);
    List<TransactionRecord> selectTransactionsByOrder(Long orderId);
    TransactionRecord selectTransactionById(Long id);

    // 分页查询
    Page<TransactionRecord> selectTransactionsByUserId(
            @Param("userId") Long userId,
            Page<TransactionRecord> page);

    int countTransactionsByCondition(@Param("userId") Long userId,
                                     @Param("type") String type,
                                     @Param("startTime") LocalDateTime startTime,
                                     @Param("endTime") LocalDateTime endTime);
}
