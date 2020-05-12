package com.platon.agent.uint.proposal;

import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;

import com.platon.agent.base.BaseSampler;
import com.platon.agent.check.InnerContractAddrEnum;
import com.platon.sdk.contracts.ppos.dto.BaseResponse;

/**
 * 查询提案结果
 * @author Rongjin Zhang
 *
 */
public class ProposalContractTest_queryResult extends BaseSampler {
	
	@Override
	public SampleResult runTest(JavaSamplerContext arg) {
		SampleResult sr = new SampleResult();
		String result = null;
		sr.sampleStart();
		try {
			String proposalID = arg.getParameter("proposalID");
			BaseResponse baseResponse = proposalContract.getProposal(proposalID).send();
			BaseResponse response = proposalContract.getTallyResult(proposalID).send();

			result = "提案信息：" + baseResponse.toString() + ",投票结果：" + response.toString();
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
		params.addArgument("stakingPrivateKey", "4484092b68df58d639f11d59738983e2b8b81824f3c0c759edd6773f9adadfe7");
		params.addArgument("chainId", "100");
		params.addArgument("proposalID", "0x5a4609ccf2ff65ac8596397cecb9795d286dfe894232bcdeb2553a49ea39200f");
		JavaSamplerContext arg0 = new JavaSamplerContext(params);
		ProposalContractTest_queryResult test = new ProposalContractTest_queryResult();
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
		params.addArgument("proposalID", "0x5a4609ccf2ff65ac8596397cecb9795d286dfe894232bcdeb2553a49ea39200f");
	}
	 

}
