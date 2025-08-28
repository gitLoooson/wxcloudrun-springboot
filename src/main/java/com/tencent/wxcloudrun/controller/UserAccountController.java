package com.tencent.wxcloudrun.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tencent.wxcloudrun.anno.MiniLog;
import com.tencent.wxcloudrun.anno.RequestAttr;
import com.tencent.wxcloudrun.anno.roles.RequiresRoles;
import com.tencent.wxcloudrun.anno.roles.RoleEnum;
import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.dto.RechargeDto;
import com.tencent.wxcloudrun.model.TransactionRecord;
import com.tencent.wxcloudrun.service.impl.UserAccountService;
import com.tencent.wxcloudrun.utils.ResultUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

// UserAccountController.java
@RestController
@RequestMapping("/api/account")
@RequiredArgsConstructor
public class UserAccountController {
    private final UserAccountService userAccountService;

    @PostMapping("/recharge")
    @RequiresRoles({RoleEnum.ADMIN_COURT})
    @MiniLog("管理员充值")
    public ApiResponse recharge(@RequestBody RechargeDto rechargeDto) {
        boolean success = userAccountService.recharge(rechargeDto.getUserId(), rechargeDto.getAmount(),
                rechargeDto.getDescription() != null ? rechargeDto.getDescription() : "用户充值");
        return success ? ApiResponse.ok() : ApiResponse.error("充值失败");
    }

    @GetMapping("/transactions")
    @MiniLog("获取个人积分交易明细")
    public ApiResponse getTransactions(
            @RequestParam(defaultValue = "1") int current,
            @RequestParam(defaultValue = "10") int size, HttpServletRequest request) {
        Page<TransactionRecord> page = new Page<>(current, size);
        Page<TransactionRecord> transactions = userAccountService.getUserTransactions(RequestAttr.USER_ID.get(request), page);
        return ApiResponse.ok(ResultUtil.getResultFromPage(transactions));
    }
}