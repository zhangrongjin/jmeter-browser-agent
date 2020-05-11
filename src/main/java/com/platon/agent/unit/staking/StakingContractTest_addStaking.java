package com.platon.agent.unit.staking;

import java.math.BigDecimal;

import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;
import org.web3j.protocol.core.methods.response.PlatonSendTransaction;
import org.web3j.utils.Convert;
import org.web3j.utils.Convert.Unit;

import com.platon.agent.base.BaseSampler;
import com.platon.agent.check.InnerContractAddrEnum;
import com.platon.sdk.contracts.ppos.dto.BaseResponse;
import com.platon.sdk.contracts.ppos.dto.enums.StakingAmountType;

public class StakingContractTest_addStaking extends BaseSampler {


	/**
	 * 增持质押 nodeId 被质押的节点Id(也叫候选人的节点Id) typ 表示使用账户自由金额还是账户的锁仓金额做质押，0: 自由金额； 1: 锁仓金额
	 * amount 质押的von
	 */
	@Override
	public SampleResult runTest(JavaSamplerContext arg) {
		SampleResult sr = new SampleResult();
		String result = null;
		sr.sampleStart();
		try {
			String nodeId = arg.getParameter("nodeId");
			BigDecimal addStakingAmount = Convert.toVon( arg.getParameter("stakingAmount"), Unit.LAT);
			PlatonSendTransaction platonSendTransaction = stakingContract.addStakingReturnTransaction(nodeId,
					StakingAmountType.FREE_AMOUNT_TYPE, addStakingAmount.toBigInteger()).send();
			BaseResponse baseResponse = stakingContract.getTransactionResponse(platonSendTransaction).send();
			result = baseResponse.toString();
			if(baseResponse.isStatusOk()) {
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
		params.addArgument("url", "http://192.168.112.171:6789");
		params.addArgument("stakingPrivateKey", "a689f0879f53710e9e0c1025af410a530d6381eebb5916773195326e123b822b");
		params.addArgument("chainId", "100");
		params.addArgument("nodeId", "0x0aa9805681d8f77c05f317efc141c97d5adb511ffb51f5a251d2d7a4a3a96d9a12adf39f06b702f0ccdff9eddc1790eb272dca31b0c47751d49b5931c58701e7");
		params.addArgument("stakingAmount", "5000000");
		JavaSamplerContext arg0 = new JavaSamplerContext(params);
		StakingContractTest_addStaking test = new StakingContractTest_addStaking();
		test.setupTest(arg0);
		SampleResult sampleResult = test.runTest(arg0);
		System.out.println("result:"+sampleResult.getResponseDataAsString());
	}

	@Override
	public InnerContractAddrEnum getType() {
		return InnerContractAddrEnum.STAKING_CONTRACT;
	}

	@Override
	public void setArguments(Arguments params) {
		params.addArgument("nodeId", "0x0aa9805681d8f77c05f317efc141c97d5adb511ffb51f5a251d2d7a4a3a96d9a12adf39f06b702f0ccdff9eddc1790eb272dca31b0c47751d49b5931c58701e7");
		params.addArgument("stakingAmount", "5000000");
	}
	 

}
