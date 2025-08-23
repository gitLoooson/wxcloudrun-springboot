package com.tencent.wxcloudrun.dao;

import com.tencent.wxcloudrun.dto.TimeSlotPrice;
import com.tencent.wxcloudrun.model.PricingDetail;
import com.tencent.wxcloudrun.model.PricingPolicy;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Mapper
public interface PricingMapper {
    int insertPricingPolicy(PricingPolicy policy);
    int updatePricingPolicy(PricingPolicy policy);
    List<PricingPolicy> selectAllPricingPolicies();

    int insertPricingDetail(PricingDetail detail);
    int updatePricingDetail(PricingDetail detail);
    int deletePricingDetail(Long id);
    List<PricingDetail> selectPricingDetailsByPolicy(Long policyId);

    BigDecimal selectActualPrice(@Param("date") LocalDate date,
                                 @Param("courtId") Long courtId,
                                 @Param("timeSlotId") Long timeSlotId,
                                 @Param("periodType") String periodType,
                                 @Param("dayType") String dayType);

    List<PricingDetail> selectPricesByConditions(@Param("courtId") Long courtId,
                                                 @Param("timeSlotId") Long timeSlotId,
                                                 @Param("periodType") String periodType,
                                                 @Param("dayType") String dayType);

    // 获取指定日期所有时间段的价格
    List<TimeSlotPrice> selectAllTimeSlotPrices(@Param("date") LocalDate date,
                                                @Param("courtId") Long courtId);

    // 获取指定日期指定球场所有时间段的价格
    List<TimeSlotPrice> selectTimeSlotPricesByCourt(@Param("date") LocalDate date,
                                                    @Param("courtId") Long courtId);
}