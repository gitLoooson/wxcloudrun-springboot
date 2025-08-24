package com.tencent.wxcloudrun.service.impl;

import com.tencent.wxcloudrun.dao.PricingMapper;
import com.tencent.wxcloudrun.dao.TimeSlotMapper;
import com.tencent.wxcloudrun.dto.TimeSlotPrice;
import com.tencent.wxcloudrun.model.PricingDetail;
import com.tencent.wxcloudrun.model.TimeSlot;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

// PricingService.java
@Service
@RequiredArgsConstructor
public class PricingService {
    private final PricingMapper pricingMapper;
    private final TimeSlotMapper timeSlotMapper;

    public BigDecimal getActualPrice(LocalDate date, Long courtId, Long timeSlotId) {
        TimeSlot timeSlot = timeSlotMapper.selectTimeSlotById(timeSlotId);
        String periodType = timeSlot.getPeriodType();
        String dayType = getDayType(date);

        return pricingMapper.selectActualPrice(date, courtId, timeSlotId, periodType, dayType);
    }

    private String getDayType(LocalDate date) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        return (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY) ? "weekend" : "weekday";
    }

    public void setWeekdayDayPrice(Long courtId, Long timeSlotId, BigDecimal price,
                                   LocalDate startDate, LocalDate endDate) {
        setPrice(1L, courtId, timeSlotId, "day", "weekday", price, startDate, endDate, 5);
    }

    public void setWeekdayNightPrice(Long courtId, Long timeSlotId, BigDecimal price,
                                     LocalDate startDate, LocalDate endDate) {
        setPrice(1L, courtId, timeSlotId, "night", "weekday", price, startDate, endDate, 5);
    }

    public void setWeekendDayPrice(Long courtId, Long timeSlotId, BigDecimal price,
                                   LocalDate startDate, LocalDate endDate) {
        setPrice(2L, courtId, timeSlotId, "day", "weekend", price, startDate, endDate, 5);
    }

    public void setWeekendNightPrice(Long courtId, Long timeSlotId, BigDecimal price,
                                     LocalDate startDate, LocalDate endDate) {
        setPrice(2L, courtId, timeSlotId, "night", "weekend", price, startDate, endDate, 5);
    }

    public void setPrice(Long policyId, Long courtId, Long timeSlotId,
                         String periodType, String dayType, BigDecimal price,
                         LocalDate startDate, LocalDate endDate, Integer priority) {
        PricingDetail detail = new PricingDetail();
        detail.setPolicyId(policyId);
        detail.setCourtId(courtId);
        detail.setTimeSlotId(timeSlotId);
        detail.setPeriodType(periodType);
        detail.setDayType(dayType);
        detail.setPrice(price);
        detail.setStartDate(startDate);
        detail.setEndDate(endDate);
        detail.setPriority(priority);

        pricingMapper.insertPricingDetail(detail);
    }

    public List<PricingDetail> getPricesByConditions(Long courtId, Long timeSlotId,
                                                     String periodType, String dayType) {
        return pricingMapper.selectPricesByConditions(courtId, timeSlotId, periodType, dayType);
    }

    public List<TimeSlotPrice> getTimeSlotPricesByCourt(LocalDate date,Long courtId) {
        return pricingMapper.selectTimeSlotPricesByCourt(date, courtId);
    }

    // 获取格式化后的价格信息（便于前端显示）
    public List<List<TimeSlotPrice>> getFormattedTimeSlotPrices(LocalDate date) {
        List<TimeSlotPrice> allPrices = getTimeSlotPricesByCourt(date,null);

        // 按球场分组
       return new ArrayList<>(allPrices.stream()
               .collect(Collectors.groupingBy(TimeSlotPrice::getCourtId))
               .values());
    }

    public int setPrices(List<PricingDetail> details){
        return pricingMapper.updateBatchPrices(details);
    }
}