package com.platon.agent.unit.proposal;

import java.util.List;

import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;

import com.platon.agent.base.BaseSampler;
import com.platon.agent.check.InnerContractAddrEnum;
import com.platon.sdk.contracts.ppos.dto.BaseResponse;
import com.platon.sdk.contracts.ppos.dto.CallResponse;
import com.platon.sdk.contracts.ppos.dto.resp.Proposal;

/**
 * 查询提案列表
 * @author Rongjin Zhang
 *
 */
public class ProposalContractTest_listProposal extends BaseSampler {
	
	
	@Override
	public SampleResult runTest(JavaSamplerContext arg) {
		SampleResult sr = new SampleResult();
		String result = null;
		sr.sampleStart();
		try {
			CallResponse<List<Proposal>> baseResponse = this.proposalContract.getProposalList().send();
            result = baseResponse.toString();
            List<Proposal> proposalList = baseResponse.getData();
            for (Proposal proposal : proposalList) {
                BaseResponse resp = this.proposalContract.getTallyResult(proposal.getProposalId()).send();
                result += ";" +resp.toString();
			}
			sr.setSuccessful(true);
		} catch (Exception e) {
			e.printStackTrace();
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
		params.addArgument("stakingPrivateKey", "4484092b68df58d639f11d59738983e2b8b81824f3c0c759edd6773f9adadfe7");
		params.addArgument("chainId", "100");
		JavaSamplerContext arg0 = new JavaSamplerContext(params);
		ProposalContractTest_listProposal test = new ProposalContractTest_listProposal();
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
		
	}
	 

}
