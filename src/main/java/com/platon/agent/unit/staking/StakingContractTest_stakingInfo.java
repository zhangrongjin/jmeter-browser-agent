package com.platon.agent.unit.staking;

import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;

import com.platon.agent.base.BaseSampler;
import com.platon.agent.check.InnerContractAddrEnum;
import com.platon.sdk.contracts.ppos.dto.BaseResponse;

/**
 * 查询质押信息
 * @author Rongjin Zhang
 *
 */
public class StakingContractTest_stakingInfo extends BaseSampler {

	/**
	 * 查询当前节点的质押信息 nodeId 被质押的节点的NodeId
	 */
	@Override
	public SampleResult runTest(JavaSamplerContext arg) {
		SampleResult sr = new SampleResult();
		String result = null;
		sr.sampleStart();
		try {
			String nodeId = arg.getParameter("nodeId");
			if(this.chainType.equals(this.chainTypeP)) {
				BaseResponse baseResponse = this.stakingContract.getStakingInfo(nodeId).send();
				result = baseResponse.toString();
				if (baseResponse.isStatusOk()) {
					sr.setSuccessful(true);
				} else {
					sr.setSuccessful(false);
				}
			} else {
				com.alaya.contracts.ppos.dto.BaseResponse baseResponse = this.stakingContractA.getStakingInfo(nodeId).send();
				result = baseResponse.toString();
				if (baseResponse.isStatusOk()) {
					sr.setSuccessful(true);
				} else {
					sr.setSuccessful(false);
				}
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
		params.addArgument("fromPrivateKey", "a689f0879f53710e9e0c1025af410a530d6381eebb5916773195326e123b822b");
		params.addArgument("chainId", "108");
		params.addArgument("gasPrice", "1000000000");
		params.addArgument("gasLimit", "4700000");
		params.addArgument("nodeId", "0x0aa9805681d8f77c05f317efc141c97d5adb511ffb51f5a251d2d7a4a3a96d9a12adf39f06b702f0ccdff9eddc1790eb272dca31b0c47751d49b5931c58701e7");
		JavaSamplerContext arg0 = new JavaSamplerContext(params);
		StakingContractTest_stakingInfo test = new StakingContractTest_stakingInfo();
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
		
	}
	 

}
