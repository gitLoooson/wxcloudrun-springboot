package com.tencent.wxcloudrun.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tencent.wxcloudrun.dao.PointTransactionRecordMapper;
import com.tencent.wxcloudrun.dao.UserCourtPointMapper;
import com.tencent.wxcloudrun.enums.TransactionStatus;
import com.tencent.wxcloudrun.enums.TransactionType;
import com.tencent.wxcloudrun.model.PointTransactionRecord;
import com.tencent.wxcloudrun.model.UserCourtPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class UserCourtPointService{

    @Autowired
    private UserCourtPointMapper userCourtPointMapper;

    @Autowired
    private PointTransactionRecordMapper pointTransactionRecordMapper;

    
    public int deleteByPrimaryKey(Long id) {
        return userCourtPointMapper.deleteByPrimaryKey(id);
    }

    
    public int insert(UserCourtPoint record) {
        return userCourtPointMapper.insert(record);
    }

    
    public int insertSelective(UserCourtPoint record) {
        return userCourtPointMapper.insertSelective(record);
    }

    
    public UserCourtPoint selectByPrimaryKey(Long id) {
        return userCourtPointMapper.selectByPrimaryKey(id);
    }

    
    public int updateByPrimaryKeySelective(UserCourtPoint record) {
        return userCourtPointMapper.updateByPrimaryKeySelective(record);
    }

    
    public int updateByPrimaryKey(UserCourtPoint record) {
        return userCourtPointMapper.updateByPrimaryKey(record);
    }

    public boolean recharge(Long userId, BigDecimal amount, String description) {
        try {
            // 1. 更新账户余额
            int updated = userCourtPointMapper.updateBalance(userId, amount, "recharge");
            if (updated == 0) {
                throw new RuntimeException("充值失败");
            }

            // 2. 获取更新后的账户信息
            UserCourtPoint account = userCourtPointMapper.selectAllByUserId(userId);

            // 3. 记录交易
            PointTransactionRecord record = new PointTransactionRecord();
            record.setUserId(userId);
            record.setAmount(amount);
            record.setType(TransactionType.RECHARGE.getCode());
            record.setBalanceBefore(account.getBalance().subtract(amount));
            record.setBalanceAfter(account.getBalance());
            record.setDescription(description);
            record.setStatus(TransactionStatus.COMPLETED.getCode());
            record.setCreateTime(LocalDateTime.now());

            pointTransactionRecordMapper.insertTransactionRecord(record);

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
            UserCourtPoint account = userCourtPointMapper.selectAllByUserId(userId);
            if (account.getBalance().compareTo(amount) < 0) {
                throw new RuntimeException("余额不足");
            }

            // 2. 扣款（金额为负数）
            int updated = userCourtPointMapper.updateBalance(userId, amount.negate(), "consumption");
            if (updated == 0) {
                throw new RuntimeException("扣款失败");
            }

            // 3. 获取更新后的账户信息
            UserCourtPoint updatedAccount = userCourtPointMapper.selectAllByUserId(userId);

            // 4. 记录交易
            PointTransactionRecord record = new PointTransactionRecord();
            record.setUserId(userId);
            record.setOrderId(orderId);
            record.setAmount(amount.negate()); // 消费金额为负
            record.setType(TransactionType.CONSUMPTION.getCode());
            record.setBalanceBefore(account.getBalance());
            record.setBalanceAfter(updatedAccount.getBalance());
            record.setDescription(description);
            record.setStatus(TransactionStatus.COMPLETED.getCode());
            record.setCreateTime(LocalDateTime.now());

            pointTransactionRecordMapper.insertTransactionRecord(record);

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
            int updated = userCourtPointMapper.updateBalance(userId, amount, "recharge");
            if (updated == 0) {
                throw new RuntimeException("退款失败");
            }

            // 2. 获取更新后的账户信息
            UserCourtPoint account = userCourtPointMapper.selectAllByUserId(userId);

            // 3. 记录交易
            PointTransactionRecord record = new PointTransactionRecord();
            record.setUserId(userId);
            record.setOrderId(orderId);
            record.setAmount(amount);
            record.setType(TransactionType.REFUND.getCode());
            record.setBalanceBefore(account.getBalance().subtract(amount));
            record.setBalanceAfter(account.getBalance());
            record.setDescription(description);
            record.setStatus(TransactionStatus.COMPLETED.getCode());
            record.setCreateTime(LocalDateTime.now());

            pointTransactionRecordMapper.insertTransactionRecord(record);

            return true;
        } catch (Exception e) {
            throw new RuntimeException("退款失败: " + e.getMessage(), e);
        }
    }

    public Page<PointTransactionRecord> getUserTransactions(Long userId, Page<PointTransactionRecord> page) {
        return pointTransactionRecordMapper.selectTransactionsByUserId(
                userId, page);
    }
}
