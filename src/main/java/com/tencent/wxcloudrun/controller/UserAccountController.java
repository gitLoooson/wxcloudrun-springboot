package com.tencent.wxcloudrun.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tencent.wxcloudrun.anno.roles.RequiresRoles;
import com.tencent.wxcloudrun.anno.roles.RoleEnum;
import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.model.TransactionRecord;
import com.tencent.wxcloudrun.model.UserAccount;
import com.tencent.wxcloudrun.service.impl.UserAccountService;
import com.tencent.wxcloudrun.utils.ResultUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

// UserAccountController.java
@RestController
@RequestMapping("/api/account")
@RequiredArgsConstructor
public class UserAccountController {
    private final UserAccountService userAccountService;

    @PostMapping("/recharge")
    @RequiresRoles({RoleEnum.ADMIN_COURT})
    public ApiResponse recharge(@RequestBody Long userId, BigDecimal amount, String description) {
        boolean success = userAccountService.recharge(userId, amount,
                description != null ? description : "用户充值");
        return success ? ApiResponse.ok() : ApiResponse.error("充值失败");
    }

    @GetMapping("/balance/{userId}")
    public ApiResponse getBalance(@PathVariable Long userId) {
        UserAccount account = userAccountService.getUserAccount(userId);
        return ApiResponse.ok(account);
    }

    @GetMapping("/transactions/{userId}")
    public ApiResponse getTransactions(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "1") int current,
            @RequestParam(defaultValue = "20") int size) {
        Page<TransactionRecord> page = new Page<>(current, size);
        Page<TransactionRecord> transactions = userAccountService.getUserTransactions(userId, page);
        return ApiResponse.ok(ResultUtil.getResultFromPage(transactions));
    }
}