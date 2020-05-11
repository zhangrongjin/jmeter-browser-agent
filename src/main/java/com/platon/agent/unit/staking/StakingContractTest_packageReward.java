package com.platon.agent.unit.staking;

import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;

import com.platon.agent.base.BaseSampler;
import com.platon.agent.check.InnerContractAddrEnum;
import com.platon.sdk.contracts.ppos.dto.BaseResponse;

public class StakingContractTest_packageReward extends BaseSampler {

	/**
	 * 查询当前节点的质押信息 nodeId 被质押的节点的NodeId
	 */
	@Override
	public SampleResult runTest(JavaSamplerContext arg) {
		SampleResult sr = new SampleResult();
		String result = null;
		sr.sampleStart();
		try {
			BaseResponse baseResponse = stakingContract.getPackageReward().send();
			result = baseResponse.toString();
			if(baseResponse.isStatusOk()) {
				sr.setSuccessful(true);
			} else {
				sr.setSuccessful(false);
			}
		} catch (Exception e) {
			result = e.toString();
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
		params.addArgument("stakingPrivateKey", "0x690a32ceb7eab4131f7be318c1672d3b9b2dadeacba20b99432a7847c1e926e0");
		params.addArgument("chainId", "100");
		JavaSamplerContext arg0 = new JavaSamplerContext(params);
		StakingContractTest_packageReward test = new StakingContractTest_packageReward();
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
		
	}
	 

}
