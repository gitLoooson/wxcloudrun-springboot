package com.tencent.wxcloudrun.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tencent.wxcloudrun.model.PointTransactionRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface PointTransactionRecordMapper {
    int insertTransactionRecord(PointTransactionRecord record);
    List<PointTransactionRecord> selectTransactionsByUser(Long userId);
    List<PointTransactionRecord> selectTransactionsByOrder(Long orderId);
    PointTransactionRecord selectTransactionById(Long id);

    // 分页查询
    Page<PointTransactionRecord> selectTransactionsByUserId(
            @Param("userId") Long userId,
            Page<PointTransactionRecord> page);

    int countTransactionsByCondition(@Param("userId") Long userId,
                                     @Param("type") String type,
                                     @Param("startTime") LocalDateTime startTime,
                                     @Param("endTime") LocalDateTime endTime);
}
