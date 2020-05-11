package com.platon.agent.unit.reward;

import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;

import com.platon.agent.base.BaseSampler;
import com.platon.agent.check.InnerContractAddrEnum;
import com.platon.sdk.contracts.ppos.dto.TransactionResponse;

public class RewardContractTest_reward extends BaseSampler {
	
	/**
	 * 发起委托 typ 表示使用账户自由金额还是账户的锁仓金额做委托，0: 自由金额； 1: 锁仓金额 nodeId 被质押的节点的NodeId amount
	 * 委托的金额(按照最小单位算，1LAT = 10**18 von)
	 */
	@Override
	public SampleResult runTest(JavaSamplerContext arg) {
		SampleResult sr = new SampleResult();
		sr.sampleStart();
		
		String result = null;
		try {
			TransactionResponse res = rewardContract.withdrawDelegateReward().send();
			result = res.toString();
			if(res.isStatusOk()) {
				sr.setSuccessful(true);
			} else {
				sr.setSuccessful(false);
			}
		} catch (Exception e) {
			result = e.getMessage();
			sr.setSuccessful(false);
		}
		sr.setResponseData(result, null);
		sr.setDataType(SampleResult.TEXT);
		sr.sampleEnd();
		return sr;
	}
	
	
	public static void main(String[] args) {
		Arguments params = new Arguments();
		params.addArgument("url", "http://192.168.112.172:8789");
		params.addArgument("delegatePrivateKey", "4484092b68df58d639f11d59738983e2b8b81824f3c0c759edd6773f9adadfe7");
		params.addArgument("chainId", "100");
		JavaSamplerContext arg0 = new JavaSamplerContext(params);
		RewardContractTest_reward test = new RewardContractTest_reward();
		test.setupTest(arg0);
		SampleResult sampleResult = test.runTest(arg0);
		System.out.println("result:"+sampleResult.getResponseDataAsString());
	}


	@Override
	public InnerContractAddrEnum getType() {
		return InnerContractAddrEnum.REWARD_CONTRACT;
	}


	@Override
	public void setArguments(Arguments params) {
		
	}
	 

}
