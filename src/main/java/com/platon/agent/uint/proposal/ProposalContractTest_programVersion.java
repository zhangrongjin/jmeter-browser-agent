package com.platon.agent.uint.proposal;

import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;
import org.web3j.platon.bean.ProgramVersion;

import com.alibaba.fastjson.JSONObject;
import com.platon.agent.base.BaseSampler;
import com.platon.agent.check.InnerContractAddrEnum;

public class ProposalContractTest_programVersion extends BaseSampler {
	
	/**
	 * 查询节点代码版本
	 */
	@Override
	public SampleResult runTest(JavaSamplerContext arg) {
		SampleResult sr = new SampleResult();
		String result = null;
		sr.sampleStart();
		try {
			ProgramVersion programVersion = web3j.getProgramVersion().send().getAdminProgramVersion();
			if(programVersion != null) {
				sr.setSuccessful(true);
				result = JSONObject.toJSONString(programVersion);
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
		params.addArgument("stakingPrivateKey", "0xa11859ce23effc663a9460e332ca09bd812acc390497f8dc7542b6938e13f8d7");
		params.addArgument("chainId", "100");
		params.addArgument("verifier", "411a6c3640b6cd13799e7d4ed286c95104e3a31fbb05d7ae0004463db648f26e93f7f5848ee9795fb4bbb5f83985afd63f750dc4cf48f53b0e84d26d6834c20c");
		JavaSamplerContext arg0 = new JavaSamplerContext(params);
		ProposalContractTest_programVersion test = new ProposalContractTest_programVersion();
		test.setupTest(arg0);
		SampleResult sampleResult = test.runTest(arg0);
		System.out.println("result:"+sampleResult.getResponseDataAsString());
	}

	@Override
	public InnerContractAddrEnum getType() {
		return InnerContractAddrEnum.PROPOSAL_CONTRACT;
	}

	@Override
	public void setArguments(Arguments params) {
		params.addArgument("verifier", "0x0aa9805681d8f77c05f317efc141c97d5adb511ffb51f5a251d2d7a4a3a96d9a12adf39f06b702f0ccdff9eddc1790eb272dca31b0c47751d49b5931c58701e7");
	}
	 

}
