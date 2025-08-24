package com.tencent.wxcloudrun.controller;

import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.model.Order;
import com.tencent.wxcloudrun.service.impl.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

// OrderController.java
@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/{orderId}/cancel")
    public ApiResponse cancelOrder(
            @PathVariable Long orderId,
            @RequestParam(required = false) String cancelReason) {

        boolean success = orderService.cancelOrder(orderId, cancelReason);
        return success ?
                ApiResponse.ok() :
                ApiResponse.error("订单取消失败!");
    }

    @PostMapping("/{orderId}/confirm")
    public ApiResponse confirmOrder(@PathVariable Long orderId) {
        boolean success = orderService.confirmOrder(orderId);
        return success ? ApiResponse.ok() : ApiResponse.error("确认订单失败!");
    }

    @GetMapping("/{orderId}")
    public ApiResponse getOrderDetail(@PathVariable Long orderId) {
        Order order = orderService.getOrderDetail(orderId);
        return order == null ? ApiResponse.ok() : ApiResponse.ok(order);
    }

    @GetMapping("/user/{userId}")
    public ApiResponse getUserOrders(@PathVariable Long userId) {
        return ApiResponse.ok(orderService.getUserOrders(userId));
    }

    @GetMapping("/getAllOrders")
    public ApiResponse getAllOrders() {
        return ApiResponse.ok(orderService.getAllOrders());
    }
}