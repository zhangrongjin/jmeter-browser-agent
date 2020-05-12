package com.platon.agent.unit.staking;

import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;
import org.web3j.protocol.core.methods.response.PlatonSendTransaction;

import com.platon.agent.base.BaseSampler;
import com.platon.agent.check.InnerContractAddrEnum;
import com.platon.sdk.contracts.ppos.dto.BaseResponse;

public class StakingContractTest_unStaking extends BaseSampler {


	/**
	 * 撤销质押(一次性发起全部撤销，多次到账) nodeId 被质押的节点的NodeId
	 */
	@Override
	public SampleResult runTest(JavaSamplerContext arg) {
		SampleResult sr = new SampleResult();
		String result = null;
		sr.sampleStart();
		try {
			String nodeId = arg.getParameter("nodeId");
			PlatonSendTransaction platonSendTransaction = stakingContract.unStakingReturnTransaction(nodeId,gasProvider).send();
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
		params.addArgument("superPrivateKey", "0xa689f0879f53710e9e0c1025af410a530d6381eebb5916773195326e123b822b");
		params.addArgument("stakingPrivateKey", "0x690a32ceb7eab4131f7be318c1672d3b9b2dadeacba20b99432a7847c1e926e0");
		params.addArgument("benefitPrivateKey", "0x3581985348bffd03b286b37712165f7addf3a8d907b25efc44addf54117e9b91");
		params.addArgument("chainId", "100");
		params.addArgument("nodeId", "77fffc999d9f9403b65009f1eb27bae65774e2d8ea36f7b20a89f82642a5067557430e6edfe5320bb81c3666a19cf4a5172d6533117d7ebcd0f2c82055499050");
		JavaSamplerContext arg0 = new JavaSamplerContext(params);
		StakingContractTest_unStaking test = new StakingContractTest_unStaking();
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
