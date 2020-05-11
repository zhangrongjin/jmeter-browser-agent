package com.platon.agent.check;


import com.platon.sdk.utlis.NetworkParameters;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * 合约枚举
 * @author Rongjin Zhang
 *
 */
public enum InnerContractAddrEnum {
    RESTRICTING_PLAN_CONTRACT(NetworkParameters.getPposContractAddressOfRestrctingPlan(108l),"锁仓合约"),
    STAKING_CONTRACT(NetworkParameters.getPposContractAddressOfStaking(108l),"质押合约"),
    DELEGATE_CONTRACT(NetworkParameters.getPposContractAddressOfStaking(108l),"质押合约"),
    SLASH_CONTRACT(NetworkParameters.getPposContractAddressOfSlash(108l),"惩罚合约"),
    PROPOSAL_CONTRACT(NetworkParameters.getPposContractAddressOfProposal(108l),"治理(提案)合约"),
    INCENTIVE_POOL_CONTRACT(NetworkParameters.getPposContractAddressOfIncentivePool(108l),"激励池合约"),
    NODE_CONTRACT(NetworkParameters.getPposContractAddressOfStaking(108l),"节点相关合约"),
    REWARD_CONTRACT(NetworkParameters.getPposContractAddressOfReward(108l),"领取奖励合约"),
    OTHER("","其他");

    private String address;
    private String desc;

    InnerContractAddrEnum(String address, String desc) {
        this.address = address;
        this.desc = desc;
    }

    public String getAddress() {
        return address;
    }

    public String getDesc() {
        return desc;
    }

	private static final Set<String> ADDRESSES = new HashSet<>();
    public static Set<String> getAddresses(){return ADDRESSES;}

    static {
        Arrays.asList(InnerContractAddrEnum.values()).forEach(innerContractAddEnum-> ADDRESSES.add(innerContractAddEnum.address));
    }
    
}
