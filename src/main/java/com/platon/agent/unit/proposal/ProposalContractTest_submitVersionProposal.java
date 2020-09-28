 package com.platon.agent.unit.proposal;

import com.platon.agent.base.BaseSampler;
import com.platon.agent.check.InnerContractAddrEnum;
import com.platon.sdk.contracts.ppos.dto.BaseResponse;
import com.platon.sdk.contracts.ppos.dto.resp.Proposal;
import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;
import org.web3j.protocol.core.methods.response.PlatonSendTransaction;

import java.math.BigInteger;

public class ProposalContractTest_submitVersionProposal extends BaseSampler {
	
	/**
	 * 提交升级提案
	 */
	@Override
	public SampleResult runTest(JavaSamplerContext arg) {
		SampleResult sr = new SampleResult();
		String result = null;
		sr.sampleStart();
		try {
			String nodeId = arg.getParameter("nodeId");
			String pIDID = arg.getParameter("pIDID");
			BigInteger newVersion =  BigInteger.valueOf(Long.parseLong(arg.getParameter("newVersion")));
        	BigInteger endVotingRounds =  BigInteger.valueOf(Long.parseLong(arg.getParameter("endVotingRounds")));
			if(this.chainType.equals(this.chainTypeP)) {
				Proposal proposal = Proposal.createSubmitVersionProposalParam(nodeId, pIDID, newVersion, endVotingRounds);
				PlatonSendTransaction platonSendTransaction = this.proposalContract.submitProposalReturnTransaction(proposal, this.gasProvider).send();
				BaseResponse baseResponse = this.proposalContract.getTransactionResponse(platonSendTransaction).send();
				result = baseResponse.toString();
				if (baseResponse.isStatusOk()) {
					sr.setSuccessful(true);
				} else {
					sr.setSuccessful(false);
				}
			} else {
				com.alaya.contracts.ppos.dto.resp.Proposal proposal = com.alaya.contracts.ppos.dto.resp.Proposal.createSubmitVersionProposalParam(nodeId, pIDID, newVersion, endVotingRounds);
				com.alaya.protocol.core.methods.response.PlatonSendTransaction platonSendTransaction = this.proposalContractA.submitProposalReturnTransaction(proposal, this.gasProviderA).send();
				com.alaya.contracts.ppos.dto.BaseResponse baseResponse = this.proposalContractA.getTransactionResponse(platonSendTransaction).send();
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
		params.addArgument("pIDID", "6");
		params.addArgument("newVersion", "65792");
		params.addArgument("endVotingRounds", "4");
		JavaSamplerContext arg0 = new JavaSamplerContext(params);
		ProposalContractTest_submitVersionProposal test = new ProposalContractTest_submitVersionProposal();
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
		params.addArgument("pIDID", "6");
		params.addArgument("newVersion", "65792");
		params.addArgument("endVotingRounds", "4");
	}
	 

}
