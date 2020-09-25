package com.platon.agent.check;

import org.web3j.utils.Numeric;

import java.math.BigInteger;

public class RestrictingBalance {
    private String account;
    private BigInteger freeBalance;
    private BigInteger lockBalance;
    private BigInteger pledgeBalance;

    public void setFreeBalance(String freeBalance) {
    	if(freeBalance == null) {
    		return ;
    	}
        this.freeBalance = Numeric.decodeQuantity(freeBalance);
    }

    public void setLockBalance(String lockBalance) {
    	if(lockBalance == null) {
    		return ;
    	}
        this.lockBalance = Numeric.decodeQuantity(lockBalance);
    }

    public void setPledgeBalance(String pledgeBalance) {
    	if(pledgeBalance == null) {
    		return ;
    	}
        this.pledgeBalance = Numeric.decodeQuantity(pledgeBalance);
    }

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public BigInteger getFreeBalance() {
		return freeBalance;
	}

	public BigInteger getLockBalance() {
		return lockBalance;
	}

	public BigInteger getPledgeBalance() {
		return pledgeBalance;
	}

}
