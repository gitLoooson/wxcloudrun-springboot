package com.tencent.wxcloudrun.dto;

import com.tencent.wxcloudrun.model.Booking;
import lombok.AllArgsConstructor;
import lombok.Data;

// BookingResult.java
@Data
@AllArgsConstructor
public class BookingResult {
    private boolean success;
    private String message;
    private Booking booking;

    public static BookingResult success(String message, Booking booking) {
        return new BookingResult(true, message, booking);
    }

    public static BookingResult error(String message) {
        return new BookingResult(false, message, null);
    }
}