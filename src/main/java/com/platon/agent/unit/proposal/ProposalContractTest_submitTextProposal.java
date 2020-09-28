package com.platon.agent.unit.proposal;

import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;
import org.web3j.protocol.core.methods.response.PlatonSendTransaction;

import com.platon.agent.base.BaseSampler;
import com.platon.agent.check.InnerContractAddrEnum;
import com.platon.sdk.contracts.ppos.dto.BaseResponse;
import com.platon.sdk.contracts.ppos.dto.resp.Proposal;

public class ProposalContractTest_submitTextProposal extends BaseSampler {
	
	/**
	 * 提交文本提案
	 */
	@Override
	public SampleResult runTest(JavaSamplerContext arg) {
		SampleResult sr = new SampleResult();
		String result = null;
		try {
			sr.sampleStart();
			if(this.chainType.equals(this.chainTypeP)) {
				Proposal proposal = Proposal.createSubmitTextProposalParam(arg.getParameter("nodeId"), arg.getParameter("pIDID"));
				PlatonSendTransaction transaction = this.proposalContract.submitProposalReturnTransaction(proposal, this.gasProvider).send();
				BaseResponse baseResponse = this.proposalContract.getTransactionResponse(transaction).send();
				result = baseResponse.toString();
				if (baseResponse.isStatusOk()) {
					sr.setSuccessful(true);
				} else {
					sr.setSuccessful(false);
				}
			}else {
				com.alaya.contracts.ppos.dto.resp.Proposal proposal = com.alaya.contracts.ppos.dto.resp.Proposal.createSubmitTextProposalParam(arg.getParameter("nodeId"), arg.getParameter("pIDID"));
				com.alaya.protocol.core.methods.response.PlatonSendTransaction transaction = this.proposalContractA.submitProposalReturnTransaction(proposal, this.gasProviderA).send();
				com.alaya.contracts.ppos.dto.BaseResponse baseResponse = this.proposalContractA.getTransactionResponse(transaction).send();
				result = baseResponse.toString();
				if (baseResponse.isStatusOk()) {
					sr.setSuccessful(true);
				} else {
					sr.setSuccessful(false);
				}
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
		params.addArgument("stakingPrivateKey", "a689f0879f53710e9e0c1025af410a530d6381eebb5916773195326e123b822b");
		params.addArgument("nodeId", "0x0aa9805681d8f77c05f317efc141c97d5adb511ffb51f5a251d2d7a4a3a96d9a12adf39f06b702f0ccdff9eddc1790eb272dca31b0c47751d49b5931c58701e7");
		params.addArgument("chainId", "100");
		params.addArgument("pIDID", "7");
		JavaSamplerContext arg0 = new JavaSamplerContext(params);
		ProposalContractTest_submitTextProposal test = new ProposalContractTest_submitTextProposal();
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
		params.addArgument("pIDID", "7");
	}
	 

}
