package com.tencent.wxcloudrun.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tencent.wxcloudrun.anno.RequestAttr;
import com.tencent.wxcloudrun.anno.roles.RequiresRoles;
import com.tencent.wxcloudrun.anno.roles.RoleEnum;
import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.model.Order;
import com.tencent.wxcloudrun.service.impl.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

// OrderController.java
@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/cancel")
    public ApiResponse cancelOrder(
            @RequestBody Order order,HttpServletRequest request) {
        boolean success = orderService.cancelOrder(order.getId(), order.getCancelReason() == null ? "用户手动取消!" : order.getCancelReason(),RequestAttr.USER_ID.get(request));
        return success ?
                ApiResponse.ok() :
                ApiResponse.error("订单取消失败!");
    }

    @PostMapping("/cancelByAdmin")
    @RequiresRoles({RoleEnum.ADMIN_COURT})
    public ApiResponse cancelByAdmin(
            @RequestBody Order order,HttpServletRequest request) {
        boolean success = orderService.cancelOrder(order.getId(), order.getCancelReason() == null ? RequestAttr.USER_ID.get(request) +"管理员取消!" : order.getCancelReason(),null);
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

    @GetMapping("/user")
    public ApiResponse getUserOrders(HttpServletRequest request,@RequestParam(defaultValue = "1") Integer current
            ,@RequestParam(defaultValue = "10") Integer size) {
        Page<Order> page = new Page<>(current, size);
        return ApiResponse.ok(orderService.getUserOrders(RequestAttr.USER_ID.get(request),page));
    }

    @GetMapping("/getAllOrders")
    @RequiresRoles({RoleEnum.ADMIN_COURT})
    public ApiResponse getAllOrders(@RequestParam(defaultValue = "1") Integer current
            ,@RequestParam(defaultValue = "10") Integer size) {
        Page<Order> page = new Page<>(current, size);
        return ApiResponse.ok(orderService.getAllOrders(page));
    }
}