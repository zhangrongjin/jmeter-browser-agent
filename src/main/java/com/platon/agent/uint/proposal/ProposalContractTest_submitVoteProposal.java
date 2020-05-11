package com.platon.agent.uint.proposal;


import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;
import org.web3j.platon.bean.ProgramVersion;

import com.platon.agent.base.BaseSampler;
import com.platon.agent.check.InnerContractAddrEnum;
import com.platon.sdk.contracts.ppos.dto.BaseResponse;
import com.platon.sdk.contracts.ppos.dto.enums.VoteOption;

public class ProposalContractTest_submitVoteProposal extends BaseSampler {
	
	
	/**
	 * 提交文本提案
	 */
	@Override
	public SampleResult runTest(JavaSamplerContext arg) {
		SampleResult sr = new SampleResult();
		String result = null;
		sr.sampleStart();
		try {
			VoteOption voteOption = null;
			switch (arg.getParameter("voteoption")) {
			case "1":
				voteOption = VoteOption.YEAS;
				break;
			case "2":
				voteOption = VoteOption.NAYS;
				break;
			case "3":
				voteOption = VoteOption.ABSTENTIONS;
				break;
			default:
				voteOption = VoteOption.YEAS;
				break;
			}
			String nodeId = arg.getParameter("nodeId");
			ProgramVersion pv = web3j.getProgramVersion().send().getAdminProgramVersion();
            BaseResponse baseResponse = proposalContract.vote(pv ,voteOption,arg.getParameter("proposalHash"),nodeId).send();
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
		params.addArgument("url", "http://192.168.112.172:8789");
		params.addArgument("votePrivateKey", "a689f0879f53710e9e0c1025af410a530d6381eebb5916773195326e123b822b");
		params.addArgument("nodeId", "0x0aa9805681d8f77c05f317efc141c97d5adb511ffb51f5a251d2d7a4a3a96d9a12adf39f06b702f0ccdff9eddc1790eb272dca31b0c47751d49b5931c58701e7");
		params.addArgument("chainId", "100");
		params.addArgument("proposalHash", "0x2a38e844c8a8969b8f8f153ccdd1a4e7da85c1d3227c46c5aaf92c55f85125d0");
		JavaSamplerContext arg0 = new JavaSamplerContext(params);
		ProposalContractTest_submitVoteProposal test = new ProposalContractTest_submitVoteProposal();
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
		params.addArgument("nodeId", "0x0aa9805681d8f77c05f317efc141c97d5adb511ffb51f5a251d2d7a4a3a96d9a12adf39f06b702f0ccdff9eddc1790eb272dca31b0c47751d49b5931c58701e7");
		params.addArgument("proposalHash", "0x2a38e844c8a8969b8f8f153ccdd1a4e7da85c1d3227c46c5aaf92c55f85125d0");
		params.addArgument("voteoption", "1");
	}
	 

}
