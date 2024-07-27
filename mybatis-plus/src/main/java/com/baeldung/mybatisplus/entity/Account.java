package com.baeldung.mybatisplus.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@Accessors(chain = true)
@TableName("account")
public class Account {

    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    private AccountType type;

    private BigDecimal principal;

    private BigDecimal interestRate;

    private Integer term = 1; // in years

    private Long clientId;

    @TableLogic
    private Integer deleted;

    enum AccountType {
        DEPOSIT, LOAN
    }

}