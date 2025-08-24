package com.tencent.wxcloudrun.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tencent.wxcloudrun.dao.TransactionRecordMapper;
import com.tencent.wxcloudrun.dao.UserAccountMapper;
import com.tencent.wxcloudrun.dao.UserMapper;
import com.tencent.wxcloudrun.enums.TransactionStatus;
import com.tencent.wxcloudrun.enums.TransactionType;
import com.tencent.wxcloudrun.model.TransactionRecord;
import com.tencent.wxcloudrun.model.UserAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

// UserAccountService.java
@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class UserAccountService {
    private final UserAccountMapper userAccountMapper;
    private final TransactionRecordMapper transactionRecordMapper;
    private final UserMapper userMapper;

    /**
     * 初始化用户账户
     */
    public void initUserAccount(Long userId) {
        UserAccount account = new UserAccount();
        account.setUserId(userId);
        account.setBalance(BigDecimal.ZERO);
        account.setTotalRecharge(BigDecimal.ZERO);
        account.setTotalConsumption(BigDecimal.ZERO);
        account.setCreateTime(LocalDateTime.now());
        account.setUpdateTime(LocalDateTime.now());

        userAccountMapper.insertUserAccount(account);
    }

    /**
     * 用户充值
     */
    public boolean recharge(Long userId, BigDecimal amount, String description) {
        try {
            // 1. 更新账户余额
            int updated = userAccountMapper.updateBalance(userId, amount, "recharge");
            if (updated == 0) {
                throw new RuntimeException("充值失败");
            }

            // 2. 获取更新后的账户信息
            UserAccount account = userAccountMapper.selectUserAccountByUserId(userId);

            // 3. 记录交易
            TransactionRecord record = new TransactionRecord();
            record.setUserId(userId);
            record.setAmount(amount);
            record.setType(TransactionType.RECHARGE.getCode());
            record.setBalanceBefore(account.getBalance().subtract(amount));
            record.setBalanceAfter(account.getBalance());
            record.setDescription(description);
            record.setStatus(TransactionStatus.COMPLETED.getCode());
            record.setCreateTime(LocalDateTime.now());

            transactionRecordMapper.insertTransactionRecord(record);

            return true;
        } catch (Exception e) {
            throw new RuntimeException("充值失败: " + e.getMessage(), e);
        }
    }

    /**
     * 消费扣款（用于订单支付）
     */
    public boolean consume(Long userId, BigDecimal amount, Long orderId, String description) {
        try {
            // 1. 检查余额是否充足
            UserAccount account = userAccountMapper.selectUserAccountByUserId(userId);
            if (account.getBalance().compareTo(amount) < 0) {
                throw new RuntimeException("余额不足");
            }

            // 2. 扣款（金额为负数）
            int updated = userAccountMapper.updateBalance(userId, amount.negate(), "consumption");
            if (updated == 0) {
                throw new RuntimeException("扣款失败");
            }

            // 3. 获取更新后的账户信息
            UserAccount updatedAccount = userAccountMapper.selectUserAccountByUserId(userId);

            // 4. 记录交易
            TransactionRecord record = new TransactionRecord();
            record.setUserId(userId);
            record.setOrderId(orderId);
            record.setAmount(amount.negate()); // 消费金额为负
            record.setType(TransactionType.CONSUMPTION.getCode());
            record.setBalanceBefore(account.getBalance());
            record.setBalanceAfter(updatedAccount.getBalance());
            record.setDescription(description);
            record.setStatus(TransactionStatus.COMPLETED.getCode());
            record.setCreateTime(LocalDateTime.now());

            transactionRecordMapper.insertTransactionRecord(record);

            return true;
        } catch (Exception e) {
            throw new RuntimeException("消费失败: " + e.getMessage(), e);
        }
    }

    /**
     * 退款
     */
    public boolean refund(Long userId, BigDecimal amount, Long orderId, String description) {
        try {
            // 1. 退款（金额为正数）
            int updated = userAccountMapper.updateBalance(userId, amount, "recharge");
            if (updated == 0) {
                throw new RuntimeException("退款失败");
            }

            // 2. 获取更新后的账户信息
            UserAccount account = userAccountMapper.selectUserAccountByUserId(userId);

            // 3. 记录交易
            TransactionRecord record = new TransactionRecord();
            record.setUserId(userId);
            record.setOrderId(orderId);
            record.setAmount(amount);
            record.setType(TransactionType.REFUND.getCode());
            record.setBalanceBefore(account.getBalance().subtract(amount));
            record.setBalanceAfter(account.getBalance());
            record.setDescription(description);
            record.setStatus(TransactionStatus.COMPLETED.getCode());
            record.setCreateTime(LocalDateTime.now());

            transactionRecordMapper.insertTransactionRecord(record);

            return true;
        } catch (Exception e) {
            throw new RuntimeException("退款失败: " + e.getMessage(), e);
        }
    }

    /**
     * 获取用户账户信息
     */
    public UserAccount getUserAccount(Long userId) {
        UserAccount account = userAccountMapper.selectUserAccountByUserId(userId);
        if (account == null) {
            // 如果账户不存在，自动创建
            initUserAccount(userId);
            account = userAccountMapper.selectUserAccountByUserId(userId);
        }
        return account;
    }

    /**
     * 获取用户交易记录
     */
    public Page<TransactionRecord> getUserTransactions(Long userId, Page<TransactionRecord> page) {
        return transactionRecordMapper.selectTransactionsByUserId(
                userId, page);
    }
}