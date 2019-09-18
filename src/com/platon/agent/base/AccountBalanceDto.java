package com.platon.agent.base;

import java.math.BigInteger;

import org.web3j.utils.Numeric;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;


@Data
public class AccountBalanceDto {
    @JsonIgnore
    private String account;
    @JsonIgnore
    private BigInteger freeBalance;
    @JsonIgnore
    private BigInteger lockBalance;

    /**
     * 地址
     * account字段转addr
     * @return
     */
    @JSONField(name = "addr")
    public String getAddr() {
        return account;
    }

    /**
     * 自由账户余额  单位von
     * freeBalance字段转free，且BigInteger转String
     * @return
     */
    @JSONField(name = "free")
    public String getFree() {
        if (freeBalance == null) {
            return "0";
        }
        return freeBalance.toString(10);
    }

    /**
     * 自由账户余额  单位von
     * lockBalance字段转lock，且BigInteger转String
     * @return
     */
    @JSONField(name = "lock")
    public String getLock() {
        if (lockBalance == null) {
            return "0";
        }
        return lockBalance.toString(10);
    }
    
    public void setFreeBalance(String balance) {
    	if(balance != null && balance.length()>0) {
    		freeBalance = Numeric.decodeQuantity(balance);
    	}else {
    		freeBalance = BigInteger.ZERO;
		}
    }
    
    public void setLockBalance(String balance) {
    	if(balance != null && balance.length()>0) {
    		lockBalance = Numeric.decodeQuantity(balance);
    	}else {
    		lockBalance = BigInteger.ZERO;
		}
    }
}
