package com.tencent.wxcloudrun.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tencent.wxcloudrun.anno.MiniLog;
import com.tencent.wxcloudrun.anno.RequestAttr;
import com.tencent.wxcloudrun.anno.roles.RequiresRoles;
import com.tencent.wxcloudrun.anno.roles.RoleEnum;
import com.tencent.wxcloudrun.config.ApiResponse;
import com.tencent.wxcloudrun.dto.RechargeDto;
import com.tencent.wxcloudrun.model.PointTransactionRecord;
import com.tencent.wxcloudrun.service.impl.UserCourtPointService;
import com.tencent.wxcloudrun.utils.ResultUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/userCourtPoint")
@RequiredArgsConstructor
public class UserPointController {
    private final UserCourtPointService userCourtPointService;

    @PostMapping("/recharge")
    @RequiresRoles({RoleEnum.ADMIN_COURT})
    @MiniLog("管理员充值")
    public ApiResponse recharge(@RequestBody RechargeDto rechargeDto) {
        boolean success = userCourtPointService.recharge(rechargeDto.getUserId(), rechargeDto.getAmount(),
                rechargeDto.getDescription() != null ? rechargeDto.getDescription() : "用户充值");
        return success ? ApiResponse.ok() : ApiResponse.error("充值失败");
    }

    @GetMapping("/transactions")
    @MiniLog("获取个人积分交易明细")
    public ApiResponse getTransactions(
            @RequestParam(defaultValue = "1") int current,
            @RequestParam(defaultValue = "10") int size, HttpServletRequest request) {
        Page<PointTransactionRecord> page = new Page<>(current, size);
        Page<PointTransactionRecord> transactions = userCourtPointService.getUserTransactions(RequestAttr.USER_ID.get(request), page);
        return ApiResponse.ok(ResultUtil.getResultFromPage(transactions));
    }
}
